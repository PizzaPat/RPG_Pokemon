import java.util.Scanner;
import java.awt.Point;
import java.io.*;

/**
 * Map Class
 */
public class Map {
    /**
     * Instances
     * char array "map" for displayable map
     * boolean array "revealed" for displaying a explored location
     */
    private char[][] map;
    private boolean[][] revealed;

    /**
     * Default Constructor
     */
    Map(){
        map = new char[5][5];
        revealed = new boolean[5][5];
    }

    /**
     * Generate areas
     * @param areaNum pass in number of Area 1 to 3
     */
    public void generateArea(int areaNum){
        int j;
        try {Scanner read;
            switch (areaNum) {
                case 1:
                    read = new Scanner(new File("Area1.txt"));
                    break;
                case 2:
                    read = new Scanner(new File("Area2.txt"));
                    break;
                case 3:
                    read = new Scanner(new File("Area3.txt"));
                    break;
                default:
                    read = new Scanner(new File("Area1.txt"));
                    break;
            }
                j = 0;
                do {
                    for (int i = 0; i < 5; i++) {
                        String line = read.next();
                        char c = line.charAt(0);
                        map[i][j] = c;
                        if(c=='s' || c=='c')revealed[i][j] = true;
                        else revealed[i][j] = false;
                    }
                    j++;
                } while (read.hasNext());
                read.close();
            }
        catch(FileNotFoundException e){
                System.out.println("File is not found");
            }
        }

    /**
     * Get Character At Location P
     * @param p pass in "point" location to be read character for
     * @return a character on that specific location
     */
    public char getCharAtLoc(Point p){
        char returnChar =' ';
        try {
            returnChar = map[(int) p.getX()][(int) p.getY()];
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("out of bound");
        }
        return returnChar;
    }

    /**
     * Display Map
     * @param p pass in "point" location to assign the coordinate
     */
    public void displayMap(Point p){
        System.out.println(" ---------- ");
        for(int j=0; j<5; j++) {
            System.out.print("| ");
            for (int i = 0; i < 5; i++) {
                if((int)p.getX()==i && (int)p.getY()==j){
                    System.out.print("* ");
                }
                else {
                    if (revealed[i][j] == true) System.out.print(map[i][j] + " ");
                    else System.out.print("x ");
                }
            }
            System.out.println("|");
        }
        System.out.println(" ---------- ");
    }

    /**
     * Find Start Location
     * @return a point with 's' assigned to map array
     */
    public Point findStartLocation(){
        Point tmp = new Point();
        for(int i=0; i<5; i++){
            for(int j=0; j<5;j++){
                if(map[i][j]=='s'){
                    tmp.setLocation(i,j);
                    return tmp.getLocation();
                }
            }
        }
        return tmp.getLocation();
    }

    /**
     * Reveal at point "p"
     * @param p pass in point "p" to be revealed in the map array
     */
    public void reveal(Point p){
        revealed[(int)p.getX()][(int)p.getY()] = true;
    }

    /**
     * Remove Opponent At Location
     * @param p pass in point "p" to assign "n"
     */
    public void removeOppAtLoc(Point p){
        map[(int)p.getX()][(int)p.getY()] = 'n';
    }






    public char[][] getMap(){
        return map;
    }

    public boolean[][] getRevealed(){
        return revealed;
    }
}
