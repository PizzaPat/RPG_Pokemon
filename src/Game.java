import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.*;

/**
 * Main class
 */
public class Game extends JPanel implements Runnable,ActionListener,MouseListener,KeyListener{


    /**
     * Player ash for player
     */
    private Player ash;

    /**
     * File to get BGM file
     */
    private File file;

    /**
     * Current map grid that will be used during the game
     */
    private Map currentMap;

    /**
     * Map holder to check the map grid
     */
    private char[][] map;

    /**
     * Reveal holder to reveal the area
     */
    private boolean[][] revealed;

    /**
     * Menu number to let user check
     */
    private int menuNum;

    /**
     * All the scenes and logic for GUI when encounter specific event
     */
    private boolean wildPokeScene,trainerScene,lockMovement,victoryScene,
            cityScene;

    /**
     * Holder of style and move
     */
    private int style,move;

    /**
     * Holder for received EXP and Money
     */
    private int receivedExp,receivedMoney;

    /**
     * BGM clip to play functional music
     */
    private Clip bgm;

    /**
     * Buffered Image to import all the graphics
     */
    private BufferedImage background,ashPic,pikachu,bulbasaur,charmander,
            squirtle, oddish, staryu,zapdos,ponyta,questionMark,city,
            wildPokemon,trainer,menu,avatar,shop;

    /**
     * Wild pokemon holder to be used when player encounter wild pokemon area
     */
    private Pokemon wildPoke;

    /**
     * Condition to check for each choices the player made when encounter
     * the fight
     */
    private boolean firstChoice,secondChoice,thirdChoice,escape;

    /**
     * Opponent holder to be used when player encounter wild pokemon area
     */
    private Opponent oppo;

    /**
     * JButton to choose pokemon
     */
    private JButton newGame,continueGame,charmanderSelect,squirtleSelect,
            bulbasaurSelect,pikachuSelect;

    /**
     * Condition to check if the setting has been setup (after welcome screen)
     */
    private boolean beginGame;

    /**
     * File forest to be played during the game
     */
    private File forestBGM;


    /**
     * Default constructor
     */
    public Game(){
        ash = null;
        file = new File("pokemon.dat");
        currentMap = new Map();
        beginGame = false;
        menuNum=1;
        wildPokeScene=false;
        trainerScene=false;
        lockMovement=false;
        victoryScene=false;
        bgm = null;
        receivedExp=0;
        receivedMoney=0;
        try {
            bgm = AudioSystem.getClip();
        }catch (LineUnavailableException e){
            System.out.println("Line Exception");
        }
        forestBGM = new File("ForestBGM.WAV");
        playSoundLoop(bgm,forestBGM);
        try{
            background = ImageIO.read(new File("graphics/background.png"));
            ashPic = ImageIO.read(new File("graphics/ash.png"));
            questionMark = ImageIO.read(new File("graphics/questionMark.png"));
            city = ImageIO.read(new File("graphics/city.png"));
            wildPokemon = ImageIO.read(new File("graphics/monster.png"));
            trainer = ImageIO.read(new File("graphics/trainer.png"));
            menu = ImageIO.read(new File("graphics/menuBackground.png"));
            avatar = ImageIO.read(new File("graphics/ashAvatar.png"));
            pikachu = ImageIO.read(new File("graphics/pikachu.png"));
            bulbasaur = ImageIO.read(new File("graphics/bulbasaur.png"));
            charmander = ImageIO.read(new File("graphics/charmander.png"));
            squirtle = ImageIO.read(new File("graphics/squirtle.png"));
            ponyta = ImageIO.read(new File("graphics/ponyta.png"));
            staryu = ImageIO.read(new File("graphics/staryu.png"));
            zapdos = ImageIO.read(new File("graphics/zapdos.png"));
            oddish = ImageIO.read(new File("graphics/oddish.png"));
            shop = ImageIO.read(new File("graphics/shoppingMall.png"));
        }catch(IOException e){
            System.out.println("One of the pictures was not loaded");
        }

        //Check New Game and Continue. If Continue, Check file existence and load saved file, if not,
        //direct a user to the new game setting
        this.setLayout(null);
        addKeyListener(this);
        setFocusable(true);
        buttonsConstuction();
    }

    /**
     * Main method
     * @param args default String argument
     */
    public static void main(String[] args) {
        Game game = new Game();
        JFrame frame = new JFrame();
        frame.setLocation(0,0);
        frame.setBounds(0,0,1200,700);
        frame.setTitle("Pokemon Element");
        frame.getContentPane().add(game);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        game.run();
    }

    /**
     * Key Press
     * @param e when event of keyboard happens
     */
    public void keyPressed(KeyEvent e) {
        char eventChar = ' ';
        escape = false;

        if(ash.getNumPotionsLeft()>0){
            if(e.getKeyCode()==KeyEvent.VK_Z){
                ash.usePotion();
            }
        }

        if(cityScene){
            if(e.getKeyCode()==KeyEvent.VK_1){
                ash.buyPotion();
            }
            if(e.getKeyCode()==KeyEvent.VK_2){
                ash.buyPokeball();
            }
            if(e.getKeyCode()==KeyEvent.VK_3){
                ash.healAllPokemon();
            }
            if(e.getKeyCode()==KeyEvent.VK_4){
                cityScene=false;
                lockMovement=false;
            }
        }
        else if (wildPokeScene) {
            if(e.getKeyCode()==KeyEvent.VK_X){
                int generateCaptureChance = (int) ((Math.random() * 5) + 1) * wildPoke.getHp();
                if(generateCaptureChance <= 30){
                    ash.addPokemon(wildPoke);
                    wildPokeScene=false;
                    victoryScene = true;
                    return;
                }
            }
            if (firstChoice) {
                if (e.getKeyCode() == KeyEvent.VK_2) {
                    escape = true;
                    if (escape) {
                        wildPokeScene = false;
                        lockMovement = false;
                    }
                } else {
                    firstChoice = false;
                    secondChoice = true;
                }
            } else if (secondChoice) {
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    style = 1;
                } else if (e.getKeyCode() == KeyEvent.VK_2) {
                    style = 2;
                }
                secondChoice = false;
                thirdChoice = true;
            } else if (thirdChoice) {
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    System.out.println("Getting second");
                    move = 1;
                } else if (e.getKeyCode() == KeyEvent.VK_2) {
                    System.out.println("Getting second2");
                    move = 2;
                } else if (e.getKeyCode() == KeyEvent.VK_3) {
                    System.out.println("Getting second2");
                    move = 3;
                }
                thirdChoice = false;
                secondChoice = true;
                double elementEffect = PokemonBattles.returnElementEffect(ash, wildPoke);
                int hit = (int) Math.round(ash.getCurrentPokemon().fight(style, move) * elementEffect);
                wildPoke.loseHp(hit);
                if (wildPoke.getHp() <= 0) {
                    wildPokeScene = false;
                    victoryScene = true;
                    return;
                }
                elementEffect = PokemonBattles.returnElementEffect(wildPoke, ash);
                hit = (int) Math.round(wildPoke.fight((int) (Math.random() * 2) + 1,
                        (int) (Math.random() * 3) + 1) * elementEffect);
                ash.getCurrentPokemon().loseHp(hit);
                if (ash.getCurrentPokemon().getHp() <= 0) {
                    wildPokeScene = false;
                    lockMovement = false;
                    return;
                }
            }
        } else if (trainerScene) {
                if (firstChoice) {
                    if (e.getKeyCode() == KeyEvent.VK_2) {
                        escape = true;
                        if (escape) {
                            trainerScene = false;
                            lockMovement = false;
                        }
                    } else {
                        firstChoice = false;
                        secondChoice = true;
                    }
                } else if (secondChoice) {
                    if (e.getKeyCode() == KeyEvent.VK_1) {
                        style = 1;
                    } else if (e.getKeyCode() == KeyEvent.VK_2) {
                        style = 2;
                    }
                    secondChoice = false;
                    thirdChoice = true;
                } else if (thirdChoice) {
                    if (e.getKeyCode() == KeyEvent.VK_1) {
                        move = 1;
                    } else if (e.getKeyCode() == KeyEvent.VK_2) {
                        move = 2;
                    } else if (e.getKeyCode() == KeyEvent.VK_3) {
                        move = 3;
                    }
                    thirdChoice = false;
                    secondChoice = true;
                    double elementEffect = PokemonBattles.returnElementEffect(ash, oppo.getCurrentPokemon());
                    int hit = (int) Math.round(ash.getCurrentPokemon().fight(style, move) * elementEffect);
                    oppo.getCurrentPokemon().loseHp(hit);
                    if (oppo.getCurrentPokemon().getHp() <= 0) {
                        if (oppo.getCurrentPokemon().getHp() <= 0 && oppo.getNextCurPokemon() != 0) {
                            oppo.setCurrentPokemon(oppo.getNextCurPokemon());
                        }
                        else {
                            trainerScene = false;
                            victoryScene = true;
                            return;
                        }
                    }
                    elementEffect = PokemonBattles.returnElementEffect(oppo.getCurrentPokemon(), ash);
                    hit = (int) Math.round(oppo.getCurrentPokemon().fight((int) (Math.random() * 2) + 1,
                            (int) (Math.random() * 3) + 1) * elementEffect);
                    ash.getCurrentPokemon().loseHp(hit);
                    if (ash.getCurrentPokemon().getHp() <= 0) {
                        trainerScene = false;
                        lockMovement = false;
                        return;
                    }
                }
            }

            if (victoryScene) {
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    victoryScene = false;
                    lockMovement = false;
                }
            }

            if (!lockMovement) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    eventChar = ash.goNorth(currentMap);
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    eventChar = ash.goWest(currentMap);
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    eventChar = ash.goSouth(currentMap);
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    eventChar = ash.goEast(currentMap);
                }
                repaint();
            }

            if (eventChar == 'o' || eventChar == 'w' || eventChar == 'c') {
                wildPokeScene = true;
                lockMovement = true;
                currentMap.reveal(ash.getLocation());
            }
            //Event s: Starting Point
            if (eventChar == 's') {
                System.out.println("Came to starting point");
            }
            //Event f: Finish line
            else if (eventChar == 'f') {
                System.out.println("Transferring to next level");
                ash.incLevel();
                currentMap.generateArea(ash.getLevel());
                try {
                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("pokemon.dat"));
                    out.writeObject(ash);
                    out.close();
                } catch (IOException error) {
                    System.out.println("Error processing file");
                }
            }
            //Event n: Nothing
            else if (eventChar == 'n') {
                System.out.println("You've encountered nothing");
            }
            //Event o: Encountered an Opponent
            else if (eventChar == 'o') {
                trainerScene = true;
                wildPokeScene = false;
                firstChoice = true;
                secondChoice = true;
                oppo = new OpponentMaker().makeRandomOpponent();
            }
            //Event w: Encountered a WildPokemon
            else if (eventChar == 'w') {
                wildPokeScene = true;
                firstChoice = true;
                secondChoice = true;
                wildPoke = PokemonMaker.makeWildPokemon();
            }
            //Event c: Arrived the city
            else if (eventChar == 'c') {
                trainerScene = false;
                wildPokeScene = false;
                cityScene = true;
            }
            //Do nothing otherwise (Logical will never arrive at this statement)
            else {
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {

                if (menuNum < ash.getPokemonNum()) {
                    ash.setCurrentPokemon(menuNum);
                    menuNum++;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                if (menuNum > 1) {
                    ash.setCurrentPokemon(menuNum);
                    menuNum--;
                }
            }
        }

    /**
     * When key is released
     * @param e event when specific key is pressed
     */
    public void keyReleased(KeyEvent e){

    }

    /**
     * When key is typed
     * @param e event when specific key is pressed
     */
    public void keyTyped(KeyEvent e){}


    /**
     * Action Performed, to receive event from buttons
     * @param a event for buttons
     */
    public void actionPerformed(ActionEvent a){
        //NEW GAME
        if(a.getSource()==newGame){
            newGame.setVisible(false);
            continueGame.setVisible(false);
            Point start = new Point(currentMap.findStartLocation());
            ash = new Player("Ash", 25, start);
            currentMap.generateArea(1);
            ash.setLocation(currentMap.findStartLocation());
            selectPokemon();
        }

        //CONTINUE
        if(a.getSource()==continueGame){
            newGame.setVisible(false);
            continueGame.setVisible(false);
            beginGame = true;
            if (file.exists()) {
                try {
                    ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                    ash = (Player) in.readObject();
                    currentMap.generateArea(ash.getLevel());
                    in.close();
                } catch (IOException e) {
                    System.out.println("File is not found");
                    System.exit(0);
                } catch (ClassNotFoundException e) {
                    System.out.println("Could not find class.");
                    System.exit(0);
                }
            } else {
                System.out.println("Saved file does not exist.\nCreating a new game");
               // welcomeMenu(ash);
                currentMap.generateArea(1);
                Point start = new Point(currentMap.findStartLocation());
                ash = new Player("Ash", 25, start);
            }
            ash.setLocation(currentMap.findStartLocation());
        }


        //Charmander
        if(a.getSource()==charmanderSelect){
            charmanderSelect.setVisible(false);
            squirtleSelect.setVisible(false);
            bulbasaurSelect.setVisible(false);
            pikachuSelect.setVisible(false);
            ash.addPokemon(PokemonMaker.makeStartPokemon(1));
            beginGame=true;
        }

        //Squirtle
        if(a.getSource()==squirtleSelect){
            charmanderSelect.setVisible(false);
            squirtleSelect.setVisible(false);
            bulbasaurSelect.setVisible(false);
            pikachuSelect.setVisible(false);
            ash.addPokemon(PokemonMaker.makeStartPokemon(2));
            beginGame=true;
        }

        if(a.getSource()==bulbasaurSelect){
            charmanderSelect.setVisible(false);
            squirtleSelect.setVisible(false);
            bulbasaurSelect.setVisible(false);
            pikachuSelect.setVisible(false);
            ash.addPokemon(PokemonMaker.makeStartPokemon(3));
            beginGame=true;
        }

        if(a.getSource()==pikachuSelect){
            charmanderSelect.setVisible(false);
            squirtleSelect.setVisible(false);
            bulbasaurSelect.setVisible(false);
            pikachuSelect.setVisible(false);
            ash.addPokemon(PokemonMaker.makeStartPokemon(4));
            beginGame=true;
        }
    }

    /**
     * Event when mouse is exited given area
     * @param e event of a mouse for specific method
     */
    public void mouseExited(MouseEvent e){

    }

    /**
     * Event when mouse is exited given area
     * @param e event of a mouse for specific method
     */
    public void mouseReleased(MouseEvent e){

    }

    /**
     * Event when mouse is exited given area
     * @param e event of a mouse for specific method
     */
    public void mouseEntered(MouseEvent e){

    }

    /**
     * Event when mouse is exited given area
     * @param e event of a mouse for specific method
     */
    public void mousePressed(MouseEvent e){

    }

    /**
     * Event when mouse is exited given area
     * @param e event of a mouse for specific method
     */
    public void mouseClicked(MouseEvent e){

    }

    /**
     * Run the thread
     */
    @Override
    public void run(){
       while(true){
            repaint();
            try{
                map = currentMap.getMap();
                revealed = currentMap.getRevealed();
                Thread.sleep(100);
            } catch(InterruptedException e){}
        }
    }

    /**
     * Paint Component to draw GUI
     * @param g Graphics class
     */
    public void paintComponent(Graphics g){
        g.drawImage(background, 0, 0, 1200, 700, null);
        //BEGIN GAME
        if(beginGame) {
            g.drawImage(menu, 570, 30, 600, 500, null);
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (revealed[i][j] == false) {
                        g.drawImage(questionMark, i * 100 + 100, j * 100 + 100, 50, 50, null);
                    } else {
                        if (map[i][j] == 'c') {
                            g.drawImage(city, i * 100 + 100, j * 100 + 100, 50, 50, null);
                        }
                        if (map[i][j] == 'w') {
                            g.drawImage(wildPokemon, i * 100 + 100, j * 100 + 100, 50, 50, null);
                        }
                        if (map[i][j] == 'o') {
                            g.drawImage(trainer, i * 100 + 100, j * 100 + 100, 50, 50, null);
                        }
                    }
                }
            }
            g.setColor(Color.red);
            g.drawImage(ashPic, (int) ash.getLocation().getX() * 100 + 100, (int) ash.getLocation().getY() * 100 + 100, 50, 50, null);


            //SCREEN MENU
            if (wildPokeScene) {
                g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                g.setColor(Color.WHITE);
                g.drawString("Z.) Use Potion", 700, 200);
                g.drawString("X.) Use Pokeball", 700, 230);
                g.drawString("HP: " + Integer.toString(ash.getCurrentPokemon().getHp()), 650, 360);
                g.drawString("HP: " + Integer.toString(wildPoke.getHp()), 1000, 270);
                g.drawString(wildPoke.getName(), 1000, 300);
                g.drawString(ash.getCurrentPokemon().getName(), 650, 390);
                g.setFont(new Font("TimesRoman", Font.BOLD, 18));
                if(firstChoice) {
                    g.drawString("1. Fight   2. Escape   ", 800, 400);
                }
                else if(secondChoice){
                    g.drawString("1. Basic Moves   2. Special Moves   ", 800, 400);
                }
                else if(style==1){
                    g.drawString("1. Slam   2. Tackle   3. Mega Punch", 800, 400);
                }
                else if(style==2){
                    g.setFont(new Font("TimesRoman", Font.BOLD, 15));
                    g.drawString(ash.getCurrentPokemon().returnSpecialMenu(), 700, 410);
                }


                switch (wildPoke.getName()) {
                    case "Pikachu":
                        g.drawImage(pikachu, 900, 140, 120, 120, null);
                        break;
                    case "Bulbasaur":
                        g.drawImage(bulbasaur, 900, 140, 120, 120, null);
                        break;
                    case "Charmander":
                        g.drawImage(charmander, 900, 140, 120, 120, null);
                        break;
                    case "Oddish":
                        g.drawImage(oddish, 900, 140, 120, 120, null);
                        break;
                    case "Ponyta":
                        g.drawImage(ponyta, 900, 140, 120, 120, null);
                        break;
                    case "Squirtle":
                        g.drawImage(squirtle, 900, 140, 120, 120, null);
                        break;
                    case "Staryu":
                        g.drawImage(staryu, 900, 140, 120, 120, null);
                        break;
                    case "Zapdos":
                        g.drawImage(zapdos, 900, 140, 120, 120, null);
                        break;
                    default:
                        break;
                }

                switch (ash.getCurrentPokemon().getName()) {
                    case "Pikachu":
                        g.drawImage(pikachu, 640, 220, 120, 120, null);
                        break;
                    case "Bulbasaur":
                        g.drawImage(bulbasaur, 640, 220, 120, 120, null);
                        break;
                    case "Charmander":
                        g.drawImage(charmander, 640, 220, 120, 120, null);
                        break;
                    case "Oddish":
                        g.drawImage(oddish, 640, 220, 120, 120, null);
                        break;
                    case "Ponyta":
                        g.drawImage(ponyta, 640, 220, 120, 120, null);
                        break;
                    case "Squirtle":
                        g.drawImage(squirtle, 640, 220, 120, 120, null);
                        break;
                    case "Staryu":
                        g.drawImage(staryu, 640, 220, 120, 120, null);
                        break;
                    case "Zapdos":
                        g.drawImage(zapdos, 640, 220, 120, 120, null);
                        break;
                    default:
                        break;
                }
            }
            else if (trainerScene) {
                g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                g.setColor(Color.WHITE);
                g.drawString("HP: " + Integer.toString(ash.getCurrentPokemon().getHp()), 650, 360);
                g.drawString("HP: " + Integer.toString(oppo.getCurrentPokemon().getHp()), 1000, 270);
                if (firstChoice)
                    g.drawString("1. Fight   2. Escape   ", 700, 400);
                else if (secondChoice) {
                    g.drawString("1. Basic Moves   2. Special Moves   ", 700, 400);
                } else if (style == 1) {
                    g.drawString("1. Slam   2. Tackle   3. Mega Punch", 700, 400);
                } else if (style == 2) {
                    g.drawString(ash.getCurrentPokemon().returnSpecialMenu(), 700, 400);
                }
                g.drawImage(trainer, 1050, 130, 80, 80, null);
                switch (oppo.getCurrentPokemon().getName()) {
                    case "Pikachu":
                        g.drawImage(pikachu, 900, 140, 120, 120, null);
                        break;
                    case "Bulbasaur":
                        g.drawImage(bulbasaur, 900, 140, 120, 120, null);
                        break;
                    case "Charmander":
                        g.drawImage(charmander, 900, 140, 120, 120, null);
                        break;
                    case "Oddish":
                        g.drawImage(oddish, 900, 140, 120, 120, null);
                        break;
                    case "Ponyta":
                        g.drawImage(ponyta, 900, 140, 120, 120, null);
                        break;
                    case "Squirtle":
                        g.drawImage(squirtle, 900, 140, 120, 120, null);
                        break;
                    case "Staryu":
                        g.drawImage(staryu, 900, 140, 120, 120, null);
                        break;
                    case "Zapdos":
                        g.drawImage(zapdos, 900, 140, 120, 120, null);
                        break;
                    default:
                        break;
                }

                switch (ash.getCurrentPokemon().getName()) {
                    case "Pikachu":
                        g.drawImage(pikachu, 670, 220, 120, 120, null);
                        break;
                    case "Bulbasaur":
                        g.drawImage(bulbasaur, 670, 200, 120, 120, null);
                        break;
                    case "Charmander":
                        g.drawImage(charmander, 670, 200, 120, 120, null);
                        break;
                    case "Oddish":
                        g.drawImage(oddish, 670, 200, 120, 120, null);
                        break;
                    case "Ponyta":
                        g.drawImage(ponyta, 670, 200, 120, 120, null);
                        break;
                    case "Squirtle":
                        g.drawImage(squirtle, 670, 200, 120, 120, null);
                        break;
                    case "Staryu":
                        g.drawImage(staryu, 670, 200, 120, 120, null);
                        break;
                    case "Zapdos":
                        g.drawImage(zapdos, 670, 140, 120, 120, null);
                        break;
                    default:
                        break;
                }
            }
            else if(cityScene){
                g.drawImage(shop, 900, 140,300,300, null);
                g.setFont(new Font("TimesRoman", Font.BOLD, 25));
                g.setColor(Color.WHITE);
                g.drawString("1. Buy Potion", 630, 200);
                g.drawString("2. Buy Pokeball", 630, 230);
                g.drawString("3. Heal All Pokemon ", 630, 260);
                g.drawString("4. Exit ", 630, 290);


                g.drawString("Potion: " + Integer.toString(ash.getNumPotionsLeft()), 630, 370);
                g.drawString("Pokeballs: " + Integer.toString(ash.getNumPokeballsLeft()), 630, 400);
                g.drawString("Money: " + Integer.toString(ash.getMoney()), 630, 340);

            }
            else if(victoryScene){
                if(currentMap.getCharAtLoc(ash.getLocation())=='w') {
                    receivedExp = wildPoke.getLevel() * 3;
                    receivedMoney = wildPoke.getLevel() * 2;
                    currentMap.removeOppAtLoc(ash.getLocation());
                }
                else if(currentMap.getCharAtLoc(ash.getLocation())=='o') {
                    receivedExp = oppo.getCurrentPokemon().getLevel() * 3;
                    receivedMoney = oppo.getCurrentPokemon().getLevel() * 2;
                    currentMap.removeOppAtLoc(ash.getLocation());
                }
                g.setFont(new Font("TimesRoman", Font.BOLD, 25));
                g.setColor(Color.WHITE);
                g.drawString("Victory!", 630, 170);
                g.setFont(new Font("TimesRoman", Font.BOLD, 15));
                g.setColor(Color.GREEN);
                g.drawString(ash.getCurrentPokemon().getName(), 630, 200);
                g.setColor(Color.WHITE);
                g.drawString("Current Exp: " + ash.getCurrentPokemon().getExp(), 630, 230);
                g.drawString("Current Next Level Exp: : " + ash.getCurrentPokemon().getNextLevelExp(), 630, 260);
                g.drawString("Gained Exp: " + receivedExp, 630, 290);
                g.drawString("Gained Pokecoins : " + receivedMoney, 630, 320);
            }
            else {
                //Main Stats
                g.drawImage(avatar, 620, 140, 120, 120, null);
                g.setFont(new Font("TimesRoman", Font.BOLD, 30));
                g.setColor(Color.WHITE);
                g.drawString(ash.getName(), 630, 300);
                g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                g.drawString("HP: " + Integer.toString(ash.getHp()), 630, 340);
                g.drawString("Z.) Use Potion", 900, 200);
                g.drawString("Potion: " + Integer.toString(ash.getNumPotionsLeft()), 630, 370);
                g.drawString("Pokeballs: " + Integer.toString(ash.getNumPokeballsLeft()), 630, 400);
                switch (ash.getCurrentPokemon().getName()) {
                    case "Pikachu":
                        g.drawImage(pikachu, 770, 140, 120, 120, null);
                        break;
                    case "Bulbasaur":
                        g.drawImage(bulbasaur, 770, 140, 120, 120, null);
                        break;
                    case "Charmander":
                        g.drawImage(charmander, 770, 140, 120, 120, null);
                        break;
                    case "Oddish":
                        g.drawImage(oddish, 770, 140, 120, 120, null);
                        break;
                    case "Ponyta":
                        g.drawImage(ponyta, 770, 140, 120, 120, null);
                        break;
                    case "Squirtle":
                        g.drawImage(squirtle, 770, 140, 120, 120, null);
                        break;
                    case "Staryu":
                        g.drawImage(staryu, 770, 140, 120, 120, null);
                        break;
                    case "Zapdos":
                        g.drawImage(zapdos, 770, 140, 120, 120, null);
                        break;
                    default:
                        break;
                }

                g.setColor(Color.WHITE);
                g.setFont(new Font("TimesRoman", Font.BOLD, 30));
                g.drawString(ash.getCurrentPokemon().getName(), 800, 300);
                g.setFont(new Font("TimesRoman", Font.BOLD, 20));
                g.drawString("HP: " + Integer.toString(ash.getCurrentPokemon().getHp()), 800, 340);
                g.drawString("Level: " + Integer.toString(ash.getCurrentPokemon().getLevel()), 800, 380);
            }
        }
    }

    /**
     * Choose Pokemon: Allow a player to choose pokemon
     * @param player pass a "player" object to change a current pokemon
     */
    private static void choosePokemon(Player player){
        int storeChoice;
        player.listPokemon();
        System.out.println((player.getPokemonNum()+1)+". Return");
        System.out.println("Select the pokemon you wish to switch.\nThe pokemon must not be fainted");
        storeChoice = CheckInput.checkIntRange(1, player.getPokemonNum()+1);
        if(storeChoice==player.getPokemonNum()+1) {
            player.setCurrentPokemon(1);
            return;
        }
        player.setCurrentPokemon(storeChoice);
        System.out.print("---> Selected: ");player.displayCurrentPokemon();
    }





    /**
     * Button Construction, to create object for buttons
     */
    private void buttonsConstuction(){
        newGame = new JButton("New Game");
        newGame.setBounds(50,50,100,50);
        newGame.addActionListener(this);
        add(newGame);

        continueGame = new JButton("Continue");
        continueGame.setBounds(50,150,100,50);
        continueGame.addActionListener(this);
        add(continueGame);


    }

    /**
     * Select Pokemon and import the icon of the picture
     */
    private void selectPokemon(){
        //Create Button
        charmanderSelect = new JButton();
        charmanderSelect.setIcon(new ImageIcon("graphics/charmander.png"));
        charmanderSelect.setBounds(50,200,150,150);
        charmanderSelect.addActionListener(this);
        add(charmanderSelect);

        squirtleSelect = new JButton();
        squirtleSelect.setIcon(new ImageIcon("graphics/squirtle.png"));
        squirtleSelect.setBounds(350,200,150,150);
        squirtleSelect.addActionListener(this);
        add(squirtleSelect);

        bulbasaurSelect = new JButton();
        bulbasaurSelect.setIcon(new ImageIcon("graphics/bulbasaur.png"));
        bulbasaurSelect.setBounds(650,200,150,150);
        bulbasaurSelect.addActionListener(this);
        add(bulbasaurSelect);

        pikachuSelect = new JButton("Squirtle");
        pikachuSelect.setIcon(new ImageIcon("graphics/pikachu.png"));
        pikachuSelect.setBounds(950,200,150,150);
        pikachuSelect.addActionListener(this);
        add(pikachuSelect);

    }

    /**
     * Player
     * @param clip to provide functionality of sound
     * @param sound selected song to play
     */
    private static void playSoundLoop(Clip clip, File sound){
        try {
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (Exception e) {
            System.out.println("Open Music Failed");
        }
    }

    /**
     * Play sound without the loop
     * @param clip to provide functionality of sound
     * @param sound selected song to play
     */
    private static void playSound(Clip clip, File sound){
        try {
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.start();
        } catch (Exception e) {
            System.out.println("Open Music Failed");
        }
    }
    /**
     * Stop Sound: Stop the Background Music
     * @param clip pass in a "clip" object to stop the current BGM
     */
    static void stopSound(Clip clip){
        try{
            clip.stop();
            clip.close();
        }catch (NullPointerException e){
            System.out.println("Stop Music Failed");
        }
    }

}