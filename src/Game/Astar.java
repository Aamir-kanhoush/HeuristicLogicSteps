package Game;

import java.util.*;


public class Astar {
    public static void searchAstar(State initialState, int maxDepth) {
        PriorityQueue<State> frontier = new PriorityQueue<>(Comparator.comparingInt(state -> heuristic(state.grid.positions.get(0), state.grid.goal) + state.grid.positions.get(0).getCost()));
        Set<State> explored = new HashSet<>();
        frontier.add(initialState);

        while (!frontier.isEmpty()) {
            State state = frontier.poll();
            explored.add(state);

            if (Rules.isWon(state)) {
                System.out.println("Solution found!");
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

        System.out.println("No solution found within depth limit");
    }

    private static int heuristic(Coordinate current, Coordinate goal) {
        return current.getCost() + current.getX() * current.getY();
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
            System.out.println("Depth: " + path.indexOf(s));
        }

        System.out.println("Path length (depth): " + path.size());
    }

}

