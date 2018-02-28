package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;
import org.junit.Test;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {


//        if (args.length < 1) {
//            System.out.println("Please enter a seed");
//            return;
//        }

        int seed = 5;
                //Integer.parseInt(args[0]);


        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
        return;
        //game.flashSequence(game.generateRandomString(5));
        //game.drawFrame(game.generateRandomString(5));
        //game.solicitNCharsInput(5);
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(this.height, 0);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        rand = new Random(seed);
        //Initialize random number generator
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        String ret = "";
        for(int i = 0; i < n; i++){
            ret += CHARACTERS[rand.nextInt(25)];
        }
        return ret;
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen

        String wot = (playerTurn) ? "Type!" : "Watch!";

        StdDraw.clear();
        StdDraw.text(0.5 * width, 0.5 * height, s);
        StdDraw.text( width / 6, 0.04 * height, "Round: " + Integer.toString(round));
        StdDraw.text( width / 2, 0.04 * height, wot);



        StdDraw.show();



    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for(int i = 0; i < letters.length(); i++){
            drawFrame(Character.toString(letters.charAt(i)));
            StdDraw.pause(1000);
            StdDraw.clear();
            drawFrame("");
            StdDraw.pause(500);
        }

    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        String temp = "";
        int count = 0;
        drawFrame("");
        while(true){
            if(StdDraw.hasNextKeyTyped()) {
                temp += Character.toString(StdDraw.nextKeyTyped());
                drawFrame(temp);
                count++;
            }
            if(count == n){
                break;
            }
        }

        return temp;
    }
    public void displayRound(){

        drawFrame("Round " + Integer.toString(round) + "!");
        StdDraw.pause(1000);

        drawFrame("");
        StdDraw.pause(1000);
    }
    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        //TODO: Establish Game loop

        gameOver = false;
        round = 1;
        int pauseInterval = 1000;
        while(!gameOver){
            String target = generateRandomString(round);

            displayRound();
            flashSequence(target);

            playerTurn = true;
            String temp = solicitNCharsInput(round);
            StdDraw.pause(pauseInterval / 4);

            if(temp.equals(target)) {
                playerTurn = false;
                drawFrame(ENCOURAGEMENT[rand.nextInt(ENCOURAGEMENT.length)]);
                StdDraw.pause(pauseInterval);
                round++;

            }else{
                gameOver = true;
                drawFrame("GAME OVER");
                StdDraw.pause(pauseInterval * 2);
            }

        }
    }

}
