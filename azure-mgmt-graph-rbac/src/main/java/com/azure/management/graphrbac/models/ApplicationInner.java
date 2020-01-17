// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License. See License.txt in the project root for
// license information.
// 
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.graphrbac.models;

import com.azure.core.annotation.Fluent;
import com.azure.management.graphrbac.AppRole;
import com.azure.management.graphrbac.GroupMembershipClaimTypes;
import com.azure.management.graphrbac.InformationalUrl;
import com.azure.management.graphrbac.OAuth2Permission;
import com.azure.management.graphrbac.OptionalClaims;
import com.azure.management.graphrbac.PreAuthorizedApplication;
import com.azure.management.graphrbac.RequiredResourceAccess;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.List;

/**
 * The Application model.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "objectType")
@JsonTypeName("Application")
@Fluent
public final class ApplicationInner extends DirectoryObjectInner {
    /*
     * The application ID.
     */
    @JsonProperty(value = "appId")
    private String appId;

    /*
     * A property on the application to indicate if the application accepts
     * other IDPs or not or partially accepts.
     */
    @JsonProperty(value = "allowGuestsSignIn")
    private Boolean allowGuestsSignIn;

    /*
     * Indicates that the application supports pass through users who have no
     * presence in the resource tenant.
     */
    @JsonProperty(value = "allowPassthroughUsers")
    private Boolean allowPassthroughUsers;

    /*
     * The url for the application logo image stored in a CDN.
     */
    @JsonProperty(value = "appLogoUrl")
    private String appLogoUrl;

    /*
     * The collection of application roles that an application may declare.
     * These roles can be assigned to users, groups or service principals.
     */
    @JsonProperty(value = "appRoles")
    private List<AppRole> appRoles;

    /*
     * The application permissions.
     */
    @JsonProperty(value = "appPermissions")
    private List<String> appPermissions;

    /*
     * Whether the application is available to other tenants.
     */
    @JsonProperty(value = "availableToOtherTenants")
    private Boolean availableToOtherTenants;

    /*
     * The display name of the application.
     */
    @JsonProperty(value = "displayName")
    private String displayName;

    /*
     * A URL provided by the author of the application to report errors when
     * using the application.
     */
    @JsonProperty(value = "errorUrl")
    private String errorUrl;

    /*
     * Configures the groups claim issued in a user or OAuth 2.0 access token
     * that the app expects.
     */
    @JsonProperty(value = "groupMembershipClaims")
    private GroupMembershipClaimTypes groupMembershipClaims;

    /*
     * The home page of the application.
     */
    @JsonProperty(value = "homepage")
    private String homepage;

    /*
     * A collection of URIs for the application.
     */
    @JsonProperty(value = "identifierUris")
    private List<String> identifierUris;

    /*
     * Represents a group of URIs that provide terms of service, marketing,
     * support and privacy policy information about an application. The default
     * value for each string is null.
     */
    @JsonProperty(value = "informationalUrls")
    private InformationalUrl informationalUrls;

    /*
     * Specifies whether this application supports device authentication
     * without a user. The default is false.
     */
    @JsonProperty(value = "isDeviceOnlyAuthSupported")
    private Boolean isDeviceOnlyAuthSupported;

    /*
     * A collection of KeyCredential objects.
     */
    @JsonProperty(value = "keyCredentials")
    private List<KeyCredentialInner> keyCredentials;

    /*
     * Client applications that are tied to this resource application. Consent
     * to any of the known client applications will result in implicit consent
     * to the resource application through a combined consent dialog (showing
     * the OAuth permission scopes required by the client and the resource).
     */
    @JsonProperty(value = "knownClientApplications")
    private List<String> knownClientApplications;

    /*
     * the url of the logout page
     */
    @JsonProperty(value = "logoutUrl")
    private String logoutUrl;

    /*
     * Whether to allow implicit grant flow for OAuth2
     */
    @JsonProperty(value = "oauth2AllowImplicitFlow")
    private Boolean oauth2AllowImplicitFlow;

    /*
     * Specifies whether during a token Request Azure AD will allow path
     * matching of the redirect URI against the applications collection of
     * replyURLs. The default is false.
     */
    @JsonProperty(value = "oauth2AllowUrlPathMatching")
    private Boolean oauth2AllowUrlPathMatching;

    /*
     * The collection of OAuth 2.0 permission scopes that the web API
     * (resource) application exposes to client applications. These permission
     * scopes may be granted to client applications during consent.
     */
    @JsonProperty(value = "oauth2Permissions")
    private List<OAuth2Permission> oauth2Permissions;

    /*
     * Specifies whether, as part of OAuth 2.0 token requests, Azure AD will
     * allow POST requests, as opposed to GET requests. The default is false,
     * which specifies that only GET requests will be allowed.
     */
    @JsonProperty(value = "oauth2RequirePostResponse")
    private Boolean oauth2RequirePostResponse;

    /*
     * A list of tenants allowed to access application.
     */
    @JsonProperty(value = "orgRestrictions")
    private List<String> orgRestrictions;

    /*
     * Specifying the claims to be included in the token.
     */
    @JsonProperty(value = "optionalClaims")
    private OptionalClaims optionalClaims;

    /*
     * A collection of PasswordCredential objects
     */
    @JsonProperty(value = "passwordCredentials")
    private List<PasswordCredentialInner> passwordCredentials;

    /*
     * list of pre-authorized applications.
     */
    @JsonProperty(value = "preAuthorizedApplications")
    private List<PreAuthorizedApplication> preAuthorizedApplications;

    /*
     * Specifies whether this application is a public client (such as an
     * installed application running on a mobile device). Default is false.
     */
    @JsonProperty(value = "publicClient")
    private Boolean publicClient;

    /*
     * Reliable domain which can be used to identify an application.
     */
    @JsonProperty(value = "publisherDomain")
    private String publisherDomain;

    /*
     * A collection of reply URLs for the application.
     */
    @JsonProperty(value = "replyUrls")
    private List<String> replyUrls;

    /*
     * Specifies resources that this application requires access to and the set
     * of OAuth permission scopes and application roles that it needs under
     * each of those resources. This pre-configuration of required resource
     * access drives the consent experience.
     */
    @JsonProperty(value = "requiredResourceAccess")
    private List<RequiredResourceAccess> requiredResourceAccess;

    /*
     * The URL to the SAML metadata for the application.
     */
    @JsonProperty(value = "samlMetadataUrl")
    private String samlMetadataUrl;

    /*
     * Audience for signing in to the application (AzureADMyOrganization,
     * AzureADAllOrganizations, AzureADAndMicrosoftAccounts).
     */
    @JsonProperty(value = "signInAudience")
    private String signInAudience;

    /*
     * The primary Web page.
     */
    @JsonProperty(value = "wwwHomepage")
    private String wwwHomepage;

    /**
     * Get the appId property: The application ID.
     * 
     * @return the appId value.
     */
    public String getAppId() {
        return this.appId;
    }

    /**
     * Set the appId property: The application ID.
     * 
     * @param appId the appId value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setAppId(String appId) {
        this.appId = appId;
        return this;
    }

    /**
     * Get the allowGuestsSignIn property: A property on the application to
     * indicate if the application accepts other IDPs or not or partially
     * accepts.
     * 
     * @return the allowGuestsSignIn value.
     */
    public Boolean isAllowGuestsSignIn() {
        return this.allowGuestsSignIn;
    }

    /**
     * Set the allowGuestsSignIn property: A property on the application to
     * indicate if the application accepts other IDPs or not or partially
     * accepts.
     * 
     * @param allowGuestsSignIn the allowGuestsSignIn value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setAllowGuestsSignIn(Boolean allowGuestsSignIn) {
        this.allowGuestsSignIn = allowGuestsSignIn;
        return this;
    }

    /**
     * Get the allowPassthroughUsers property: Indicates that the application
     * supports pass through users who have no presence in the resource tenant.
     * 
     * @return the allowPassthroughUsers value.
     */
    public Boolean isAllowPassthroughUsers() {
        return this.allowPassthroughUsers;
    }

    /**
     * Set the allowPassthroughUsers property: Indicates that the application
     * supports pass through users who have no presence in the resource tenant.
     * 
     * @param allowPassthroughUsers the allowPassthroughUsers value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setAllowPassthroughUsers(Boolean allowPassthroughUsers) {
        this.allowPassthroughUsers = allowPassthroughUsers;
        return this;
    }

    /**
     * Get the appLogoUrl property: The url for the application logo image
     * stored in a CDN.
     * 
     * @return the appLogoUrl value.
     */
    public String getAppLogoUrl() {
        return this.appLogoUrl;
    }

    /**
     * Set the appLogoUrl property: The url for the application logo image
     * stored in a CDN.
     * 
     * @param appLogoUrl the appLogoUrl value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setAppLogoUrl(String appLogoUrl) {
        this.appLogoUrl = appLogoUrl;
        return this;
    }

    /**
     * Get the appRoles property: The collection of application roles that an
     * application may declare. These roles can be assigned to users, groups or
     * service principals.
     * 
     * @return the appRoles value.
     */
    public List<AppRole> getAppRoles() {
        return this.appRoles;
    }

    /**
     * Set the appRoles property: The collection of application roles that an
     * application may declare. These roles can be assigned to users, groups or
     * service principals.
     * 
     * @param appRoles the appRoles value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setAppRoles(List<AppRole> appRoles) {
        this.appRoles = appRoles;
        return this;
    }

    /**
     * Get the appPermissions property: The application permissions.
     * 
     * @return the appPermissions value.
     */
    public List<String> getAppPermissions() {
        return this.appPermissions;
    }

    /**
     * Set the appPermissions property: The application permissions.
     * 
     * @param appPermissions the appPermissions value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setAppPermissions(List<String> appPermissions) {
        this.appPermissions = appPermissions;
        return this;
    }

    /**
     * Get the availableToOtherTenants property: Whether the application is
     * available to other tenants.
     * 
     * @return the availableToOtherTenants value.
     */
    public Boolean isAvailableToOtherTenants() {
        return this.availableToOtherTenants;
    }

    /**
     * Set the availableToOtherTenants property: Whether the application is
     * available to other tenants.
     * 
     * @param availableToOtherTenants the availableToOtherTenants value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setAvailableToOtherTenants(Boolean availableToOtherTenants) {
        this.availableToOtherTenants = availableToOtherTenants;
        return this;
    }

    /**
     * Get the displayName property: The display name of the application.
     * 
     * @return the displayName value.
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Set the displayName property: The display name of the application.
     * 
     * @param displayName the displayName value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Get the errorUrl property: A URL provided by the author of the
     * application to report errors when using the application.
     * 
     * @return the errorUrl value.
     */
    public String getErrorUrl() {
        return this.errorUrl;
    }

    /**
     * Set the errorUrl property: A URL provided by the author of the
     * application to report errors when using the application.
     * 
     * @param errorUrl the errorUrl value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
        return this;
    }

    /**
     * Get the groupMembershipClaims property: Configures the groups claim
     * issued in a user or OAuth 2.0 access token that the app expects.
     * 
     * @return the groupMembershipClaims value.
     */
    public GroupMembershipClaimTypes getGroupMembershipClaims() {
        return this.groupMembershipClaims;
    }

    /**
     * Set the groupMembershipClaims property: Configures the groups claim
     * issued in a user or OAuth 2.0 access token that the app expects.
     * 
     * @param groupMembershipClaims the groupMembershipClaims value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setGroupMembershipClaims(GroupMembershipClaimTypes groupMembershipClaims) {
        this.groupMembershipClaims = groupMembershipClaims;
        return this;
    }

    /**
     * Get the homepage property: The home page of the application.
     * 
     * @return the homepage value.
     */
    public String getHomepage() {
        return this.homepage;
    }

    /**
     * Set the homepage property: The home page of the application.
     * 
     * @param homepage the homepage value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setHomepage(String homepage) {
        this.homepage = homepage;
        return this;
    }

    /**
     * Get the identifierUris property: A collection of URIs for the
     * application.
     * 
     * @return the identifierUris value.
     */
    public List<String> getIdentifierUris() {
        return this.identifierUris;
    }

    /**
     * Set the identifierUris property: A collection of URIs for the
     * application.
     * 
     * @param identifierUris the identifierUris value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setIdentifierUris(List<String> identifierUris) {
        this.identifierUris = identifierUris;
        return this;
    }

    /**
     * Get the informationalUrls property: Represents a group of URIs that
     * provide terms of service, marketing, support and privacy policy
     * information about an application. The default value for each string is
     * null.
     * 
     * @return the informationalUrls value.
     */
    public InformationalUrl getInformationalUrls() {
        return this.informationalUrls;
    }

    /**
     * Set the informationalUrls property: Represents a group of URIs that
     * provide terms of service, marketing, support and privacy policy
     * information about an application. The default value for each string is
     * null.
     * 
     * @param informationalUrls the informationalUrls value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setInformationalUrls(InformationalUrl informationalUrls) {
        this.informationalUrls = informationalUrls;
        return this;
    }

    /**
     * Get the isDeviceOnlyAuthSupported property: Specifies whether this
     * application supports device authentication without a user. The default
     * is false.
     * 
     * @return the isDeviceOnlyAuthSupported value.
     */
    public Boolean isDeviceOnlyAuthSupported() {
        return this.isDeviceOnlyAuthSupported;
    }

    /**
     * Set the isDeviceOnlyAuthSupported property: Specifies whether this
     * application supports device authentication without a user. The default
     * is false.
     * 
     * @param isDeviceOnlyAuthSupported the isDeviceOnlyAuthSupported value to
     * set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setIsDeviceOnlyAuthSupported(Boolean isDeviceOnlyAuthSupported) {
        this.isDeviceOnlyAuthSupported = isDeviceOnlyAuthSupported;
        return this;
    }

    /**
     * Get the keyCredentials property: A collection of KeyCredential objects.
     * 
     * @return the keyCredentials value.
     */
    public List<KeyCredentialInner> getKeyCredentials() {
        return this.keyCredentials;
    }

    /**
     * Set the keyCredentials property: A collection of KeyCredential objects.
     * 
     * @param keyCredentials the keyCredentials value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setKeyCredentials(List<KeyCredentialInner> keyCredentials) {
        this.keyCredentials = keyCredentials;
        return this;
    }

    /**
     * Get the knownClientApplications property: Client applications that are
     * tied to this resource application. Consent to any of the known client
     * applications will result in implicit consent to the resource application
     * through a combined consent dialog (showing the OAuth permission scopes
     * required by the client and the resource).
     * 
     * @return the knownClientApplications value.
     */
    public List<String> getKnownClientApplications() {
        return this.knownClientApplications;
    }

    /**
     * Set the knownClientApplications property: Client applications that are
     * tied to this resource application. Consent to any of the known client
     * applications will result in implicit consent to the resource application
     * through a combined consent dialog (showing the OAuth permission scopes
     * required by the client and the resource).
     * 
     * @param knownClientApplications the knownClientApplications value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setKnownClientApplications(List<String> knownClientApplications) {
        this.knownClientApplications = knownClientApplications;
        return this;
    }

    /**
     * Get the logoutUrl property: the url of the logout page.
     * 
     * @return the logoutUrl value.
     */
    public String getLogoutUrl() {
        return this.logoutUrl;
    }

    /**
     * Set the logoutUrl property: the url of the logout page.
     * 
     * @param logoutUrl the logoutUrl value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
        return this;
    }

    /**
     * Get the oauth2AllowImplicitFlow property: Whether to allow implicit
     * grant flow for OAuth2.
     * 
     * @return the oauth2AllowImplicitFlow value.
     */
    public Boolean isOauth2AllowImplicitFlow() {
        return this.oauth2AllowImplicitFlow;
    }

    /**
     * Set the oauth2AllowImplicitFlow property: Whether to allow implicit
     * grant flow for OAuth2.
     * 
     * @param oauth2AllowImplicitFlow the oauth2AllowImplicitFlow value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setOauth2AllowImplicitFlow(Boolean oauth2AllowImplicitFlow) {
        this.oauth2AllowImplicitFlow = oauth2AllowImplicitFlow;
        return this;
    }

    /**
     * Get the oauth2AllowUrlPathMatching property: Specifies whether during a
     * token Request Azure AD will allow path matching of the redirect URI
     * against the applications collection of replyURLs. The default is false.
     * 
     * @return the oauth2AllowUrlPathMatching value.
     */
    public Boolean isOauth2AllowUrlPathMatching() {
        return this.oauth2AllowUrlPathMatching;
    }

    /**
     * Set the oauth2AllowUrlPathMatching property: Specifies whether during a
     * token Request Azure AD will allow path matching of the redirect URI
     * against the applications collection of replyURLs. The default is false.
     * 
     * @param oauth2AllowUrlPathMatching the oauth2AllowUrlPathMatching value
     * to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setOauth2AllowUrlPathMatching(Boolean oauth2AllowUrlPathMatching) {
        this.oauth2AllowUrlPathMatching = oauth2AllowUrlPathMatching;
        return this;
    }

    /**
     * Get the oauth2Permissions property: The collection of OAuth 2.0
     * permission scopes that the web API (resource) application exposes to
     * client applications. These permission scopes may be granted to client
     * applications during consent.
     * 
     * @return the oauth2Permissions value.
     */
    public List<OAuth2Permission> getOauth2Permissions() {
        return this.oauth2Permissions;
    }

    /**
     * Set the oauth2Permissions property: The collection of OAuth 2.0
     * permission scopes that the web API (resource) application exposes to
     * client applications. These permission scopes may be granted to client
     * applications during consent.
     * 
     * @param oauth2Permissions the oauth2Permissions value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setOauth2Permissions(List<OAuth2Permission> oauth2Permissions) {
        this.oauth2Permissions = oauth2Permissions;
        return this;
    }

    /**
     * Get the oauth2RequirePostResponse property: Specifies whether, as part
     * of OAuth 2.0 token requests, Azure AD will allow POST requests, as
     * opposed to GET requests. The default is false, which specifies that only
     * GET requests will be allowed.
     * 
     * @return the oauth2RequirePostResponse value.
     */
    public Boolean isOauth2RequirePostResponse() {
        return this.oauth2RequirePostResponse;
    }

    /**
     * Set the oauth2RequirePostResponse property: Specifies whether, as part
     * of OAuth 2.0 token requests, Azure AD will allow POST requests, as
     * opposed to GET requests. The default is false, which specifies that only
     * GET requests will be allowed.
     * 
     * @param oauth2RequirePostResponse the oauth2RequirePostResponse value to
     * set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setOauth2RequirePostResponse(Boolean oauth2RequirePostResponse) {
        this.oauth2RequirePostResponse = oauth2RequirePostResponse;
        return this;
    }

    /**
     * Get the orgRestrictions property: A list of tenants allowed to access
     * application.
     * 
     * @return the orgRestrictions value.
     */
    public List<String> getOrgRestrictions() {
        return this.orgRestrictions;
    }

    /**
     * Set the orgRestrictions property: A list of tenants allowed to access
     * application.
     * 
     * @param orgRestrictions the orgRestrictions value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setOrgRestrictions(List<String> orgRestrictions) {
        this.orgRestrictions = orgRestrictions;
        return this;
    }

    /**
     * Get the optionalClaims property: Specifying the claims to be included in
     * the token.
     * 
     * @return the optionalClaims value.
     */
    public OptionalClaims getOptionalClaims() {
        return this.optionalClaims;
    }

    /**
     * Set the optionalClaims property: Specifying the claims to be included in
     * the token.
     * 
     * @param optionalClaims the optionalClaims value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setOptionalClaims(OptionalClaims optionalClaims) {
        this.optionalClaims = optionalClaims;
        return this;
    }

    /**
     * Get the passwordCredentials property: A collection of PasswordCredential
     * objects.
     * 
     * @return the passwordCredentials value.
     */
    public List<PasswordCredentialInner> getPasswordCredentials() {
        return this.passwordCredentials;
    }

    /**
     * Set the passwordCredentials property: A collection of PasswordCredential
     * objects.
     * 
     * @param passwordCredentials the passwordCredentials value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setPasswordCredentials(List<PasswordCredentialInner> passwordCredentials) {
        this.passwordCredentials = passwordCredentials;
        return this;
    }

    /**
     * Get the preAuthorizedApplications property: list of pre-authorized
     * applications.
     * 
     * @return the preAuthorizedApplications value.
     */
    public List<PreAuthorizedApplication> getPreAuthorizedApplications() {
        return this.preAuthorizedApplications;
    }

    /**
     * Set the preAuthorizedApplications property: list of pre-authorized
     * applications.
     * 
     * @param preAuthorizedApplications the preAuthorizedApplications value to
     * set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setPreAuthorizedApplications(List<PreAuthorizedApplication> preAuthorizedApplications) {
        this.preAuthorizedApplications = preAuthorizedApplications;
        return this;
    }

    /**
     * Get the publicClient property: Specifies whether this application is a
     * public client (such as an installed application running on a mobile
     * device). Default is false.
     * 
     * @return the publicClient value.
     */
    public Boolean isPublicClient() {
        return this.publicClient;
    }

    /**
     * Set the publicClient property: Specifies whether this application is a
     * public client (such as an installed application running on a mobile
     * device). Default is false.
     * 
     * @param publicClient the publicClient value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setPublicClient(Boolean publicClient) {
        this.publicClient = publicClient;
        return this;
    }

    /**
     * Get the publisherDomain property: Reliable domain which can be used to
     * identify an application.
     * 
     * @return the publisherDomain value.
     */
    public String getPublisherDomain() {
        return this.publisherDomain;
    }

    /**
     * Set the publisherDomain property: Reliable domain which can be used to
     * identify an application.
     * 
     * @param publisherDomain the publisherDomain value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setPublisherDomain(String publisherDomain) {
        this.publisherDomain = publisherDomain;
        return this;
    }

    /**
     * Get the replyUrls property: A collection of reply URLs for the
     * application.
     * 
     * @return the replyUrls value.
     */
    public List<String> getReplyUrls() {
        return this.replyUrls;
    }

    /**
     * Set the replyUrls property: A collection of reply URLs for the
     * application.
     * 
     * @param replyUrls the replyUrls value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setReplyUrls(List<String> replyUrls) {
        this.replyUrls = replyUrls;
        return this;
    }

    /**
     * Get the requiredResourceAccess property: Specifies resources that this
     * application requires access to and the set of OAuth permission scopes
     * and application roles that it needs under each of those resources. This
     * pre-configuration of required resource access drives the consent
     * experience.
     * 
     * @return the requiredResourceAccess value.
     */
    public List<RequiredResourceAccess> getRequiredResourceAccess() {
        return this.requiredResourceAccess;
    }

    /**
     * Set the requiredResourceAccess property: Specifies resources that this
     * application requires access to and the set of OAuth permission scopes
     * and application roles that it needs under each of those resources. This
     * pre-configuration of required resource access drives the consent
     * experience.
     * 
     * @param requiredResourceAccess the requiredResourceAccess value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setRequiredResourceAccess(List<RequiredResourceAccess> requiredResourceAccess) {
        this.requiredResourceAccess = requiredResourceAccess;
        return this;
    }

    /**
     * Get the samlMetadataUrl property: The URL to the SAML metadata for the
     * application.
     * 
     * @return the samlMetadataUrl value.
     */
    public String getSamlMetadataUrl() {
        return this.samlMetadataUrl;
    }

    /**
     * Set the samlMetadataUrl property: The URL to the SAML metadata for the
     * application.
     * 
     * @param samlMetadataUrl the samlMetadataUrl value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setSamlMetadataUrl(String samlMetadataUrl) {
        this.samlMetadataUrl = samlMetadataUrl;
        return this;
    }

    /**
     * Get the signInAudience property: Audience for signing in to the
     * application (AzureADMyOrganization, AzureADAllOrganizations,
     * AzureADAndMicrosoftAccounts).
     * 
     * @return the signInAudience value.
     */
    public String getSignInAudience() {
        return this.signInAudience;
    }

    /**
     * Set the signInAudience property: Audience for signing in to the
     * application (AzureADMyOrganization, AzureADAllOrganizations,
     * AzureADAndMicrosoftAccounts).
     * 
     * @param signInAudience the signInAudience value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setSignInAudience(String signInAudience) {
        this.signInAudience = signInAudience;
        return this;
    }

    /**
     * Get the wwwHomepage property: The primary Web page.
     * 
     * @return the wwwHomepage value.
     */
    public String getWwwHomepage() {
        return this.wwwHomepage;
    }

    /**
     * Set the wwwHomepage property: The primary Web page.
     * 
     * @param wwwHomepage the wwwHomepage value to set.
     * @return the ApplicationInner object itself.
     */
    public ApplicationInner setWwwHomepage(String wwwHomepage) {
        this.wwwHomepage = wwwHomepage;
        return this;
    }
}
