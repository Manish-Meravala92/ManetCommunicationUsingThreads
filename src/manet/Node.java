package manet;

import java.io.Serializable;

public class Node implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Character currentNode; // Source name
	private Character neighbourNode; // Destination name
	private int distance; // Distance between nodes


	public Character getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(Character currentNode) {
		this.currentNode = currentNode;
	}

	public Character getNeighbourNode() {
		return neighbourNode;
	}

	public void setNeighbourNode(Character neighbourNode) {
		this.neighbourNode = neighbourNode;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

}
