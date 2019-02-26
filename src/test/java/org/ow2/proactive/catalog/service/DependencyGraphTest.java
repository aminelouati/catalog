/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package org.ow2.proactive.catalog.service;

import static com.google.common.truth.Truth.assertThat;
import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;

import org.junit.Test;
import org.ow2.proactive.catalog.service.dependencygraph.DependencyGraphHolder;
import org.ow2.proactive.catalog.service.dependencygraph.GraphNode;

/**
 * @author ActiveEon Team
 * @since 2019-02-25
 */

public class DependencyGraphTest {

     DependencyGraphHolder dependencyGraphHolder = DependencyGraphHolder.getInstance();

    @Test
    public void testAddNodeOK(){
        GraphNode graphNode1 = new GraphNode("bucket1", "wf1");
        GraphNode graphNode2 = new GraphNode("bucket2", "wf2");
        // Add two nodes
        dependencyGraphHolder.addNode(graphNode1);
        dependencyGraphHolder.addNode(graphNode2);
        assertThat(dependencyGraphHolder.order()).isEqualTo(2);
        GraphNode graphNode11 = new GraphNode("bucket1", "wf1");
        // Check that an existing node cannot be added to the dependency graph
        dependencyGraphHolder.addNode(graphNode11);
        assertThat(dependencyGraphHolder.order()).isEqualTo(2);
        // Add a dependOn edge between graphNode1 and graphNode2 which adds systematically a calledBy edge between graphNode2 and graphNode1
        assertTrue(dependencyGraphHolder.addDependsOnAndCalledByEdges(graphNode1, graphNode2));

        // Add 2 more nodes and 2 more depends on dependencies
        GraphNode graphNode3 = new GraphNode("bucket3", "wf3");
        GraphNode graphNode4 = new GraphNode("bucket4", "wf4");
        dependencyGraphHolder.addNode(graphNode3);
        dependencyGraphHolder.addNode(graphNode4);
        assertTrue(dependencyGraphHolder.addDependsOnAndCalledByEdges(graphNode1, graphNode3));
        assertTrue(dependencyGraphHolder.addDependsOnAndCalledByEdges(graphNode3, graphNode4));

        assertThat(dependencyGraphHolder.size()).isEqualTo(6);
        assertThat(dependencyGraphHolder.order()).isEqualTo(4);


        System.out.println(dependencyGraphHolder.toString());

    }

}
