package Game;

public class Move {


    public static boolean isValidPosition(int positionIndex,State state){

        if (positionIndex < 0 || positionIndex >= state.grid.positions.size()) {
            return false;
        }
        return true;
    }


    public static boolean isValidMove(int positionIndex, int direction, State state) {

        Coordinate location = state.grid.positions.get(positionIndex);
        int x = location.getX();
        int y = location.getY();
        int cost = location.getCost();

        int newX = x;
        int newY = y;
        if (direction == 8) {
            newX = x - 1;
        } else if (direction == 6) {
            newY = y + 1;
        } else if (direction == 4) {
            newY = y - 1;
        } else if (direction == 2) {
            newX = x + 1;
        }

        for (int i = 0; i < state.grid.positions.size(); i++) {
            if (i == positionIndex) {
                continue;
            }
            Coordinate otherLocation = state.grid.positions.get(i);
            if (otherLocation.getX() == newX && otherLocation.getY() == newY) {
                return false;
            }
        }

        if ((direction == 8 || direction == 2) && newX < 0) {
            return false;
        }
        if ((direction == 6 || direction == 4) && newY < 0) {
            return false;
        }
        if ((direction == 8 || direction == 2) && newX >= state.grid.rows) {
            return false;
        }
        if ((direction == 6 || direction == 4) && newY >= state.grid.columns) {
            return false;
        }

        Coordinate newLocation = new Coordinate(newX, newY,cost);
        if (state.grid.positions.contains(newLocation)) {
            return false;
        }

        if (state.grid.grid[newX][newY] == 0 || state.grid.grid[newX][newY] == 5) {
            return false;
        }

        return true;
    }

    public static State moveLocation(int positionIndex, int direction, State state) {

        if (isValidMove(positionIndex, direction, state)&&isValidPosition(positionIndex,state)) {
            Coordinate location = state.grid.positions.get(positionIndex);
            int x = location.getX();
            int y = location.getY();
            int cost = location.getCost();
            switch (direction) {
                case 8: // Up
                    x--;
                    break;
                case 6: // Right
                    y++;
                    break;
                case 4: // Left
                    y--;
                    break;
                case 2: // Down
                    x++;
                    break;
                default:
                    System.out.println("Invalid direction. Please use the numpad keys: 8 (up), 6 (right), 4 (left), or 2 (down).");
                    return state;
            }
            State copy=State.deepCopy(state);
            copy.grid.grid[x][y] += 1;
            copy.grid.positions.set(positionIndex, new Coordinate(x, y, cost));
            return copy;
        } else {
            System.out.println("Invalid move.");
            return state;
        }
    }
}
