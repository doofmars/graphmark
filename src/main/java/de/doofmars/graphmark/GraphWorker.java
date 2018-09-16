package de.doofmars.graphmark;

import org.janusgraph.core.EdgeLabel;
import org.janusgraph.core.JanusGraph;
import org.janusgraph.core.JanusGraphEdge;
import org.janusgraph.core.JanusGraphFactory;
import org.janusgraph.core.JanusGraphVertex;
import org.janusgraph.core.Multiplicity;
import org.janusgraph.core.PropertyKey;
import org.janusgraph.core.VertexLabel;
import org.janusgraph.core.schema.JanusGraphManagement;
import org.janusgraph.diskstorage.BackendException;

public class GraphWorker {

	private static final String VERTEX_PROPERTY_NAME = "name";
	private static final String VERTEX_LABEL_SINK = "sink";
	private static final String VERTEX_LABEL_SOURCE = "source";
	private static final String EDGE_LABEL = "partOf";
	private static final String EDGE_PROPERTY_CRITERIA = "criteria";

	public void initReferenceSchema() {
		JanusGraph graph = JanusGraphFactory.open("localhost.properties");
		JanusGraphManagement mgmt = graph.openManagement();
		// source
		VertexLabel source = mgmt.makeVertexLabel(VERTEX_LABEL_SOURCE).make();
		// sink
		VertexLabel sink = mgmt.makeVertexLabel(VERTEX_LABEL_SINK).make();
		PropertyKey sinkName = mgmt.makePropertyKey(VERTEX_PROPERTY_NAME).dataType(String.class).make();
		mgmt.addProperties(sink, sinkName);
		// Edge
		EdgeLabel edge = mgmt.makeEdgeLabel(EDGE_LABEL).multiplicity(Multiplicity.ONE2MANY).make();
		PropertyKey criteriaPoroperty = mgmt.makePropertyKey(EDGE_PROPERTY_CRITERIA).dataType(Integer.class).make();
		mgmt.addProperties(edge, criteriaPoroperty);
		// connection
		mgmt.addConnection(edge, source, sink);

		mgmt.commit();
		graph.close();
	}

	public void fillReferenceGraph() {
		JanusGraph graph = JanusGraphFactory.open("localhost.properties");
		JanusGraphVertex vSource = graph.addVertex(VERTEX_LABEL_SOURCE);
		JanusGraphVertex vSink = graph.addVertex(VERTEX_LABEL_SINK);
		vSink.property(VERTEX_PROPERTY_NAME, "v1");
		JanusGraphEdge edge = vSource.addEdge(EDGE_LABEL, vSink);
		edge.property(EDGE_PROPERTY_CRITERIA, 1);
		graph.close();
	}
	
	public void initMediatorSchema() {

	}

	public void dropGraph() {
		try (JanusGraph graph = JanusGraphFactory.open("localhost.properties")){
			JanusGraphFactory.drop(graph);
			// wait until cluster has balanced
			try {
				Thread.sleep(10 * 1000);
			} catch (InterruptedException iEx) {
				// nothing to do...
			}
		} catch (BackendException e) {
			e.printStackTrace();
		}
	}

}
