/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.fluentcore.dag;

import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.HasName;
import com.microsoft.azure.v2.management.resources.fluentcore.dag.IndexableTaskItem;
import com.microsoft.azure.v2.management.resources.fluentcore.dag.TaskGroup;
import com.microsoft.azure.v2.management.resources.fluentcore.model.Indexable;
import org.junit.Assert;
import org.junit.Test;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

import java.util.HashMap;

public class InvokeRootTests {
    @Test
    public void testIgnoreCachedResultOnRootWithNoProxy() {
        TestTaskItem taskItem1 = new TestTaskItem("A");
        TestTaskItem taskItem2 = new TestTaskItem("B");

        taskItem1.addDependency(taskItem2);

        final HashMap<String, Integer> seen = new HashMap<>();

        taskItem1.taskGroup().invokeAsync(taskItem1.taskGroup().newInvocationContext())
                .map(new Function<Indexable, Indexable>() {
                    @Override
                    public Indexable apply(Indexable item) {
                        SupportCountingAndHasName c = (SupportCountingAndHasName) item;
                        if (seen.containsKey(c.name())) {
                            Integer a = seen.get(c.name()) + 1;
                            seen.put(c.name(), a);
                        } else {
                            seen.put(c.name(), 1);
                        }
                        return item;
                    }
                }).blockingLast();

        Assert.assertEquals(2, seen.size());
        Assert.assertTrue(seen.containsKey("A"));
        Assert.assertTrue(seen.containsKey("B"));
        Assert.assertEquals(1, (long) seen.get("A"));
        Assert.assertEquals(1, (long) seen.get("B"));


        Assert.assertEquals(1, taskItem1.getCallCount());
        Assert.assertEquals(1, taskItem2.getCallCount());

        seen.clear();

        taskItem1.taskGroup().invokeAsync(taskItem1.taskGroup().newInvocationContext())
                .map(new Function<Indexable, Indexable>() {
                    @Override
                    public Indexable apply(Indexable item) {
                        SupportCountingAndHasName c = (SupportCountingAndHasName) item;
                        if (seen.containsKey(c.name())) {
                            Integer a = seen.get(c.name()) + 1;
                            seen.put(c.name(), a);
                        } else {
                            seen.put(c.name(), 1);
                        }
                        return item;
                    }
                }).blockingLast();

        Assert.assertEquals(2, seen.size());
        Assert.assertTrue(seen.containsKey("A"));
        Assert.assertTrue(seen.containsKey("B"));
        Assert.assertEquals(1, (long) seen.get("A"));
        Assert.assertEquals(1, (long) seen.get("B"));


        Assert.assertEquals(2, taskItem1.getCallCount());
        Assert.assertEquals(1, taskItem2.getCallCount());
    }

    @Test
    public void testIgnoreCachedResultOnRootWithProxy() {
        TestTaskItem taskItem1 = new TestTaskItem("X");
        TestTaskItem taskItem2 = new TestTaskItem("Y");
        TestTaskItem taskItem3 = new TestTaskItem("Z");

        taskItem1.addDependency(taskItem2);
        taskItem1.addPostRunDependent(taskItem3);

        final HashMap<String, Integer> seen = new HashMap<>();

        taskItem1.taskGroup().invokeAsync(taskItem1.taskGroup().newInvocationContext())
                .map(new Function<Indexable, Indexable>() {
                    @Override
                    public Indexable apply(Indexable item) {
                        SupportCountingAndHasName c = (SupportCountingAndHasName) item;
                        if (seen.containsKey(c.name())) {
                            Integer a = seen.get(c.name()) + 1;
                            seen.put(c.name(), a);
                        } else {
                            seen.put(c.name(), 1);
                        }
                        return item;
                    }
                }).blockingLast();

        Assert.assertEquals(3, seen.size()); // X, Y, Z

        Assert.assertTrue(seen.containsKey("X"));
        Assert.assertTrue(seen.containsKey("Y"));
        Assert.assertTrue(seen.containsKey("Z"));
        Assert.assertEquals(2, (long) seen.get("X"));   // Due to proxy two Xs
        Assert.assertEquals(1, (long) seen.get("Y"));
        Assert.assertEquals(1, (long) seen.get("Z"));

        Assert.assertEquals(1, taskItem1.getCallCount());
        Assert.assertEquals(1, taskItem2.getCallCount());
        Assert.assertEquals(1, taskItem3.getCallCount());

        seen.clear();

        taskItem1.taskGroup().invokeAsync(taskItem1.taskGroup().newInvocationContext())
                .map(new Function<Indexable, Indexable>() {
                    @Override
                    public Indexable apply(Indexable item) {
                        SupportCountingAndHasName c = (SupportCountingAndHasName) item;
                        if (seen.containsKey(c.name())) {
                            Integer a = seen.get(c.name()) + 1;
                            seen.put(c.name(), a);
                        } else {
                            seen.put(c.name(), 1);
                        }
                        return item;
                    }
                }).blockingLast();

        Assert.assertEquals(3, seen.size());

        Assert.assertTrue(seen.containsKey("X"));
        Assert.assertTrue(seen.containsKey("Y"));
        Assert.assertTrue(seen.containsKey("Z"));
        Assert.assertEquals(2, (long) seen.get("X")); // Due to proxy two Xs
        Assert.assertEquals(1, (long) seen.get("Y"));
        Assert.assertEquals(1, (long) seen.get("Z"));

        // Though proxy is the root still actual must be called twice
        //
        Assert.assertEquals(2, taskItem1.getCallCount());
        Assert.assertEquals(1, taskItem2.getCallCount());
        Assert.assertEquals(1, taskItem3.getCallCount());
    }

    @Test
    public void testIgnoreCachedResultOnRootWithProxyWithDescendantProxy() {
        TestTaskItem taskItem1 = new TestTaskItem("1");
        TestTaskItem taskItem2 = new TestTaskItem("2");
        TestTaskItem taskItem3 = new TestTaskItem("3");
        TestTaskItem taskItem4 = new TestTaskItem("4");
        TestTaskItem taskItem5 = new TestTaskItem("5");

        taskItem1.addDependency(taskItem2);
        taskItem1.addPostRunDependent(taskItem3);
        taskItem4.addDependency(taskItem1);
        taskItem4.addPostRunDependent(taskItem5);

        final HashMap<String, Integer> seen = new HashMap<>();

        taskItem4.taskGroup().invokeAsync(taskItem1.taskGroup().newInvocationContext())
                .map(new Function<Indexable, Indexable>() {
                    @Override
                    public Indexable apply(Indexable item) {
                        SupportCountingAndHasName c = (SupportCountingAndHasName) item;
                        if (seen.containsKey(c.name())) {
                            Integer a = seen.get(c.name()) + 1;
                            seen.put(c.name(), a);
                        } else {
                            seen.put(c.name(), 1);
                        }
                        return item;
                    }
                }).blockingLast();

        Assert.assertEquals(5, seen.size());

        Assert.assertTrue(seen.containsKey("1"));
        Assert.assertTrue(seen.containsKey("2"));
        Assert.assertTrue(seen.containsKey("3"));
        Assert.assertTrue(seen.containsKey("4"));
        Assert.assertTrue(seen.containsKey("5"));

        Assert.assertEquals(2, (long) seen.get("1")); // Due to proxy two 1s
        Assert.assertEquals(1, (long) seen.get("2"));
        Assert.assertEquals(1, (long) seen.get("3"));
        Assert.assertEquals(2, (long) seen.get("4")); // Due to proxy two 1s
        Assert.assertEquals(1, (long) seen.get("5"));

        Assert.assertEquals(1, taskItem1.getCallCount());
        Assert.assertEquals(1, taskItem2.getCallCount());
        Assert.assertEquals(1, taskItem3.getCallCount());
        Assert.assertEquals(1, taskItem4.getCallCount());
        Assert.assertEquals(1, taskItem5.getCallCount());

        seen.clear();

        taskItem4.taskGroup().invokeAsync(taskItem1.taskGroup().newInvocationContext())
                .map(new Function<Indexable, Indexable>() {
                    @Override
                    public Indexable apply(Indexable item) {
                        SupportCountingAndHasName c = (SupportCountingAndHasName) item;
                        if (seen.containsKey(c.name())) {
                            Integer a = seen.get(c.name()) + 1;
                            seen.put(c.name(), a);
                        } else {
                            seen.put(c.name(), 1);
                        }
                        return item;
                    }
                }).blockingLast();

        Assert.assertEquals(5, seen.size());

        Assert.assertTrue(seen.containsKey("1"));
        Assert.assertTrue(seen.containsKey("2"));
        Assert.assertTrue(seen.containsKey("3"));
        Assert.assertTrue(seen.containsKey("4"));
        Assert.assertTrue(seen.containsKey("5"));

        Assert.assertEquals(2, (long) seen.get("1")); // Due to proxy two 1s
        Assert.assertEquals(1, (long) seen.get("2"));
        Assert.assertEquals(1, (long) seen.get("3"));
        Assert.assertEquals(2, (long) seen.get("4")); // Due to proxy two 1s
        Assert.assertEquals(1, (long) seen.get("5"));

        Assert.assertEquals(1, taskItem1.getCallCount());
        Assert.assertEquals(1, taskItem2.getCallCount());
        Assert.assertEquals(1, taskItem3.getCallCount());
        Assert.assertEquals(2, taskItem4.getCallCount());   // Only Root must be called twice
        Assert.assertEquals(1, taskItem5.getCallCount());
    }

    class TestTaskItem extends IndexableTaskItem implements SupportCountingAndHasName {
        private final String name;
        private int callCount = 0;

        TestTaskItem(String name) {
            super(name);
            this.name = name;
        }

        @Override
        public String name() {
            return this.name;
        }

        @Override
        public int getCallCount() {
            return this.callCount;
        }

        @Override
        protected Observable<Indexable> invokeTaskAsync(TaskGroup.InvocationContext context) {
            return Observable.just(this)
                    .map(new Function<IndexableTaskItem, Indexable>() {
                        @Override
                        public Indexable apply(IndexableTaskItem r) {
                            callCount++;
                            return r;
                        }
                    });
        }
    }

    interface SupportCountingAndHasName extends HasName {
        int getCallCount();
    }
}
