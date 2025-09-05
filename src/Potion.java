import java.util.Random;
import java.util.regex.Pattern;

public class Potion extends GameItem {

    public Potion() {}

    public Potion(String name, String description) {
        super(name, description);
    }

    // Array of all available potion names for randomisation and easy readability and potential expansion of potions
    private static final String[] potionNames = {"Sleeping potion", "X-ray potion"};

    public static String[] getPotionNames() {
        return potionNames;
    }

    public static String buildPotionNamesRegex() {
        StringBuilder patternBuilder = new StringBuilder("(?i)");
        for (String potionName : getPotionNames()) {
            if (patternBuilder.length() > 0) {
                patternBuilder.append("|");
            }
            patternBuilder.append((potionName));
        }
        return patternBuilder.toString();
    }

    public void use() {
        UI.displayMessage("You drink the mysterious liquid");
    }
    public void use(Player player) {}
    public boolean use(Level level, Player player) {
        return false;
    }

}
