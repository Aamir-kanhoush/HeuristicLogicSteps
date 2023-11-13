package Game;

public class Rules {


    public static boolean isWon(State state) {
        for (int i = 0; i < state.grid.rows; i++) {
            for (int j = 0; j < state.grid.columns; j++) {
                if (!(state.grid.grid[i][j] == 0 || state.grid.grid[i][j] == 5)) {
                    return false;
                }
            }
        }
        return true;
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