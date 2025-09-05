import java.lang.reflect.Array;
import java.util.Random;

public class GameItemFactory {

    /**
     * Box item may be spell or potion
     * @return Returns box item
     */
    public static GameItem makeBoxItem() {
        short rnd = (short) (Math.random()*20);
        if (rnd <10) {
            return GameItemFactory.createRandomPotion();
        } else {
            return GameItemFactory.createRandomSpell();
        }
    }

    // Creates food objects
    public static Food createFood(String name) {
        return switch (name) {
            case "Cake" -> new Food(name, "A slice of cake", 3);
            case "Sandwich" -> new Food(name, "A hearty looking sandwich", 10);
            case "Bread" -> new Food("Loaf of bread", "A nutritious loaf of bread", 5);
            default -> throw new IllegalArgumentException("Unknown food: " + name);
        };
    }
    public static Food createRandomFood() {
        Random rnd = new Random();
        int randomIndex = rnd.nextInt(Food.getFoodNames().length);
        return createFood(Food.getFoodNames()[randomIndex]);
    }

    // Creates potion objects
    public static Potion createPotion(String name) {
        return switch (name) {
            case "Sleeping potion" -> new SleepingPotion("Potion", "A corked vial of mysterious unlabelled liquid");
            case "X-ray potion" -> new XrayPotion("Potion", "A corked vial of mysterious unlabelled liquid");
            default -> throw new IllegalArgumentException("Unknown potion: " + name);
        };
    }
    public static Potion createRandomPotion() {
        Random rnd = new Random();
        int randomIndex = rnd.nextInt(Potion.getPotionNames().length);
        return createPotion(Potion.getPotionNames()[randomIndex]);
    }

    // Creates spell objects
    public static Spell createSpell(String name) {
        return switch (name) {
            case "Freeze spell" -> new FreezeSpell(name, "A spell for freezing things", true);
            case "Teleport spell" -> new TeleportSpell(name, "A teleportation spell... but to who knows where", false);
            default -> throw new IllegalArgumentException("Unknown spell: " + name);
        };
    }
    public static Spell createRandomSpell() {
        Random rnd = new Random();
        int randomIndex = rnd.nextInt(Spell.getSpellNames().length);
        return createSpell(Spell.getSpellNames()[randomIndex]);
    }

    // Creates tool objects
    public static Tool createTool(String name) {
        return switch (name) {
            case "Hammer" -> new Hammer(name, "I imagine this will be useful for smashing something", true);
            case "Spanner" -> new Spanner(name, "Perhaps this could help me get into something");
            case "Alarm clock" -> new Tool(name, "I imagine this will wake me up if necessary", false);
            default -> throw new IllegalArgumentException("Unknown tool: " + name);
        };
    }
    public static Tool createRandomTool() {
        Random rnd = new Random();
        int randomIndex = rnd.nextInt(Tool.getToolNames().length);
        return createTool(Tool.getToolNames()[randomIndex]);
    }
}
