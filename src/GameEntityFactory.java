// Object creation logic for all possible game entities in rooms in one place
public class GameEntityFactory {

    /**
     * For making random entities, this will depend on game difficulty
     * @return A random GameEntity
     */
    public static GameEntity makeRandomGameEntity() {
        short rnd = (short) (Math.random()*20);
        switch (Game.getDifficulty()) {
            case EASY:
                if (rnd <= 4) {
                    return GameItemFactory.createRandomFood();
                } else if (rnd < 9) {
                    return GameItemFactory.createRandomTool();
                } else if (rnd < 14) {
                    return GameItemFactory.createRandomSpell();
                } else if (rnd < 17) {
                    return GameItemFactory.createRandomPotion();
                } else {
                    return ObstacleFactory.createRandomObstacle();
                }
            case MEDIUM:
                if (rnd <= 4) {
                    return GameItemFactory.createRandomFood();
                } else if (rnd < 8) {
                    return GameItemFactory.createRandomTool();
                } else if (rnd < 11) {
                    return GameItemFactory.createRandomSpell();
                } else if (rnd < 15) {
                    return GameItemFactory.createRandomPotion();
                } else {
                    return ObstacleFactory.createRandomObstacle();
                }
            case HARD:
                if (rnd <= 3) {
                    return GameItemFactory.createRandomFood();
                } else if (rnd < 6) {
                    return GameItemFactory.createRandomTool();
                } else if (rnd < 9) {
                    return GameItemFactory.createRandomSpell();
                } else if (rnd < 13) {
                    return GameItemFactory.createRandomPotion();
                } else {
                    return ObstacleFactory.createRandomObstacle();
                }
            default:
                throw new IllegalArgumentException("Unknown difficulty level: " + Game.getDifficulty());
        }
    }

}
