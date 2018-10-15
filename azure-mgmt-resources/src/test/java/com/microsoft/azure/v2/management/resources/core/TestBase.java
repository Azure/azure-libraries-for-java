/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.core;

import com.microsoft.azure.v2.AzureEnvironment;
import com.microsoft.azure.v2.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.v2.credentials.AzureCliCredentials;
import com.microsoft.azure.v2.credentials.AzureTokenCredentials;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.ProviderRegistrationPolicyFactory;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.ResourceManagerThrottlingPolicyFactory;
import com.microsoft.azure.v2.management.resources.fluentcore.utils.SdkContext;
import com.microsoft.rest.v2.http.HttpPipeline;
import com.microsoft.rest.v2.http.HttpPipelineBuilder;
import com.microsoft.rest.v2.http.HttpPipelineOptions;
import com.microsoft.rest.v2.http.NettyClient;
import com.microsoft.azure.v2.policy.AsyncCredentialsPolicyFactory;
import com.microsoft.rest.v2.policy.HttpLogDetailLevel;
import com.microsoft.rest.v2.policy.HttpLoggingPolicyFactory;
import com.microsoft.rest.v2.policy.RetryPolicyFactory;
import com.microsoft.rest.v2.policy.TimeoutPolicyFactory;
import org.junit.After;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public abstract class TestBase {
    private PrintStream out;
    private String baseUri;

    public static String generateRandomResourceName(String prefix, int maxLen) {
        return SdkContext.randomResourceName(prefix, maxLen);
    }

    protected enum RunCondition {
        MOCK_ONLY,
        LIVE_ONLY,
        BOTH
    }

    public enum TestMode {
        PLAYBACK,
        RECORD
    }

    protected final static String ZERO_SUBSCRIPTION = "00000000-0000-0000-0000-000000000000";
    protected final static String ZERO_TENANT = "00000000-0000-0000-0000-000000000000";
    private static final String PLAYBACK_URI_BASE = "http://localhost:";
    protected static String playbackUri = null;

    private final RunCondition runCondition;

    protected TestBase() {
        this(RunCondition.BOTH);
    }

    protected TestBase(RunCondition runCondition) {
        this.runCondition = runCondition;
    }

    private String shouldCancelTest(boolean isPlaybackMode) {
        // Determine whether to run the test based on the condition the test has been configured with
        switch (this.runCondition) {
        case MOCK_ONLY:
            return (!isPlaybackMode) ? "Test configured to run only as mocked, not live." : null;
        case LIVE_ONLY:
            return (isPlaybackMode) ? "Test configured to run only as live, not mocked." : null;
        default:
            return null;
        }
    }

    private static TestMode testMode = null;

    private static void initTestMode() throws IOException {
        String azureTestMode = System.getenv("AZURE_TEST_MODE");
        if (azureTestMode != null) {
            if (azureTestMode.equalsIgnoreCase("Record")) {
                testMode = TestMode.RECORD;
            } else if (azureTestMode.equalsIgnoreCase("Playback")) {
                testMode = TestMode.PLAYBACK;
            } else {
                throw new IOException("Unknown AZURE_TEST_MODE: " + azureTestMode);
            }
        } else {
            //System.out.print("Environment variable 'AZURE_TEST_MODE' has not been set yet. Using 'Playback' mode.");
            testMode = TestMode.PLAYBACK;
        }
    }

    private static void initPlaybackUri() throws IOException {
        if (isPlaybackMode()) {
            Properties mavenProps = new Properties();
            InputStream in = TestBase.class.getResourceAsStream("/maven.properties");
            if (in == null) {
                throw new IOException("The file \"maven.properties\" has not been generated yet. Please execute \"mvn compile\" to generate the file.");
            }
            mavenProps.load(in);
            String port = mavenProps.getProperty("playbackServerPort");
            playbackUri = PLAYBACK_URI_BASE + port;
        } else {
            playbackUri = PLAYBACK_URI_BASE + "1234";
        }
    }

    public static boolean isPlaybackMode() {
        if (testMode == null) try {
            initTestMode();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't init test mode.");
        }
        return testMode == TestMode.PLAYBACK;
    }

    public static boolean isRecordMode() {
        return  !isPlaybackMode();
    }

    @Rule
    public TestName testName = new TestName();

    protected InterceptorManager interceptorManager = null;

    private static void printThreadInfo(String what) {
        long id = Thread.currentThread().getId();
        String name = Thread.currentThread().getName();
        System.out.println(String.format("\n***\n*** [%s:%s] - %s\n***\n", name, id, what));
    }

    @BeforeClass
    public static void beforeClass() throws IOException {
        printThreadInfo("beforeClass");
        initTestMode();
        initPlaybackUri();
    }

    @Before
    public void beforeTest() throws IOException {
        printThreadInfo(String.format("%s: %s", "beforeTest", testName.getMethodName()));
        final String skipMessage = shouldCancelTest(isPlaybackMode());
        Assume.assumeTrue(skipMessage, skipMessage == null);

        interceptorManager = InterceptorManager.create(testName.getMethodName(), testMode);

        AzureTokenCredentials credentials;
        HttpPipeline pipeline;
        String defaultSubscription;

        if (isPlaybackMode()) {
            credentials = new AzureTestCredentials(playbackUri, ZERO_TENANT, true);
            pipeline = buildRestClient(new HttpPipelineBuilder()
                            .withRequestPolicy(new RetryPolicyFactory())
                            .withRequestPolicy(new AsyncCredentialsPolicyFactory(credentials))
                            .withRequestPolicy(new ResourceManagerThrottlingPolicyFactory())
                            .withRequestPolicy(new HttpLoggingPolicyFactory(HttpLogDetailLevel.BODY_AND_HEADERS, true))
                            .withHttpClient(interceptorManager.initPlaybackClient())
                            .withDecodingPolicy()
                    ,true);

            defaultSubscription = ZERO_SUBSCRIPTION;
            System.out.println(playbackUri);
            out = System.out;
            System.setOut(new PrintStream(new OutputStream() {
                public void write(int b) {
                    //DO NOTHING
                }
            }));
        }
        else { // Record mode
            final File credFile = new File(System.getenv("AZURE_AUTH_LOCATION"));
            credentials = ApplicationTokenCredentials.fromFile(credFile);
            pipeline = buildRestClient(new HttpPipelineBuilder(new HttpPipelineOptions().withHttpClient(NettyClient.createDefault()))
                    .withRequestPolicy(new RetryPolicyFactory())
                    .withRequestPolicy(new ProviderRegistrationPolicyFactory(credentials))
                    .withRequestPolicy(new AsyncCredentialsPolicyFactory(credentials))
                    .withRequestPolicy(new TimeoutPolicyFactory(3, TimeUnit.MINUTES))
                    .withRequestPolicy(interceptorManager.initRecordPolicy())
                    .withRequestPolicy(new ResourceManagerThrottlingPolicyFactory())
                    .withRequestPolicy(new HttpLoggingPolicyFactory(HttpLogDetailLevel.BODY_AND_HEADERS, true))
                    .withDecodingPolicy()
                    ,false);

            defaultSubscription = credentials.defaultSubscriptionId();
            interceptorManager.addTextReplacementRule(defaultSubscription, ZERO_SUBSCRIPTION);
            interceptorManager.addTextReplacementRule(credentials.domain(), ZERO_TENANT);
            interceptorManager.addTextReplacementRule(baseUri(), playbackUri + "/");
            interceptorManager.addTextReplacementRule("https://graph.windows.net/", playbackUri + "/");
        }
        initializeClients(pipeline, defaultSubscription, credentials.domain(), credentials.environment());
    }

    @After
    public void afterTest() throws IOException {
        if(shouldCancelTest(isPlaybackMode()) != null) {
            return;
        }
        cleanUpResources();
        interceptorManager.finalizeInterceptor();
    }

    protected void addTextReplacementRule(String from, String to ) {
        interceptorManager.addTextReplacementRule(from, to);
    }

    protected void setBaseUri(String baseUri) {
        this.baseUri = baseUri;
    }

    protected String baseUri() {
        if (this.baseUri != null) {
            return this.baseUri;
        } else {
            return AzureEnvironment.AZURE.url(AzureEnvironment.Endpoint.RESOURCE_MANAGER);
        }
    }

    protected HttpPipeline buildRestClient(HttpPipelineBuilder builder, boolean isMocked) {
        return builder.build();
    }

    protected abstract void initializeClients(HttpPipeline pipeline, String defaultSubscription, String domain, AzureEnvironment azureEnvironment) throws IOException;
    protected abstract void cleanUpResources();
}
