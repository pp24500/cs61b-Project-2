package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class Rooms {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;

    protected int x;
    protected int y;
    protected int roomWidth;
    protected int roomHeight;
    protected Door[] doors = new Door[2];

    private class Door{
        int x;
        int y;

        public Door(int doorX, int doorY){
            x = doorX;
            y = doorY;
        }
    }

    public void createDoor(int doorNum, int x, int y){
        if(doorNum != 0 && doorNum != 1){
            throw new RuntimeException("doorNum has to be 0 or 1");
        }
        doors[doorNum] = new Door(x, y);
    }

    public Rooms(int x, int y, int roomWidth, int roomHeight){
        if(roomWidth <= 0 || roomHeight <= 0){
            throw new RuntimeException("width and height have to be greater than 0");
        }
        this.x = x;
        this.y = y;
        this.roomWidth = roomWidth;
        this.roomHeight = roomHeight;
    }

    public void draw(TETile[][] tiles){
        TETile floor = Tileset.FLOOR;
        //drawing floors
        for(int w = 0; w < roomWidth; w++){
            for(int h = 0; h < roomHeight; h++){
                tiles[x + w][y + h] = floor;
            }
        }
        // drawing walls
        TETile wall = Tileset.WALL;
        for(int w = 0; w < roomWidth; w++){
            tiles[x + w][y] = wall;
            tiles[x + w][y + roomHeight - 1] = wall;
        }
        for(int h = 0; h < roomHeight; h++){
            tiles[x][y + h] = wall;
            tiles[x + roomWidth - 1][y + h] = wall;
        }
        //drawing doors
        TETile door = Tileset.FLOOR;
        for(int i = 0; i < doors.length; i++){
            if(doors[i] != null){
                tiles[doors[i].x][doors[i].y] = door;
            }
        }
    }

    public static boolean isBetween(int value, int min, int max) // inclusive min, exclusive max.
    {
        return((min < value) && (value <= max));
    }
    public boolean isCollided(Rooms room){
        if (
                (isBetween(room.x + room.roomWidth, this.x,this.x + this.roomWidth) ||
                        isBetween(this.x + this.roomWidth, room.x, room.x + room.roomWidth)) &&
                        (isBetween(room.y + room.roomHeight, this.y, this.y + this.roomHeight) ||
                                isBetween(this.y + this.roomHeight, room.y, room.y + room.roomHeight))){
                return true;
        }
        return false;
    }
    
}

