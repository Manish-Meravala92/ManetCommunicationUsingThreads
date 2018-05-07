package manet;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class RoutingThread implements Runnable {
	ConcurrentLinkedDeque<ArrayList<String>> pathQueue;
	public ArrayList<String> alSelectedPath;
	public static int waiting = 0;
	public CountDownLatch countDownLatch;
	LinkedHashMap<Character, ArrayList<Node>> lhmNodeData;

	public static ArrayList<ArrayList<String>> alCompleteRoutes = new ArrayList<ArrayList<String>>();;

	boolean bIsThreadEnabled = false;

	public static Semaphore semaphore = new Semaphore(1);
	public static Semaphore semaphore1 = new Semaphore(1);

	public RoutingThread(CountDownLatch countDownLatch, LinkedHashMap<Character, ArrayList<Node>> lhmNodeData,
			ConcurrentLinkedDeque<ArrayList<String>> stack) {
		alSelectedPath = null;
		this.countDownLatch = countDownLatch;
		this.lhmNodeData = lhmNodeData;
		this.pathQueue = stack;
		bIsThreadEnabled = true;
	}

	public RoutingThread(LinkedHashMap<Character, ArrayList<Node>> lhmNodeData,
			ConcurrentLinkedDeque<ArrayList<String>> pathQueue) {
		alSelectedPath = null;
		this.lhmNodeData = lhmNodeData;
		this.pathQueue = pathQueue;
	}

	@Override
	public void run() {
		try {
			routingPath();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.countDownLatch.countDown();
		}
	}

	public void routingPath() {
		ArrayList<String> alPath = null;
		try {
			while (true) {

				synchronized (pathQueue) {
					while (pathQueue.isEmpty()) {
						if (bIsThreadEnabled) {
							waiting++;
							if (waiting == ConcurrentRouting.Thread_Count) {
								pathQueue.notify();
								return;
							}
							pathQueue.wait();
							waiting--;
						} else {
							return;
						}
					}
					alPath = pathQueue.pop();
				}

				if (alPath.contains("T")) {
					if (alSelectedPath == null
							|| Integer.parseInt(alPath.get(0)) < Integer.parseInt(alSelectedPath.get(0))) {
						alSelectedPath = alPath;
						alCompleteRoutes.add(alSelectedPath);
					}
				} else {
					char currentNode = alPath.get(alPath.size() - 1).charAt(0);
					ArrayList<Node> alNodes = lhmNodeData.get(currentNode);

					if (alNodes != null) {
						for (int b = 0; b < alNodes.size(); b++) {

							Node node = alNodes.get(b);
							char neighbourNode = node.getNeighbourNode();
							int distance = node.getDistance();

							if (distance > 0) {
								ArrayList<String> alNewPath = new ArrayList<String>(alPath);
								alNewPath.add(neighbourNode + "");
								alNewPath.set(0, String.valueOf(Integer.parseInt(alNewPath.get(0)) + distance));
								synchronized (pathQueue) {
									pathQueue.push(alNewPath);
									pathQueue.notify();
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<String> getShortestPath() {
		ArrayList<String> best_tour = null;
		for (int i = 0; i < alCompleteRoutes.size(); i++) {

			ArrayList<String> bestTour = alCompleteRoutes.get(i);
			if (i != 0) {
				if (Integer.parseInt(bestTour.get(0)) < Integer.parseInt(best_tour.get(0))) {
					best_tour = bestTour;
				}
			} else {
				best_tour = bestTour;
			}
		}

		return best_tour;
	}

	public static ConcurrentLinkedDeque<ArrayList<String>> createQueue() {
		ConcurrentLinkedDeque<ArrayList<String>> pathQueue = new ConcurrentLinkedDeque<ArrayList<String>>();
		ArrayList<String> alPath = new ArrayList<String>();
		alPath.add("0");
		alPath.add("A");
		pathQueue.push(alPath);

		return pathQueue;
	}

}
