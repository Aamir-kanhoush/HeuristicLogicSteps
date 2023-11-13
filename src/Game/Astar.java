package Game;
// the original game doesn't take this algo so this is just some help
import java.util.*;
public class Astar {public static void searchAStar(State initialState, int maxDepth) {
    PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(s -> s.grid.positions.stream().mapToInt(Coordinate::getCost).sum() + heuristic(s)));
    Map<State, Integer> costSoFar = new HashMap<>();
    Map<State, State> cameFrom = new HashMap<>();

    queue.add(initialState);
    costSoFar.put(initialState, 0);

    while (!queue.isEmpty()) {
        State currentState = queue.poll();
        System.out.println("Cost: " + costSoFar.get(currentState));
        System.out.println("Heuristic: " + heuristic(currentState));
        currentState.grid.printLocations();
        currentState.grid.printGrid();

        if (queue.size() > maxDepth) {
            System.out.println("No winning state found");
            return;
        }
        if (Rules.isWon(currentState)) {
            System.out.println("Winning state found: " + currentState);
            printSteps(currentState, costSoFar, cameFrom);
            return;
        }
        Set<State> nextStates = State.getNextState(currentState);
        for (State nextState : nextStates) {
            int newCost = costSoFar.get(currentState);
            Coordinate movedCoordinate = getMovedCoordinate(currentState, nextState);
            if (movedCoordinate != null) {
                newCost += movedCoordinate.getCost()+heuristic(nextState);
            }
            if (!costSoFar.containsKey(nextState) || newCost < costSoFar.get(nextState)) {
                costSoFar.put(nextState, newCost);
                queue.add(nextState);
                cameFrom.put(nextState, currentState);
            }
        }
    }
    System.out.println("No winning state found within the specified depth");
}

    //needs edit based on how to represent closeness to goal and stuff
    public static int heuristic(State state) {
        int heuristicCost = 0;
        for (Coordinate coordinate : state.grid.positions) {
            heuristicCost += coordinate.getCost();
        }
        return heuristicCost;
    }

    private static Coordinate getMovedCoordinate(State currentState, State nextState) {
        List<Coordinate> currentCoordinates = currentState.grid.positions;
        List<Coordinate> nextCoordinates = nextState.grid.positions;

        for (Coordinate nextCoordinate : nextCoordinates) {
            if (!currentCoordinates.contains(nextCoordinate)) {
                return nextCoordinate;
            }
        }

        return null;
    }

    public static void printSteps(State finalState, Map<State, Integer> costSoFar, Map<State, State> cameFrom) {
        List<State> path = new ArrayList<>();
        State currentState = finalState;
        while (currentState != null) {
            path.add(currentState);
            currentState = cameFrom.get(currentState);
        }
        Collections.reverse(path);

        System.out.println("A* Path:");
        for (State state : path) {
            System.out.println("State: " + state);
            System.out.println("Cost: " + costSoFar.get(state));
            System.out.println("Heuristic: " + heuristic(state));
            System.out.println("--------------------");
        }
    }

}