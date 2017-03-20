import java.io.Serializable;
public abstract class Entity implements Serializable {
    private String name;
    private int hp;
    private int maxHp;

    /**
     * Constructor
     * @param name
     * @param hp
     */
    public Entity(String name, int hp){
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
    }

    /**
     * Get Name
     * @return Name
     */
    public String getName(){
        return this.name;
    }

    /**
     * Get Hp
     * @return Hp
     */
    public int getHp(){
        return this.hp;
    }

    /**
     * Lose Hp
     * @param hit
     * @return Hp after damage
     */
    public int loseHp(int hit){
        int health = getHp()-hit;
        if(health<=0){
            this.hp = 0;
            return this.hp;
        }
        else{
            this.hp = health;
            return health;
        }
    }

    /**
     * Gain Hp
     * @param heal
     * @return Hp after the heal
     */
    public int gainHp(int heal){
        int health = getHp()+heal;
        if(health>=this.maxHp){
            return this.hp = this.maxHp;
        }
        else{
            this.hp = health;
            return health;
        }
    }

    /**
     * Increase max Hp
     */
    public void incMaxHp(){
        //1 Level increases 10 HP
        this.maxHp = maxHp + 10;
        //reward for level up: free Hp!
        this.hp = maxHp;
    }

}
