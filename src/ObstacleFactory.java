import java.util.Random;

public class ObstacleFactory {

    // Creates Obstacle objects
    public static Obstacle createObstacle(String name) {
        return switch (name) {
            case "Floor trap" -> new Trap(name, "An array of spikes ready to spring covering the centre of the room", 5, "You fail to safely navigate the floor trap, the spikes spring up damaging you");
            case "Mad scientist" -> new MadScientist(name, "A crazed character babbling nonsense that won't let you pass", 10, "The crazed scientist forces you to sacrifice some life force for his dark experiments");
            default -> throw new IllegalArgumentException("Unknown obstacle: " + name);
        };
    }

    public static Obstacle createRandomObstacle() {
        Random rnd = new Random();
        int randomIndex = rnd.nextInt(Obstacle.getObstacleNames().length);
        return createObstacle(Obstacle.getObstacleNames()[randomIndex]);
    }

}
