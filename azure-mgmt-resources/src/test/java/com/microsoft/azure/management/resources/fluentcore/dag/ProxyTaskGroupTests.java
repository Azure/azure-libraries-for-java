/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.management.resources.fluentcore.dag;

import org.junit.Assert;
import org.junit.Test;
import rx.Observable;
import rx.functions.Action1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProxyTaskGroupTests {

    @Test
    public void testSampleTaskGroupSanity() {
        // Prepare sample group
        //
        /**
         *
         *   |------------------->B------------|
         *   |                                 |
         *   |                                 ↓
         *   F            ------->C----------->A
         *   |            |                    ^
         *   |            |                    |
         *   |------------>E                   |
         *                |                    |
         *                |                    |
         *                ------->D-------------
         */
        final List<String> groupItems = new ArrayList<>();
        TaskGroup<String, StringTaskItem> group = createSampleTaskGroup("A", "B",
                "C", "D",
                "E", "F",
                groupItems);



        // Invocation of group should invoke all the tasks
        //
        group.invokeAsync(group.newInvocationContext())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String value) {
                        Assert.assertTrue(groupItems.contains(value));
                        groupItems.remove(value);
                    }
                });

        Assert.assertEquals(0, groupItems.size());

        // Test order
        //
        LinkedList<String> expectedOrder = new LinkedList<>();

        expectedOrder.push("F");
        expectedOrder.push("E");
        expectedOrder.push("D");
        expectedOrder.push("C");
        expectedOrder.push("B");
        expectedOrder.push("A");

        group.prepareForEnumeration();
        for (TaskGroupEntry<String, StringTaskItem> entry = group.getNext(); entry != null; entry = group.getNext()) {
            String top = expectedOrder.poll();
            Assert.assertNotNull(top);
            Assert.assertEquals(top, entry.key());
            group.reportCompletion(entry);
        }

        Assert.assertEquals(0, expectedOrder.size());

    }

    @Test
    public void testTaskGroupInvocationShouldNotInvokeDependentTaskGroup() {
        // Prepare group-1
        //
        /**
         *
         *   |------------------->B------------|
         *   |                                 |
         *   |                                 ↓
         *   F            ------->C----------->A
         *   |            |                    ^
         *   |            |                    |    [group-1]
         *   |------------>E                   |
         *                |                    |
         *                |                    |
         *                ------->D-------------
         */
        final List<String> group1Items = new ArrayList<>();
        final TaskGroup<String, StringTaskItem> group1 = createSampleTaskGroup("A", "B",
                "C", "D",
                "E", "F",
                group1Items);

        // Prepare group-2
        //
        /**
         *
         *   |------------------->H------------|
         *   |                                 |
         *   |                                 ↓
         *   L            ------->I----------->G
         *   |            |                    ^    [group-2]
         *   |            |                    |
         *   |------------>K                   |
         *                |                    |
         *                |                    |
         *                ------->J-------------
         */
        final List<String> group2Items = new ArrayList<>();
        final TaskGroup<String, StringTaskItem> group2 = createSampleTaskGroup("G", "H",
                "I", "J",
                "K", "L",
                group2Items);

        // Expand group-2 by adding group-1 as it's dependency
        //
        /**
         *
         *     |------------------->H------------|
         *     |                                 |
         *     |                                 ↓
         * |---L             ------->I---------->G
         * |   |            |                    ^          [group-2]
         * |   |            |                    |
         * |   |------------>K                   |
         * |                |                    |
         * |                |                    |
         * |                ------->J-------------
         * |
         * |        |------------------->B------------|
         * |        |                                 |
         * |        |                                 ↓
         * |------->F            ------->C----------->A
         *          |            |                    ^     [group-1]
         *          |            |                    |
         *          |------------>E                   |
         *                        |                   |
         *                        |                   |
         *                        ------->D------------
         */
        group2.addDependencyTaskGroup(group1);

        // Invocation of group-1 should not invoke group-2
        //
        group1.invokeAsync(group1.newInvocationContext())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String value) {
                        Assert.assertTrue(group1Items.contains(value));
                        group1Items.remove(value);
                    }
                });

        Assert.assertEquals(0, group1Items.size());

        // Test order
        //
        LinkedList<String> expectedOrder = new LinkedList<>();

        expectedOrder.push("F");
        expectedOrder.push("E");
        expectedOrder.push("D");
        expectedOrder.push("C");
        expectedOrder.push("B");
        expectedOrder.push("A");

        group1.prepareForEnumeration();
        for (TaskGroupEntry<String, StringTaskItem> entry = group1.getNext(); entry != null; entry = group1.getNext()) {
            String top = expectedOrder.poll();
            Assert.assertNotNull(top);
            Assert.assertEquals(top, entry.key());
            group1.reportCompletion(entry);
        }

        Assert.assertEquals(0, expectedOrder.size());
    }

    @Test
    public void testTaskGroupInvocationShouldInvokeDependencyTaskGroup() {
        // Prepare group-1
        //
        /**
         *
         *   |------------------->B------------|
         *   |                                 |
         *   |                                 ↓
         *   F            ------->C----------->A
         *   |            |                    ^    [group-1]
         *   |            |                    |
         *   |------------>E                   |
         *                |                    |
         *                |                    |
         *                ------->D-------------
         */
        final List<String> group1Items = new ArrayList<>();
        final TaskGroup<String, StringTaskItem> group1 = createSampleTaskGroup("A", "B",
                "C", "D",
                "E", "F",
                group1Items);

        // Prepare group-2
        //
        /**
         *
         *   |------------------->H------------|
         *   |                                 |
         *   |                                 ↓
         *   L            ------->I----------->G
         *   |            |                    ^    [group-2]
         *   |            |                    |
         *   |------------>K                   |
         *                |                    |
         *                |                    |
         *                ------->J-------------
         */
        final List<String> group2Items = new ArrayList<>();
        final TaskGroup<String, StringTaskItem> group2 = createSampleTaskGroup("G", "H",
                "I", "J",
                "K", "L",
                group2Items);

        // Expand group-2 by adding it as group-1's dependent
        //
        /**
         *
         *     |------------------->H------------|
         *     |                                 |
         *     |                                 ↓
         * |---L            ------->I----------->G
         * |   |            |                    ^          [group-2]
         * |   |            |                    |
         * |   |------------>K                   |
         * |                |                    |
         * |                |                    |
         * |                ------->J-------------
         * |
         * |        |------------------->B------------|
         * |        |                                 |
         * |        |                                 ↓
         * |------->F             ------->C---------->A
         *          |             |                   ^
         *          |             |                   |     [group-1]
         *          |------------>E                   |
         *                        |                   |
         *                        |                   |
         *                        ------->D------------
         */
        group2.addDependencyTaskGroup(group1);

        group2Items.addAll(group1Items);

        // Invocation of group-2 should invoke group-2 and group-1
        //
        group2.invokeAsync(group2.newInvocationContext())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String value) {
                        Assert.assertTrue(group2Items.contains(value));
                        group2Items.remove(value);
                    }
                });

        Assert.assertEquals(0, group2Items.size());

        // Test order
        //
        LinkedList<String> expectedOrder = new LinkedList<>();

        expectedOrder.push("L");
        expectedOrder.push("F");
        expectedOrder.push("K");
        expectedOrder.push("E");
        expectedOrder.push("J");
        expectedOrder.push("I");
        expectedOrder.push("H");
        expectedOrder.push("D");
        expectedOrder.push("C");
        expectedOrder.push("B");
        expectedOrder.push("G");
        expectedOrder.push("A");

        group2.prepareForEnumeration();
        for (TaskGroupEntry<String, StringTaskItem> entry = group2.getNext(); entry != null; entry = group2.getNext()) {
            String top = expectedOrder.poll();
            Assert.assertNotNull(top);
            Assert.assertEquals(top, entry.key());
            group2.reportCompletion(entry);
        }

        Assert.assertEquals(0, expectedOrder.size());
    }

    @Test
    public void testTaskGroupInvocationShouldInvokePostRunDependentTaskGroup() {
        // Prepare group-1
        //
        /**
         *
         *   |------------------->B------------|
         *   |                                 |
         *   |                                 ↓
         *   F            ------->C----------->A
         *   |            |                    ^    [group-1]
         *   |            |                    |
         *   |------------>E                   |
         *                |                    |
         *                |                    |
         *                ------->D-------------
         */
        final LinkedList<String> group1Items = new LinkedList<>();
        final TaskGroup<String, StringTaskItem> group1 = createSampleTaskGroup("A", "B",
                "C", "D",
                "E", "F",
                group1Items);

        // Prepare group-2
        //
        /**
         *
         *   |------------------->H------------|
         *   |                                 |
         *   |                                 ↓
         *   L            ------->I----------->G
         *   |            |                    ^    [group-2]
         *   |            |                    |
         *   |------------>K                   |
         *                |                    |
         *                |                    |
         *                ------->J-------------
         */
        final LinkedList<String> group2Items = new LinkedList<>();
        final TaskGroup<String, StringTaskItem> group2 = createSampleTaskGroup("G", "H",
                "I", "J",
                "K", "L",
                group2Items);

        // Add group-2 as group-1's "post run" dependent
        //
        /**
         *
         *                         |------------------->H------------|
         *                         |                                 |
         *         --------------->L                                 |
         *         |               |                                 ↓
         *         |           |---L            |------->I---------->G
         *         |           |   |            |                    ^
         *         |           |   |            |                    |             [group-2]
         *         |           |   |------------>K                   |
         *         |           |                |                    |
         *         |           |                |                    |
         *   Proxy F"          |                ------->J-------------
         *         |           |
         *         |           |        |------------------->B------------|
         *         |           |        |                                 |
         *         |           |        |                                 |
         *         |           |------->F                                 ↓
         *         |                    |              ------->C--------->A
         *         |------------------->F            |                    ^
         *                              |            |                    |        [group-1]
         *                              |------------>E                   |
         *                                            |                   |
         *                                            |                   |
         *                                            ------->D------------
         */

        group1.addPostRunDependentTaskGroup(group2);

        group1Items.addAll(group2Items);

        // Invocation of group-1 should run group-1 and it's "post run" dependent group-2
        //
        group1.invokeAsync(group1.newInvocationContext())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String value) {
                        Assert.assertTrue(group1Items.contains(value));
                        group1Items.remove(value);
                    }
                });

        Assert.assertEquals(0, group1Items.size());

//        // Test order
//        //
        LinkedList<String> expectedOrder = new LinkedList<>();

        expectedOrder.push("proxy-F");
        expectedOrder.push("L");
        expectedOrder.push("F");
        expectedOrder.push("K");
        expectedOrder.push("E");
        expectedOrder.push("J");
        expectedOrder.push("I");
        expectedOrder.push("H");
        expectedOrder.push("D");
        expectedOrder.push("C");
        expectedOrder.push("B");
        expectedOrder.push("G");
        expectedOrder.push("A");

        group1.proxyTaskGroupWrapper.proxyTaskGroup().prepareForEnumeration();
        for (TaskGroupEntry<String, TaskItem<String>> entry = group1.proxyTaskGroupWrapper.proxyTaskGroup().getNext();
             entry != null;
             entry = group1.proxyTaskGroupWrapper.proxyTaskGroup().getNext()) {
            String top = expectedOrder.poll();
            Assert.assertNotNull(top);
            Assert.assertEquals(top, entry.key());
            group1.proxyTaskGroupWrapper.proxyTaskGroup().reportCompletion(entry);
        }
        Assert.assertEquals(0, expectedOrder.size());
    }

    @Test
    public void testPostRunTaskGroupInvocationShouldInvokeDependencyTaskGroup() {
        // Prepare group-1
        //
        /**
         *
         *   |------------------->B------------|
         *   |                                 |
         *   |                                 ↓
         *   F            ------->C----------->A
         *   |            |                    ^    [group-1]
         *   |            |                    |
         *   |------------>E                   |
         *                |                    |
         *                |                    |
         *                ------->D-------------
         */
        final LinkedList<String> group1Items = new LinkedList<>();
        final TaskGroup<String, StringTaskItem> group1 = createSampleTaskGroup("A", "B",
                "C", "D",
                "E", "F",
                group1Items);

        // Prepare group-2
        //
        /**
         *
         *   |------------------->H------------|
         *   |                                 |
         *   |                                 ↓
         *   L            ------->I----------->G
         *   |            |                    ^    [group-2]
         *   |            |                    |
         *   |------------>K                   |
         *                |                    |
         *                |                    |
         *                ------->J-------------
         */
        final List<String> group2Items = new ArrayList<>();
        final TaskGroup<String, StringTaskItem> group2 = createSampleTaskGroup("G", "H",
                "I", "J",
                "K", "L",
                group2Items);

        // Add group-2 as group-1's "post run" dependent
        //
        /**
         *
         *                         |------------------->H------------|
         *                         |                                 |
         *         --------------->L                                 |
         *         |               |                                 ↓
         *         |           |---L            |------->I---------->G
         *         |           |   |            |                    ^
         *         |           |   |            |                    |            [group-2]
         *         |           |   |------------>K                   |
         *         |           |                |                    |
         *         |           |                |                    |
         *   Proxy F"          |                ------->J-------------
         *         |           |
         *         |           |        |------------------->B------------|
         *         |           |        |                                 |
         *         |           |        |                                 |
         *         |           |------->F                                 ↓
         *         |                    |              ------->C--------->A
         *         |------------------->F            |                    ^
         *                              |            |                    |        [group-1]
         *                              |------------>E                   |
         *                                            |                   |
         *                                            |                   |
         *                                            ------->D------------
         */

        group1.addPostRunDependentTaskGroup(group2);

        group2Items.addAll(group1Items);

        // Invocation of group-2 should run group-2 and group-1
        //
        group2.invokeAsync(group2.newInvocationContext())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String value) {
                        Assert.assertTrue(group2Items.contains(value));
                        group2Items.remove(value);
                    }
                });

        Assert.assertEquals(0, group2Items.size());

        // Test order
        //
        LinkedList<String> expectedOrder = new LinkedList<>();

        expectedOrder.push("L");
        expectedOrder.push("F");
        expectedOrder.push("K");
        expectedOrder.push("E");
        expectedOrder.push("J");
        expectedOrder.push("I");
        expectedOrder.push("H");
        expectedOrder.push("D");
        expectedOrder.push("C");
        expectedOrder.push("B");
        expectedOrder.push("G");
        expectedOrder.push("A");

        group2.prepareForEnumeration();
        for (TaskGroupEntry<String, StringTaskItem> entry = group2.getNext();
             entry != null;
             entry = group2.getNext()) {
            String top = expectedOrder.poll();
            Assert.assertNotNull(top);
            Assert.assertEquals(top, entry.key());
            group2.reportCompletion(entry);
        }
        Assert.assertEquals(0, expectedOrder.size());
    }

    @Test
    public void testParentReassignmentUponProxyTaskGroupActivation() {
        // Prepare group-1
        //
        /**
         *
         *   |------------------->B------------|
         *   |                                 |
         *   |                                 ↓
         *   F            ------->C----------->A
         *   |            |                    ^      [group-1]
         *   |            |                    |
         *   |------------>E                   |
         *                |                    |
         *                |                    |
         *                ------->D-------------
         */
        final LinkedList<String> group1Items = new LinkedList<>();
        final TaskGroup<String, StringTaskItem> group1 = createSampleTaskGroup("A", "B",
                "C", "D",
                "E", "F",
                group1Items);

        // Prepare group-2
        //
        /**
         *
         *   |------------------->H------------|
         *   |                                 |
         *   |                                 ↓
         *   L            ------->I----------->G
         *   |            |                    ^       [group-2]
         *   |            |                    |
         *   |------------>K                   |
         *                |                    |
         *                |                    |
         *                ------->J-------------
         */
        final List<String> group2Items = new ArrayList<>();
        final TaskGroup<String, StringTaskItem> group2 = createSampleTaskGroup("G", "H",
                "I", "J",
                "K", "L",
                group2Items);

        // Make group-2 as group-1's parent by adding group-1 as group-2's dependency.
        //
        /**
         *
         *     |------------------->H------------|
         *     |                                 |
         *     |                                 ↓
         * |---L            ------->I----------->G
         * |   |            |                    ^          [group-2]
         * |   |            |                    |
         * |   |------------>K                   |
         * |                |                    |
         * |                |                    |
         * |                ------->J-------------
         * |
         * |        |------------------->B------------|
         * |        |                                 |
         * |        |                                 ↓
         * |------->F             ------->C---------->A
         *          |             |                   ^
         *          |             |                   |     [group-1]
         *          |------------>E                   |
         *                        |                   |
         *                        |                   |
         *                        ------->D------------
         */

        group2.addDependencyTaskGroup(group1);

        // Check parent
        Assert.assertEquals(1, group1.parentDAGs.size());
        Assert.assertTrue(group1.parentDAGs.contains(group2));

        // Prepare group-3
        //
        /**
         *
         *   |------------------->N------------|
         *   |                                 |
         *   |                                 ↓
         *   R            ------->O----------->M
         *   |            |                    ^            [group-3]
         *   |            |                    |
         *   |----------->Q                    |
         *                |                    |
         *                |                    |
         *                ------->P-------------
         */

        final LinkedList<String> group3Items = new LinkedList<>();
        final TaskGroup<String, StringTaskItem> group3 = createSampleTaskGroup("M", "N",
                "O", "P",
                "Q", "R",
                group3Items);

        // Make group-3 as group-1's 'post-run" dependent. This activate proxy group, should do parent re-assignment
        // i.e. the parent "group-2" of "group-1" will become parent of "group-1's proxy".
        //
        /**
         *                [group-2]
         *
         *           |------------------->H------------|
         *           |                                 |
         *     ------L            |------->I---------->G                             |------------------->B-----------|
         *     |     |            |                    ^                             |                                |
         *     |     |            |                    |                             |                                ↓
         *     |     |------------>K                   |                             |             ------>C---------->A
         *     |                  |                    |        |------------------->F             |                  ^      [group-1]
         *     |                  |                    |        |            ------->F             |                  |
         *     |                  ------->J-------------        |           |        |------------>E                  |
         *     |                                                |           |                      |                  |
         *     |                                                |           |                      |                  |
         *     |                                                |           |                      ------->D-----------
         *     |-------------------------------------------->Proxy F"       |
         *                                                      |           |       |------------------->N------------|
         *                                                      |           |       |                                 |
         *                                                      |           |       |                                 ↓
         *                                                      |           --------R            ------->O----------->M
         *                                                      |------------------>R            |                    ^      [group-3]
         *                                                                          |            |                    |
         *                                                                          |----------->Q                    |
         *                                                                                       |                    |
         *                                                                                       |                    |
         *                                                                                        ------->P-----------
         *
         */

         group1.addPostRunDependentTaskGroup(group3);

        // Check parent reassignment
        //
        Assert.assertEquals(2, group1.parentDAGs.size());
        Assert.assertTrue(group1.parentDAGs.contains(group3));
        Assert.assertTrue(group1.parentDAGs.contains(group1.proxyTaskGroupWrapper.proxyTaskGroup()));
        Assert.assertEquals(1, group1.proxyTaskGroupWrapper.proxyTaskGroup().parentDAGs.size());
        Assert.assertTrue( group1.proxyTaskGroupWrapper.proxyTaskGroup().parentDAGs.contains(group2));


        // Test invocation order for group-2
        //
        LinkedList<String> group2ExpectedOrder = new LinkedList<>();

        group2ExpectedOrder.push("L");
        group2ExpectedOrder.push("proxy-F");
        group2ExpectedOrder.push("R");
        group2ExpectedOrder.push("F");
        group2ExpectedOrder.push("Q");
        group2ExpectedOrder.push("K");
        group2ExpectedOrder.push("E");
        group2ExpectedOrder.push("P");
        group2ExpectedOrder.push("O");
        group2ExpectedOrder.push("N");
        group2ExpectedOrder.push("J");
        group2ExpectedOrder.push("I");
        group2ExpectedOrder.push("H");
        group2ExpectedOrder.push("D");
        group2ExpectedOrder.push("C");
        group2ExpectedOrder.push("B");
        group2ExpectedOrder.push("M");
        group2ExpectedOrder.push("G");
        group2ExpectedOrder.push("A");

        group2.prepareForEnumeration();
        for (TaskGroupEntry<String, StringTaskItem> entry = group2.getNext(); entry != null; entry = group2.getNext()) {
            String top = group2ExpectedOrder.poll();
            Assert.assertNotNull(top);
            Assert.assertEquals(top, entry.key());
            group2.reportCompletion(entry);
        }
        Assert.assertEquals(0, group2ExpectedOrder.size());

        // Test invocation order for "group-1 proxy"
        //
        LinkedList<String> proxyGroup1ExpectedOrder = new LinkedList<>();
        proxyGroup1ExpectedOrder.push("proxy-F");
        proxyGroup1ExpectedOrder.push("R");
        proxyGroup1ExpectedOrder.push("F");
        proxyGroup1ExpectedOrder.push("Q");
        proxyGroup1ExpectedOrder.push("E");
        proxyGroup1ExpectedOrder.push("P");
        proxyGroup1ExpectedOrder.push("O");
        proxyGroup1ExpectedOrder.push("N");
        proxyGroup1ExpectedOrder.push("D");
        proxyGroup1ExpectedOrder.push("C");
        proxyGroup1ExpectedOrder.push("B");
        proxyGroup1ExpectedOrder.push("M");
        proxyGroup1ExpectedOrder.push("A");

        TaskGroup<String, TaskItem<String>> group1Proxy = group1.proxyTaskGroupWrapper.proxyTaskGroup();
        group1Proxy.prepareForEnumeration();
        for (TaskGroupEntry<String, TaskItem<String>> entry = group1Proxy.getNext(); entry != null; entry = group1Proxy.getNext()) {
            String top = proxyGroup1ExpectedOrder.poll();
            Assert.assertNotNull(top);
            Assert.assertEquals(top, entry.key());
            group1Proxy.reportCompletion(entry);
        }
    }

    @Test
    public void testParentProxyReassignmentUponProxyTaskGroupActivation() {
        // Prepare group-1
        //
        /**
         *
         *   |------------------->B------------|
         *   |                                 |
         *   |                                 ↓
         *   F            ------->C----------->A
         *   |            |                    ^      [group-1]
         *   |            |                    |
         *   |------------>E                   |
         *                |                    |
         *                |                    |
         *                ------->D-------------
         */
        final LinkedList<String> group1Items = new LinkedList<>();
        final TaskGroup<String, StringTaskItem> group1 = createSampleTaskGroup("A", "B",
                "C", "D",
                "E", "F",
                group1Items);

        // Prepare group-2
        //
        /**
         *
         *   |------------------->H------------|
         *   |                                 |
         *   |                                 ↓
         *   L            ------->I----------->G
         *   |            |                    ^       [group-2]
         *   |            |                    |
         *   |------------>K                   |
         *                |                    |
         *                |                    |
         *                ------->J-------------
         */
        final List<String> group2Items = new ArrayList<>();
        final TaskGroup<String, StringTaskItem> group2 = createSampleTaskGroup("G", "H",
                "I", "J",
                "K", "L",
                group2Items);

        // Make group-2 as group-1's parent by adding group-1 as group-2's dependency.
        //
        /**
         *
         *     |------------------->H------------|
         *     |                                 |
         *     |                                 ↓
         * |---L            ------->I----------->G
         * |   |            |                    ^          [group-2]
         * |   |            |                    |
         * |   |------------>K                   |
         * |                |                    |
         * |                |                    |
         * |                ------->J-------------
         * |
         * |        |------------------->B------------|
         * |        |                                 |
         * |        |                                 ↓
         * |------->F             ------->C---------->A
         *          |             |                   ^
         *          |             |                   |     [group-1]
         *          |------------>E                   |
         *                        |                   |
         *                        |                   |
         *                        ------->D------------
         */

        group2.addDependencyTaskGroup(group1);

        // Check parent
        Assert.assertEquals(1, group1.parentDAGs.size());
        Assert.assertTrue(group1.parentDAGs.contains(group2));

        // Prepare group-3
        //
        /**
         *
         *   |------------------->N------------|
         *   |                                 |
         *   |                                 ↓
         *   R            ------->O----------->M
         *   |            |                    ^            [group-3]
         *   |            |                    |
         *   |----------->Q                    |
         *                |                    |
         *                |                    |
         *                ------->P-------------
         */

        final LinkedList<String> group3Items = new LinkedList<>();
        final TaskGroup<String, StringTaskItem> group3 = createSampleTaskGroup("M", "N",
                "O", "P",
                "Q", "R",
                group3Items);

        // Make group-3 (Root-R) as group-1's (Root-F) 'post-run" dependent. This activate "group-1 proxy group",
        // should do parent re-assignment i.e. the parent "group-2" of "group-1" will become parent of "group-1's proxy".
        //
        /**
         *                [group-2]
         *
         *           |------------------->H------------|
         *           |                                 |
         *     ------L            |------->I---------->G                             |------------------->B-----------|
         *     |     |            |                    ^                             |                                |
         *     |     |            |                    |                             |                                ↓
         *     |     |------------>K                   |                             |             ------>C---------->A
         *     |                  |                    |        |------------------->F             |                  ^      [group-1]
         *     |                  |                    |        |            ------->F             |                  |
         *     |                  ------->J-------------        |           |        |------------>E                  |
         *     |                                                |           |                      |                  |
         *     |                                                |           |                      |                  |
         *     |                                                |           |                      ------->D-----------
         *     |-------------------------------------------->Proxy F"       |
         *                                                      |           |       |------------------->N------------|
         *                                                      |           |       |                                 |
         *                                                      |           |       |                                 ↓
         *                                                      |           --------R            ------->O----------->M
         *                                                      |------------------>R            |                    ^      [group-3]
         *                                                                          |            |                    |
         *                                                                          |----------->Q                    |
         *                                                                                       |                    |
         *                                                                                       |                    |
         *                                                                                        ------->P-----------
         *
         */

        group1.addPostRunDependentTaskGroup(group3);

        // Check parent reassignment
        //
        Assert.assertEquals(2, group1.parentDAGs.size());
        Assert.assertTrue(group1.parentDAGs.contains(group3));
        Assert.assertTrue(group1.parentDAGs.contains(group1.proxyTaskGroupWrapper.proxyTaskGroup()));
        Assert.assertEquals(1, group1.proxyTaskGroupWrapper.proxyTaskGroup().parentDAGs.size());
        Assert.assertTrue( group1.proxyTaskGroupWrapper.proxyTaskGroup().parentDAGs.contains(group2));


        // Prepare group-4
        //
        /**
         *
         *   |------------------->T------------|
         *   |                                 |
         *   |                                 ↓
         *   X            ------->U----------->S
         *   |            |                    ^            [group-4]
         *   |            |                    |
         *   |----------->W                    |
         *                |                    |
         *                |                    |
         *                ------->V-------------
         */

        final LinkedList<String> group4Items = new LinkedList<>();
        final TaskGroup<String, StringTaskItem> group4 = createSampleTaskGroup("S", "T",
                "U", "V",
                "W", "X",
                group4Items);

        // Prepare group-5
        //
        /**
         *
         *   |------------------->2------------|
         *   |                                 |
         *   |                                 ↓
         *   6            ------->3----------->1
         *   |            |                    ^            [group-5]
         *   |            |                    |
         *   |----------->5                    |
         *                |                    |
         *                |                    |
         *                ------->4-------------
         */

        final LinkedList<String> group5Items = new LinkedList<>();
        final TaskGroup<String, StringTaskItem> group5 = createSampleTaskGroup("1", "2",
                "3", "4",
                "5", "6",
                group5Items);

        // Make group-5 as group-4's 'post-run" dependent. This activates "group-4 proxy group".

        /**
         *
         *                         |------------------->2------------|
         *                         |                                 |
         *         --------------->6                                 ↓
         *         |           |---6            |------>3----------->1
         *         |           |   |            |                    ^        [group-5]
         *         |           |   |            |                    |
         *         |           |   |------------>5                   |
         *         |           |                |                    |
         *         |           |                |                    |
         *   Proxy X"          |                ------->4-------------
         *         |           |
         *         |           |        |------------------->T------------|
         *         |           |        |                                 |
         *         |           |        |                                 ↓
         *         |           |------->X            ------->U----------->S
         *         |------------------->X            |                    ^   [group-4]
         *                              |            |                    |
         *                              |------------>W                   |
         *                                            |                   |
         *                                            |                   |
         *                                            ------->V------------
         */

        group4.addPostRunDependentTaskGroup(group5);

        // Make group-4 (Root-x) as group-1's (Root-F) 'post-run" dependent.
        //
        /**
         *
         *                                                                                          |------------------->2------------|
         *                                                                                          |                                 |
         *                                                                          --------------->6                                 ↓
         *                                                                          |           |---6            |------>3----------->1
         *                                                                          |           |   |            |                    ^        [group-5]
         *                                                                          |           |   |            |                    |
         *                                                                          |           |   |------------>5                   |
         *                                                                          |           |                |                    |
         *                                                                          |           |                |                    |
         *                                                      ------------->Proxy X"          |                ------->4-------------
         *                                                      |                   |           |
         *                                                      |                   |           |        |------------------->T------------|
         *                                                      |                   |           |        |                                 |
         *                                                      |                   |           |        |                                 ↓
         *                                                      |                   |           |------->X            ------->U----------->S
         *                                                      |                    ------------------->X            |                    ^   [group-4]
         *                                                      |           -----------------------------X            |                    |
         *                                                      |           |                            |------------>W                   |
         *                                                      |           |                                         |                    |
         *                                                      |           |                                         |                    |
         *                                                      |           |                                         ------->V-------------
         *                                                      |           |
         *                                                      |           |
         *                                                      |           |
         *                                                      |           |
         *                                                      |           |
         *                                                      |           |
         *                [group-2]                             |           |
         *                                                      |           |
         *           |------------------->H------------|        |           |
         *           |                                 |        |           |
         *     ------L            |------->I---------->G        |           |        |------------------->B-----------|
         *     |     |            |                    ^        |           |        |                                |
         *     |     |            |                    |        |           |        |                                ↓
         *     |     |------------>K                   |        |           -------->F             ------>C---------->A
         *     |                  |                    |        |------------------->F             |                  ^      [group-1]
         *     |                  |                    |        |            ------->F             |                  |
         *     |                  ------->J-------------        |           |        |------------>E                  |
         *     |                                                |           |                      |                  |
         *     |                                                |           |                      |                  |
         *     |                                                |           |                      ------->D-----------
         *     |-------------------------------------------->Proxy F"       |
         *                                                      |           |       |------------------->N------------|
         *                                                      |           |       |                                 |
         *                                                      |           |       |                                 ↓
         *                                                      |           --------R            ------->O----------->M
         *                                                      |------------------>R            |                    ^      [group-3]
         *                                                                          |            |                    |
         *                                                                          |----------->Q                    |
         *                                                                                       |                    |
         *                                                                                       |                    |
         *                                                                                        ------->P-----------
         *
         */

        group1.addPostRunDependentTaskGroup(group4);


        // Test invocation order for group-1 (which gets delegated to group-1's proxy)
        // This cause -> group-1, group-3, group-4 and group-5 to invoked
        //
        LinkedList<String> proxyGroup1ExpectedOrder = new LinkedList<>();

        proxyGroup1ExpectedOrder.push("proxy-F");
        proxyGroup1ExpectedOrder.push("proxy-X");
        proxyGroup1ExpectedOrder.push("6");
        proxyGroup1ExpectedOrder.push("X");
        proxyGroup1ExpectedOrder.push("R");
        proxyGroup1ExpectedOrder.push("F");
        proxyGroup1ExpectedOrder.push("5");
        proxyGroup1ExpectedOrder.push("W");
        proxyGroup1ExpectedOrder.push("Q");
        proxyGroup1ExpectedOrder.push("E");
        proxyGroup1ExpectedOrder.push("4");
        proxyGroup1ExpectedOrder.push("3");
        proxyGroup1ExpectedOrder.push("2");
        proxyGroup1ExpectedOrder.push("V");
        proxyGroup1ExpectedOrder.push("U");
        proxyGroup1ExpectedOrder.push("T");
        proxyGroup1ExpectedOrder.push("P");
        proxyGroup1ExpectedOrder.push("O");
        proxyGroup1ExpectedOrder.push("N");
        proxyGroup1ExpectedOrder.push("D");
        proxyGroup1ExpectedOrder.push("C");
        proxyGroup1ExpectedOrder.push("B");
        proxyGroup1ExpectedOrder.push("1");
        proxyGroup1ExpectedOrder.push("S");
        proxyGroup1ExpectedOrder.push("M");
        proxyGroup1ExpectedOrder.push("A");

        TaskGroup<String, TaskItem<String>> group1Proxy = group1.proxyTaskGroupWrapper.proxyTaskGroup();
        group1Proxy.prepareForEnumeration();
        for (TaskGroupEntry<String, TaskItem<String>> entry = group1Proxy.getNext(); entry != null; entry = group1Proxy.getNext()) {
            String top = proxyGroup1ExpectedOrder.poll();
            Assert.assertNotNull(top);
            Assert.assertEquals(top, entry.key());
            group1Proxy.reportCompletion(entry);
        }
        Assert.assertEquals(0, proxyGroup1ExpectedOrder.size());

        // Test invocation order for group-1 (which gets delegated to group-1's proxy).
        // This cause -> group-1, group-4 and group-5 to invoked
        //
        LinkedList<String> proxyGroup4ExpectedOrder = new LinkedList<>();

        proxyGroup4ExpectedOrder.push("proxy-X");
        proxyGroup4ExpectedOrder.push("6");
        proxyGroup4ExpectedOrder.push("X");
        proxyGroup4ExpectedOrder.push("F");
        proxyGroup4ExpectedOrder.push("W");
        proxyGroup4ExpectedOrder.push("5");
        proxyGroup4ExpectedOrder.push("E");
        proxyGroup4ExpectedOrder.push("V");
        proxyGroup4ExpectedOrder.push("U");
        proxyGroup4ExpectedOrder.push("T");
        proxyGroup4ExpectedOrder.push("4");
        proxyGroup4ExpectedOrder.push("3");
        proxyGroup4ExpectedOrder.push("2");
        proxyGroup4ExpectedOrder.push("D");
        proxyGroup4ExpectedOrder.push("C");
        proxyGroup4ExpectedOrder.push("B");
        proxyGroup4ExpectedOrder.push("S");
        proxyGroup4ExpectedOrder.push("1");
        proxyGroup4ExpectedOrder.push("A");

        TaskGroup<String, TaskItem<String>> group4Proxy = group4.proxyTaskGroupWrapper.proxyTaskGroup();
        group4Proxy.prepareForEnumeration();
        for (TaskGroupEntry<String, TaskItem<String>> entry = group4Proxy.getNext(); entry != null; entry = group4Proxy.getNext()) {
            String top = proxyGroup4ExpectedOrder.poll();
            Assert.assertNotNull(top);
            Assert.assertEquals(top, entry.key());
            group4Proxy.reportCompletion(entry);
        }
        Assert.assertEquals(0, proxyGroup4ExpectedOrder.size());


        // Test invocation order for group-2 (which gets delegated to group-2's proxy).
        // This cause -> all groups to be invoked, group-1, group-3, group-4 and group-5 to invoked

        LinkedList<String> group2ExpectedOrder = new LinkedList<>();

        group2ExpectedOrder.push("L");
        group2ExpectedOrder.push("proxy-F");
        group2ExpectedOrder.push("proxy-X");
        group2ExpectedOrder.push("6");
        group2ExpectedOrder.push("X");
        group2ExpectedOrder.push("R");
        group2ExpectedOrder.push("F");
        group2ExpectedOrder.push("5");
        group2ExpectedOrder.push("W");
        group2ExpectedOrder.push("Q");
        group2ExpectedOrder.push("K");
        group2ExpectedOrder.push("E");
        group2ExpectedOrder.push("4");
        group2ExpectedOrder.push("3");
        group2ExpectedOrder.push("2");
        group2ExpectedOrder.push("V");
        group2ExpectedOrder.push("U");
        group2ExpectedOrder.push("T");
        group2ExpectedOrder.push("P");
        group2ExpectedOrder.push("O");
        group2ExpectedOrder.push("N");
        group2ExpectedOrder.push("J");
        group2ExpectedOrder.push("I");
        group2ExpectedOrder.push("H");
        group2ExpectedOrder.push("D");
        group2ExpectedOrder.push("C");
        group2ExpectedOrder.push("B");
        group2ExpectedOrder.push("1");
        group2ExpectedOrder.push("S");
        group2ExpectedOrder.push("M");
        group2ExpectedOrder.push("G");
        group2ExpectedOrder.push("A");

        group2.prepareForEnumeration();
        for (TaskGroupEntry<String, StringTaskItem> entry = group2.getNext(); entry != null; entry = group2.getNext()) {
            String top = group2ExpectedOrder.poll();
            Assert.assertNotNull(top);
            Assert.assertEquals(top, entry.key());
            group2.reportCompletion(entry);
        }
        Assert.assertEquals(0, group2ExpectedOrder.size());


        final List<String> allItems = new ArrayList<>();
        allItems.addAll(group1Items);
        allItems.addAll(group2Items);
        allItems.addAll(group3Items);
        allItems.addAll(group4Items);
        allItems.addAll(group5Items);
        allItems.add("X");  // Duplicate emitted by proxy-X
        allItems.add("F");  // Duplicate emitted by proxy-F

        // Invoke group-2
        //
        final ArrayList<String> seen = new ArrayList<>();
        group2.invokeAsync(group2.newInvocationContext())
                .toBlocking()
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String value) {
                        Assert.assertTrue(allItems.contains(value));
                        allItems.remove(value);
                    }
                });

        Assert.assertEquals(0, allItems.size());
    }

    private TaskGroup<String, StringTaskItem> createSampleTaskGroup(String vertex1,
                                                                    String vertex2,
                                                                    String vertex3,
                                                                    String vertex4,
                                                                    String vertex5,
                                                                    String vertex6,
                                                                    List<String> verticesNames) {
        verticesNames.add(vertex6);
        verticesNames.add(vertex5);
        verticesNames.add(vertex4);
        verticesNames.add(vertex3);
        verticesNames.add(vertex2);
        verticesNames.add(vertex1);

        /**
         * Creates a task group with following shape.
         *
         *   |------------------->group2------------|
         *   |                                      |
         *   |                                      ↓
         * group6         ------->group3--------->group1
         *   |            |                         ^
         *   |            |                         |
         *   |-------->group5                       |
         *                |                         |
         *                |                         |
         *                ------->group4-------------
         */

        TaskGroupTerminateOnErrorStrategy terminateStrategy = TaskGroupTerminateOnErrorStrategy.TERMINATE_ON_INPROGRESS_TASKS_COMPLETION;
        TaskGroup<String, StringTaskItem> group1 = new TaskGroup<>(vertex1, new StringTaskItem(vertex1), terminateStrategy);
        TaskGroup<String, StringTaskItem> group2 = new TaskGroup<>(vertex2, new StringTaskItem(vertex2), terminateStrategy);
        TaskGroup<String, StringTaskItem> group3 = new TaskGroup<>(vertex3, new StringTaskItem(vertex3), terminateStrategy);
        TaskGroup<String, StringTaskItem> group4 = new TaskGroup<>(vertex4, new StringTaskItem(vertex4), terminateStrategy);
        TaskGroup<String, StringTaskItem> group5 = new TaskGroup<>(vertex5, new StringTaskItem(vertex5), terminateStrategy);
        TaskGroup<String, StringTaskItem> group6 = new TaskGroup<>(vertex6, new StringTaskItem(vertex6), terminateStrategy);

        group2.addDependencyTaskGroup(group1);
        group3.addDependencyTaskGroup(group1);
        group4.addDependencyTaskGroup(group1);

        group5.addDependencyTaskGroup(group3);
        group5.addDependencyTaskGroup(group4);

        group6.addDependencyTaskGroup(group2);
        group6.addDependencyTaskGroup(group5);

        return group6;
    }


    private static class StringTaskItem implements TaskItem<String> {
        private final String name;
        private String producedValue = null;

        StringTaskItem(String name) {
            this.name = name;
        }

        @Override
        public String result() {
            return this.producedValue;
        }

        @Override
        public void prepare() {
        }

        @Override
        public boolean isHot() {
            return false;
        }

        @Override
        public Observable<String> invokeAsync(final TaskGroup.InvocationContext context) {
            this.producedValue = this.name;
            return Observable.just(this.producedValue);
        }
    }
}
