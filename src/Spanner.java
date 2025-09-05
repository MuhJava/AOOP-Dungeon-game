public class Spanner extends Tool {
    public Spanner(String name, String description) {
        super(name, description, false);
    }

    /**
     * If box exists in a room, spanner allows for game item to be returned
     * @param room
     * @return
     */
    public GameItem use(Room room) {
        if (room.getContainsBox()) {
            UI.displayMessage("You use the spanner on the box...after some twisting and jiggling it flips open!");
            GameItem boxItem = GameItemFactory.makeBoxItem();
            UI.displayMessage("You find a " + boxItem.getName() + " inside!");
            room.setContainsBox(false);
            return boxItem;
        } else {
            UI.displayMessage("There's nothing for you to use that on in here");
        }
        return null;
    }

}
