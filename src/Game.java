import com.sun.java.accessibility.util.TopLevelWindowListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game {
    private Player player;
    private static Difficulty difficulty;
    private Level level;
    private boolean winLoseConditionMet;
    Room previousRoom = null;
    private boolean displayedRoom = false;

    Game() {}

    // Game constructor to build from save file
    Game(Player savedPlayer, Difficulty savedDifficulty, Level savedLevel) {
        this.player = savedPlayer;
        difficulty = savedDifficulty;
        this.level = savedLevel;
    }


    public static void main(String[] args) {

        Game game = new Game();

        //If player has saved game, else...
        game.startNewGame();

        UI.displayMessage("\nYou have bravely entered the abandoned ruins of an ancient castle, what trials or treasures await?");

        game.runGame();

    }


    public void runGame() {

        Scanner scanner = new Scanner(System.in);
        player = this.getPlayer();
        Level currentLevel = this.getLevel();
        player.setCurrentLevel(currentLevel);
        Room currentRoom;

        while (!winLoseConditionMet) {
            // Breaks loop when player reaches next level incrementing level number for player object, then loadLevel() will set level object level number to current player level number to match
            while (player.getCurrentLevelNumber() == level.getCurrentLevelNumber() && !winLoseConditionMet) {
                currentRoom = player.getCurrentRoom();
                // 3 basic conditions for game state, playing a scenario (room), starting a level, or completing the game
                if (!Arrays.equals(currentRoom.getRoomCoordinate(), currentLevel.getEntrySection()) && !Arrays.equals(currentRoom.getRoomCoordinate(), currentLevel.getExitSection())) {
                    playCurrentRoom(scanner, currentRoom);
                } else if (Arrays.equals(currentRoom.getRoomCoordinate(), currentLevel.getEntrySection())) {
                    UI.displayMessage("\nYou find yourself at the large doors to a dungeon floor");
                    playCurrentRoom(scanner, currentRoom);
                } else {
                    UI.displayMessage("Congratulations you got the gold");
                }
                checkWinLoseCondition();
            }
            loadLevel();
        }
    }

    /**
     * Take a player command input
     * @param scanner Receives scanner for input
     * @param player Takes player object to run command input method on
     */
    public void getPlayerCommand(Scanner scanner, Player player) {
        UI.displayCommandPromptMessage();
        String playerCommand = scanner.nextLine();
        player.takeCommandInput(this.getLevel(), playerCommand);
    }
    // Overloading command input for obstacle scenarios
    /**
     * Take a player command input
     * @param scanner Receives scanner for input
     * @param player Takes player object to run command input method on
     * @param obstacle Obstacle in the room currently interacting with player
     */
    public void getPlayerCommand(Scanner scanner, Player player, Obstacle obstacle) {
        UI.displayCommandPromptMessage();
        String playerCommand = scanner.nextLine();
        player.takeCommandInput(this.getLevel(), playerCommand, obstacle);
    }

    /**
     * To play out a room scenario, displays room, shows player HUD and will take input
     * @param scanner Receives scanner for input
     * @param currentRoom Room object currently interacting with player
     */
    public void playCurrentRoom(Scanner scanner, Room currentRoom) {
        UI.updateMap(player.getCurrentCoordinates());
        // Prevent repeatedly displaying the same room
        if (previousRoom != null && previousRoom.equals(currentRoom)) {
            displayedRoom = true;
        } else if (previousRoom != null && !previousRoom.equals(currentRoom)) {
            displayedRoom = false;
        }
       if (currentRoom.hasObstacle()) {
           while (currentRoom.hasObstacle()) {
               // Displayed room boolean purposely excluded so the player can still see an obstacle if one exists in the room
               UI.displayHUD(player);
               if (!displayedRoom) {
                   currentRoom.displayRoom();
               }
               Obstacle obstacle = currentRoom.getObstacle();
               getPlayerCommand(scanner, player, obstacle);
               previousRoom = currentRoom;
           }
       } else {
           UI.displayHUD(player);
           if (!displayedRoom) {
               currentRoom.displayRoom();
           }
           getPlayerCommand(scanner, player);
           previousRoom = currentRoom;
       }
    }
    // Starting a new game, involving a new player and level on which to begin playing
    public void startNewGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\nWelcome to the dungeons!");
        UI.displayHelpMessage();
        setDifficulty(scanner);
        this.setPlayer(createPlayer(scanner));
        this.setLevel(new Level());
        this.loadLevel();
    }

    // Load the level and its rooms on which the player will play on, static current level kept track in Level class on loading player placed at entry point, required to run each new level
    /*
    public void loadLevel() {
        this.getLevel().generateLevel(level.getCurrentLevelNumber());
        this.getLevel().generateRooms();
        this.getPlayer().setCurrentCoordinates(this.getLevel().getEntrySection());
        this.getPlayer().setCurrentLevelNumber(level.getCurrentLevelNumber());
    }

     */
    // Upon loading a level, take what level number the player is supposed to be on, set to that, generate that level and its rooms, initialise player location at level entrance
    public void loadLevel() {
        this.getLevel().setCurrentLevelNumber(getPlayer().getCurrentLevelNumber());
        this.getLevel().generateLevel(this.getLevel().getCurrentLevelNumber());
        this.getLevel().generateRooms();
        this.getPlayer().setCurrentCoordinates(this.getLevel().getEntrySection());
        UI.startNewMap();
    }

    /**
     * Handles creating a player based on name provided and difficulty level
     * @param scanner Scanner for input
     * @return Returns new player object
     */
    public Player createPlayer(Scanner scanner) {
        UI.displayMessage("Greetings mighty adventurer! What name are you known by?\nPlease enter name: ");
        String inputName = scanner.nextLine();
        while (!InputValidation.checkForInputLength(inputName) || !InputValidation.checkForAlphabetInput(inputName)) {
            UI.displayMessage("\nPlease enter name: ");
            inputName = scanner.nextLine();
        }
        return new Player(inputName, getDifficulty());
    }

    // Allows player to set the game difficulty
    public void setDifficulty(Scanner scanner) {
        UI.displayMessage("Select difficultly level: \n1 -> EASY\n2 -> MEDIUM\n3 -> HARD");
        String userMenuOption = scanner.nextLine();
        while (!InputValidation.checkForMenuOptions(new String[]{"1", "2", "3"}, userMenuOption)) {
            UI.displayMessage("Select difficultly level: \n1 -> EASY\n2 -> MEDIUM\n3 -> HARD");
            userMenuOption = scanner.nextLine();
        }
        switch (userMenuOption) {
            case "1" -> difficulty = Difficulty.EASY;
            case "2" -> difficulty = Difficulty.MEDIUM;
            case "3" -> difficulty = Difficulty.HARD;
            default -> throw new IllegalStateException("Unexpected value for setting difficulty: " + userMenuOption);
        }
    }
    public Player getPlayer() {
        return this.player;
    }
    public static Difficulty getDifficulty() {
        return difficulty;
    }
    public Level getLevel() {
        return this.level;
    }
    public void setPlayer(Player player) {
        this.player = player;
    }
    public void setLevel(Level level) {
        this.level = level;
    }

    public boolean isWinLoseConditionMet() {
        return winLoseConditionMet;
    }
    public void setWinLoseConditionMet(boolean winLoseConditionMet) {
        this.winLoseConditionMet = winLoseConditionMet;
    }
    public boolean getDisplayedRoom() {
        return displayedRoom;
    }
    public void setDisplayedRoom(boolean displayedRoom) {
        this.displayedRoom = displayedRoom;
    }

    // Player loses if powerpoints reach or beneath 0, player wins if on final level and reaches exit
    public void checkWinLoseCondition() {
        if (player.getPowerPoints() <= 0) {
            setWinLoseConditionMet(true);
            UI.displayMessage("\n+-+-+-+-+-+\n|You died.|\n+-+-+-+-+-+");
        } else if (player.getCurrentLevelNumber() == level.getMaxLevels() + 1 && Arrays.equals(player.getCurrentCoordinates(), level.getExitSection())) {
            setWinLoseConditionMet(true);
            UI.displayMessage("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\n|You found the treasure and escaped!|\n+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
        }
    }
}