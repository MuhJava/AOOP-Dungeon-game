public class SleepingPotion extends Potion {
    public SleepingPotion(String name, String description) {
        super(name, description);
    }

    public void use(Player player) {
        UI.displayMessage("You begin to feel drowsy... and fall into a slumber\n");
        if (player.toolBeltContains("Alarm clock")) {
            UI.displayMessage("The next thing you know your alarm clock goes off!");
            UI.displayMessage("You snap out of it feeling alert");
        } else {
            player.takesDamage(10);
            UI.displayMessage("You wake up feeling drained of energy");
        }
    }
}
