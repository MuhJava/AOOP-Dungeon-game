import java.util.Random;

public class Food extends GameItem {
    private int pointsRestored;
    public Food(String name, String description, int pointsRestored) {
        super(name, description);
        this.setPointsRestored(pointsRestored);
    }

    // Array of all available food names for randomisation and easy readability and potential expansion of food
    private static final String[] foodNames = {"Cake", "Sandwich", "Bread"};

    public int getPointsRestored() {
        return pointsRestored;
    }

    public void setPointsRestored(int pointsRestored) {
        this.pointsRestored = pointsRestored;
    }

    /**
     * Restores players powerpoints by food restore value
     * @param player Player object to receive powerpoint restore
     */
    public void use(Player player) {
        UI.displayMessage("You eat the " + this.getName().toLowerCase() + ", it restores " + this.pointsRestored + " power points");
        player.restorePowerpoints(this.pointsRestored);
    }

    /**
     *
     * @return Returns array of valid food names
     */
    public static String[] getFoodNames() {
        return foodNames;
    }

    /**
     *
     * @return Returns regex for matching valid food names
     */
    public static String buildFoodNamesRegex() {
        StringBuilder patternBuilder = new StringBuilder("(?i)");
        for (String foodName : getFoodNames()) {
            if (patternBuilder.length() > 0) {
                patternBuilder.append("|");
            }
            patternBuilder.append((foodName));
        }
        return patternBuilder.toString();
    }
}
