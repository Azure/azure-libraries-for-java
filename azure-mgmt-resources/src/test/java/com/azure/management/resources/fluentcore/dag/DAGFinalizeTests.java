/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.azure.management.resources.fluentcore.dag;

import org.junit.Assert;
import org.junit.Test;

/**
 * The tests for prepare stage of the graph (i.e. adding sub-graph in prepare stage).
 */
public class DAGFinalizeTests {
    @Test
    public void testWithoutFinalize() {
        /**
         *   |------------------>[D](2)----------->[B](1)-------------->[A](0)
         *   |                                        ^                   ^
         *   |                                        |                   |
         *  [F](4)---->[E](3)-------------------------|                   |
         *   |          |                                                 |
         *   |          |------->[G](2)----------->[C](1)------------------
         *   |
         *   |------------------------------------>[H](1)-------------->[I](0)
         */

        // Define pizzas with instant pizzas
        //
        // Level 0 pizzas
        PizzaImpl pizzaA = new PizzaImpl("A");
        PizzaImpl pizzaI = new PizzaImpl("I");
        // Level 1 pizzas
        PizzaImpl pizzaB = new PizzaImpl("B");
        pizzaB.withInstantPizza(pizzaA);
        PizzaImpl pizzaC = new PizzaImpl("C");
        pizzaC.withInstantPizza(pizzaA);
        PizzaImpl pizzaH = new PizzaImpl("H");
        pizzaH.withInstantPizza(pizzaI);
        // Level 2 pizzas
        PizzaImpl pizzaD = new PizzaImpl("D");
        pizzaD.withInstantPizza(pizzaB);
        PizzaImpl pizzaG = new PizzaImpl("G");
        pizzaG.withInstantPizza(pizzaC);
        // Level 3 pizzas
        PizzaImpl pizzaE = new PizzaImpl("E");
        pizzaE.withInstantPizza(pizzaB);
        pizzaE.withInstantPizza(pizzaG);
        // Level 4 pizzas
        PizzaImpl pizzaF = new PizzaImpl("F");
        pizzaF.withInstantPizza(pizzaD);
        pizzaF.withInstantPizza(pizzaE);
        pizzaF.withInstantPizza(pizzaH);

        // Run create to set up the underlying graph nodes with dependent details
        IPizza rootPizza = pizzaF.create();
        Assert.assertNotNull(rootPizza);

        // Check dependencies and dependents
        //
        // ----------------------------------------------------------------------------------
        // LEVEL - 0
        // ----------------------------------------------------------------------------------
        //
        // Level 0 - "A"
        Assert.assertEquals(pizzaA.getTaskGroup().getNodes().size(), 1);
        TaskGroupEntry<TaskItem> nodeA = pizzaA.getTaskGroup().getNode(pizzaA.getKey());
        Assert.assertNotNull(nodeA);
        Assert.assertEquals(nodeA.dependencyKeys().size(), 0);
        Assert.assertEquals(nodeA.dependentKeys().size(), 2);
        for (String dependentKey : nodeA.dependentKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaB.getKey())
                    || dependentKey.equalsIgnoreCase(pizzaC.getKey()));
        }
        // Level 0 - "I"
        Assert.assertEquals(pizzaI.getTaskGroup().getNodes().size(), 1);
        TaskGroupEntry<TaskItem> nodeI = pizzaI.getTaskGroup().getNode(pizzaI.getKey());
        Assert.assertNotNull(nodeI);
        Assert.assertEquals(nodeI.dependencyKeys().size(), 0);
        Assert.assertEquals(nodeI.dependentKeys().size(), 1);
        for (String dependentKey : nodeI.dependentKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaH.getKey()));
        }

        // ----------------------------------------------------------------------------------
        // LEVEL - 1
        // ----------------------------------------------------------------------------------
        //
        // Level 1 - "B"
        Assert.assertEquals(pizzaB.getTaskGroup().getNodes().size(), 2);
        Assert.assertNotNull(pizzaB.getTaskGroup().getNode(pizzaA.getKey()));
        TaskGroupEntry<TaskItem> nodeB = pizzaB.getTaskGroup().getNode(pizzaB.getKey());
        Assert.assertNotNull(nodeB);
        Assert.assertEquals(nodeB.dependencyKeys().size(), 1);
        for (String dependentKey : nodeB.dependencyKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaA.getKey()));
        }
        Assert.assertEquals(nodeB.dependentKeys().size(), 2);
        for (String dependentKey : nodeB.dependentKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaD.getKey())
                    || dependentKey.equalsIgnoreCase(pizzaE.getKey()));
        }
        // Level 1 - "C"
        Assert.assertEquals(pizzaC.getTaskGroup().getNodes().size(), 2);
        Assert.assertNotNull(pizzaC.getTaskGroup().getNode(pizzaA.getKey()));
        TaskGroupEntry<TaskItem> nodeC = pizzaC.getTaskGroup().getNode(pizzaC.getKey());
        Assert.assertNotNull(nodeC);
        Assert.assertEquals(nodeC.dependencyKeys().size(), 1);
        for (String dependentKey : nodeC.dependencyKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaA.getKey()));
        }
        Assert.assertEquals(nodeC.dependentKeys().size(), 1);
        for (String dependentKey : nodeC.dependentKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaG.getKey()));
        }
        // Level 1 - "H"
        Assert.assertEquals(pizzaH.getTaskGroup().getNodes().size(), 2);
        Assert.assertNotNull(pizzaH.getTaskGroup().getNode(pizzaI.getKey()));
        TaskGroupEntry<TaskItem> nodeH = pizzaH.getTaskGroup().getNode(pizzaH.getKey());
        Assert.assertNotNull(nodeH);
        Assert.assertEquals(nodeH.dependencyKeys().size(), 1);
        for (String dependentKey : nodeH.dependencyKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaI.getKey()));
        }
        Assert.assertEquals(nodeH.dependentKeys().size(), 1);
        for (String dependentKey : nodeH.dependentKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaF.getKey()));
        }

        // ----------------------------------------------------------------------------------
        // LEVEL - 2
        // ----------------------------------------------------------------------------------
        //
        // Level 2 - "D"
        Assert.assertEquals(pizzaD.getTaskGroup().getNodes().size(), 3);
        Assert.assertNotNull(pizzaD.getTaskGroup().getNode(pizzaA.getKey()));
        Assert.assertNotNull(pizzaD.getTaskGroup().getNode(pizzaB.getKey()));
        TaskGroupEntry<TaskItem> nodeD = pizzaD.getTaskGroup().getNode(pizzaD.getKey());
        Assert.assertNotNull(nodeD);
        Assert.assertEquals(nodeD.dependencyKeys().size(), 1);
        for (String dependentKey : nodeD.dependencyKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaB.getKey()));
        }
        Assert.assertEquals(nodeD.dependentKeys().size(), 1);
        for (String dependentKey : nodeD.dependentKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaF.getKey()));
        }
        // Level 2 - "G"
        Assert.assertEquals(pizzaG.getTaskGroup().getNodes().size(), 3);
        Assert.assertNotNull(pizzaG.getTaskGroup().getNode(pizzaA.getKey()));
        Assert.assertNotNull(pizzaG.getTaskGroup().getNode(pizzaC.getKey()));
        TaskGroupEntry<TaskItem> nodeG = pizzaG.getTaskGroup().getNode(pizzaG.getKey());
        Assert.assertNotNull(nodeG);
        Assert.assertEquals(nodeG.dependencyKeys().size(), 1);
        for (String dependentKey : nodeG.dependencyKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaC.getKey()));
        }
        Assert.assertEquals(nodeG.dependentKeys().size(), 1);
        for (String dependentKey : nodeG.dependentKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaE.getKey()));
        }

        // ----------------------------------------------------------------------------------
        // LEVEL - 3
        // ----------------------------------------------------------------------------------
        //
        // Level 3 - "E"
        Assert.assertEquals(pizzaE.getTaskGroup().getNodes().size(), 5);
        Assert.assertNotNull(pizzaE.getTaskGroup().getNode(pizzaA.getKey()));
        Assert.assertNotNull(pizzaE.getTaskGroup().getNode(pizzaB.getKey()));
        Assert.assertNotNull(pizzaE.getTaskGroup().getNode(pizzaC.getKey()));
        Assert.assertNotNull(pizzaE.getTaskGroup().getNode(pizzaG.getKey()));
        TaskGroupEntry<TaskItem> nodeE = pizzaE.getTaskGroup().getNode(pizzaE.getKey());
        Assert.assertNotNull(nodeE);
        Assert.assertEquals(nodeE.dependencyKeys().size(), 2);
        for (String dependentKey : nodeE.dependencyKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaB.getKey())
                    || dependentKey.equalsIgnoreCase(pizzaG.getKey()));
        }
        Assert.assertEquals(nodeE.dependentKeys().size(), 1);
        for (String dependentKey : nodeE.dependentKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaF.getKey()));
        }

        // ----------------------------------------------------------------------------------
        // LEVEL - 4
        // ----------------------------------------------------------------------------------
        //
        // Level 4 - "F"
        Assert.assertEquals(pizzaF.getTaskGroup().getNodes().size(), 9);
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaA.getKey()));
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaB.getKey()));
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaC.getKey()));
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaG.getKey()));
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaI.getKey()));
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaH.getKey()));
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaE.getKey()));
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaD.getKey()));
        TaskGroupEntry<TaskItem> nodeF = pizzaF.getTaskGroup().getNode(pizzaF.getKey());
        Assert.assertNotNull(nodeF);
        Assert.assertEquals(nodeF.dependencyKeys().size(), 3);
        for (String dependentKey : nodeF.dependencyKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaD.getKey())
                    || dependentKey.equalsIgnoreCase(pizzaE.getKey())
                    || dependentKey.equalsIgnoreCase(pizzaH.getKey()));
        }
        Assert.assertEquals(nodeF.dependentKeys().size(), 0);
    }

    @Test
    public void testFinalize() {
        // Initial dependency graph
        //
        /**
         *   |------------------>[D](2)----------->[B](1)------------->[A](0)
         *   |                                        ^                   ^
         *   |                                        |                   |
         *  [F](4)---->[E](3)-------------------------|                   |
         *   |          |                                                 |
         *   |          |------->[G](2)----------->[C](1)------------------
         *   |
         *   |------------------------------------>[H](1)-------------->[I](0)
         */

        // Level 0 pizzas
        PizzaImpl pizzaA = new PizzaImpl("A");
        PizzaImpl pizzaI = new PizzaImpl("I");
        // Level 1 pizzas
        PizzaImpl pizzaB = new PizzaImpl("B");
        pizzaB.withInstantPizza(pizzaA);
        PizzaImpl pizzaC = new PizzaImpl("C");
        pizzaC.withInstantPizza(pizzaA);
        PizzaImpl pizzaH = new PizzaImpl("H");
        pizzaH.withInstantPizza(pizzaI);
        // Level 2 pizzas
        PizzaImpl pizzaD = new PizzaImpl("D");
        pizzaD.withInstantPizza(pizzaB);
        PizzaImpl pizzaG = new PizzaImpl("G");
        pizzaG.withInstantPizza(pizzaC);
        // Level 3 pizzas
        PizzaImpl pizzaE = new PizzaImpl("E");
        pizzaE.withInstantPizza(pizzaB);
        pizzaE.withInstantPizza(pizzaG);
        // Level 4 pizzas
        PizzaImpl pizzaF = new PizzaImpl("F");
        pizzaF.withInstantPizza(pizzaD);
        pizzaF.withInstantPizza(pizzaE);
        pizzaF.withInstantPizza(pizzaH);

        // Update the above setup by adding delayed pizzas in finalize (prepare).
        // Define 3 (J, K, L) delayed pizzas (edges with '==' symbol), two of them (J, L)
        // with instant pizzas.
        //    - The delayed pizza J has an instance pizza N
        //    - The delayed pizza L has an instance pizza P
        //        - The instance pizza P has a delayed pizza Q
        /**
         *                                                       |------------>[M](0)
         *                                                       |
         *                                             |=========>[J](1)------>[N](0)
         *                                             |
         *   |------------------>[D](4)-->[B](3)----->[A](2)==================>[K](0)
         *   |                             ^           ^
         *   |                             |           |
         *  [F](6)---->[E](5)--------------|           |
         *   |          |                              |
         *   |          |------->[G](4)-->[C](3)--------
         *   |                    |
         *   |                    |==================>[L](2)----->[P](1)======>[Q](0)
         *   |
         *   |--------------------------------------------------->[H](1)------>[I](0)
         */

        PizzaImpl pizzaJ = new PizzaImpl("J");
        PizzaImpl pizzaM = new PizzaImpl("M");
        PizzaImpl pizzaN = new PizzaImpl("N");
        pizzaJ.withInstantPizza(pizzaM);
        pizzaJ.withInstantPizza(pizzaN);
        PizzaImpl pizzaK = new PizzaImpl("K");
        pizzaA.withDelayedPizza(pizzaJ);
        pizzaA.withDelayedPizza(pizzaK);
        PizzaImpl pizzaL = new PizzaImpl("L");
        PizzaImpl pizzaP = new PizzaImpl("P");
        PizzaImpl pizzaQ = new PizzaImpl("Q");
        pizzaP.withDelayedPizza(pizzaQ);
        pizzaL.withInstantPizza(pizzaP);
        pizzaG.withDelayedPizza(pizzaL);

        // Run create to set up the underlying graph nodes with dependent details
        IPizza rootPizza = pizzaF.create();
        Assert.assertNotNull(rootPizza);
        // Check dependencies and dependents
        //
        // ----------------------------------------------------------------------------------
        // LEVEL - 0
        // ----------------------------------------------------------------------------------
        //
        // Level 0 - "M"
        Assert.assertEquals(pizzaM.getTaskGroup().getNodes().size(), 1);
        TaskGroupEntry<TaskItem> nodeM = pizzaM.getTaskGroup().getNode(pizzaM.getKey());
        Assert.assertNotNull(nodeM);
        Assert.assertEquals(nodeM.dependencyKeys().size(), 0);
        Assert.assertEquals(nodeM.dependentKeys().size(), 1);
        for (String dependentKey : nodeM.dependentKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaJ.getKey()));
        }
        // Level 0 - "N"
        Assert.assertEquals(pizzaN.getTaskGroup().getNodes().size(), 1);
        TaskGroupEntry<TaskItem> nodeN = pizzaN.getTaskGroup().getNode(pizzaN.getKey());
        Assert.assertNotNull(nodeN);
        Assert.assertEquals(nodeN.dependencyKeys().size(), 0);
        Assert.assertEquals(nodeN.dependentKeys().size(), 1);
        for (String dependentKey : nodeN.dependentKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaJ.getKey()));
        }
        // Level 0 - "K"
        Assert.assertEquals(pizzaK.getTaskGroup().getNodes().size(), 1);
        TaskGroupEntry<TaskItem> nodeK = pizzaK.getTaskGroup().getNode(pizzaK.getKey());
        Assert.assertNotNull(nodeK);
        Assert.assertEquals(nodeK.dependencyKeys().size(), 0);
        Assert.assertEquals(nodeK.dependentKeys().size(), 1);
        for (String dependentKey : nodeK.dependentKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaA.getKey()));
        }
        // Level 0 - "I"
        Assert.assertEquals(pizzaI.getTaskGroup().getNodes().size(), 1);
        TaskGroupEntry<TaskItem> nodeI = pizzaI.getTaskGroup().getNode(pizzaI.getKey());
        Assert.assertNotNull(nodeI);
        Assert.assertEquals(nodeI.dependencyKeys().size(), 0);
        Assert.assertEquals(nodeI.dependentKeys().size(), 1);
        for (String dependentKey : nodeI.dependentKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaH.getKey()));
        }
        // Level 0 - "Q"
        Assert.assertEquals(pizzaQ.getTaskGroup().getNodes().size(), 1);
        TaskGroupEntry<TaskItem> nodeQ = pizzaQ.getTaskGroup().getNode(pizzaQ.getKey());
        Assert.assertNotNull(nodeQ);
        Assert.assertEquals(nodeQ.dependencyKeys().size(), 0);
        Assert.assertEquals(nodeQ.dependentKeys().size(), 1);
        for (String dependentKey : nodeQ.dependentKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaP.getKey()));
        }
        //
        // ----------------------------------------------------------------------------------
        // LEVEL - 1
        // ----------------------------------------------------------------------------------
        //
        // Level 1 - "H"
        Assert.assertEquals(pizzaH.getTaskGroup().getNodes().size(), 2);
        Assert.assertNotNull(pizzaH.getTaskGroup().getNode(pizzaI.getKey()));
        TaskGroupEntry<TaskItem> nodeH = pizzaH.getTaskGroup().getNode(pizzaH.getKey());
        Assert.assertNotNull(nodeH);
        Assert.assertEquals(nodeH.dependencyKeys().size(), 1);
        for (String dependentKey : nodeH.dependencyKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaI.getKey()));
        }
        Assert.assertEquals(nodeH.dependentKeys().size(), 1);
        for (String dependentKey : nodeH.dependentKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaF.getKey()));
        }
        // Level 1 - "J"
        Assert.assertEquals(pizzaJ.getTaskGroup().getNodes().size(), 3);
        Assert.assertNotNull(pizzaJ.getTaskGroup().getNode(pizzaM.getKey()));
        Assert.assertNotNull(pizzaJ.getTaskGroup().getNode(pizzaN.getKey()));
        TaskGroupEntry<TaskItem> nodeJ = pizzaJ.getTaskGroup().getNode(pizzaJ.getKey());
        Assert.assertNotNull(nodeJ);
        Assert.assertEquals(nodeJ.dependencyKeys().size(), 2);
        for (String dependentKey : nodeJ.dependencyKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaM.getKey())
                    || dependentKey.equalsIgnoreCase(pizzaN.getKey()));
        }
        Assert.assertEquals(nodeJ.dependentKeys().size(), 1);
        for (String dependentKey : nodeJ.dependentKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaA.getKey()));
        }
        // Level 1 - "P"
        Assert.assertEquals(pizzaP.getTaskGroup().getNodes().size(), 2);
        Assert.assertNotNull(pizzaP.getTaskGroup().getNode(pizzaQ.getKey()));
        TaskGroupEntry<TaskItem> nodeP = pizzaP.getTaskGroup().getNode(pizzaP.getKey());
        Assert.assertNotNull(nodeP);
        Assert.assertEquals(nodeP.dependencyKeys().size(), 1);
        for (String dependentKey : nodeP.dependencyKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaQ.getKey()));
        }
        Assert.assertEquals(nodeP.dependentKeys().size(), 1);
        for (String dependentKey : nodeP.dependentKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaL.getKey()));
        }
        // ----------------------------------------------------------------------------------
        // LEVEL - 2
        // ----------------------------------------------------------------------------------
        //
        // Level 1 - "L"
        Assert.assertEquals(pizzaL.getTaskGroup().getNodes().size(), 3);
        Assert.assertNotNull(pizzaL.getTaskGroup().getNode(pizzaQ.getKey()));
        Assert.assertNotNull(pizzaL.getTaskGroup().getNode(pizzaP.getKey()));
        TaskGroupEntry<TaskItem> nodeL = pizzaL.getTaskGroup().getNode(pizzaL.getKey());
        Assert.assertNotNull(nodeL);
        Assert.assertEquals(nodeL.dependencyKeys().size(), 1);
        for (String dependentKey : nodeL.dependencyKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaP.getKey()));
        }
        Assert.assertEquals(nodeL.dependentKeys().size(), 1);
        for (String dependentKey : nodeL.dependentKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaG.getKey()));
        }
        // Level 2 - "A"
        Assert.assertEquals(pizzaA.getTaskGroup().getNodes().size(), 5);
        Assert.assertNotNull(pizzaA.getTaskGroup().getNode(pizzaM.getKey()));
        Assert.assertNotNull(pizzaA.getTaskGroup().getNode(pizzaN.getKey()));
        Assert.assertNotNull(pizzaA.getTaskGroup().getNode(pizzaJ.getKey()));
        Assert.assertNotNull(pizzaA.getTaskGroup().getNode(pizzaK.getKey()));
        TaskGroupEntry<TaskItem> nodeA = pizzaA.getTaskGroup().getNode(pizzaA.getKey());
        Assert.assertNotNull(nodeA);
        Assert.assertEquals(nodeA.dependencyKeys().size(), 2);
        for (String dependentKey : nodeA.dependencyKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaJ.getKey())
                    || dependentKey.equalsIgnoreCase(pizzaK.getKey()));
        }
        Assert.assertEquals(nodeA.dependentKeys().size(), 2);
        for (String dependentKey : nodeA.dependentKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaB.getKey())
                    || dependentKey.equalsIgnoreCase(pizzaC.getKey()));
        }
        // ----------------------------------------------------------------------------------
        // LEVEL - 3
        // ----------------------------------------------------------------------------------
        //
        // Level 3 - "B"
        Assert.assertEquals(pizzaB.getTaskGroup().getNodes().size(), 6);
        Assert.assertNotNull(pizzaB.getTaskGroup().getNode(pizzaM.getKey()));
        Assert.assertNotNull(pizzaB.getTaskGroup().getNode(pizzaN.getKey()));
        Assert.assertNotNull(pizzaB.getTaskGroup().getNode(pizzaA.getKey()));
        Assert.assertNotNull(pizzaB.getTaskGroup().getNode(pizzaK.getKey()));
        Assert.assertNotNull(pizzaB.getTaskGroup().getNode(pizzaJ.getKey()));
        TaskGroupEntry<TaskItem> nodeB = pizzaB.getTaskGroup().getNode(pizzaB.getKey());
        Assert.assertNotNull(nodeB);
        Assert.assertEquals(nodeB.dependencyKeys().size(), 1);
        for (String dependentKey : nodeB.dependencyKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaA.getKey()));
        }
        Assert.assertEquals(nodeB.dependentKeys().size(), 2);
        for (String dependentKey : nodeB.dependentKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaD.getKey())
                    || dependentKey.equalsIgnoreCase(pizzaE.getKey()));
        }
        // ----------------------------------------------------------------------------------
        // LEVEL - 4
        // ----------------------------------------------------------------------------------
        //
        // Level 4 - "D"
        Assert.assertEquals(pizzaD.getTaskGroup().getNodes().size(), 7);
        Assert.assertNotNull(pizzaD.getTaskGroup().getNode(pizzaA.getKey()));
        Assert.assertNotNull(pizzaD.getTaskGroup().getNode(pizzaB.getKey()));
        Assert.assertNotNull(pizzaD.getTaskGroup().getNode(pizzaJ.getKey()));
        Assert.assertNotNull(pizzaD.getTaskGroup().getNode(pizzaM.getKey()));
        Assert.assertNotNull(pizzaD.getTaskGroup().getNode(pizzaN.getKey()));
        Assert.assertNotNull(pizzaD.getTaskGroup().getNode(pizzaK.getKey()));
        TaskGroupEntry<TaskItem> nodeD = pizzaD.getTaskGroup().getNode(pizzaD.getKey());
        Assert.assertNotNull(nodeD);
        Assert.assertEquals(nodeD.dependencyKeys().size(), 1);
        for (String dependentKey : nodeD.dependencyKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaB.getKey()));
        }
        Assert.assertEquals(nodeD.dependentKeys().size(), 1);
        for (String dependentKey : nodeD.dependentKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaF.getKey()));
        }

        // Level 4 - "G"
        Assert.assertEquals(pizzaG.getTaskGroup().getNodes().size(), 10);
        Assert.assertNotNull(pizzaG.getTaskGroup().getNode(pizzaC.getKey()));
        Assert.assertNotNull(pizzaG.getTaskGroup().getNode(pizzaQ.getKey()));
        Assert.assertNotNull(pizzaG.getTaskGroup().getNode(pizzaL.getKey()));
        Assert.assertNotNull(pizzaG.getTaskGroup().getNode(pizzaP.getKey()));
        Assert.assertNotNull(pizzaG.getTaskGroup().getNode(pizzaA.getKey()));
        Assert.assertNotNull(pizzaG.getTaskGroup().getNode(pizzaJ.getKey()));
        Assert.assertNotNull(pizzaG.getTaskGroup().getNode(pizzaM.getKey()));
        Assert.assertNotNull(pizzaG.getTaskGroup().getNode(pizzaN.getKey()));
        Assert.assertNotNull(pizzaG.getTaskGroup().getNode(pizzaK.getKey()));
        TaskGroupEntry<TaskItem> nodeG = pizzaG.getTaskGroup().getNode(pizzaG.getKey());
        Assert.assertNotNull(nodeG);
        Assert.assertEquals(nodeG.dependencyKeys().size(), 2);
        for (String dependentKey : nodeG.dependencyKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaC.getKey())
                    || dependentKey.equalsIgnoreCase(pizzaL.getKey()));
        }
        Assert.assertEquals(nodeG.dependentKeys().size(), 1);
        for (String dependentKey : nodeG.dependentKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaE.getKey()));
        }
        // ----------------------------------------------------------------------------------
        // LEVEL - 5
        // ----------------------------------------------------------------------------------
        //
        // Level 5 - "E"
        Assert.assertEquals(pizzaE.getTaskGroup().getNodes().size(), 12);
        Assert.assertNotNull(pizzaE.getTaskGroup().getNode(pizzaG.getKey()));
        Assert.assertNotNull(pizzaE.getTaskGroup().getNode(pizzaQ.getKey()));
        Assert.assertNotNull(pizzaE.getTaskGroup().getNode(pizzaB.getKey()));
        Assert.assertNotNull(pizzaE.getTaskGroup().getNode(pizzaC.getKey()));
        Assert.assertNotNull(pizzaE.getTaskGroup().getNode(pizzaA.getKey()));
        Assert.assertNotNull(pizzaE.getTaskGroup().getNode(pizzaJ.getKey()));
        Assert.assertNotNull(pizzaE.getTaskGroup().getNode(pizzaL.getKey()));
        Assert.assertNotNull(pizzaE.getTaskGroup().getNode(pizzaM.getKey()));
        Assert.assertNotNull(pizzaE.getTaskGroup().getNode(pizzaN.getKey()));
        Assert.assertNotNull(pizzaE.getTaskGroup().getNode(pizzaK.getKey()));
        Assert.assertNotNull(pizzaE.getTaskGroup().getNode(pizzaP.getKey()));
        TaskGroupEntry<TaskItem> nodeE = pizzaE.getTaskGroup().getNode(pizzaE.getKey());
        Assert.assertNotNull(nodeE);
        Assert.assertEquals(nodeE.dependencyKeys().size(), 2);
        for (String dependentKey : nodeE.dependencyKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaB.getKey())
                    || dependentKey.equalsIgnoreCase(pizzaG.getKey()));
        }
        Assert.assertEquals(nodeE.dependentKeys().size(), 1);
        for (String dependentKey : nodeE.dependentKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaF.getKey()));
        }
        // ----------------------------------------------------------------------------------
        // LEVEL - 6
        // ----------------------------------------------------------------------------------
        //
        // Level 6 - "F"
        Assert.assertEquals(pizzaF.getTaskGroup().getNodes().size(), 16);
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaA.getKey()));
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaB.getKey()));
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaC.getKey()));
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaD.getKey()));
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaE.getKey()));
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaG.getKey()));
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaH.getKey()));
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaI.getKey()));
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaJ.getKey()));
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaK.getKey()));
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaL.getKey()));
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaM.getKey()));
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaN.getKey()));
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaP.getKey()));
        Assert.assertNotNull(pizzaF.getTaskGroup().getNode(pizzaQ.getKey()));
        TaskGroupEntry<TaskItem> nodeF = pizzaF.getTaskGroup().getNode(pizzaF.getKey());
        Assert.assertNotNull(nodeF);
        Assert.assertEquals(nodeF.dependencyKeys().size(), 3);
        for (String dependentKey : nodeF.dependencyKeys()) {
            Assert.assertTrue(dependentKey.equalsIgnoreCase(pizzaD.getKey())
                    || dependentKey.equalsIgnoreCase(pizzaE.getKey())
                    || dependentKey.equalsIgnoreCase(pizzaH.getKey()));
        }
        Assert.assertEquals(nodeF.dependentKeys().size(), 0);
    }
}
