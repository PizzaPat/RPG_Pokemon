/**
 * Pokemon Class
 */
public abstract class Pokemon extends Entity {
    /**
     * Instances
     * level: of a pokemon
     * exp: of a pokemon
     * nextLevelExp: next exp to reach a new level (current level cap)
     */
    private int level;
    private int exp;
    private int nextLevelExp;

    /**
     * Pokemon Constructor
     * @param name of a pokemon
     * @param level of a pokemon
     */
    public Pokemon(String name, int level){
        super(name,((int)(Math.random()*10)+10*(level-1))+1);
        this.level = level;
        this.nextLevelExp = ((int)(Math.random()*3)+5*(level-1))+1;
        this.exp = 0;
    }

    /**
     * Get Type of Pokemon
     * @return type of pokemon
     */
    public abstract int getType();

    /**
     * Special Fight
     * @param move pass in move for a pokemon to attack
     * @return move damage
     */
    public abstract int specialFight(int move);

    /**
     * Display Special Menu of moves
     */
    public abstract void displaySpecialMenu();

    /**
     * Return String of Special Menu
     * @return menu string
     */
    public abstract String returnSpecialMenu();


    public abstract String returnMoveName(int move);

    /**
     * Get level
     * @return Level
     */
    public int getLevel(){
        return this.level;
    }

    /**
     * Get Exp
     * @return Exp
     */
    public int getExp(){
        return this.exp;
    }

    /**
     * Get next level xp
     * @return nextLevelExp
     */
    public int getNextLevelExp(){
        return this.nextLevelExp;
    }

    /**
     * Display Pokemon
     */
    public void displayPokemon(){
        System.out.print(getName()+"; HP: "+getHp()+", Level: "+getLevel()+"\n");
    }

    /**
     * Gain exp
     * @param exp current exp
     * @return exp after addition of exp param
     */
    public int gainExp(int exp){
        this.exp = this.exp+exp;
        while(this.exp >= this.nextLevelExp){
            System.out.println("LEVEL UP!");
            incMaxHp();
            this.level++;
            this.exp = this.exp - this.nextLevelExp;
            this.nextLevelExp = this.nextLevelExp+3;
        }
        return this.exp;
    }

    /**
     * Display Basic Menu
     */
    public void displayBasicMenu(){
        System.out.println("1. Slam\n2. Tackle\n3. Mega Punch");
    }

    /**
     * Basic Fight
     * @param move
     * @return selected move
     */
    public int basicFight(int move){
        switch(move){
            case 1: return slam();
            case 2: return tackle();
            case 3: return megaPunch();
                default: return 0;
        }
    }

    /**
     * Fight finalization
     * @param style chosen
     * @param move chosen
     * @return damage based from passed style and move
     */
    public int fight(int style, int move){
        switch(style){
            case 1: return basicFight(move);
            case 2: return specialFight(move);
            default: return 0;
        }
    }

    /**
     * Using Basic Attack: Slam
     * @return damage of slam
     */
    public int slam(){
        System.out.println(getName()+" uses Slam!");
        return (int)((Math.random()*4)+2*level);
    }

    /**
     * Using Basic Attack: Tackle
     * @return damage of tackle
     */
    public int tackle(){
        System.out.println(getName()+" uses Tackle!");
        return (int)((Math.random()*5)+2*level);
    }

    /**
     * Using Basic Attack: Mega Punch
     * @return damage of mega punch
     */
    public int megaPunch(){
        System.out.println(getName()+" uses Mega Punch!");
        return (int)((Math.random()*4)+2*level);
    }
}
