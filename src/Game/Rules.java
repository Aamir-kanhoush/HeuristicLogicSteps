package Game;

import java.util.*;

public class Rules {


    public static boolean isWon(State state) {
        List<Coordinate> positions = state.grid.positions != null ? new ArrayList<>(state.grid.positions) : new ArrayList<>();
        Coordinate goal = state.grid.goal;

        for (Coordinate position : positions) {
            if (position.getX() == goal.getX() && position.getY() == goal.getY()) {
                return true;
            }
        }

        return false;
    }



    public static boolean isStuck(State state) {
        for (int i = 0; i < state.grid.positions.size(); i++) {
            if (Move.isValidMove(i, 8, state) || Move.isValidMove(i, 6, state) || Move.isValidMove(i, 4, state) || Move.isValidMove(i, 2, state)) {
                return false;
            }
        }
        return true;
    }

}