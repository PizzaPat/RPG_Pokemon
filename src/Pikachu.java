/**
 * Pikachu Class
 */
public class Pikachu extends Pokemon implements Electric {
    /**
     * Default Constructor
     */
    public Pikachu() {
        super("Pikachu", (int)(Math.random()*3)+1);
    }

    /**
     * Constructor, with level assignment
     * @param level assigned
     */
    public Pikachu(int level){
        super("Pikachu",level);
    }
    /**
     * Return damage of Thunder Shock
     * @return Damage
     */
    @Override
    public int thunderShock(){
        System.out.println(getName()+" uses Thunder Shock!");
        return ((int)(Math.random()*5)+2*getLevel());
    }

    /**
     * Return damage of Thunderbolt
     * @return Damage
     */
    @Override
    public int thunderbolt(){
        System.out.println(getName()+" uses Thunderbolt!");
        return ((int)(Math.random()*5)+2*getLevel());
    }

    /**
     * Return damage of Thunder Punch
     * @return Damage
     */
    @Override
    public int thunderPunch(){
        System.out.println(getName()+" uses Thunder Punch!");
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
            case 1: return thunderShock();
            case 2: return thunderbolt();
            case 3: return thunderPunch();
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


    @Override
    public String getThunderPunch(){
        return "Thunder Punch!";
    }

    @Override
    public String getThunderbolt(){
        return "Thunderbolt!";
    }

    @Override
    public String getThunderShock(){
        return "Thunder Shock!";
    }

}
