# Pathfinding

Searching for the shortest path from a starting location to a goal location on a square grid using Breadth-first search, Iterative deepening search, and A* search. However, this grid will have some impassable locations and varying terrain costs that must be accounted for. 

Reads in a map file that specifies the map in the following format.
First line: dimensions of the map
Second line: coordinates of the starting location (row, column)
Third line: coordinates of the goal location
After that is the specification of the map, which consists of digits between 0 and 5, separated by spaces. These numbers represent the movement cost for moving to a given space on the grid. The number 0 is a special case and is considered impassable terrain.  The numbers 1-5 are the number of turns required to move to the given square, with 1 being the lowest cost and 5 being the highest.  There is no cost for moving to the starting location. 

The following is an example of the map format:
5 7
1 2
4 3
2 4 2 1 4 5 2
0 1 2 3 5 3 1
2 0 4 4 1 2 4
2 5 5 3 2 0 1
4 3 3 2 1 0 1

Includes repeat-state checking implementation, so it does not revisit states it has already expanded. A* search uses the Manhattan distance as the heuristic.

There's a 3min time cutoff. After the 3 mins, the following information will be printed:

-The cost of the path found
-The number of nodes expanded 
-The maximum number of nodes held in memory
-The runtime of the algorithm in milliseconds
-The path as a sequence of coordinates (row, col), (row col), … , (row, col)

