import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

/**
 * Pathfinder Class
 * @author Marisol Chacon
 *
 */
public class Pathfinder {

	//if search fails
	static boolean fail = false;
	//if node has been checked
	static boolean check = false;
	//runtime
	static long duration;
	//map scanned in
	static int[][] map;
	//cost of path
	static int pathCost;
	//max number of nodes
	static int maxNum;
	//goal node
	static Node goal;
	//starting location node
	static Node start;
	//dimensions of map
	static int[] dimension = { 0, 0 };
	//starting coordinates
	static int[] starting = { 0, 0 };
	//goal coordinates
	static int[] goalCoordinate = { 0, 0 };
	//queue of nodes, keep track of who is next 
	static Queue<Node> queue = new LinkedList();
	//keeps track of path taken
	static ArrayList<Node> path = new ArrayList();
	//keeps track of nodes that have been checked
	static ArrayList<Node> checked = new ArrayList();
	static Stack<Node> stack = new Stack<Node>();
	static PriorityQueue<Node> pQueue = new PriorityQueue<Node>(new NodeComparator());

	/**
	 * readFile method reads the file and stores all the information 
	 * for easy use in Search algorithms
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 */
	private static int[][] readFile(String file) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(file));

		//scan in dimensions
		dimension[0] = scanner.nextInt();
		dimension[1] = scanner.nextInt();

		//scan in starting location
		starting[0] = scanner.nextInt();
		starting[1] = scanner.nextInt();

		//scan in goal location
		goalCoordinate[0] = scanner.nextInt();
		goalCoordinate[1] = scanner.nextInt();

		//initialize map based on dimensions
		int[][] map = new int[dimension[0]][dimension[1]];

		//scan in map
		while (scanner.hasNextInt()) {
			for (int i = 0; i < map.length; i++) {
				for (int j = 0; j < map[i].length; j++) {
					map[i][j] = scanner.nextInt();
				}
			}
		}

		scanner.close();
		return map;
	}

	/**
	 * printInfo method is called when the searching for a path is finished whether it was
	 * a success or not. Prints information accordingly.
	 */
	private static void printInfo() {

		//if it didn't fail, add up path cost and print out the path
		if (!fail) {
			System.out.print("Path taken: ");
			for (int i = 0; i < path.size(); i++) {
				Node n = path.get(i);
				
				//if path size is 1, it means the starting location was the goal location
				if (path.size() == 1)
					pathCost = 0;
				
				else
					pathCost = pathCost + n.cost;
				System.out.print(" (" + n.coordinate[0] + ", " + n.coordinate[1] + ")");
			}
		} else {
			System.out.println("Path taken: NULL");
			pathCost = -1;
		}
		System.out.println();
		System.out.println("Total path cost: " + pathCost);
		System.out.println("Total number of nodes: " + path.size());
		System.out.println("Max number of nodes: " + maxNum);
		System.out.println("Total runtime:" + duration);
	}

	/**
	 * Breadth First Search method
	 * @param start
	 * @param goal
	 */
	private static void BFS(Node start, Node goal) {
		
		//start timer
		long cutoffStart = System.currentTimeMillis();
				
		//check to see if it started in goal location
		if (start.coordinate[0] == goal.coordinate[0] && start.coordinate[1] == goal.coordinate[1]) {
			maxNum++;
			System.out.println("Started off in the goal!");
		}

		else {
			//add to queue
			queue.add(start);
			maxNum++;
			checked.add(start);

			//if the queue is not empty, keep removing
			while (!queue.isEmpty()) {
				
				//check to see if cutoff time has been reached, if it has. return to main
				long cutoffEnd = cutoffStart + 60*1000; // 60 seconds * 1000 ms/sec
				if(System.currentTimeMillis() > cutoffEnd)
				{
					System.out.println("Ran out of time");
					fail = true;
					return;
				}
		        
				Node current = queue.remove();
				//check if node is impassable 
				if (current.cost == 0) {
					checked.add(current);
				} else {
					//add to path
					path.add(current);
					
					//check to see if it's in goal state
					if (current.coordinate[0] == goal.coordinate[0] && current.coordinate[1] == goal.coordinate[1]) {
						maxNum++;
						System.out.println("Path found!");
						return;
					}

					else {
						//check to see if neighbors have already been added to current node, if not add them
						if (!current.hasNeighbors()) {
							
							//check if neighbor can be added on top
							if (current.coordinate[0] != 0) {
								int[] c = { current.coordinate[0] - 1, current.coordinate[1] };
								int c2 = current.cost;
								Node n = new Node(c, c2);
								current.addNeighbor(n);
							}
							
							//check if neighbor can be added on 
							if (current.coordinate[1] != 0) {
								int[] c = { current.coordinate[0], current.coordinate[1] - 1 };
								int c2 = current.cost;
								Node n = new Node(c, c2);
								current.addNeighbor(n);
							}

							//check if neighbor can be added on 
							if (current.coordinate[0] != dimension[0]) {
								int[] c = { current.coordinate[0] + 1, current.coordinate[1] };
								int c2 = current.cost;
								Node n = new Node(c, c2);
								current.addNeighbor(n);
							}

							//check if neighbor can be added on 
							if (current.coordinate[1] != dimension[1]) {
								int[] c = { current.coordinate[0], current.coordinate[1] + 1 };
								int c2 = current.cost;
								Node n = new Node(c, c2);
								current.addNeighbor(n);
							}
						}
						//get node's neighbors
						ArrayList<Node> neighbors = current.getNeighbors();
						for (int i = 0; i < neighbors.size(); i++) {
							Node n = neighbors.get(i);
							
							//check to see if they've already been checked
							for (int j = 0; j < checked.size(); j++) {
								Node temp = checked.get(j);
								
								if (temp.coordinate[0] == n.coordinate[0] && temp.coordinate[1] == n.coordinate[1]) {
									check = true;
								}
							}

							//if they haven't been checked add them to queue
							if (!check) {
								queue.add(n);
								checked.add(n);
								maxNum++;
							}
							check = false;
						}
					}
				}
			}
			
			//if queue is empty, then no path was found;
			if (queue.isEmpty()) {
				System.out.println("There's no path to goal.");
				fail = true;
			}
		}
	}

	/**
	 * Iterative Deepening Search
	 * @param start
	 * @param goal
	 */
	private static void IDS(Node start, Node goal) {
		
		//start timer
		long cutoffStart = System.currentTimeMillis();
		
		//start with 0 depth
		int depth = 0;
		int maxDepth = 1;
		
		//check to see if current state
		if (start.coordinate[0] == goal.coordinate[0] && start.coordinate[1] == goal.coordinate[1]) {
			maxNum++;
			path.add(start);
			System.out.println("Started off in the goal!");
		}

		else {
			stack.add(start);
			maxNum++;
			checked.add(start);

			//if queue isn't empty, keep going through queue
			while (!stack.isEmpty()) {
				
				//check to see if it's been 3 minutes
				long cutoffEnd = cutoffStart + 60*1000; // 60 seconds * 1000 ms/sec
				if(System.currentTimeMillis() > cutoffEnd)
				{
					System.out.println("Ran out of time");
					fail = true;
					return;
				}
				
				//if the depth is not at max, keep going
				if (depth <= maxDepth) {
					Node current = stack.pop();
					
					//check if impassable
					if (current.cost == 0) {
						checked.add(current);
					} else {
						//add to path
						path.add(current);
						
						//check to see if in goal state
						if (current.coordinate[0] == goal.coordinate[0]
								&& current.coordinate[1] == goal.coordinate[1]) {
							maxNum++;
							System.out.println("Path found!");
							return;
						}

						else {
							//check to see if node has neighbors added already
							if (!current.hasNeighbors()) {

								//check if neighbor can be added on 
								if (current.coordinate[0] != 0) {
									int[] c = { current.coordinate[0] - 1, current.coordinate[1] };
									int c2 = current.cost;
									Node n = new Node(c, c2);
									current.addNeighbor(n);
								}

								//check if neighbor can be added on 
								if (current.coordinate[1] != 0) {
									int[] c = { current.coordinate[0], current.coordinate[1] - 1 };
									int c2 = current.cost;
									Node n = new Node(c, c2);
									current.addNeighbor(n);
								}

								//check if neighbor can be added on 
								if (current.coordinate[0] != dimension[0]) {
									int[] c = { current.coordinate[0] + 1, current.coordinate[1] };
									int c2 = current.cost;
									Node n = new Node(c, c2);
									current.addNeighbor(n);
								}

								//check if neighbor can be added on 
								if (current.coordinate[1] != dimension[1]) {
									int[] c = { current.coordinate[0], current.coordinate[1] + 1 };
									int c2 = current.cost;
									Node n = new Node(c, c2);
									current.addNeighbor(n);
								}
							}
							//get neighbors
							ArrayList<Node> neighbors = current.getNeighbors();
							for (int i = 0; i < neighbors.size(); i++) {
								Node n = neighbors.get(i);
								
								//check to see if they've been checked
								for (int j = 0; j < checked.size(); j++) {
									Node temp = checked.get(j);
									if (temp.coordinate[0] == n.coordinate[0]
											&& temp.coordinate[1] == n.coordinate[1]) {
										check = true;
									}
								}

								//if they haven't been checked, add to queue
								if (!check) {
									stack.add(n);
									checked.add(n);
									maxNum++;
								}
								check = false;
								//increase depth
								depth++;
							}
						}
					}
				} else {
					//gradually increasing depth
					maxDepth++;
				}
			}
			
			//if queue is empty then no path to goal was found
			if (queue.isEmpty()) {
				System.out.println("There's no path to goal.");
				fail = true;
			}
		}
	}
	
	/**
	 * Class overrides Comparator for Priority queue
	 */
	public static class NodeComparator implements Comparator<Node>{
		
		//prioritizes based on their F score
		public int compare(Node n, Node n2) {
			if(n.fScore > n2.fScore) {
				return 1;
			}
			else return 0;
		}
	}
	
	/**
	 * A* Search Algorithm
	 * @param start
	 * @param goal
	 */
	public static void AS(Node start, Node goal) {
		
		//start timer
		long cutoffStart = System.currentTimeMillis();
		
		//check to see if current state
		if (start.coordinate[0] == goal.coordinate[0] && start.coordinate[1] == goal.coordinate[1]) {
			maxNum++;
			path.add(start);
			System.out.println("Started off in the goal!");
		}
		else {
			//compute manhattan distance and f value
			start.computeManhattanDistance(goal);
			
			pQueue.add(start);
			maxNum++;
			checked.add(start);
			
			while(!pQueue.isEmpty()) {
				
				//check to see if it's been 3 minutes
				long cutoffEnd = cutoffStart + 60*1000; // 60 seconds * 1000 ms/sec
				if(System.currentTimeMillis() > cutoffEnd)
				{
					System.out.println("Ran out of time");
					fail = true;
					return;
				}
				Node current = pQueue.remove();
				
				//check if impassable
				if (current.cost == 0) {
					checked.add(current);
				} else {
					//add to path
					path.add(current);
					
					//check to see if in goal state
					if (current.coordinate[0] == goal.coordinate[0]
							&& current.coordinate[1] == goal.coordinate[1]) {
						maxNum++;
						System.out.println("Path found!");
						return;
					}

					else {
						//check to see if node has neighbors added already
						if (!current.hasNeighbors()) {

							//check if up neighbor can be added
							if (current.coordinate[0] != 0) {
								int[] c = { current.coordinate[0] - 1, current.coordinate[1] };
								int c2 = current.cost;
								Node n = new Node(c, c2);
								n.computeManhattanDistance(goal);
								current.addNeighbor(n);
							}

							//check if left neighbor can be added on 
							if (current.coordinate[1] != 0) {
								int[] c = { current.coordinate[0], current.coordinate[1] - 1 };
								int c2 = current.cost;
								Node n = new Node(c, c2);
								n.computeManhattanDistance(goal);
								current.addNeighbor(n);
							}

							//check if down neighbor can be added on 
							if (current.coordinate[0] != dimension[0]) {
								int[] c = { current.coordinate[0] + 1, current.coordinate[1] };
								int c2 = current.cost;
								Node n = new Node(c, c2);
								n.computeManhattanDistance(goal);
								current.addNeighbor(n);
							}

							//check if right neighbor can be added on 
							if (current.coordinate[1] != dimension[1]) {
								int[] c = { current.coordinate[0], current.coordinate[1] + 1 };
								int c2 = current.cost;
								Node n = new Node(c, c2);
								n.computeManhattanDistance(goal);
								current.addNeighbor(n);
							}
						}
						//get neighbors
						ArrayList<Node> neighbors = current.getNeighbors();
						for (int i = 0; i < neighbors.size(); i++) {
							Node n = neighbors.get(i);
							
							//check to see if they've been checked
							for (int j = 0; j < checked.size(); j++) {
								Node temp = checked.get(j);
								if (temp.coordinate[0] == n.coordinate[0]
										&& temp.coordinate[1] == n.coordinate[1]) {
									check = true;
								}
							}
							
							//getcostfrombeginning
							int costFromStart = current.cost + n.fScore;
							//if they haven't been checked and if new cost is less than old cost, add to queue
							if (!check && costFromStart > n.costFromStart) {
								
								//compute new cost and new f value
								n.costFromStart = costFromStart;
								n.computeManhattanDistance(goal);
								pQueue.add(n);
								checked.add(n);
								maxNum++;
							}
							check = false;
						}
					}
				}
			}
			//if queue is empty then no path to goal was found
			if (queue.isEmpty()) {
				System.out.println("There's no path to goal.");
				fail = true;
			}
		}
				
	}

	/**
	 * main method, takes in arguments.
	 * Opens file and calls search algorithm based on these arguments.
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {

		//get arguments
		String file = args[0];
		String algo = args[1];
		
		//open file
		try {
			map = readFile(file);
		} catch (FileNotFoundException e) {
			System.out.println("Unable to open file: '" + file + "'");
			return;
		}

		//initialize start and goal nodes
		start = new Node(starting, map[starting[0]][starting[1]]);
		goal = new Node(goalCoordinate, map[goalCoordinate[0]][goalCoordinate[1]]);

		//start timer
		long startTime = System.nanoTime();
		
		//run desired algorithm
		if(algo.equals("BFS")) BFS(start, goal);
		else if (algo.equals("IDS")) IDS(start, goal);
		else if (algo.equals("AS")) AS(start, goal);
		
		//end timer
		long endTime = System.nanoTime();
		
		//get time and convert to milliseconds
		duration = (endTime - startTime) / 1000000;
		
		//call method to print out all the information
		printInfo();
	}

}
