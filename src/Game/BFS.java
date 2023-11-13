package Game;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BFS {
    public static void searchBFS(State initialState, int maxDepth) {
        Queue<State> queue = new LinkedList<>();
        queue.add(initialState);
        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            currentState.grid.printLocations();
            currentState.grid.printGrid();
            if (queue.size() > maxDepth) {
                System.out.println("No winning state found");
                return;
            }
            if (Rules.isWon(currentState)) {
                System.out.println("Winning state found: " + currentState);
                printSteps(currentState);
                return;
            }
            Set<State> nextStates = State.getNextState(currentState);
            for (State nextState : nextStates) {
                if (!queue.contains(nextState)) {
                    queue.add(nextState);
                }
            }
        }
        System.out.println("No winning state found within the specified depth");
    }

    private static void printSteps(State state) {
        LinkedList<State> stack = new LinkedList<>();
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
