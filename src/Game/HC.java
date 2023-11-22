package Game;

import java.util.*;

public class HC {

    public static void searchHillClimbing(State initialState, int maxDepth) {
        State currentState = initialState;
        int currentDepth = 0;

        while (currentDepth < maxDepth) {
            System.out.println("Depth: " + currentDepth);
            System.out.println("Current state: " + currentState);
            System.out.println("Heuristic value: " + calculateHeuristic(currentState));

            Set<State> nextStates = State.getNextState(currentState);
            State bestState = null;
            int bestHeuristic = Integer.MIN_VALUE;

            for (State nextState : nextStates) {
                int heuristic = calculateHeuristic(nextState);
                if (heuristic > bestHeuristic) {
                    bestState = nextState;
                    bestHeuristic = heuristic;
                }
            }

            if (bestState == null) {
                System.out.println("No better state found");
                return;
            }

            if (Rules.isWon(bestState)) {
                System.out.println("Winning state found: " + bestState);
                printPath(bestState);
                return;
            }

            currentState = bestState;
            currentDepth++;
        }

        System.out.println("No winning state found within the specified depth");
    }

    private static int calculateHeuristic(State state) {
        int heuristicValue = 0;
        for (int i = 0; i < state.grid.rows; i++) {
            for (int j = 0; j < state.grid.rows; j++) {
                if (state.grid.grid[i][j] != 0) {
                    heuristicValue += Math.abs(state.grid.grid[i][j] - 5);
                }
            }
        }
        return heuristicValue;
    }

    private static void printPath(State state) {
        LinkedList<State> stack = new LinkedList<>();
        State currentState = state;
        while (currentState != null) {
            stack.push(currentState);
            currentState = State.getParentState(currentState);
        }
        System.out.println("Path to the winning state:");
        while (!stack.isEmpty()) {
            State step = stack.pop();
            System.out.println(step);
        }
    }
}
