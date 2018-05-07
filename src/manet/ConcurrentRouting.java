package manet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;

public class ConcurrentRouting {
	public static final int Thread_Count = 4;

	public void doConcurrentRouting(LinkedHashMap<Character, ArrayList<Node>> lhmNodeData) throws Exception {

		ConcurrentLinkedDeque<ArrayList<String>> pathQueue =RoutingThread.createQueue();
		RoutingThread.alCompleteRoutes = new ArrayList<ArrayList<String>>();
		Thread thread = null;
		CountDownLatch countDownLatch = new CountDownLatch(Thread_Count);
		RoutingThread objRouteThread = null;

		for (int iThreadCnt = 0; iThreadCnt < Thread_Count; iThreadCnt++) {

			objRouteThread = new RoutingThread(countDownLatch, lhmNodeData, pathQueue);
			thread = new Thread(objRouteThread, "Thread" + iThreadCnt);
			thread.start();
			System.out.println("Routing Thread " + iThreadCnt + " Started.");
		}

		countDownLatch.await();

		System.out.println("Shortest Path----" + RoutingThread.getShortestPath());

	}
}
