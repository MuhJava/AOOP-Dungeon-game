public class Trap extends Obstacle implements Freezable, Hammerable {
    public Trap(String name, String description, int baseDamage, String onActivation) {
        super(name, description, baseDamage, onActivation);
    }

    public void onFreeze() {
        freeze();
    }
    @Override
    public void freeze() {
        UI.displayMessage("You freeze the " + this.getName().toLowerCase() + " solid" );
        this.setIsActive(false);
    }
    public void onHammer() {
        hammer();
    }
    @Override
    public void hammer() {
        UI.displayMessage("You swing the hammer at the " + this.getName().toLowerCase() + "... it smashes to bits");
        this.setIsActive(false);
    }
}
