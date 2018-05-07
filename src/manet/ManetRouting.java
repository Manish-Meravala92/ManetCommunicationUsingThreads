package manet;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ManetRouting {

	public static void main(String[] args) {
		new ManetRouting().init();
	}

	public void init() {
		try {
			RetrieveDatafromGraph datafromGraph = new RetrieveDatafromGraph();
			LinkedHashMap<Character, ArrayList<Node>> lhmNodeData = datafromGraph.retrieveNetworkData();

			long startLinearTime = System.currentTimeMillis();
			new LinearRouting().doLinearRouting(lhmNodeData);
			System.out.println("end time --- sequential---" + (System.currentTimeMillis() - startLinearTime) + " ms");

			long startConcurrentTime = System.currentTimeMillis();
			new ConcurrentRouting().doConcurrentRouting(lhmNodeData);
			System.out.println("end time --- parallel---" + (System.currentTimeMillis() - startConcurrentTime) + " ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
