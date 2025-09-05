import java.util.Arrays;
import java.util.Scanner;

public class XrayPotion extends Potion {

    Scanner scanner = new Scanner(System.in);
    public XrayPotion(String name, String description) {
        super(name, description);
    }

    /**
     * Provides option to target direction and applies display room to chosen adjacent section
     * @param level Level object for level values
     * @param player Player object for player location
     */

    public boolean use(Level level, Player player) {
        UI.displayMessage("The walls around you appear to be getting thinner...\n");
        UI.displayMessage("In which cardinal direction would you like to look? (north, east, south, west): ");
        String directionToLook = scanner.nextLine().toLowerCase();
        switch (directionToLook) {
            case "west":
                short[] westSection = player.getSurroundingCoordinates(player.getCurrentCoordinates()).get(3);
                if (!player.checkXrayCommand(level, westSection)) {
                    UI.displayMessage("I just see blackness, must be a wall section");
                } else if (Level.containsCoordinate(level.getRoomSections(), (westSection))) {
                    level.getRoomAtCoordinate(westSection).displayRoom();
                }
                return true;
            case "east":
                short[] eastSection = player.getSurroundingCoordinates(player.getCurrentCoordinates()).get(1);
                if (!player.checkXrayCommand(level, eastSection)) {
                    UI.displayMessage("I just see blackness, must be a wall section");
                } else if (Level.containsCoordinate(level.getRoomSections(), (eastSection))) {
                    level.getRoomAtCoordinate(eastSection).displayRoom();
                }
                return true;
            case "north":
                short[] northSection = player.getSurroundingCoordinates(player.getCurrentCoordinates()).get(0);
                if (!player.checkXrayCommand(level, northSection)) {
                    UI.displayMessage("I just see blackness, must be a wall section");
                } else if (Level.containsCoordinate(level.getRoomSections(), (northSection))) {
                   level.getRoomAtCoordinate(northSection).displayRoom();
                }
                return true;
            case "south":
                short[] southSection = player.getSurroundingCoordinates(player.getCurrentCoordinates()).get(2);
                if (!player.checkXrayCommand(level, southSection)) {
                    UI.displayMessage("I just see blackness, must be a wall section");
                } else if (Level.containsCoordinate(level.getRoomSections(), (southSection))) {
                    level.getRoomAtCoordinate(southSection).displayRoom();
                }
                return true;
            default:
                UI.displayMessage("Unrecognised direction");
                return false;
        }
    }
}
