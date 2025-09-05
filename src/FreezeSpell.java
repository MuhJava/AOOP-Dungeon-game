public class FreezeSpell extends Spell {
    public FreezeSpell(String name, String description, boolean requiresTarget) {
        super(name, description, requiresTarget);
    }

    /**
     *
     * @param gameEntity Game entity to cast freeze on
     */
    public void use(GameEntity gameEntity) {
        gameEntity.onFreeze();
    }
}
