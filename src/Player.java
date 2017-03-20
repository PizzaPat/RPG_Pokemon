import java.awt.Point;
import java.io.*;
import java.lang.*;

/**
 * Player Class
 */
public class Player extends Trainer implements Serializable {
    /**
     * potions: Number of current potions
     * pokeballs: Number of current pokeballs
     * money: Amount of money
     * level: Current level that the player is at in the map
     * attackSpeech: Player attack speech
     * lossSpeech: Player loss speech
     * winSpeech: Player win speech
     */
    private int potions;
    private int pokeballs;
    private int money;
    private int level;
    private String attackSpeech;
    private String lossSpeech;
    private String winSpeech;
    private Point location = new Point();

    /**
     * Player Constructor
     * @param name of the player
     * @param hp of the player
     * @param start point of beginning location
     */
    public Player(String name, int hp, Point start){
        super(name,hp);
        this.location = start;
        this.attackSpeech = "Ash: Time to fight!";
        this.lossSpeech = "Ash: Awww, man. I lost";
        this.winSpeech = "Ash: Yes! I've won!";
        this.level=1;
        this.potions=3;
        this.pokeballs=5;
        this.money=20;
    }

    /**
     * Get Location
     * @return the current location of the player
     */
    public Point getLocation(){
        return location;
    }

    /**
     * Set Location
     * @param p pass in "p" point to set the location
     * @return location after the setting
     */
    public boolean setLocation(Point p){
        location.setLocation(p.getLocation());
        return true;
    }

    /**
     * Get Level
     * @return current player map level
     */
    public int getLevel(){
        return level;
    }

    /**
     * Increase Level of the map (modded by 3 to iterate between map 1 to 3
     */
    public void incLevel(){
        this.level = (level%3)+1;
    }

    /**
     * Go North
     * @param m current map
     * @return new location after moving
     */
    public char goNorth(Map m){
        if((int)location.getY()>0) {
            location.move((int) location.getX(), (int) location.getY() - 1);
            m.reveal(location);
        }
        else{
            return 'F';
        }
        return m.getCharAtLoc(location);
    }

    /**
     * Go South
     * @param m current map
     * @return new location after moving
     */
    public char goSouth(Map m){
        if((int)location.getY()<4) {
            location.move((int) location.getX(), (int) location.getY() + 1);
            m.reveal(location);
        }
        else{
            return 'F';
        }
        return m.getCharAtLoc(location);
    }

    /**
     * Go East
     * @param m current map
     * @return new location after moving
     */
    public char goEast(Map m){
        if((int)location.getX()<4) {
            location.move((int) location.getX() + 1, (int) location.getY());
            m.reveal(location);
        }
        else{
            return 'F';
        }
        return m.getCharAtLoc(location);
    }

    /**
     * Go West
     * @param m current map
     * @return new location after moving
     */
    public char goWest(Map m){
        if((int)location.getX()>0) {
            location.move((int) location.getX() - 1, (int) location.getY());
            m.reveal(location);
        }
        else {
            return 'F';
        }
        return m.getCharAtLoc(location);
    }

    /**
     * Get Attack Speech
     * @return attack speech
     */
    @Override
    public String getAttackSpeech(){
        return attackSpeech;
    }

    /**
     * Get Winning Speech
     * @return winning speech
     */
    @Override
    public String getWinSpeech(){
        return winSpeech;
    }

    /**
     * Get Losing Speech
     * @return losing speech
     */
    @Override
    public String getLossSpeech(){
        return lossSpeech;
    }

    /**
     * Choose Style
     * @return Choice to attack with basic or special attack
     */
    @Override
    public int chooseStyle(){
        System.out.println("What do you want your pokemon to do?");
        System.out.println();
        return CheckInput.checkIntRange(1,2);
    }

    /**
     * Choose a move based from style
     * @param style pass in style either basic or special attack
     * @return move decision
     */
    @Override
    public int chooseMove(int style){
        if(style==1){
            getCurrentPokemon().displayBasicMenu();
            int move = CheckInput.checkIntRange(1,3);
            return move;
        }
        else if(style==2) {
            getCurrentPokemon().displaySpecialMenu();
            int move = CheckInput.checkIntRange(1,3);
            return move;
        }
        else{return 0;}
    }


    /**
     * Buy Potion
     */
    public void buyPotion(){
        if(money>=2) {
            System.out.println("You have purchased a Potion for 2 Pokecoins");
            spendMoney(2);
            potions++;
        }
        else{
            System.out.println("You don't have enough money");
        }
    }

    /**
     * Buy pokeball
     */
    public void buyPokeball(){
        if(money>=5) {
            System.out.println("You have purchased a pokeball for 5 Pokecoins");
            spendMoney(5);
            pokeballs++;
        }
        else{
            System.out.println("You don't have enough money");
        }
    }

    /**
     * Use pokeball
     */
    public void usePokeball(){
            pokeballs--;
            System.out.println(getName() + " used pokeball");
    }

    /**
     * Use potion
     */
    public void usePotion(){
            potions--;
            System.out.println(getCurrentPokemon().getName()+" +10 HP");
            getCurrentPokemon().gainHp(10);
    }

    /**
     * get number of potions left
     * @return number of potions
     */
    public int getNumPotionsLeft(){
        return potions;
    }

    /**
     * get number of pokeballs left
     * @return number of pokeballs
     */
    public int getNumPokeballsLeft(){
        return pokeballs;
    }

    /**
     * Deduct money
     * @param price amount to deduct
     */
    public void spendMoney(int price){
        this.money = this.money - price;
        System.out.println(this.money+" Pokecoins");
    }

    public int getMoney(){
        return money;
    }

}
