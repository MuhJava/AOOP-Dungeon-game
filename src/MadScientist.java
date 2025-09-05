public class MadScientist extends Obstacle implements Freezable {
    public MadScientist(String name, String description, int baseDamage, String onActivation) {
        super(name, description, baseDamage, onActivation);
    }

    public void onFreeze() {
        freeze();
    }

    @Override
    public void freeze() {
        UI.displayMessage("You freeze the " + this.getName() + " solid");
        this.setIsActive(false);
    }
}
