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
package org.ow2.proactive.catalog.service.dependencygraph;

import org.apache.log4j.Logger;

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;


/**
 * @author ActiveEon Team
 * @since 2019-02-25
 */
public class DependencyGraphHolder {

    private static final Logger logger = Logger.getLogger("DependencyGraphHolder");

    private DependencyGraphHolder() {

    }

    private static final DependencyGraphHolder INSTANCE = new DependencyGraphHolder();

    private final MutableGraph<GraphNode> dependencyGraph = GraphBuilder.directed().build();


    public static DependencyGraphHolder getInstance() {
        return INSTANCE;
    }

    public boolean addNode(GraphNode graphNode) {
        logger.info("A new graph node " + graphNode.toString() + " is added to the dependency matrix");
        return dependencyGraph.addNode(graphNode);
    }

    public boolean addDependsOnAndCalledByEdges(GraphNode graphNode1, GraphNode graphNode2) {
        logger.info("A new dependsOn edge and a new calledBy edge are added to the dependency matrix");
        return dependencyGraph.putEdge(graphNode1, graphNode2) && dependencyGraph.putEdge(graphNode2, graphNode1);

    }

    public int order() {
        return dependencyGraph.nodes().size();
    }

    public int size() {
        return dependencyGraph.edges().size();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("dependencyGraph {").append(dependencyGraph);
        sb.append('}');
        return sb.toString();
    }
}
