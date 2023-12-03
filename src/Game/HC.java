package Game;

import java.util.*;


public class HC {
    private static int heuristic(Coordinate current, Coordinate goal) {
        return Math.abs(current.getX() - goal.getX()) + Math.abs(current.getY() - goal.getY());
    }

    public static State getBestNeighbor(State currentState) {
        State bestNeighbor = null;
        int maxHeuristic = Integer.MIN_VALUE;

        Set<State> neighbors = State.getNextState(currentState);
        for (State neighbor : neighbors) {

            for (Coordinate c : neighbor.grid.positions) {
                int heuristic = heuristic(neighbor.grid.goal, c);
                if (heuristic > maxHeuristic) {
                    maxHeuristic = heuristic;
                    bestNeighbor = neighbor;
                    if(Rules.isWon(bestNeighbor)){
                        return bestNeighbor; }
                }
            }
        }
        return bestNeighbor;
    }

    public static void hillClimbing(State initialState, int maxIterations) {
        State currentState = initialState;
        List<State> path = new ArrayList<>();
        path.add(currentState);
        int iterations = 0;

        while (iterations < maxIterations) {
            if(Rules.isWon(currentState)){break;}
            State bestNeighbor = getBestNeighbor(currentState);
            if (bestNeighbor == null || bestNeighbor.getTotalCost() <= currentState.getTotalCost()) {
                break;
            }
            currentState = bestNeighbor;
            path.add(currentState);
            iterations++;
        }

        System.out.println("Maxima found (could be local):");
        System.out.println("Goal: \n" + currentState.grid.goal);
        System.out.println("Path taken:");
        for (State state: path){
            System.out.println(state+" "+state.totalCost);
        }
    }
}
