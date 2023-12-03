package Game;

import java.util.*;


public class Astar {
    public static void searchAstar(State initialState) {
        initialState.grid.printGrid();
        initialState.grid.printGoal();
        initialState.grid.printLocations();
        PriorityQueue<State> frontier = new PriorityQueue<>(Comparator.comparingInt(state -> heuristic(state.grid.positions.get(0), state.grid.goal) + state.getTotalCost()));
        Set<State> explored = new HashSet<>();
        frontier.add(initialState);

        while (!frontier.isEmpty()) {
            State state = frontier.poll();
            explored.add(state);

            if (Rules.isWon(state)) {
                System.out.println("Solution found!\nThe Path:");
                printPathAndHeuristics(state);
                return;
            }

            Set<State> neighbors = State.getNextState(state);
            for (State neighbor : neighbors) {
                if (!explored.contains(neighbor) && !frontier.contains(neighbor)) {
                    frontier.add(neighbor);
                }
            }
        }

        System.out.println("No solution found.");
    }

    private static int heuristic(Coordinate current, Coordinate goal) {
        return Math.abs(current.getX() - goal.getX()) + Math.abs(current.getY() - goal.getY());
    }

    private static void printPathAndHeuristics(State goalState) {
        List<State> path = new ArrayList<>();
        State state = goalState;
        while (state != null) {
            path.add(state);
            state = state.parentState;
        }
        Collections.reverse(path);

        for (State s : path) {
            System.out.println("State: " + s);
            System.out.println("Heuristic value: " + heuristic(s.grid.positions.get(0), s.grid.goal));
            System.out.println("Total cost: "+s.getTotalCost());
            System.out.println("sum of them (comparison based on this): "+ (heuristic(s.grid.positions.get(0), s.grid.goal)+s.getTotalCost()) );
            System.out.println("Depth: " + path.indexOf(s));
        }

        System.out.println("Path length (depth): " + path.size());
    }

}