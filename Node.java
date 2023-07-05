import java.util.ArrayList;
import java.util.Comparator;

/**
 * Node class - contains all info needed for finding a path towards the goal
 * @author hp
 *
 */
public class Node {

	//keeps track of coordinate, cost, and neighbors
	int[] coordinate;
	int cost;
	ArrayList<Node> neighbors;
	boolean hasN;
	int fScore;
	int manhattanDistance;
	int costFromStart;
	
	/**
	 * Constructor for initialzing a Node
	 * @param coordinate
	 * @param cost
	 */
	public Node(int[] coordinate, int cost) {
		this.coordinate = coordinate;
		this.cost = cost;
		this.hasN = false;
		this.neighbors = new ArrayList();
	}
	
	/**
	 * Adds neighbors to Node
	 * @param n
	 */
	public void addNeighbor(Node n) {
		this.neighbors.add(n);
		hasN = true;
	}
	
	/**
	 * Returns a list of all the neighbors
	 * @return
	 */
	public ArrayList<Node> getNeighbors(){
		return neighbors;
	}
	
	/**
	 * Returns true if neighbors have been added
	 * @return
	 */
	public boolean hasNeighbors() {
		return hasN;
	}
	
	/**
	 * Computes Manhattan Distance based on coordinates of goal and current node coordinates
	 * @param g
	 */
	public void computeManhattanDistance(Node g) {
		int x, y;
		if(coordinate[0]> g.coordinate[0]) {
			x = coordinate[0]-g.coordinate[0];
		}
		else x = g.coordinate[0]-coordinate[0];
		
		if(coordinate[1]> g.coordinate[1]) {
			y = coordinate[1]-g.coordinate[1];
		}
		else y = g.coordinate[1]-coordinate[1];
		
		manhattanDistance = x+y;
		computeF();
	}
	
	/**
	 * Computes and updates the F Score value
	 */
	public void computeF() {
		fScore = manhattanDistance + costFromStart;
	}
}
