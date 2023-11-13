package Game;

import java.util.*;

public class UCS {

    public static void searchUCS(State initialState, int maxDepth) {
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(s -> s.grid.positions.stream().mapToInt(Coordinate::getCost).sum()));
        Map<State, Integer> costSoFar = new HashMap<>();
        Map<State, State> cameFrom = new HashMap<>();

        queue.add(initialState);
        costSoFar.put(initialState, 0);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            System.out.println("Cost so far:"+costSoFar.get(currentState));
            currentState.grid.printLocations();
            currentState.grid.printGrid();

            if (queue.size() > maxDepth) {
                System.out.println("No winning state found");
                return;
            }
            if (Rules.isWon(currentState)) {
                System.out.println("Winning state found: " + currentState);
                printSteps(currentState,costSoFar);
                return;
            }
            Set<State> nextStates = State.getNextState(currentState);
            for (State nextState : nextStates) {
                int newCost = costSoFar.get(currentState);
                Coordinate movedCoordinate = getMovedCoordinate(currentState, nextState);
                if (movedCoordinate != null) {
                    newCost += movedCoordinate.getCost();
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

    private static void printSteps(State state, Map<State, Integer> costSoFar) {
        LinkedList<State> stack = new LinkedList<>();
        State currentState = state;
        while (currentState != null) {
            stack.push(currentState);
            currentState = State.getParentState(currentState);
        }
        System.out.println("Steps to reach the winning state:");
        while (!stack.isEmpty()) {
            State step = stack.pop();
            System.out.println(step);
            System.out.println("Cost so far: " + costSoFar.get(step));
        }
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
}
