import java.util.Random;

public class Obstacle extends GameEntity {
    String onActivation;
    int damageDealt;
    public Obstacle(String name, String description, int baseDamage, String onActivation) {
        super(name, description);
        this.damageDealt = setDamageDealt(baseDamage, Game.getDifficulty());
        this.onActivation = onActivation;
    }

    // Array of all available obstacle names for randomisation and easy readability and potential expansion of obstacles
    private static final String[] obstacleNames = {"Floor trap", "Mad scientist"};

    public static String[] getObstacleNames() {
        return obstacleNames;
    }

    public static String buildObstacleNamesRegex() {
        StringBuilder patternBuilder = new StringBuilder();
        for (String obstacleName : getObstacleNames()) {
            if (patternBuilder.length() > 0) {
                patternBuilder.append("|");
            }
            patternBuilder.append((obstacleName));
        }
        return patternBuilder.toString();
    }

    /**
     * Scaling damage with difficulty
     * @param baseDamage An obstacles base damage
     * @param difficulty Difficulty level
     * @return Obstacles calculated damage to deal
     */
    public int setDamageDealt(int baseDamage, Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> baseDamage;
            case MEDIUM -> (int) (baseDamage * 1.5);
            case HARD -> baseDamage * 2;
        };
    }

    /**
     * Displays prompt to player upon obstacle activating, deals damage
     * @param player Player object reduces powerpoints by damage dealt
     */
    public void activate(Player player) {
        UI.displayMessage(this.getOnActivation());
        player.takesDamage(this.getDamageDealt());
    }
    public String getOnActivation() {
        return onActivation;
    }

    public void setOnActivation(String onActivation) {
        this.onActivation = onActivation;
    }
    public int getDamageDealt() {
        return damageDealt;
    }
}
