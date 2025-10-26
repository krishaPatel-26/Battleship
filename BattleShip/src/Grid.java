/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Krisha
 */
public class Grid {

    private int row;
    private int col;
    private int[][] arrGrid;
    String green = "\u001B[32m";
    String blue = "\u001B[34m";
    String reset = "\u001B[0m";

    public Grid(int row, int col) {
        this.row = row;
        this.col = col;

        arrGrid = new int[row][col];

    }

    public void shipValue(int r, int c, int v) {
        arrGrid[r][c] = v;
    }

    public void displayGrid() {
        char ch = 65;
        char space = 32;
        int num = 1;

        System.out.print(space + "  ");
        for (int i = 1; i < row + 1; i++) {
            System.out.print(green + num + " " + reset);
            num++;
        }
        System.out.println();

        for (int[] arrGrid1 : arrGrid) {
            System.out.print(blue + ch + " " + reset);
            ch++;
            for (int c = 0; c < arrGrid[0].length; c++) {
                System.out.print(" " + arrGrid1[c]);
            }
            System.out.println();
        }

    }

    public int getGridValue(int r, int c) {
        return arrGrid[r][c];
    }

    public void shootValue(int row, int col) {
        arrGrid[row][col] = 9;
    }

    public void missValue(int row, int col) {
        arrGrid[row][col] = 8;
    }

    public boolean isSunk(int num) {
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (arrGrid[r][c] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int[][] getArrGrid() {
        return arrGrid;
    }

    public void setArrGrid(int[][] arrGrid) {
        this.arrGrid = arrGrid;
    }

}

