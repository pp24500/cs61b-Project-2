package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final long SEED = 28731;
    private static final Random RANDOM = new Random();
    /*Draw a single Hexagon with length s at position x, y.
    x is the most-left position to start drawing.


     */

    private static void helperExpand(TETile[][] tiles, int x, int y, int s, int currentLength, TETile tileStyle){
        // if current row number is greater than s, then we call helperContrast to build the bottom half of the hexagon.
        if((currentLength - s) / 2 + 1 == s){
            for(int i = 0; i < currentLength; i++){
                tiles[x + i][y] = tileStyle;
                helperContrast(tiles, x, y - 1, s, currentLength, tileStyle);
            }
            return;
        }
        for(int i = 0; i < currentLength; i++){
            tiles[x + i][y] = tileStyle;
        }
        helperExpand(tiles, x - 1, y - 1, s, currentLength + 2, tileStyle);
    }
    private static void helperContrast(TETile[][] tiles, int x, int y, int s, int currentLength, TETile tileStyle){
        if(currentLength == s){
            for(int i = 0; i < currentLength; i++){
                tiles[x + i][y] = tileStyle;
            }
            return;
        }
        for(int i = 0; i < currentLength; i++){
            tiles[x + i][y] = tileStyle;
        }
        helperContrast(tiles, x + 1, y - 1, s, currentLength - 2, tileStyle);
    }

    public static void addHexagon(TETile[][] tiles, int x, int y, int s){
        TETile randomTile = randomTile();
        helperExpand(tiles, x, y, s, s, randomTile);

    }
    public static void hexagonTesselation(TETile[][] tiles, int x, int y, int s){
        //building the middle column, total of 5 hexagon
        for(int i = 0; i < 5; i++){
            addHexagon(tiles, x, y - i * 2 * s, s);
        }
        //the column to the right of the middle column
        for(int i = 0; i < 4; i++){
            addHexagon(tiles, (x + (2 * s - 1)), (y - s) - i * 2 * s, s);
        }
        //column to the left of middle column
        for(int i = 0; i < 4; i++){
            addHexagon(tiles, (x - (2 * s - 1)), (y - s) - i * 2 * s, s);
        }
        //right-most
        for(int i = 0; i < 3; i++){
            addHexagon(tiles, (x + 2 * (2 * s - 1)), (y - 2 * s) - i * 2 * s, s);
        }
        //left-most
        for(int i = 0; i < 3; i++){
            addHexagon(tiles, (x - 2 * (2 * s - 1)), (y - 2 * s) - i * 2 * s, s);
        }
    }
    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(3);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.PLAYER;
            default: return Tileset.WALL;
        }
    }



    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] hexagonTiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                hexagonTiles[x][y] = Tileset.NOTHING;
            }
        }
        hexagonTesselation(hexagonTiles, 25, 40, 3);
        ter.renderFrame(hexagonTiles);
    }
}
