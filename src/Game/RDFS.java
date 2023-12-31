package Game;

import java.util.*;

public class RDFS {
    private static Stack<State> visitedStates = new Stack<>();
    private static boolean solutionFound = false;

    public static void searchRDFS(State initialState, int maxDepth) {
        visitedStates.clear();
        solutionFound = false;
        searchDFSRecursive(initialState, maxDepth, 0);
    }

    private static void searchDFSRecursive(State currentState, int maxDepth, int currentDepth) {
        if (currentDepth > maxDepth || solutionFound) {
            return;
        }

        currentState.grid.printLocations();
        currentState.grid.printGrid();

        if (Rules.isWon(currentState)) {
            System.out.println("Winning state found!");
            solutionFound = true;
            printSteps(currentState);
            return;
        }

        visitedStates.add(currentState);
        Set<State> nextStates = State.getNextState(currentState);

        for (State nextState : nextStates) {
            if (!visitedStates.contains(nextState)) {
                nextState.parentState = currentState;
                System.out.println("Depth " + (currentDepth + 1));
                searchDFSRecursive(nextState, maxDepth, currentDepth + 1);
            }
        }
    }

    private static void printSteps(State state) {
        Stack<State> stack = new Stack<>();
        State currentState = state;

        while (currentState != null) {
            stack.push(currentState);
            currentState = currentState.parentState;
        }

        System.out.println("Steps to reach the winning state:");
        while (!stack.isEmpty()) {

            System.out.println(stack.pop());
        }
    }
}