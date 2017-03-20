/**
 * Opponent Class
 */
public class Opponent extends Trainer {
    /**
     * Instances
     * type: specify an opponent type element
     * attackSpeech: When a fight start, opponent says this line
     * winSpeech: When a fight ends and opponent win, opponent says this line
     * lossSpeech: When a fight ends and opponent lost, opponent says this line
     */
    private int type;
    private String attackSpeech;
    private String winSpeech;
    private String lossSpeech;


    /**
     * Opponent Constructor
     * @param name of the opponent
     * @param hp of the opponent
     * @param attackSpeech of the opponent
     * @param lossSpeech of the opponent
     * @param winSpeech of the opponent
     */
    public Opponent(String name, int hp, String attackSpeech, String lossSpeech, String winSpeech){
        super(name,hp);
        this.type = (int)(Math.random()*3);
        this.attackSpeech = attackSpeech;
        this.lossSpeech = lossSpeech;
        this.winSpeech = winSpeech;
    }


    /**
     * Attack Speech
     */
    @Override
    public String getAttackSpeech(){
        return attackSpeech;
    }

    /**
     * Winning Speech
     */
    @Override
    public String getWinSpeech(){
        return winSpeech;
    }

    /**
     * Losing Speech
     */
    @Override
    public String getLossSpeech(){
        return lossSpeech;
    }


    /**
     * Choose Style
     * @return Random style
     */
    @Override
    public int chooseStyle(){
        return (int)((Math.random()*2)+1);
    }

    /**
     * Choose Move
     * @param style style does not matter in this random move method (opponent random)
     * @return move to attack
     */
    @Override
    public int chooseMove(int style){
        return (int)((Math.random()*3)+1);
    }

    /**
     * Get Opponent Type
     * @return type of pokemon
     */
    public int getOppType(){
        return type;
    }
}
