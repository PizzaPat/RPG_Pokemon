import javax.swing.*;

/**
 * Pokemon Battles Class
 */
public class PokemonBattles extends JPanel{
    /**
     * Instance
     * fightTable: double type 2D array to make a factor to the pokemon damage
     */
    private final static double [][] fightTable = {{.5,.5,2,1},{2,.5,.5,1},{.5,2,.5,1},{1,2,.5,.5}};


    /**
     * Attacker makes damage to Defender
     * @param attacker Attack who causes a damage
     * @param defender Defender who receiving a damage
     */
    private static void trainerBattle(Trainer attacker, Trainer defender){
                double attackerElementEffect = fightTable[attacker.getCurrentPokemon().getType()]
                        [defender.getCurrentPokemon().getType()];
                int hit = attacker.battle();
                hit = (int) Math.round(hit * attackerElementEffect);
                defender.getCurrentPokemon().loseHp(hit);
                hitSummary(hit, attackerElementEffect);
    }

    /**
     * Entering Pokemon Battle
     * @param player ash
     * @param poke wild
     */
    public static void wildPokemonBattle(Player player, Pokemon poke){
        boolean fightEnd = false;
        while(!fightEnd) {
            fightOverviewWildPoke(player, poke);
            boolean caughtPokemon;
            boolean turnRepeat = false;
            //Player Attack
            System.out.println("1. Fight\n2. Bag\n3. Pokemon");
            switch (CheckInput.checkIntRange(1, 3)) {
                case 1: //Fight
                    //Player damage attack
                    playerAttack(player, poke);
                    break;
                case 2: // Bag
                    caughtPokemon = bag(player, poke, 1);
                    if (caughtPokemon == true)
                        return;
                    turnRepeat = true;
                    break;
                case 3: //Pokemon
                    choosePokemon(player, 1);
                    turnRepeat = true;
                    break;
                default: //Unreachable Condition
                    break;
            }
            if(turnRepeat!=true) {
                //Check Wild Pokemon Hp
                if (poke.getHp() <= 0) {
                    victory(player, poke);
                    return;
                }

                //End of Player turn

                //Wild Pokemon Attack
                wildPokemonAttack(poke, player);
                //Check Player's Pokemon Hp
                if (player.getCurrentPokemon().getHp() <= 0) {
                    boolean live = choosePokemon(player, 2);
                    if (live == false) {
                        System.out.println(player.getLossSpeech());
                        return;
                    }
                }
            }
        }
    }

    /**
     * Entering an Opponent Battle (A Trainer, or Team Rocket)
     * @param player ash
     * @param opponent normal trainer or team rocket
     */
    public static void opponentBattle(Player player, Opponent opponent) {
        System.out.println(player.getAttackSpeech());
        System.out.println(opponent.getAttackSpeech());
        boolean fightEnd = false;
        while(fightEnd!=true) {
            boolean turnRepeat = false;
            fightOverviewTrainers(player, opponent);
            //Player Turn
            System.out.println("1. Fight\n2. Bag\n3. Pokemon");
            switch (CheckInput.checkIntRange(1, 3)) {
                case 1:
                    trainerBattle(player, opponent);
                    break;
                case 2: // Bag
                    bag(player, opponent.getCurrentPokemon(), 2);
                    turnRepeat = true;
                    break;
                case 3: //Pokemon
                    choosePokemon(player, 1);
                    turnRepeat = true;
                    break;
                default:
                    break;
            }
            if(turnRepeat!=true) {
                //Check if opponent has more Pokemons and Pokemon's Hp
                if (opponent.getCurrentPokemon().getHp() <= 0 && opponent.getNextCurPokemon() != 0) {
                    opponent.setCurrentPokemon(opponent.getNextCurPokemon());
                    System.out.println(opponent.getName() + " chose " + opponent.getCurrentPokemon().getName() + "!!");
                }
                if (opponent.getCurrentPokemon().getHp() <= 0) {
                    victory(player, opponent.getCurrentPokemon());
                    System.out.println(player.getWinSpeech());
                    System.out.println(opponent.getLossSpeech());
                    return;
                } else {
                    //OpponentAttack
                    trainerBattle(opponent, player);

                    //Check Player Pokemon Hp
                    if (player.getCurrentPokemon().getHp() <= 0) {
                        System.out.println("Your pokemon is fainted");
                        boolean live = choosePokemon(player, 2);
                        if (live == false) {
                            System.out.println(player.getLossSpeech());
                            System.out.println(opponent.getWinSpeech());
                            return;
                        }
                    }
                }
            }
        }
    }


    /**
     * Angry Pokemon attacking Ash
     * @param trainer ash
     */
    public static void angryPokemon(Trainer trainer){
        System.out.println("An angry pokemon hit you!");
        int damage = (int)(Math.random()*4+2);
        trainer.loseHp(damage);
    }

    /**
     * Angry Trainer attacking Ash
     * @param trainer ash
     */
    public static void angryTrainer(Trainer trainer){
        System.out.println("An angry trainer hit you!");
        int damage = (int)(Math.random()*3+1);
        trainer.loseHp(damage);
    }

    /**
     * Display Victory Summary
     * @param player ash
     * @param defeatedPokemon wild
     */
    private static void victory(Player player, Pokemon defeatedPokemon){
        System.out.println("        Victory!\n**---------------------**");
        System.out.println("--->"+player.getCurrentPokemon().getName());
        System.out.println("Current Exp: "+player.getCurrentPokemon().getExp());
        System.out.println("Current Next Level Exp: "+player.getCurrentPokemon().getNextLevelExp());
        System.out.print("Current Pokecoins: ");player.spendMoney(0);
        System.out.println("-----------------");
        int receivedExp = defeatedPokemon.getLevel()*3;
        int receivedMoney = defeatedPokemon.getLevel()*2;
        System.out.println("Gained Exp: "+receivedExp);
        System.out.println("Gained Pokecoins: "+receivedMoney);
        System.out.println("-----------------");

        System.out.println("Current Exp: "+player.getCurrentPokemon().gainExp(receivedExp));
        System.out.println("Current Next Level Exp: "+player.getCurrentPokemon().getNextLevelExp());
        System.out.print("Current Pokecoins: ");player.spendMoney(-receivedMoney);
        System.out.println("-----------------");


    }


    /**
     * Fight Overview for Wild Pokemon Battle
     * @param player ash
     * @param poke wild
     */
    public static void fightOverviewWildPoke(Trainer player, Pokemon poke){
        System.out.println("----------------*****-------------------");
        System.out.println("\tWild Pokemon");
        System.out.print("\t\t");poke.displayPokemon();
        System.out.print("\t\t\t\t vs.\n  ");
        System.out.println("\t"+player.getName()+"'s");
        System.out.print("\t\t");
        player.getCurrentPokemon().displayPokemon();
        System.out.println("----------------*****-------------------");

    }

    /**
     * Fight Overview for Trainer Battle
     * @param player ash
     * @param opponent a normal trainer or Team Rocket
     */
    public static void fightOverviewTrainers(Player player, Opponent opponent){
        System.out.println("----------------*****-------------------");
        System.out.println("\t"+opponent.getName()+"'s");
        System.out.print("\t\t");opponent.getCurrentPokemon().displayPokemon();
        System.out.print("\t\t\t\t vs.\n  ");
        System.out.println("\t"+player.getName()+"'s");
        System.out.print("\t\t");
        player.getCurrentPokemon().displayPokemon();
        System.out.println("----------------*****-------------------");
    }

    /**
     * Details for a hit and an effect from the element
     * @param hit damage
     * @param elementEffect factors caused by each pokemon element
     */
    private static void hitSummary(int hit, double elementEffect){
        System.out.println("---------------------");
        System.out.print("Damage: "+hit+"\n");
        if(Math.floor(elementEffect)==0){
            System.out.println("It was not effective...");
        }
        else if (Math.floor(elementEffect)==1){
            System.out.println("It was a normal damage");
        }
        else if(Math.floor(elementEffect)==2){
            System.out.println("It was very effective!!");
        }
        else{}
        System.out.println("---------------------");
    }

    /**
     * Bag menu
     * @param player ash
     * @param poke opponent pokemon (wild or trainer)
     * @return a counter to compare the condition value
     */
    private static boolean bag(Player player, Pokemon poke, int opponentKind){
        System.out.println("Number of pokeballs left: "+player.getNumPokeballsLeft());
        System.out.println("Number of potions left: "+player.getNumPotionsLeft());
        System.out.println("-------------------------");
        System.out.println("1. Use pokeball\n2. Use potion");
        switch(CheckInput.checkIntRange(1,2)){//Returning 0 means no move taken
            case 1:
                if(opponentKind==1) {//opponentKind == 1 (wild pokemon)
                    if (player.getNumPokeballsLeft() > 0) {
                        player.usePokeball();
                        System.out.println("...");
                        System.out.println("!!!");
                        int generateCaptureChance = (int) ((Math.random() * 5) + 1) * poke.getHp();
                        if (generateCaptureChance <= 30) {//success
                            player.addPokemon(poke);
                            poke.displayPokemon();
                            System.out.print(" is caught!!\n");
                            return true;
                        } else System.out.println(poke.getName() + " broke free!");break;//fail
                    } else
                        System.out.println("You have no Pokeballs left"); return false;
                }
                else{//opponentKind == 2 (Trainer)
                    System.out.println("You cannot catch a trainer Pokemon!"); return false;
                }

            case 2:
                if(player.getNumPotionsLeft()>0){
                    player.usePotion();return false;}
                else {System.out.println("You have no potions left");return false;}
            default: return false;
        }
        return false;
    }

    /**
     * Choose Pokemon, 1 for scenario normal choosing, 2 for fainted pokemon
     * @param player ash
     * @return integer to make a counter in certain situation
     */
    private static boolean choosePokemon(Player player, int scenario){
        if(scenario==1) {//scenario==1 (Choosing Pokemon normally)
            do {
                player.listPokemon();
                System.out.println("Select the pokemon you wish to switch.\nThe pokemon must not be fainted");
                player.setCurrentPokemon(CheckInput.checkIntRange(1, player.getPokemonNum()));
                System.out.print("Selected: ");
                player.displayCurrentPokemon();
            } while (player.getCurrentPokemon().getHp() <= 0);
            return true;
        }
        else {//scenario==2 (Pokemon fainted)
            do {
                player.listPokemon();
                System.out.println((player.getPokemonNum() + 1) + ". Give up");
                System.out.println("Your Pokemon has fainted\nChoose a live pokemon");
                int choice = CheckInput.checkIntRange(1, player.getPokemonNum() + 1);
                if (choice == player.getPokemonNum() + 1)
                    return false;
                player.setCurrentPokemon(choice);
                System.out.print("Selected: ");
                player.displayCurrentPokemon();
            } while (player.getCurrentPokemon().getHp() <= 0);
            return true;
        }
    }

    /**
     * Attacking a wild pokemon
     * @param player ash
     * @param poke wild pokemon
     */
    private static void playerAttack(Player player, Pokemon poke){
        double elementEffect = fightTable[player.getCurrentPokemon().getType()]
                [poke.getType()];
        int hit = (int)Math.round(player.battle()*elementEffect);
        poke.loseHp(hit);
        hitSummary(hit,elementEffect);
        if(player.getCurrentPokemon().getHp()<=0)
            choosePokemon(player,2);
    }

    /**
     * Getting hit by a wild pokemon
     * @param poke wild pokemon
     * @param player ash
     */
    private static void wildPokemonAttack(Pokemon poke, Player player){
        double elementEffect = fightTable[poke.getType()]
                [player.getCurrentPokemon().getType()];
        int hit = (int)Math.round(poke.fight((int)(Math.random()*2)+1,
                (int)(Math.random()*3)+1)*elementEffect);
        player.getCurrentPokemon().loseHp(hit);
        hitSummary(hit,elementEffect);
    }

    public static double returnElementEffect(Player player, Pokemon poke){
        return fightTable[player.getCurrentPokemon().getType()]
                [poke.getType()];
    }

    public static double returnElementEffect(Pokemon poke, Player player){
        return fightTable[poke.getType()]
                [player.getCurrentPokemon().getType()];
    }

}
