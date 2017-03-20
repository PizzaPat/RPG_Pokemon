import java.util.*;

/**
 * Pokemon Maker class
 */
public class PokemonMaker {

    /**
     * Make random wild pokemon
     * @return random pokmon
     */
    static Pokemon makeWildPokemon(){
        switch((int)(Math.random()*8)+1){
            case 1: return new Pikachu();
            case 2: return new Zapdos();
            case 3: return new Oddish();
            case 4: return new Bulbasaur();
            case 5: return new Staryu();
            case 6: return new Squirtle();
            case 7: return new Ponyta();
            case 8: return new Charmander();
            default: return new Pikachu();
        }
    }

    /**
     * Make random pokemon
     * @param type assigning type to random between 2 pokemons
     * @return return pokemon based on input type
     */
    static Pokemon makeTypePokemon(int type){
        int tmp = (int)(Math.random()*2)+1;
        switch(type){
            case 0: if(tmp==1){return new Charmander();} else return new Ponyta();
            case 1: if(tmp==1){return new Squirtle();} else return new Staryu();
            case 2: if(tmp==1){return new Bulbasaur();} else return new Oddish();
            case 3: if(tmp==1){return new Pikachu();} else return new Zapdos();
                default: return new Pikachu();
        }
    }

    /**
     * Starter pokemon
     * @param start selected choice with default of a level 3 pokemon that is selected
     * @return one of the pokemon for beginner
     */
    public static Pokemon makeStartPokemon(int start){
        switch(start){
            case 1: return new Charmander(3);
            case 2: return new Squirtle(3);
            case 3: return new Bulbasaur(3);
            case 4: return new Pikachu(3);
            default: return new Pikachu(3);
        }
    }
}
