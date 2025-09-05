import java.util.ArrayList;

public class UnitTesting {

    public static void testEntityFactoryRandomisation() {
        ArrayList<GameEntity> returnedObjects = new ArrayList<>();
        int foodCounter = 0;
        int toolCounter = 0;
        int spellCounter = 0;
        int potionCounter = 0;
        int obstacleCounter = 0;

        for (int i = 0; i < 1000; i++) {
            returnedObjects.add(GameEntityFactory.makeRandomGameEntity());
        }
        for (GameEntity gameEntity : returnedObjects) {
            if (gameEntity instanceof Food) {
                foodCounter++;
            } else if (gameEntity instanceof Tool) {
                toolCounter++;
            } else if (gameEntity instanceof Spell) {
                spellCounter++;
            } else if (gameEntity instanceof Potion) {
                potionCounter++;
            } else if (gameEntity instanceof Obstacle) {
                obstacleCounter++;
            } else {
                System.out.println("Game entity type not found: " + gameEntity.getName());
            }
        }
        // Counter values are only divided by 10 because dividing by 1000 then multiplying by 100 for % is the same
        if (Game.getDifficulty() == Difficulty.EASY) {
            if ((double) foodCounter / 10 >= 22.5 && (double) foodCounter / 10 <= 27.5) {
                System.out.println("Food: " + (foodCounter / 10 + "%") + " PASS");
            } else {
                System.out.println("Food: " + (foodCounter / 10 + "%") + " FAIL");
            }
            if (toolCounter / 10 >= 18 && toolCounter / 10 <= 22) {
                System.out.println("Tool: " + (toolCounter / 10 + "%") + " PASS");
            } else {
                System.out.println("Tool: " + (toolCounter / 10 + "%") + " FAIL");
            }
            if ((double) spellCounter / 10 >= 22.5 && (double) spellCounter / 10 <= 27.5) {
                System.out.println("Spell: " + (spellCounter / 10 + "%") + " PASS");
            } else {
                System.out.println("Spell: " + (spellCounter / 10 + "%") + " FAIL");
            }
            if ((double) potionCounter / 10 >= 13.5 && (double) potionCounter / 10 <= 16.5) {
                System.out.println("Potion: " + (potionCounter / 10 + "%") + " PASS");
            } else {
                System.out.println("Potion: " + (potionCounter / 10 + "%") + " FAIL");
            }
            if ((double) obstacleCounter / 10 >= 13.5 && (double) obstacleCounter / 10 <= 16.5) {
                System.out.println("Obstacle: " + (obstacleCounter / 10 + "%") + " PASS");
            } else {
                System.out.println("Obstacle: " + (obstacleCounter / 10 + "%") + " FAIL");
            }
        } else if (Game.getDifficulty() == Difficulty.MEDIUM) {
            if ((double) foodCounter / 10 >= 22.5 && (double) foodCounter / 10 <= 27.5) {
                System.out.println("Food: " + (foodCounter / 10 + "%") + " PASS");
            } else {
                System.out.println("Food: " + (foodCounter / 10 + "%") + " FAIL");
            }
            if ((double) toolCounter / 10 >= 13.5 && (double) toolCounter / 10 <= 16.5) {
                System.out.println("Tool: " + (toolCounter / 10 + "%") + " PASS");
            } else {
                System.out.println("Tool: " + (toolCounter / 10 + "%") + " FAIL");
            }
            if ((double) spellCounter / 10 >= 13.5 && (double) spellCounter / 10 <= 16.5) {
                System.out.println("Spell: " + (spellCounter / 10 + "%") + " PASS");
            } else {
                System.out.println("Spell: " + (spellCounter / 10 + "%") + " FAIL");
            }
            if (potionCounter / 10 >= 18 && potionCounter / 10 <= 22) {
                System.out.println("Potion: " + (potionCounter / 10 + "%") + " PASS");
            } else {
                System.out.println("Potion: " + (potionCounter / 10 + "%") + " FAIL");
            }
            if ((double) obstacleCounter / 10 >= 22.5 && (double) obstacleCounter / 10 <= 27.5) {
                System.out.println("Obstacle: " + (obstacleCounter / 10 + "%") + " PASS");
            } else {
                System.out.println("Obstacle: " + (obstacleCounter / 10 + "%") + " FAIL");
            }
        } else if (Game.getDifficulty() == Difficulty.HARD) {
            if (foodCounter / 10 >= 18 && foodCounter / 10 <= 22) {
                System.out.println("Food: " + (foodCounter / 10 + "%") + " PASS");
            } else {
                System.out.println("Food: " + (foodCounter / 10 + "%") + " FAIL");
            }
            if (toolCounter / 10 >= 9 && toolCounter / 10 <= 11) {
                System.out.println("Tool: " + (toolCounter / 10 + "%") + " PASS");
            } else {
                System.out.println("Tool: " + (toolCounter / 10 + "%") + " FAIL");
            }
            if ((double) spellCounter / 10 >= 13.5 && (double) spellCounter / 10 <= 16.5) {
                System.out.println("Spell: " + (spellCounter / 10 + "%") + " PASS");
            } else {
                System.out.println("Spell: " + (spellCounter / 10 + "%") + " FAIL");
            }
            if (potionCounter / 10 >= 18 && potionCounter / 10 <= 22) {
                System.out.println("Potion: " + (potionCounter / 10 + "%") + " PASS");
            } else {
                System.out.println("Potion: " + (potionCounter / 10 + "%") + " FAIL");
            }
            if ((double) obstacleCounter / 10 >= 31.5 && (double) obstacleCounter / 10 <= 38.5) {
                System.out.println("Obstacle: " + (obstacleCounter / 10 + "%") + " PASS");
            } else {
                System.out.println("Obstacle: " + (obstacleCounter / 10 + "%") + " FAIL");
            }
        } else {
            System.out.println("Somehow difficulty doesn't exist?");
        }

        /* The code for executing in the main method
        for (int i = 1; i < 6; i++) {
            System.out.println("TEST NUMBER " + i);
            UnitTesting.testEntityFactoryRandomisation();
        }
         */
    }

    public static void testPlayerCommands(Level level, Player player) {
        String[] actionCommands = {"take", "drop", "use", "examine", "look", "eat", "open", "check", "cast", "move", "", " ", "123", "--==\\", "#;234"};
        String[] erroneousActionArgs = {"", "  ", "asd", "123", "///\"|\"$%&*", "  poton", "spells", "cae", "madscntist", "pot", "cak"
                + "cake4", "potion11", "freeze", "inven", "34inventory!", "floor", "loaf"};
        for (String actionCommand : actionCommands) {
            System.out.println("ActionCommand is " + actionCommand);
            for (String erroneousArg : erroneousActionArgs) {
                player.takeCommandInput(level, actionCommand + " " + erroneousArg);
            }
        }
    }

    public static void testCapitalisation() {
        String[] inputs = {"", " ", "   ", "a", "A", "abc", "123", "a123", "word", "Test", "tTesting", "';;;'';", "$%^%$*^", " sd dfs ", "nOrMalISePleasE"};
        String[] expectedOutputs = {"", " ", "   ", "A", "A", "Abc", "123", "a123", "Word", "Test", "Ttesting", "';;;'';", "$%^%$*^", " sd dfs ", "Normaliseplease"};
        for (int i = 0; i < inputs.length; i++) {
            if (InputValidation.capitalise(inputs[i]).equals(expectedOutputs[i])) {
                System.out.println("PASS");
            } else {
                System.out.println("FAIL");
            }
        }
    }

    public static void testNumericalInputOnly() {
        String[] inputs = {"", " ", "   ", "a", "A", "abc", "123", "a123", "word", "Test", "tTesting", "';;;'';", "$%^%$*^", "23123", "213jhj213", "12398p123712321376", "91283891367123 ", "32434266"};
        boolean[] expectedOutputs = {false, false, false, false, false, false, true, false, false, false, false, false, false, true, false, false, false, true};
        for (int i = 0; i < inputs.length; i++) {
            if (InputValidation.checkForNumericalInput(inputs[i]) == expectedOutputs[i]) {
                System.out.println("PASS");
            } else {
                System.out.println("FAIL");
            }
        }
    }

    public static void testAlphabeticalInputOnly() {
        String[] inputs = {"", " ", "   ", "a", "A", "abc", "123", "a123", "word", "Test", "tTesting", "';;;'';", "$%^%$*^", "23123", "213jhj213", "12398p123712321376", "91283891367123 ", "32434266"};
        boolean[] expectedOutputs = {false, true, true, true, true, true, false, false, true, true, true, false, false, false, false, false, false, false};
        for (int i = 0; i < inputs.length; i++) {
            if (InputValidation.checkForAlphabetInput(inputs[i]) == expectedOutputs[i]) {
                System.out.println("PASS");
            } else {
                System.out.println("FAIL");
            }
        }
    }

}
