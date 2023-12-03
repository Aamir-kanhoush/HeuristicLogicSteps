package Game;

import java.util.*;

public class DFS {
    public static Stack<State> stack;


    public static void searchDFS(State initialState, int maxDepth) {
        stack = new Stack<>();
        Set<State> visited = new HashSet<>();
        stack.push(initialState);
        visited.add(initialState);

        while (!stack.isEmpty()) {
            State currentState = stack.pop();
            currentState.grid.printGrid();
            currentState.grid.printGoal();
            currentState.grid.printLocations();
            System.out.println("Depth: "+stack.size());
            if (Rules.isWon(currentState)) {
                printSteps(currentState);
                return;
            }
            if (stack.size() >= maxDepth) {
                System.out.println("No winning state found in this depth");
                return;
            }

            Set<State> nextStates = State.getNextState(currentState);
            for (State nextState : nextStates) {
                if (!visited.contains(nextState)) {
                    stack.push(nextState);
                    visited.add(nextState);
                }
            }
        }
    }

    public static void printSteps(State finalState) {
        System.out.println("winning state found:");
        Stack<State> path = new Stack<>();
        State currentState = finalState;
        while (currentState != null) {
            path.push(currentState);
            currentState = State.getParentState(currentState);
        }
        while (!path.isEmpty()) {
            System.out.println(path.pop());
        }
    }
}
