package manet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class LinearRouting {

	/**
	 * 1)check if same source exists in the path then i)select the shortest path ii)
	 * 3)
	 */
	public void doLinearRouting(LinkedHashMap<Character, ArrayList<Node>> lhmNodeData) throws Exception {

		ConcurrentLinkedDeque<ArrayList<String>> pathQueue = RoutingThread.createQueue();

		RoutingThread.alCompleteRoutes = new ArrayList<ArrayList<String>>();
		RoutingThread objRouteThread = new RoutingThread(lhmNodeData, pathQueue);

		objRouteThread.routingPath();

		System.out.println("Shortest Path----" + RoutingThread.getShortestPath());
	}

}
