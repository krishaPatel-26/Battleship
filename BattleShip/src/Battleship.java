
import java.util.Random;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Krisha 
 */
class Battleship {    

    String red = "\u001B[31m"; //hit
    String green = "\u001B[32m";//sunk
    String blue = "\u001B[34m";//player grid
    String yellow = "\u001B[33m";//miss
    String purple = "\u001B[35m"; //computer grid
    String cyan = "\u001B[36m";//score 
    String reset = "\u001B[0m"; //reset

    private Grid playerGrid;
    private Grid compGrid;
    private int row;
    private int col;
    private int[] ship;
    private int playerScore, AIscore = 0;
    private boolean win, lose = false;
    private final Random rand = new Random();

    public Battleship(int row, int col) {
        this.row = row;
        this.col = col;

        compGrid = new Grid(row, col);
        playerGrid = new Grid(row, col);

        ship = new int[5];
        ship[0] = 2;
        ship[1] = 3;
        ship[2] = 3;
        ship[3] = 4;
        ship[4] = 5;

        
    }

    public void init() {
        System.out.println(purple + "\nComputer's Grid" + reset);
        compGrid.displayGrid();
        System.out.println(blue + "\nPlayer's Grid" + reset);
        playerGrid.displayGrid();
        playerPlaceShip();
        compPlaceShip();
        ShootGrid();
    }

    public void playerPlaceShip() {
        Scanner s = new Scanner(System.in);
        //Instraction
        System.out.println(green + "Please put coordinates for 5 ships.");
        System.out.println("Ship 1=2, ship 2 =3, ship 3 =3, ship 4 =4, ship 5 =5" + reset);
        System.out.print(cyan + "Enter coordinates 0 : Horizontal, 1: Vertical (e.g., 0A1): " + reset);
        //player ship placemeant
        for (int shipNum = 0; shipNum < ship.length; shipNum++) {

            //check for vaild input 
            boolean isValid = false;
            while (!isValid) {
                System.out.print(green + "Ship Number: " + (shipNum + 1) + reset);
                String str = s.next().toUpperCase();

                int cor = Integer.parseInt(str.substring(0, 1));
                int r = str.charAt(1) - 65;
                int c = Integer.parseInt(str.substring(2));
                isValid = true;

                if (cor >= 2 || r > 10 || c > 10) {
                    isValid = false;
                    System.out.println(red + "Invalid input, Try again" + reset);
                }

                for (int p = 0; p < ship[shipNum]; p++) {
                    if (cor == 0 && playerGrid.getGridValue(r, c + p - 1) != 0) {
                        isValid = false;
                        System.out.println(red + "Invalid input you already have a ship here, Try again" + reset);
                        break;
                    } else if (cor == 1 && playerGrid.getGridValue(r + p, c - 1) != 0) {
                        isValid = false;
                        System.out.println(red + "Invalid input you already have a ship here, Try again" + reset);
                        break;
                    }

                    if (isValid) {

                        if (cor == 0) {
                            playerGrid.shipValue(r, c + p - 1, shipNum + 1);
                        }
                        if (cor == 1) {
                            playerGrid.shipValue(r + p, c - 1, shipNum + 1);
                        }
                    }

                }
                System.out.println(blue + "\nPlayer's Grid" + reset);
                playerGrid.displayGrid();

            }
        }

    }

    public void compPlaceShip() {
        //computer ship placement
        for (int i = 1; i <= ship.length; i++) {
            //check for valid random 
            boolean validPlacement = false;

            while (!validPlacement) {
                //random 
                int num = 11 - ship[i - 1];
                int cor = rand.nextInt(2);
                int r = rand.nextInt(num);
                int c = rand.nextInt(num);

                validPlacement = true;

                for (int p = 0; p < ship[i - 1]; p++) {
                    if (cor == 0 && compGrid.getGridValue(r, c + p) != 0) {
                        validPlacement = false;
                        break;
                    } else if (cor == 1 && compGrid.getGridValue(r + p, c) != 0) {
                        validPlacement = false;
                        break;
                    }
                }

                if (validPlacement) {
                    for (int p = 0; p < ship[i - 1]; p++) {
                        if (cor == 0) {
                            compGrid.shipValue(r, c + p, i);
                        }
                        if (cor == 1) {
                            compGrid.shipValue(r + p, c, i);
                        }
                    }

                }

            }
        }
        System.out.println(purple + "\nComputer's Grid" + reset);
        compGrid.displayGrid();

    }

    public void playerShoot() {

        Scanner s = new Scanner(System.in);
        String str = "";
        char ch = 65;

        System.out.print(green + "\nEnter coordinates to shoot on the computer grid (Miss= 8 and Hit=9) (e.g., A1): " + reset);

        String input = s.next().toUpperCase();

        int r = input.charAt(0) - 65;
        int c = Integer.parseInt(input.substring(1)) - 1;
        int shipNum = compGrid.getGridValue(r, c);

        System.out.println(purple + "\nComputer's Grid" + reset);

        //if hits
        if (shipNum != 0 && shipNum <= 5) {
            compGrid.shootValue(r, c);
            str = red + "You Hit Computer's Ship: " + shipNum + reset;

            //if one ship is all sunk
            if (compGrid.isSunk(shipNum)) {
                playerScore++;
                System.out.println(red + "You sunk a computer's ship: " + shipNum + reset);
            }

        } //if misses
        else if (shipNum == 0) {
            str = yellow + " You Missed!" + reset;
            compGrid.missValue(r, c);
        }

        System.out.println("(" + (char) (ch + r) + (c + 1) + ")" + str);
        System.out.println(cyan + "Player Score: " + playerScore + reset);
        compGrid.displayGrid();

        if (shipNum >= 6) {
            System.out.println(red + "Invalid Input, you have already shoot there Try again" + reset);
            playerShoot();
        } //check if the shoot has already been done

    }

    public void ShootGrid() {
        do {
            playerShoot();
            if (playerScore == ship.length) {
                win = true;
                System.out.println(red + "YOU WON!!" + reset);
            }
            else {
                compShoot();
                if (AIscore == ship.length) {
                    lose = true;
                    System.out.println(red + "YOU WON!!" + reset);
                }
            }

        } while (!win && !lose);
    }

    public void compShoot() {

        //random r,c 
        int r = rand.nextInt(10);
        int c = rand.nextInt(10);
        String s = "";
        char ch = 65;
        int shipNum = playerGrid.getGridValue(r, c);

        System.out.println(blue + "\nYour Grid" + reset);

        //if hits
        if (shipNum != 0) {
            playerGrid.shootValue(r, c);
            s = red + "Computer Hit your ship:" + shipNum + reset;

            //is one ship sunk
            if (playerGrid.isSunk(shipNum)) {
                AIscore++;
                System.out.println(red + "Computer sunk your ship: " + shipNum + reset);
            }

        } //if misses
        else if (playerGrid.getGridValue(r, c) == 0) {
            s = yellow + " Computer Missed " + reset;
            playerGrid.missValue(r, c);

        }
        System.out.println("(" + (char) (ch + r) + (c + 1) + ")" + s);
        System.out.println(cyan + "AI Score: " + AIscore + reset);

        playerGrid.displayGrid();

    }

    public Grid getPlayerGrid() {
        return playerGrid;
    }

    public void setPlayerGrid(Grid playerGrid) {
        this.playerGrid = playerGrid;
    }

    public Grid getCompGrid() {
        return compGrid;
    }

    public void setCompGrid(Grid compGrid) {
        this.compGrid = compGrid;
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

    public int[] getShip() {
        return ship;
    }

    public void setShip(int[] ship) {
        this.ship = ship;
    }

}
