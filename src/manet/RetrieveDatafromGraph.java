package manet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class RetrieveDatafromGraph {

	public LinkedHashMap<Character, ArrayList<Node>> retrieveNetworkData() {
		BufferedReader reader = null;
		LinkedHashMap<Character, ArrayList<Node>> lhmNodeData = new LinkedHashMap<Character, ArrayList<Node>>();
		try {
			reader = new BufferedReader(
					new FileReader("src/manet/manet_route.txt"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				ArrayList<Node> alConnectingNodes = new ArrayList<Node>();
				String[] arrNoOfNodes = line.split(",");
				for (int iNode = 1; iNode < arrNoOfNodes.length; iNode++) {
					Node objNode = new Node();
					objNode.setCurrentNode(arrNoOfNodes[0].charAt(0));

					String[] arrNodeDate = arrNoOfNodes[iNode].split(" ");

					objNode.setNeighbourNode(arrNodeDate[0].charAt(0));
					objNode.setDistance(Integer.parseInt(arrNodeDate[1]));
					alConnectingNodes.add(objNode);
				}

				lhmNodeData.put(arrNoOfNodes[0].charAt(0), alConnectingNodes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return lhmNodeData;
	}
}
