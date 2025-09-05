import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class Spell extends GameItem {

    private boolean requiresTarget;

    public Spell(String name, String description, boolean requiresTarget) {

        super(name, description);
        this.requiresTarget = requiresTarget;

    }

    // Array of all available spell names for randomisation and easy readability and potential expansion of spells
    private static final String[] spellNames = {"Freeze spell", "Teleport spell"};

    public static String buildSpellNamesRegex() {
        StringBuilder patternBuilder = new StringBuilder("(?i)");
        for (String spellName : getSpellNames()) {
            if (patternBuilder.length() > 0) {
                patternBuilder.append("|");
            }
            patternBuilder.append((spellName));
        }
        return patternBuilder.toString();
    }

    public static String[] getSpellNames() {
        return spellNames;
    }

    public void use() {}
    public void use(GameEntity target) {}
    public boolean getRequiresTarget() {
        return requiresTarget;
    }

    public void use(Level level, Player player) {
    }
}
