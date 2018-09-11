/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.v2.management.resources.childresource;

import com.microsoft.azure.v2.management.resources.fluentcore.arm.models.implementation.ExternalChildResourceImpl;
import com.microsoft.azure.v2.management.resources.fluentcore.model.Creatable;
import com.microsoft.azure.v2.management.resources.fluentcore.model.Indexable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import org.junit.Assert;
import org.junit.Test;
import io.reactivex.Observer;
import io.reactivex.exceptions.CompositeException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class ExternalChildResourceTests {
    @Test
    public void noCommitIfNoChange() throws InterruptedException {
        ChickenImpl chicken = new ChickenImpl(); // Parent resource
        PulletsImpl pullets = chicken.pullets(); // Child resource collection
        final CountDownLatch monitor = new CountDownLatch(1);
        // Note that commitAsync() won't be exposed to the end-user as it's a part of child resource impl
        // pullets.commitAsync will be called from (Applicable)chicken.applyAsync() or (Creatable)chicken.createAsyncStreaming().
        //
        // Observable<Chicken> Chicken::ApplyAsync() {
        //      [1] update chicken
        //      [2] update pullets by calling pullets.commitAsync()
        // }
        //
        // In the unit test cases we call it directly as we testing external child resource here.
        //
        pullets.commitAsync()
                .subscribe(new Observer<PulletImpl>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                    }

                    @Override
                    public void onComplete() {
                        monitor.countDown();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        monitor.countDown();
                        Assert.fail("nothing to commit onError should not be invoked");
                    }

                    @Override
                    public void onNext(PulletImpl pullet) {
                        Assert.fail("nothing to commit onNext should not be invoked");
                    }
                });
        monitor.await();
    }

    @Test
    public void shouldCommitCreateUpdateAndDelete() throws InterruptedException {
        ChickenImpl chicken = new ChickenImpl(); // Parent resource
        chicken
            .defineNewPullet("alice")
                .withAge(1)
                .attach()
            .updatePullet("Clover")
                .withAge(2)
                .parent()
            .withoutPullet("Pinky");

        final List<PulletImpl> changedPuppets = new ArrayList<>();
        final CountDownLatch monitor = new CountDownLatch(1);

        PulletsImpl pullets = chicken.pullets();
        pullets.commitAsync().subscribe(new Observer<PulletImpl>() {

            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(PulletImpl pullet) {
                changedPuppets.add(pullet);
            }

            @Override
            public void onError(Throwable throwable) {
                monitor.countDown();
                Assert.fail("onError should not be invoked");
            }

            @Override
            public void onComplete() {
                monitor.countDown();
            }
        });

        monitor.await();
        Assert.assertEquals(2, changedPuppets.size());
        for (PulletImpl pullet : changedPuppets) {
            Assert.assertEquals(ExternalChildResourceImpl.PendingOperation.None, pullet.pendingOperation());
        }
    }

    @Test
    public void shouldEmitErrorAfterAllSuccessfulCommit() throws InterruptedException {
        ChickenImpl chicken = new ChickenImpl(); // Parent resource
        chicken
            .defineNewPullet("alice")
                .withAge(1)
                .withFailFlag(PulletImpl.FailFlag.OnCreate)
                .attach()
            .updatePullet("Clover")
                .withAge(2)
                .parent()
            .updatePullet("Goldilocks")
                .withAge(2)
                .withFailFlag(PulletImpl.FailFlag.OnUpdate)
                .parent()
            .withoutPullet("Pinky");

        final List<PulletImpl> changedPuppets = new ArrayList<>();
        final List<Throwable> throwables = new ArrayList<>();
        final CountDownLatch monitor = new CountDownLatch(1);
        PulletsImpl pullets = chicken.pullets();
        pullets.commitAsync()
                .blockingSubscribe(new Observer<PulletImpl>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(PulletImpl pullet) {
                        changedPuppets.add(pullet);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        try {
                            CompositeException exception = (CompositeException) throwable;
                            Assert.assertNotNull(exception);
                            for (Throwable innerThrowable : exception.getExceptions()) {
                                throwables.add(innerThrowable);
                            }
                        } finally {
                            monitor.countDown();
                        }
                    }

                    @Override
                    public void onComplete() {
                        monitor.countDown();
                        Assert.fail("onCompleted should not be invoked");
                    }
                });

        monitor.await();
        Assert.assertEquals(2, throwables.size());
        Assert.assertEquals(1, changedPuppets.size());
    }

    @Test
    public void canStreamAccumulatedResult() throws InterruptedException {
        ChickenImpl chicken = new ChickenImpl();
        chicken.defineNewPullet("alice")
                .withAge(1)
                .attach()
                .updatePullet("Clover")
                .withAge(2)
                .attach()
                .withoutPullet("Pinky");

        PulletsImpl pullets = chicken.pullets();
        List<PulletImpl> result = pullets.commitAndGetAllAsync().blockingGet();
        Assert.assertEquals(2, result.size());
    }

    @Test
    public void canCrossReferenceChildren() throws Exception {
        SchoolsImpl schools = new SchoolsImpl();

        Observable<Indexable> items = schools.define("redmondSchool")
                .withAddress("sc-address")
                .defineTeacher("maria")
                .withSubject("Maths")
                .attach()
                .defineStudent("bob")
                .withAge(10)
                .withTeacher("maria")   // Refer another creatable external child resource with key 'maria' in the parent
                .attach()
                .createAsync();

        final SchoolsImpl.SchoolImpl foundSchool[] = new SchoolsImpl.SchoolImpl[1];
        final SchoolsImpl.TeacherImpl foundTeacher[] = new SchoolsImpl.TeacherImpl[1];
        final SchoolsImpl.StudentImpl foundStudent[] = new SchoolsImpl.StudentImpl[1];

        items.doOnNext(indexable -> {
                    if (indexable instanceof SchoolsImpl.SchoolImpl) {
                        foundSchool[0] = (SchoolsImpl.SchoolImpl) indexable;
                    }
                    if (indexable instanceof SchoolsImpl.TeacherImpl) {
                        foundTeacher[0] = (SchoolsImpl.TeacherImpl) indexable;
                    }
                    if (indexable instanceof SchoolsImpl.StudentImpl) {
                        foundStudent[0] = (SchoolsImpl.StudentImpl) indexable;
                    }
                }).blockingLast();

        Assert.assertNotNull(foundSchool[0]);
        Assert.assertNotNull(foundTeacher[0]);
        Assert.assertNotNull(foundStudent[0]);

        Assert.assertTrue(foundSchool[0].isInvoked());
        Assert.assertTrue(foundTeacher[0].isInvoked());
        Assert.assertTrue(foundStudent[0].isInvoked());
    }

    @Test
    public void canCreateChildrenIndependently() throws Exception {
        SchoolsImpl schools = new SchoolsImpl();

        Creatable<SchoolsImpl.TeacherImpl>  creatableTeacher = schools.independentTeachers()
                .define("john")
                .withSubject("physics");

        Creatable<SchoolsImpl.StudentImpl> creatableStudent = schools.independentStudents()
                .define("nit")
                .withAge(15)
                .withTeacher(creatableTeacher);

        final SchoolsImpl.TeacherImpl foundTeacher[] = new SchoolsImpl.TeacherImpl[1];
        final SchoolsImpl.StudentImpl foundStudent[] = new SchoolsImpl.StudentImpl[1];

        creatableStudent.createAsync()
                .doOnNext(indexable -> {
                    if (indexable instanceof SchoolsImpl.TeacherImpl) {
                        foundTeacher[0] = (SchoolsImpl.TeacherImpl) indexable;
                    }
                    if (indexable instanceof SchoolsImpl.StudentImpl) {
                        foundStudent[0] = (SchoolsImpl.StudentImpl) indexable;
                    }
                }).blockingLast();

        Assert.assertNotNull(foundTeacher[0]);
        Assert.assertNotNull(foundStudent[0]);

        Assert.assertTrue(foundTeacher[0].isInvoked());
        Assert.assertTrue(foundStudent[0].isInvoked());
    }
}
