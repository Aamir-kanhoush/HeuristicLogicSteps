package Game;

import java.util.Set;
import java.util.Stack;

public class DFS {
    public static void searchDFS(State initialState, int maxDepth) {
        Stack<State> stack = new Stack<>();
        stack.push(initialState);
        while (!stack.isEmpty()) {

            if (stack.size() > maxDepth) { 
                System.out.println("No winning state found within the specified depth");
                return;
            }
            State currentState = stack.pop();
            currentState.grid.printLocations();
            currentState.grid.printGrid();
            if (Rules.isWon(currentState)) {
                System.out.println("Winning state found: " + currentState);
                printSteps(currentState);
                return;
            }
            Set<State> nextStates = State.getNextState(currentState);
            for (State nextState : nextStates) {
                if (!stack.contains(nextState)) {
                    stack.push(nextState);
                }
            }
        }
        System.out.println("No winning state found");
    }

    private static void printSteps(State state) {
        Stack<State> stack = new Stack<>();
        State currentState = state;
        while (currentState != null) {
            stack.push(currentState);
            currentState = State.getParentState(currentState);
        }
        System.out.println("Steps to reach the winning state:");
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }
}
