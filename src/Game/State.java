package Game;

import java.util.*;

public class State {
    public Grid grid;

    public State parentState;
    public int totalCost;
    private Coordinate movedCoordinate;

    public State(Grid grid) {
        this.grid = grid;
        this.totalCost=0;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object)
            return true;
        if (object == null || getClass() != object.getClass())
            return false;
        State state = (State) object;
        List<Coordinate> thisPositionsSorted = new ArrayList<>(this.grid.positions);
        List<Coordinate> objectPositionsSorted = new ArrayList<>(state.grid.positions);
        Collections.sort(thisPositionsSorted);
        Collections.sort(objectPositionsSorted);
        return Arrays.deepEquals(this.grid.grid, state.grid.grid)
                && thisPositionsSorted.equals(objectPositionsSorted)
                && this.totalCost == state.totalCost;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < grid.positions.size(); i++) {
            int x = grid.positions.get(i).getX() ;
            int y = grid.positions.get(i).getY() ;
            int cost = grid.positions.get(i).getCost();
            string.append("(X: ").append(x).append(", Y:").append(y).append(", Cost: ").append(cost).append(")");
        }

        return string.toString();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    public static State deepCopy(State state) {
        Grid copiedGrid = new Grid();
        copiedGrid.rows = state.grid.rows;
        copiedGrid.columns = state.grid.columns;
        copiedGrid.goal=new Coordinate(state.grid.goal.getX(),state.grid.goal.getY(),0 );
        copiedGrid.grid=new int[copiedGrid.rows][copiedGrid.columns];
        for (int i=0;i<copiedGrid.rows;i++){
            for (int j=0;j<copiedGrid.columns;j++){
                copiedGrid.grid[i][j]=state.grid.grid[i][j];
            }
        }
        copiedGrid.positions = new ArrayList<>();
        for (Coordinate coordinate : state.grid.positions) {
            copiedGrid.positions.add(new Coordinate(coordinate.getX(), coordinate.getY(), coordinate.getCost()));
        }
        State copiedState=new State(copiedGrid);
        return copiedState;
    }



    public static Set<State> getNextState(State state) {
        Set<State> nextStates = new HashSet<>();
        for (int i = 0; i < state.grid.positions.size(); i++) {
            int[] directions = {8, 6, 4, 2};
            for (int direction : directions) {
                if (Move.isValidMove(i, direction, state)) {
                    State copy = State.deepCopy(state);
                    copy=Move.moveLocation(i, direction, copy);
                    copy.parentState = state;
                    copy.totalCost+=state.grid.positions.get(i).getCost();
                    if (!nextStates.contains(copy)) {
                        nextStates.add(copy);
                    }
                }
            }
        }
        return nextStates;
    }

    public static State getParentState(State state) {
        return state.parentState;
    }

    public int getTotalCost() {
        return this.totalCost;
    }

    public void updateTotalCost() {
        int movedCoordinateCost = movedCoordinate.getCost();
        int cellValue = grid.grid[movedCoordinate.getX()][movedCoordinate.getY()];
        totalCost += movedCoordinateCost + cellValue;
    }

}