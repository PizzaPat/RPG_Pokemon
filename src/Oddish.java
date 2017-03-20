/**
 * Oddish Class
 */
public class Oddish extends Pokemon implements Grass{
    /**
     * Default Constructor
     */
    public Oddish() {
        super("Oddish", (int)(Math.random()*3)+1);
    }

    /**
     * Return damage of Vine Whip
     * @return Damage
     */
    @Override
    public int vineWhip(){
        System.out.println(getName()+" uses Vine Whip!");
        return ((int)(Math.random()*4)+1*getLevel());
    }

    /**
     * Return damage of Razor Leaf
     * @return Damage
     */
    @Override
    public int razorLeaf(){
        System.out.println(getName()+" uses Razor Leaf!");
        return ((int)(Math.random()*3)+1*getLevel());
    }

    /**
     * Return damage of Solar Beam
     * @return Damage
     */
    @Override
    public int solarBeam(){
        System.out.println(getName()+" uses Solar Beam!");
        return ((int)(Math.random()*5)+2*getLevel());
    }

    /**
     * Return Type
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
    public int specialFight(int move) {
        switch(move){
            case 1: return vineWhip();
            case 2: return razorLeaf();
            case 3: return solarBeam();
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
