/**
 * Squirtle Class
 */
public class Squirtle extends Pokemon implements Water{
    /**
     * Default Constructor
     */
    public Squirtle() {
        super("Squirtle", (int)(Math.random()*3)+1);
    }

    /**
     * Constructor, with level assignment
     * @param level assigned
     */
    public Squirtle(int level){
        super("Squirtle",level);
    }

    /**
     * Return damage of Water Gun
     * @return Damage
     */
    @Override
    public int waterGun(){
        System.out.println(getName()+" uses Water Gun!");
        return ((int)(Math.random()*4)+2*getLevel());
    }

    /**
     * Return damage of Bubble Beam
     * @return Damage
     */
    @Override
    public int bubbleBeam(){
        System.out.println(getName()+" uses Bubble Beam!");
        return ((int)(Math.random()*5)+2*getLevel());
    }

    /**
     * Return damage of Water Fall
     * @return Damage
     */
    @Override
    public int waterFall(){
        System.out.println(getName()+" uses Water Fall!");
        return ((int)(Math.random()*4)+2*getLevel());
    }

    /**
     * Return type
     * @return Return type of pokemon
     */
    @Override
    public int getType(){
        return type;
    }

    /**
     * Special Fight selection
     * @param move pass in a move style
     * @return damage of a selected move
     */
    @Override
    public int specialFight(int move) {
        switch(move){
            case 1: return waterGun();
            case 2: return bubbleBeam();
            case 3: return waterFall();
            default: return 0;
        }
    }

    /**
     * Display the special abilities
     */
    @Override
    public void displaySpecialMenu() {
        System.out.println(typeMenu);
    }

    /**
     * Return String of Special Menu
     * @return menu string
     */
    @Override
    public String returnSpecialMenu(){
        return typeMenu;
    }

    @Override
    public String returnMoveName(int move){
        if(move==1){
            return "Thunder Punch!";
        }
        else if(move==2){
            return "Thunderbolt";
        }
        else if(move==3){
            return "Thunder Shock!";
        }
        else{
            return " ";
        }
    }
}
