package de.doofmars.graphmark;

/**
 * Unit test for simple App.
 */
public class GraphWorkerTest {
	
	@org.junit.Test
	public void testReference() throws Exception {
		GraphWorker gw = new GraphWorker();
		gw.dropGraph();
		gw.initReferenceSchema();
		gw.fillReferenceGraph();
	}
}
