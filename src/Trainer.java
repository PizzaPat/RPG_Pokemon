import java.io.Serializable;
import java.util.*;
public abstract class Trainer extends Entity implements Serializable {
    private ArrayList<Pokemon> pokemon = new ArrayList<Pokemon>();
    private int currentPokemon=0;
    //private static int numberPokemon = 1;

    /**
     * Constructor
     * @param name
     * @param hp
     */
    public Trainer(String name,int hp){
        super(name,hp);
    }

    public abstract String getAttackSpeech();
    public abstract String getWinSpeech();
    public abstract String getLossSpeech();
    public abstract int chooseStyle();
    public abstract int chooseMove(int style);

    public int getPokemonNum(){
        return pokemon.size();
    }
    //public void incPokemonNum(){
    //    this.numberPokemon++;
    //}

    /**
     * Display Current Pokemon
     */
    public void displayCurrentPokemon(){
        getCurrentPokemon().displayPokemon();
    }

    /**
     * Get Current Pokemon
     * @return the current pokemon
     */
    public Pokemon getCurrentPokemon(){
        return this.pokemon.get(currentPokemon-1);
    }

    /**
     * Add a pokemon p to the pokemon array
     * @param p
     */
    public void addPokemon(Pokemon p){
        pokemon.add(p);
        this.currentPokemon++;
    }

    /**
     * Print all the pokemons
     */
    public void listPokemon() {
        for(int i=0; i<pokemon.size(); i++){
            System.out.print((i+1)+". ");
            this.pokemon.get(i).displayPokemon();
        }
    }

    /**
     * Heal all the pokemons
     */
    public void healAllPokemon(){
        for(int i=0; i<pokemon.size(); i++){
                pokemon.get(i).gainHp(200);
        }
        System.out.println("All Pokemons have been healed!");
    }

    /**
     * Set current pokemon to the cur index
     * @param cur
     * @return the cur value
     */
    public int setCurrentPokemon(int cur){
        this.currentPokemon = cur;
        return this.currentPokemon;
    }

    /**
     * Get next pokemon array index
     * @return
     */
    public int getNextCurPokemon(){
        return currentPokemon-1;
    }

    /**
     * Start the attack damage
     * @return Damage
     */
    public int battle(){
        int style = chooseStyle();
        int move = chooseMove(style);
        return getCurrentPokemon().fight(style,move);
    }

}
