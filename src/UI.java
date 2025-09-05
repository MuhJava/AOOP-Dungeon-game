import java.util.ArrayList;
import java.util.Scanner;

public class UI {
    static char[][] viewableMap = new char[11][11];

    static Integer previousPlayerLocationX = null;
    static Integer previousPlayerLocationY = null;

    public static void displayMessage(String message) {
        System.out.println(message);
    }
    public static void displayTakeMessage(String itemTaken) {
        System.out.println("You take the " + itemTaken);
    }
    public static void displayDropMessage(String itemDropped) {
        System.out.println("You drop the " + itemDropped);
    }
    public static void displayHelpMessage() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("====================== Playing the game ======================");
        System.out.println("# The objective of the game is to successfully navigate the dungeon to the treasure room");
        System.out.println("# You may lose powerpoints through room encounters, reaching less than 1 powerpoint will result in a loss");
        System.out.println("# There are many in-game items that may help you along your way! How you use them is up to you");
        System.out.println("# You have a bag for items, a spellbook holder and a basic toolbelt. You may open your inventory at any time");
        System.out.println("====================== Commands ======================");
        System.out.println("# To perform actions in game, enter commands in the format of an action word followed by the specific thing to perform that action on");
        System.out.println("# Action examples: Walk, Take, Drop, Use, Eat, Drink, Cast, Examine, Look");
        System.out.println("# Action argument examples: North, Loaf of bread, Spanner, Cake, Potion, Freeze spell, Hammer, Around");
        System.out.println("# You may quit at any time with \"quit\" command");
        System.out.println("====================== Navigation ======================");
        System.out.println("# Navigating is based on top-down perspective, i.e up and north are equivalent");
        System.out.println("# There are multiple levels to the dungeon, finding the exit will progress you to the next one until one contains the treasure");
        System.out.println("# You will need to find your way through rooms that may have doors or walls around them");
        System.out.println("# The grand wizard that sent you on your quest supplied you with a magic map that inscribes upon it where you've been and where you are");
        System.out.println("# You may view your map at any time with map command");
        System.out.println("# ====================== View again with command \" help \" ======================");

        System.out.println("\n\n Press enter to continue...");
        scanner.nextLine();

        printGap();

    }
    // To remove the clutter of previous prints to console
    public static void printGap() {
        for (int i = 0; i < 20; i++) {
            System.out.println();
        }
    }

    public static void displayCommandPromptMessage() {
        System.out.print("\nWhat would you like to do?\n>>> ");
    }
    /**
     * Displays the players utilities and health
     * @param player Player objects stored values to get
     */
    public static void displayHUD(Player player) {
        ArrayList<String> spells = new ArrayList<>();
        ArrayList<String> tools = new ArrayList<>();
        for (Spell spell : player.getSpellbookHolder()) {
            if (spell != null) {
                spells.add(spell.getName());
            }
        }
        for (Tool tool : player.getToolBelt()) {
            if (tool != null) {
                tools.add(tool.getName());
            }
        }
        if (spells.isEmpty()) {
            spells.add("Empty");
        } else {
            spells.remove("Empty");
        }
        if (tools.isEmpty()) {
            tools.add("Empty");
        } else {
            tools.remove("Empty");
        }
        // Counting length of fixed words such as Powerpoints and then accounting for the item that may be in that slot to correct for the number of asterisk in display
        int totalAsteriksLength = 16 + Integer.toString(player.getPowerPoints()).length() + 14 + spells.get(0).length() + 13 + tools.get(0).length() + 6;
        // Getting the number of asterisk to put on one half of the display either side of name
        int asterisksAndNameLength = (totalAsteriksLength - player.getPlayerName().length() - 2) / 2;

        /* Beginning of original method for printing asterisk
        for (int i = 0; i < asterisksAndNameLength; i++) {
            System.out.print('*');
        }
        System.out.print(" " + player.getPlayerName() + " ");
        for (int i = 0; i < asterisksAndNameLength; i++) {
            System.out.print('*');
        }
        // Since dividing name and length by two it may be an odd number that is rounded, hence adding an asterisk if it does not sum to total required length
        if (asterisksAndNameLength < totalAsteriksLength) {
            System.out.print('*');
        }


        System.out.println();
        System.out.println("* Power points: " + player.getPowerPoints() + " | Spellbook: " + spells + " | Toolbelt: " + tools + " *");

        for (int i = 0; i < totalAsteriksLength; i++) {
            System.out.print('*');
        }
        System.out.println();

         End of orignal method for printing asterisk
         */

        // Start of CoPilots version when queried for correct design for asterisk output, it is the same process of building a String except simply allocating the String parts to a StringBuilder which is easier to read and understand
        StringBuilder asteriskLine = new StringBuilder();
        // First half of asterisk before name
        for (int i = 0; i < asterisksAndNameLength; i++) {
            asteriskLine.append('*');
        }

        asteriskLine.append(" ").append(player.getPlayerName()).append(" ");

        // Second half of asterisk after name
        for (int i = 0; i < asterisksAndNameLength; i++) {
            asteriskLine.append('*');
        }
        // Accounting for is the sum is an odd number
        if (asteriskLine.length() < totalAsteriksLength) {
            asteriskLine.append('*');
        }
        System.out.println(asteriskLine);
        System.out.println("* Power points: " + player.getPowerPoints() + " | Spellbook: " + spells + " | Toolbelt: " + tools + " *");
        for (int i = 0; i < totalAsteriksLength; i++) {
            System.out.print("*");
        }
        System.out.println();
    }

    /**
     * Creates a new viewable map filled with spaces
     */
    public static void startNewMap() {
        for (int i = 10; i >= 0; i--) {
            for (int j = 0; j < 11; j++) {
                viewableMap[i][j] = ' ';
            }
        }
    }

    /**
     * Updates the players viewable map with their most recent position
     * @param playerCoordinates
     */
    public static void updateMap(short[] playerCoordinates) {
        if (previousPlayerLocationX != null && previousPlayerLocationY != null) {
            viewableMap[previousPlayerLocationX][previousPlayerLocationY] = ' ';
        }
        int horizontalLeftX = (playerCoordinates[0] * 2);
        int horizontalRightX = ((playerCoordinates[0] * 2) + 2);
        int horizontalY = ((playerCoordinates[1] * 2) + 1);
        int verticalX = ((playerCoordinates[0] * 2) + 1);
        int verticalTopY = ((playerCoordinates[1] * 2) + 2);
        int verticalBottomY = ((playerCoordinates[1] * 2));
        viewableMap[horizontalY][horizontalLeftX] = '|';
        viewableMap[horizontalY][horizontalRightX] = '|';
        viewableMap[verticalTopY][verticalX] = '—';
        viewableMap[verticalBottomY][verticalX] = '—';
        viewableMap[(playerCoordinates[1] * 2) + 1][(playerCoordinates[0] * 2) + 1] = 'X';
        previousPlayerLocationX = ((playerCoordinates[1] * 2) + 1);
        previousPlayerLocationY = ((playerCoordinates[0] * 2) + 1);
    }

    /**
     * Displays the current state of the viewable map
     */
    public static void displayMap() {
        for (int i = 10; i >= 0; i--) {
            for (int j = 0; j < 11; j++) {
                System.out.print(viewableMap[i][j]);
            }
            System.out.println();
        }
    }
    // Co-pilot queried for how to italicise output but implemented my own way no code copied
    public static void italicEnabled(boolean bool) {
        if (bool == true) {
            System.out.println("\033[3m");
        } else {
            System.out.println("\033[0m");
        }
    }

    public static String getEntityIcon(GameEntity gameEntity) {
        if (gameEntity instanceof Obstacle) {
            return "!";
        } else if (gameEntity instanceof Potion) {
            return "?";
        } else if (gameEntity instanceof Food) {
            return "+";
        } else if (gameEntity instanceof Tool) {
            return "┌";
        } else if (gameEntity instanceof Spell) {
            return "☼";
        }
        return " ";
    }
}
