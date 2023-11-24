package Game;

import java.util.*;

public class HC {
    public static void searchHillClimbing(State initialState, int maxDepth) {
        int depth = 0;
        int visitedCount = 0;
        while (depth < maxDepth) {
            System.out.println("Depth: " + depth);
            initialState.grid.printGrid();
            initialState.grid.printGoal();
            initialState.grid.printLocations();
            Set<State> nextStates = State.getNextState(initialState);
            Coordinate movedCoordinate = getMovedCoordinate(State.getParentState(initialState), initialState);
            System.out.println("Heuristic value: "+ heuristicValue(initialState));
            State bestState = null;
            int bestCount = Integer.MAX_VALUE;
            for (State state : nextStates) {
                visitedCount++;
                if (state != null && state.grid != null) {
                    movedCoordinate = getMovedCoordinate(state, initialState);
                    int count = heuristicValue(state);
                    if (count < bestCount) {
                        bestState = state;
                        bestCount = count;
                    }
                }
            }
            if (bestState == null) {
                break;
            }
            bestState.parentState = initialState;
            initialState = bestState;
            depth++;
        }
        System.out.println("Maximum found (it may be local):");
        System.out.println("Number of visited states: " + visitedCount);
        printSteps(initialState, depth);
    }

    private static int heuristicValue(State state) {
        int count = 0;
        Grid grid = state.grid;
        for (int i = 0; i < grid.rows; i++) {
            for (int j = 0; j < grid.columns; j++) {
                if (grid.grid[i][j] != 0 && grid.grid[i][j] != 5) {
                    count += Math.abs(grid.goal.getX() - i) + Math.abs(grid.goal.getY() - j);
                }
            }
        }
        return count;
    }

    private static Coordinate getMovedCoordinate(State currentState, State nextState) {
        ArrayList<Coordinate> currentCoordinates = currentState != null ? currentState.grid.positions : new ArrayList<>();
        ArrayList<Coordinate> nextCoordinates = nextState.grid.positions;

        for (Coordinate nextCoordinate : nextCoordinates) {
            if (!currentCoordinates.contains(nextCoordinate)) {
                return nextCoordinate;
            }
        }

        return nextCoordinates.size() > 0 ? nextCoordinates.get(0) : null;
    }

    private static void printSteps(State state, int depth) {
        LinkedList<State> stack = new LinkedList<>();
        State currentState = state;
        while (currentState != null) {
            stack.push(currentState);
            currentState = currentState.parentState;
        }
        System.out.println("Steps taken state:");
        state.grid.printGoal();
        while (!stack.isEmpty()) {
            State step = stack.pop();
            System.out.println(step);
            step.grid.printGrid();
            Coordinate movedCoordinate = getMovedCoordinate(step, state);
            int moveCost = movedCoordinate != null ? movedCoordinate.getCost() * step.grid.grid[movedCoordinate.getX()][movedCoordinate.getY()] : 0;
            int heuristic = heuristicValue(step);
            System.out.println("Heuristic value: " + heuristic);
            System.out.println("Depth: " + (depth - stack.size()));
        }
    }
}
