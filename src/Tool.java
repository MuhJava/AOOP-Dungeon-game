import java.util.Random;

public class Tool extends GameItem {

    private boolean requiresTarget;
    public Tool(String name, String description, boolean requiresTarget) {
        super(name, description);
        this.requiresTarget = requiresTarget;
    }

    // Array of all available tool names for randomisation and easy readability and potential expansion of tools
    private static final String[] toolNames = {"Hammer", "Spanner", "Alarm clock"};
    public static String[] getToolNames() {
        return toolNames;
    }
    public static String buildToolNamesRegex() {
        StringBuilder patternBuilder = new StringBuilder("(?i)");
        for (String toolName : getToolNames()) {
            if (patternBuilder.length() > 0) {
                patternBuilder.append("|");
            }
            patternBuilder.append((toolName));
        }
        return patternBuilder.toString();
    }

    public GameItem use(Room room) {
        return null;
    }
    public boolean getRequiresTarget() {
        return requiresTarget;
    }
}
