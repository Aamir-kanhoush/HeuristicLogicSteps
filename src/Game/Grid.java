package Game;

import java.util.*;
import java.io.*;


public class Grid {
    public int rows, columns;
    public int[][] grid;
    public ArrayList<Coordinate> positions;



    public void loadLevel(String filepath) {
        try {
            Scanner scanner = new Scanner(new File(filepath));

            rows = scanner.nextInt();
            columns = scanner.nextInt();

            grid = new int[rows][columns];

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    grid[i][j] = scanner.nextInt();
                }
            }


            positions = new ArrayList<>();
            while (scanner.hasNext()) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                int cost = scanner.nextInt();
                positions.add(new Coordinate(x, y, cost));
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error loading the level from the file: " + filepath);
        }
    }


    public void printGrid() {
        System.out.println();
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                System.out.print(this.grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }


    public void printLocations() {
        for (int i = 0; i < positions.size(); i++) {
            Coordinate location = positions.get(i);
            System.out.println("Location #" + (i) + ":\nX: " + (location.getX()) + ", Y: " + (location.getY())+", Cost:"+location.getCost());
        }
    }




}