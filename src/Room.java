import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Room {

    List<GameEntity> contents;

    private boolean containsBox;

    private short[] roomCoordinate;

    Room() {
        this.contents = new ArrayList<>();
    }
    private short maxNumberOfContents = 4;
    private short minNumberOfContents = 1;

    Random random = new Random();

    List<Room> rooms = new ArrayList<>();

    /**
     * Uses game entity facotry class to create any game objects for each room on a level, number of objects range using class min max contents values
     * @return List of game entities
     */
    public List<GameEntity> generateContents() {

        short rndNumberOfContents = (short) (Math.random()*(maxNumberOfContents - minNumberOfContents) + minNumberOfContents);
        List<GameEntity> contentsHolder = new ArrayList<>();

        for (int i = 0; i<rndNumberOfContents; i++) {
            contentsHolder.add(GameEntityFactory.makeRandomGameEntity());
        }
        return contentsHolder;
    }

    /**
     * Prints information about room contents
     */
    public void displayRoom() {
        UI.italicEnabled(true);
        UI.displayMessage("You find yourself within a dimly lit room");
        UI.italicEnabled(false);
        System.out.println();
        // Mixing up the game message
        String[] listOfObserveDialogue = {"You see", "You spot", "There's", "You make out"};
        ArrayList<String> listOfObservedItemNames = new ArrayList<>();
        int randomObserveDialogue;

        System.out.println("---------------------------Current Room----------------------------");
        // Can't make a switch statement using instanceof and seems too uncomplicated to make nested methods for so, I've left this logic somewhat "raw" as it's still readable and easy to understand
        for (GameEntity roomEntity : this.getContents()) {
            randomObserveDialogue = random.nextInt(listOfObserveDialogue.length);
            if (listOfObservedItemNames.contains(roomEntity.getName())) {
                UI.displayMessage(UI.getEntityIcon(roomEntity) + " " + listOfObserveDialogue[randomObserveDialogue] + " another " + roomEntity.getName().toLowerCase());
            } else {
                UI.displayMessage(UI.getEntityIcon(roomEntity) + " " + listOfObserveDialogue[randomObserveDialogue] + " a " + roomEntity.getName().toLowerCase());
            }
            listOfObservedItemNames.add(roomEntity.getName());
        }
        if (this.getContainsBox()) {
            UI.displayMessage("? A large box sits in the centre of the room, it appears to be locked");
        } else if (!this.getContainsBox() && listOfObservedItemNames.isEmpty()) {
            UI.displayMessage("This room appears to be empty");
        }
        System.out.println("-------------------------------------------------------------------");
        System.out.println();
    }

    /**
     * Checks for content in a room
     * @param contentName Content name String to check for
     * @return True if room contains entity
     */
    public boolean contentsInclude(String contentName) {
        for (GameEntity roomContent: this.getContents()) {
            if (roomContent.getName().equalsIgnoreCase(contentName)) {
                return true;
            }
        }
        return false;
    }

    /** Overloading for list of names to check against
     * Checks for content in a room
     * @param contentNames List of content names String to check for
     * @return True if room contains any of the contents
     */
    public boolean contentsInclude(List<String> contentNames) {
        for (GameEntity roomContent: this.getContents()) {
            if (contentNames.contains(roomContent.getName())) {
                return true;
            }
        }
        return false;
    }
    /** Overloading for regex content to check for
     * Checks for content in a room
     * @param pattern Content name regex String to check for
     * @return True if room contains entity
     */
    public boolean contentsInclude(Pattern pattern) {
        for (GameEntity roomContent : this.getContents()) {
            Matcher matcher = pattern.matcher(roomContent.getName());
            if (matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a room contains an obstacle
     * @return True if contains obstacle
     */
    public boolean hasObstacle() {
        for (GameEntity gameEntity: this.getContents()) {
            if (gameEntity instanceof Obstacle) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets obstacle object the room contains
     * @return Obstacle in room contents
     */
    public Obstacle getObstacle() {
        for (GameEntity gameEntity: this.getContents()) {
            if (gameEntity instanceof Obstacle) {
                return (Obstacle) gameEntity;
            }
        }
        throw new RuntimeException("Obstacle was searched for but not found");
    }

    /**
     * Gets game entity that exists in room contents
     * @param entityName Name of entity to get
     * @return Game entity from room contents
     */
    public GameEntity getRoomEntity(String entityName) {
        if (this.contentsInclude(entityName)) {
            for (GameEntity gameEntity: this.getContents()) {
                if (gameEntity.getName().equalsIgnoreCase(entityName)) {
                    return gameEntity;
                }
            }
        } else {
            UI.displayMessage("Cannot find " + entityName + " in the room");
        }
        return null;
    }

    /**
     * Gets game item that exists in room contents
     * @param itemName Name of item to get
     * @return Game item from room contents
     */
    public GameItem getRoomItem(String itemName) {
        if (this.contentsInclude(itemName)) {
            for (GameEntity gameEntity: this.getContents()) {
                if (gameEntity.getName().equalsIgnoreCase(itemName) && gameEntity instanceof GameItem) {
                    return (GameItem) gameEntity;
                }
            }
        }
        return null;
    }

    // Support for this method code found at <https://stackoverflow.com/questions/8520808/how-to-remove-specific-object-from-arraylist-in-java>

    /**
     * Removes a content from a room
     * @param contentName Name of content to remove
     * @return True if content successfully removed
     */
    public boolean removeContent(String contentName) {
    if (contentsInclude(contentName)) {
        Iterator<GameEntity> iterator = this.getContents().iterator();
        while (iterator.hasNext()) {
            GameEntity entity = iterator.next();
            if (entity.getName().equalsIgnoreCase(contentName)) {
                iterator.remove();
                // System.out.println(contentName + " removed from the room.");
                return true;
            }
        }
    } else {
        UI.displayMessage("Room does not contain " + contentName + " so it cannot be removed.");
    }
    return false;
}

    public short[] getRoomCoordinate() {
        return roomCoordinate;
    }

    public void setRoomCoordinate(short[] roomCoordinate) {
        this.roomCoordinate = roomCoordinate;
    }

    public List<GameEntity> getContents() {
        return contents;
    }

    public void setContents(List<GameEntity> contents) {
        this.contents = contents;
    }

    public String toString() {
        return null;
    }
    public boolean getContainsBox() {
        return containsBox;
    }

    public void setContainsBox(boolean containsBox) {
        this.containsBox = containsBox;
    }

    /**
     * Adds a game item to a rooms contents
     * @param gameItem Game item object to add
     */
    public void addItem(GameItem gameItem) {
        this.getContents().add(gameItem);
        // Just for checking purposes when needed
        // System.out.println(gameItem.getName() + " added to room");
    }
}
