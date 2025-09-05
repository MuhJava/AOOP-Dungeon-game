public class GameItem extends GameEntity {
    public GameItem() {}

    public GameItem(String name) {
        super(name);
    }

    public GameItem(String name, String description) {
        super(name, description);
    }

    // Default message for no use cases
    public void use(GameEntity entityName) {
        UI.displayMessage("The " + this.getName() + " has no effect on the " + entityName);
    }
    public void use() {
        UI.displayMessage("You can't use that right now");
    }
    public void examine() {
        UI.displayMessage(this.getDescription());
    }
}
