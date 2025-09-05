import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Level {

    // Normally this value could be changed to increase gametime as level walls, entry and exits would be automatically generated, but for the scope of this assignment this is the maximum coded for
    private final short maxLevels = 3;
    private short currentLevelNumber = 1;

    // As with maxLevels, levelWidth and levelHeight should be customisable really, but for the scope again, they can only be 5. This is just to show how it would look
    private final short levelWidth = 5;
    private final short levelHeight = 5;
    private short[][] wallSections;
    ArrayList<short[]> roomSections = new ArrayList<>();
    private short[] exitSection;
    private short[] entrySection;

    private List<Room> levelRooms = new ArrayList<>();

    // Wouldn't normally be hardcoded but since we are not making a procedurally generated map

    /**
     * Generates the level map sections based on level number
     * @param currentLevel The current level number to generate
     */
    public void generateLevel(int currentLevel) {
        // Reset entry and exit for automatic re-generation
        setEntrySection(null);
        setExitSection(null);
        switch (currentLevel) {
            case 1:
                wallSections = new short[][] {{0,0}, {0,1}, {0,2}, {0,3}, {0,4}, {2,0}, {2,1}, {2,2}};
                // For hardcoded assignment set values
                // exitSection = new short[] {3, 4};
                // entrySection = new short[] {1,0};
                randomiseEntryExitSections();
                addRoomSections();
                break;
            case 2:
                wallSections = new short[][] {{0,1}, {0,2}, {2,1}, {2,2}, {2,3}, {2,4}};
                // For hardcoded assignment set values
                // exitSection = new short[] {0, 4};
                // entrySection = new short[] {3,0};
                randomiseEntryExitSections();
                addRoomSections();
                break;
            case 3:
                wallSections = new short[][] {{0,1}, {1,1}, {2,1}, {1,3}, {2,3}, {3,3}, {4,3}};
                // For hardcoded assignment set values
                // exitSection = new short[] {0, 4};
                // entrySection = new short[] {0,0};
                randomiseEntryExitSections();
                addRoomSections();
                break;
        }
    }

    /**
     * Creates the room objects for the levels available room sections, matching its coordinates to its respective section and setting its contents, a chance for a box is added here
     */
    public void generateRooms() {
        for (short[] roomSectionCoord: roomSections) {
            Room room = new Room();
            room.setRoomCoordinate(roomSectionCoord);
            room.setContents(room.generateContents());
            short boxChance = (short) (Math.random()*(5));
            if (boxChance == 3) {
                room.setContainsBox(true);
            }
            levelRooms.add(room);
        }
    }

    /**
     * Adds the available room section coordinates to the level object for current level number
     */
    public void addRoomSections() {
        for (short x = 0; x<levelWidth; x++) {
            for (short y = 0; y<levelHeight; y++) {
                short[] tempCoordinate = {x,y};
                if (!containsCoordinate(wallSections, tempCoordinate) && !Arrays.equals(exitSection, tempCoordinate)) {
                    roomSections.add(tempCoordinate);
                }
            }
        }
        roomSections.add(this.getExitSection());
    }

    /**
     * Check if an array of coordinates contains a provided coordinate
     * @param array Array of coordinates
     * @param coordinate Coordinate to check array for
     * @return True if contains
     */
    public static boolean containsCoordinate(short[][] array, short[] coordinate) {

        for (short[] coord : array) {
            if (Arrays.equals(coord, coordinate)) {
                return true;
            }
        }
        return false;
    }
    public static boolean containsCoordinate(ArrayList<short[]> array, short[] coordinate) {

        for (short[] coord : array) {
            if (Arrays.equals(coord, coordinate)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the room object on a rooms coordinates
     * @param roomCoordinates The rooms coordinate
     * @return Returns room object
     */
    public Room getRoomAtCoordinate(short[] roomCoordinates) {
        for (Room room : this.getLevelRooms()) {
            if (Arrays.equals(room.getRoomCoordinate(), roomCoordinates)) {
                return room;
            }
        }
        throw new IllegalArgumentException("Room not found at room coordinates " + Arrays.toString(roomCoordinates));
    }

    public void randomiseEntryExitSections() {
        short spaceBetweenEntryExit = 2;
        // Loop until entry and exit sections resolved
        while (getEntrySection() == null || getExitSection() == null) {
            ArrayList<short[]> possibleEntryExitSections = new ArrayList<>();

            for (short y = 0; y < levelHeight; y++) {
                for (short x = 0; x < levelWidth; x++) {
                    short[] temp = new short[]{x, y};
                    // If the section is not a wall and the x is minimum or maximum value or the y is minimum or maximum value to find all the edge sections
                    if (!containsCoordinate(wallSections, temp) && ((temp[0] == 0 || temp[0] == levelWidth - 1) || (temp[1] == 0 || temp[1] == levelHeight - 1))) {
                        possibleEntryExitSections.add(temp);
                    }
                }
            }
            // Take a random edge section from the list
            int numberOfEntry = possibleEntryExitSections.size();
            short rnd = (short) (Math.random() * numberOfEntry);
            setEntrySection(possibleEntryExitSections.get(rnd));

            // Set the exit such that it is at least the third section away from the entrance in x or y along the edge of the map
            ArrayList<short[]> possibleExitSections = new ArrayList<>();
            for (short[] section : possibleEntryExitSections) {
                if ((section[0] > getEntrySection()[0] + spaceBetweenEntryExit || section[0] < getEntrySection()[0] - spaceBetweenEntryExit) || (section[1] > getEntrySection()[1] + spaceBetweenEntryExit || section[1] < getEntrySection()[1] - spaceBetweenEntryExit)) {
                    possibleExitSections.add(section);
                }
            }
            int numberOfExits = possibleExitSections.size();
            short rndTwo = (short) (Math.random() * numberOfExits);
            setExitSection(possibleExitSections.get(rndTwo));
        }
        /* Uncomment to make navigating the levels easier for testing purposes
        UI.displayMessage("Entry is " + Arrays.toString(this.getEntrySection()));
        UI.displayMessage("Exit is " + Arrays.toString(this.getExitSection()));
         */
    }

    public short getCurrentLevelNumber() {
        return currentLevelNumber;
    }
    public void setCurrentLevelNumber(short currentLevelNumber) {
        this.currentLevelNumber = currentLevelNumber;
    }


    public short[] getEntrySection() {
        return entrySection;
    }
    public short[] getExitSection() {
        return exitSection;
    }

    public short[][] getWallSections() {
        return wallSections;
    }
    public short getLevelWidth() {
        return levelWidth;
    }
    public short getLevelHeight() {
        return levelHeight;
    }
    public ArrayList<short[]> getRoomSections() {
        return roomSections;
    }
    public List<Room> getLevelRooms() {
        return levelRooms;
    }
    public short getMaxLevels() {
        return maxLevels;
    }
    public void setExitSection(short[] exitSection) {
        this.exitSection = exitSection;
    }

    public void setEntrySection(short[] entrySection) {
        this.entrySection = entrySection;
    }

    public void testEntryExitSectionGeneration() {

        char[][] mapGrid = new char[5][5];
        for (int i = 4; i >= 0; i--) {
            for (int j = 0; j <= 4; j++) {
                short[] coordTemp = new short[] {(short) j, (short) i};
                if (Arrays.equals(coordTemp, getEntrySection())) {
                    mapGrid[i][j] = 'E';
                } else if (Arrays.equals(coordTemp, getExitSection())) {
                    mapGrid[i][j] = 'X';
                } else if (containsCoordinate(this.getWallSections(), coordTemp)) {
                    mapGrid[i][j] = 'W';
                }
                System.out.print(mapGrid[i][j]);
            }
            System.out.println();
        }
        // For unit testing loop, wipe to re-assign
        setEntrySection(null);
        setExitSection(null);
    }

}
