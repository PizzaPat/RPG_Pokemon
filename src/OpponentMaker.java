import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Opponent Maker Class
 */
public class OpponentMaker {
    /**
     * Instance
     * opponentList: creating array list for Opponent object
     */
    ArrayList<Opponent> opponentList;

    /**
     * Opponent Maker: read file from OpponentList.txt and assign instances to Opponent
     * array list
     */
    public OpponentMaker(){
        opponentList = new ArrayList<Opponent>();
        try {
            Scanner read = new Scanner(new File("OpponentList.txt"));
            do{
                String name = read.nextLine();
                String hpStr = read.nextLine();
                int hp = Integer.parseInt(hpStr);
                String attackSp = read.nextLine();
                attackSp = attackSp.replaceAll("#","\n");
                String lossSp = read.nextLine();
                lossSp = lossSp.replaceAll("#","\n");
                String winSp = read.nextLine();
                winSp = winSp.replaceAll("#","\n");
                opponentList.add(new Opponent(name,hp,name+": "+attackSp,name+": "+lossSp,name+": "+winSp));
            }while(read.hasNext());
            read.close();
        }catch(FileNotFoundException fnf) {
            System.out.println("File was not found");
        }
    }

    /**
     * Make Random Opponent
     * @return new Opponent object
     */
    public Opponent makeRandomOpponent(){
        int r = (int)(Math.random()*(opponentList.size()));
        //Assigning elements from opponentList into a temporary variables
        String newName = opponentList.get(r).getName();
        int newHp = opponentList.get(r).getHp();
        String newAttSp = opponentList.get(r).getAttackSpeech();
        String newLossSp = opponentList.get(r).getLossSpeech();
        String newWinSp = opponentList.get(r).getWinSpeech();

        //Creating a new Opponent object with assigned elements
        Opponent getNewOpponent = new Opponent(newName,newHp,newAttSp,newLossSp,newWinSp);

        //Team Rocket gets one more pokemon
        if(getNewOpponent.getName().compareTo("Team Rocket")==0)
            getNewOpponent.addPokemon(PokemonMaker.makeTypePokemon((int)Math.random()*3));

        //Any trainer has at least 1 pokemon
        getNewOpponent.addPokemon(PokemonMaker.makeTypePokemon((int)Math.random()*3));

        return getNewOpponent;
    }



}
