package Game;

import java.util.*;
public class UCS {
    private static Map<State, State> cameFrom = new HashMap<>();

    public static void searchUCS(State initialState, int maxDepth) {
        PriorityQueue<State> queue = new PriorityQueue<>(Comparator.comparingInt(State::getTotalCost));

        Map<State, Integer> costSoFar = new HashMap<>();

        queue.add(initialState);
        costSoFar.put(initialState, 0);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();

            if (queue.size() > maxDepth) {
                System.out.println("No winning state found");
                return;
            }
            if (Rules.isWon(currentState)) {
                System.out.println("Winning state found: " + currentState);
                printSteps(currentState, costSoFar);
                return;
            }
            Set<State> nextStates = State.getNextState(currentState);
            for (State nextState : nextStates) {
                System.out.println("costSoFar.get(currentState)"+costSoFar.get(currentState));
                System.out.println("nextState.getTotalCost()"+nextState.getTotalCost());
                int newCost = costSoFar.get(currentState) + nextState.getTotalCost();
                System.out.println("newCost"+newCost);
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
        state.grid.printGrid();
        LinkedList<State> stack = new LinkedList<>();
        State currentState = state;
        while (currentState != null) {
            stack.push(currentState);
            currentState = cameFrom.get(currentState);
        }
        System.out.println("Steps to reach the winning state:");
        while (!stack.isEmpty()) {
            State step = stack.pop();
            System.out.println(step);
            System.out.println("Cost so far: " + costSoFar.get(step));
        }
    }

}
