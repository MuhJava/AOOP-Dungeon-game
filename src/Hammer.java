public class Hammer extends Tool {
    public Hammer(String name, String description, boolean requiresTarget) {
        super(name, description, requiresTarget);
    }

    public void use(GameEntity gameEntity) {
        gameEntity.onHammer();
    }
}
