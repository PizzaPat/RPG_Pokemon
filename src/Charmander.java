/**
 * Charmander Class
 */
public class Charmander extends Pokemon implements Fire{
    /**
     * Default Constructor
     */
    public Charmander(){
        super("Charmander", (int)(Math.random()*3)+1);
    }

    /**
     * Constructor, with level assignment
     * @param level assigned
     */
    public Charmander(int level){super("Charmander",level);}
    /**
     * Return damage of Fire Punch
     * @return Damage
     */
    @Override
    public int firePunch(){
        System.out.println(getName()+" uses Fire Punch!");
        return ((int)(Math.random()*5)+2*getLevel());
    }

    /**
     * Return damage of Ember
     * @return Damage
     */
    @Override
    public int ember(){
        System.out.println(getName()+" uses Ember!");
        return ((int)(Math.random()*5)+1*getLevel());
    }

    /**
     * Return damage of Fire Blast
     * @return Damage
     */
    @Override
    public int fireBlast(){
        System.out.println(getName()+" uses Fire Blast!");
        return ((int)(Math.random()*5)+2*getLevel());
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
    public int specialFight(int move) {
        switch(move){
            case 1: return firePunch();
            case 2: return ember();
            case 3: return fireBlast();
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
    public String getFirePunch(){
        return "Fire Punch!";
    }

    @Override
    public String getEmber(){
        return "Ember!";
    }

    @Override
    public String getFireBlast(){
        return "Fire Blast!";
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