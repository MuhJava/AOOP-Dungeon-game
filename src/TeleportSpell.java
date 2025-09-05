public class TeleportSpell extends Spell {
    public TeleportSpell(String name, String description, boolean requiresTarget) {
        super(name, description, requiresTarget);
    }

    /**
     * Changes player objects coordinates to random room coordinate in level object
     * @param level Level object to get traversable coordinate values
     * @param player Player object to affect coordinates on
     */
    public void use(Level level, Player player) {
        UI.displayMessage("The room around you begins to twist and blur....");
        short rndRoomNumber = (short) (Math.random()*(level.getRoomSections().size()));
        short[] randomRoomCoordinates = level.getRoomSections().get(rndRoomNumber);
        player.setCurrentCoordinates(randomRoomCoordinates);
        UI.displayMessage("Suddenly you seem to be somewhere you were not before");
    }
}
