import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Player {

    public Player() {
    }

    // Upon player creation, they will have a new inventory, spell and tool holder, and initial powerpoints based on difficulty
    public Player(String name, Difficulty difficulty) {
        this.playerName = name;
        this.powerPoints = setPowerPoints(difficulty);
        this.inventory = new ArrayList<>();
        this.spellbookHolder = new ArrayList<>();
        this.toolBelt = new ArrayList<>();
        this.setPowerPointsMax(this.powerPoints);
    }

    Scanner scanner = new Scanner(System.in);
    Level currentLevel;
    private short[] currentCoordinates;
    private List<GameItem> inventory;
    private List<Spell> spellbookHolder;
    private List<Tool> toolBelt;
    private short spellbookHolderMax = 1;
    private short toolBeltMax = 1;
    private final int inventorySize = 10;
    private String playerName;
    private int powerPoints;
    private int powerPointsMax;
    private short currentLevelNumber = 1;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPowerPoints() {
        return powerPoints;
    }

    public short[] getCurrentCoordinates() {
        return currentCoordinates;
    }

    public void setCurrentCoordinates(short[] currentCoordinates) {
        this.currentCoordinates = currentCoordinates;
    }

    public short getCurrentLevelNumber() {
        return currentLevelNumber;
    }

    public void setCurrentLevelNumber(short currentLevelNumber) {
        this.currentLevelNumber = currentLevelNumber;
    }

    public void restorePowerpoints(int restoreAmount) {
        if ((this.getPowerPoints() + restoreAmount) >= this.getPowerPointsMax()) {
            this.setPowerPoints(this.getPowerPointsMax());
        } else {
            this.setPowerPoints(this.getPowerPoints() + restoreAmount);
        }
    }

    // Overloading setPowerPoints, one for initialisation based on set difficulty, another for runtime updating
    public int setPowerPoints(Difficulty difficulty) {
        return switch (difficulty) {
            case EASY -> 120;
            case MEDIUM -> 100;
            case HARD -> 80;
        };
    }

    public void setPowerPoints(int powerPointsValue) {
        this.powerPoints = powerPointsValue;
    }

    /**
     * Take a players input for target
     * @return Object targeted by player
     */
    public Obstacle getTarget() {
        Room currentRoom = getCurrentRoom();
        String inputTarget;
        UI.displayMessage("Enter desired target: ");
        inputTarget = scanner.nextLine();
        inputTarget = InputValidation.capitalise(inputTarget);

        if (currentRoom.contentsInclude(inputTarget)) {
            if (currentRoom.getRoomEntity(inputTarget) instanceof Obstacle) {
                UI.displayMessage("You have targeted " + inputTarget);
                return (Obstacle) currentRoom.getRoomEntity(inputTarget);
            } else {
                UI.displayMessage("Cannot target " + inputTarget);
            }
        } else {
            UI.displayMessage("Target not found");
        }
        return null;
    }

    /**
     * Take console input from player, splits into "doing" action argument 1, and target for action argument 2, invalid input defaulted to a message
     * @param level Level object to have level values for player action
     * @param commandInput Players input command
     */
    public void takeCommandInput(Level level, String commandInput) {
        UI.printGap();
        String[] inputArgs = commandInput.split(" ", 2);
        String commandActionType = inputArgs[0].toLowerCase();
        String commandActionArgument = (inputArgs.length > 1) ? inputArgs[1].toLowerCase() : "";

        if (commandActionType.matches("(?i)(move|walk|go)")) {
            executeMoveCommand(level, commandActionArgument);
        } else if (commandActionType.matches("(?i)look")) {
            executeLookCommand(level, commandActionArgument);
        } else if (commandActionType.matches("(?i)check|open") && commandActionArgument.matches("(?i)inventory")) {
            openInventory();
        } else if (commandActionType.matches("(?i)examine")) {
            executeExamineCommand(commandActionArgument);
        } else if (commandActionType.matches("(?i)take")) {
            executeTakeCommand(commandActionArgument);
        } else if (commandActionType.matches("(?i)drop")) {
            executeDropCommand(commandActionArgument);
        } else if (commandActionType.matches("(?i)(use|cast)") && commandActionArgument.matches(Spell.buildSpellNamesRegex())) {
            executeSpellCommand(level, commandActionArgument);
            // Necessary to include the name potion alone as the player is not to know if they have a sleeping or xray potion as per assignment direction, but retain the ability to add further potion names that perhaps could be known
        } else if (commandActionType.matches("(?i)(use|drink)") && (commandActionArgument.matches(Potion.buildPotionNamesRegex()) || commandActionArgument.matches("(?i)potion"))) {
            executePotionCommand(level, commandActionArgument);
        } else if (commandActionType.matches("(?i)(use|eat)") && commandActionArgument.matches(Food.buildFoodNamesRegex())) {
            executeFoodCommand(commandActionArgument);
        } else if (commandActionType.matches("(?i)use") && commandActionArgument.matches(Tool.buildToolNamesRegex())) {
            executeToolCommand(commandActionArgument);
        } else if (commandActionType.matches("(?i)map") || (commandActionType.matches("(?i)(view|use|check)") && commandActionArgument.matches("(?i)map"))) {
            UI.displayMap();
        } else if (commandActionType.matches("(?i)help")) {
            executeHelpCommand();
        } else if (commandActionType.matches("(?i)quit")) {
            System.exit(0);
        } else {
            System.out.println("I can't do that");
        }
    }
    /** Overloading for obstacle scenario
     * Take console input from player, splits into "doing" action argument 1, and target for action argument 2, invalid input defaulted to a message. If argument two empty defaults to empty string
     * @param level Level object to have level values for player action
     * @param commandInput Players input command
     * @param obstacle Obstacle object encountered
     */
    // CoPilot was queried for regex syntax i.e (?i) but the way I take command input is all my code, with prior knowledge of splitting and ternary operator
    public void takeCommandInput(Level level, String commandInput, Obstacle obstacle) {
        UI.printGap();
        String[] inputArgs = commandInput.split(" ", 2);
        String commandActionType = inputArgs[0].toLowerCase();
        String commandActionArgument = (inputArgs.length > 1) ? inputArgs[1].toLowerCase() : "";

        if (commandActionType.matches("(?i)(move|walk|go)")) {
            if (triggerObstacle(obstacle)) {
                if (!this.getCurrentRoom().hasObstacle()) {
                    executeMoveCommand(level, commandActionArgument);
                }
            }
        } else if (commandActionType.matches("(?i)look")) {
            executeLookCommand(level, commandActionArgument);
        } else if (commandActionType.matches("(?i)check|open") && commandActionArgument.matches("(?i)inventory")) {
            openInventory();
        } else if (commandActionType.matches("(?i)examine")) {
            executeExamineCommand(commandActionArgument);
        } else if (commandActionType.matches("(?i)take")) {
            if (triggerObstacle(obstacle)) {
                executeTakeCommand(commandActionArgument);
            }
        } else if (commandActionType.matches("(?i)drop")) {
            executeDropCommand(commandActionArgument);
        } else if (commandActionType.matches("(?i)(use|cast)") && commandActionArgument.matches(Spell.buildSpellNamesRegex())) {
            executeSpellCommand(level, commandActionArgument);
        } else if (commandActionType.matches("(?i)(use|drink)") && (commandActionArgument.matches(Potion.buildPotionNamesRegex()) || commandActionArgument.matches("(?i)potion"))) {
            executePotionCommand(level, commandActionArgument);
        } else if (commandActionType.matches("(?i)(use|eat)") && commandActionArgument.matches(Food.buildFoodNamesRegex())) {
            executeFoodCommand(commandActionArgument);
        } else if (commandActionType.matches("(?i)use") && commandActionArgument.matches(Tool.buildToolNamesRegex())) {
            executeToolCommand(commandActionArgument);
        } else if (commandActionType.matches("(?i)map") || (commandActionType.matches("(?i)(view|use|check)") && commandActionArgument.matches("(?i)map"))) {
            UI.displayMap();
        } else if (commandActionType.matches("(?i)help")) {
            executeHelpCommand();
        } else if (commandActionType.matches("(?i)quit")) {
            System.exit(0);
        } else {
            UI.displayMessage("I can't do that");
        }
    }

    /**
     * For executing a move command, including validating and printing useful information about move attempts
     * @param level Level object on which player traversing
     * @param moveDirection Direction player wishes to move
     */
    public void executeMoveCommand(Level level, String moveDirection) {
        short[] nextCoordinate;
        List<short[]> surroundingCoordinates = getSurroundingCoordinates(this.getCurrentCoordinates());

        if (moveDirection.matches("(?i)(up|north)")) {
            nextCoordinate = surroundingCoordinates.get(0);
            if (checkMoveCommand(level, nextCoordinate)) {
                this.setCurrentCoordinates(nextCoordinate);
                UI.displayMessage("You move to the next room");
            } else {
                UI.displayMessage("That direction is walled off");
            }
        } else if (moveDirection.matches("(?i)(right|east)")) {
            nextCoordinate = surroundingCoordinates.get(1);
            if (checkMoveCommand(level, nextCoordinate)) {
                this.setCurrentCoordinates(nextCoordinate);
                UI.displayMessage("You move to the next room");
            } else {
                UI.displayMessage("That direction is walled off");
            }
        } else if (moveDirection.matches("(?i)(down|south)")) {
            nextCoordinate = surroundingCoordinates.get(2);
            if (checkMoveCommand(level, nextCoordinate)) {
                this.setCurrentCoordinates(nextCoordinate);
                UI.displayMessage("You move to the next room");
            } else {
                UI.displayMessage("That direction is walled off");
            }
        } else if (moveDirection.matches("(?i)(left|west)")) {
            nextCoordinate = surroundingCoordinates.get(3);
            if (checkMoveCommand(level, nextCoordinate)) {
                this.setCurrentCoordinates(nextCoordinate);
                UI.displayMessage("You move to the next room");
            } else {
                UI.displayMessage("That direction is walled off");
            }
        } else {
            UI.displayMessage("Direction unrecognised");
        }
    }

    /**
     * Return true or false for valid or invalid move command respectively
     * @param level Level on which traversing
     * @param nextCoordinate Coordinate attempting to move to
     * @return True for valid
     */
    public boolean checkMoveCommand(Level level, short[] nextCoordinate) {
        if (!isValidCoordinate(level, nextCoordinate)) {
            return false;
        } else {
            if (Level.containsCoordinate(level.getWallSections(), nextCoordinate)) {
                return false;
            } else if (Arrays.equals(level.getEntrySection(), nextCoordinate)) {
                UI.displayMessage("This is back where you started");
                return true;
            } else if (Arrays.equals(level.getExitSection(), nextCoordinate)) {
                UI.displayMessage("You've found the way out!");
                setCurrentLevelNumber((short) (getCurrentLevelNumber() + 1));
                return true;
            } else {
                return true;
            }
        }
    }
    public boolean checkXrayCommand(Level level, short[] nextCoordinate) {
        if (!isValidCoordinate(level, nextCoordinate)) {
            return false;
        } else {
            if (Level.containsCoordinate(level.getWallSections(), nextCoordinate)) {
                return false;
            } else if (Arrays.equals(level.getEntrySection(), nextCoordinate)) {
                UI.displayMessage("That is back where you started");
                return true;
            } else if (Arrays.equals(level.getExitSection(), nextCoordinate)) {
                UI.displayMessage("You see the way out!");
                setCurrentLevelNumber((short) (getCurrentLevelNumber() + 1));
                return true;
            } else {
                UI.displayMessage("You look into the next room...");
                return true;
            }
        }
    }

    /**
     * Executes look command, including validation check logic for move command
     * @param level Level on which player is on
     * @param lookDirection Direction in which to look
     */
    public void executeLookCommand(Level level, String lookDirection) {
        List<short[]> surroundingCoordinates = getSurroundingCoordinates(this.getCurrentCoordinates());
        short[] lookTargetCoordinates;

        if (lookDirection.matches("(up|north)")) {
            lookTargetCoordinates = surroundingCoordinates.get(0);
            UI.displayMessage(checkLookCommand(level, lookTargetCoordinates));
        } else if (lookDirection.matches("(right|east)")) {
            lookTargetCoordinates = surroundingCoordinates.get(1);
            UI.displayMessage(checkLookCommand(level, lookTargetCoordinates));
        } else if (lookDirection.matches("(down|south)")) {
            lookTargetCoordinates = surroundingCoordinates.get(2);
            UI.displayMessage(checkLookCommand(level, lookTargetCoordinates));
        } else if (lookDirection.matches("(left|west)")) {
            lookTargetCoordinates = surroundingCoordinates.get(3);
            UI.displayMessage(checkLookCommand(level, lookTargetCoordinates));
        } else if (lookDirection.equals("around")) {
            UI.displayMessage("You take a look around...\n");
            this.getCurrentRoom().displayRoom();
            String[] cardinals = {"North -> ", "East -> ", "South -> ", "West -> "};
            for (int i = 0; i<4 ; i++) {
                lookTargetCoordinates = surroundingCoordinates.get(i);
                UI.displayMessage(cardinals[i] + checkLookCommand(level, lookTargetCoordinates));
            }
        } else {
            UI.displayMessage("That's not something I can look at");
        }
    }

    // Move command logic but for looking, checks what kind of section is in the direction
    public String checkLookCommand(Level level, short[] nextCoordinate) {
        if (!isValidCoordinate(level, nextCoordinate)) {
            return "That direction is walled off";
        } else {
            if (Level.containsCoordinate(level.getWallSections(), nextCoordinate)) {
                return "That direction is walled off";
            } else if (Arrays.equals(level.getEntrySection(), nextCoordinate)) {
                return "That would be back where you started";
            } else if (Arrays.equals(level.getExitSection(), nextCoordinate)) {
                return "There's a staircase in that direction";
            } else {
                return "There's a large doorway in that direction";
            }
        }
    }

    // Prints game entity's description value
    public void executeExamineCommand(String examineTarget) {
        InputValidation.capitalise(examineTarget);
        if (this.getCurrentRoom().contentsInclude(examineTarget)) {
            GameEntity entityToExamine = this.getCurrentRoom().getRoomEntity(examineTarget);
            UI.displayMessage(entityToExamine.getDescription());
        } else if (this.inventoryContains(examineTarget)) {
            GameEntity entityToExamine = this.getCurrentRoom().getRoomItem(examineTarget);
            UI.displayMessage(entityToExamine.getDescription());
        } else {
            UI.displayMessage("There's no " + examineTarget + " to examine");
        }
    }

    /**
     * Executes take command, if spell or tool goes to respective utility "bar", otherwise inventory assuming valid item otherwise message provided
     * @param itemName Item to take
     */
    public void executeTakeCommand(String itemName) {
        if (this.getCurrentRoom().contentsInclude(itemName)) {
            GameItem itemToTake = this.getCurrentRoom().getRoomItem(itemName);
            if (itemToTake instanceof Spell) {
                if (this.addToSpellbookHolder((Spell) itemToTake)) {
                    this.getCurrentRoom().removeContent(itemName);
                }
            } else if (itemToTake instanceof Tool) {
                if (this.addToToolBelt((Tool) itemToTake)) {
                    this.getCurrentRoom().removeContent(itemName);
                }
            } else {
                if (this.addToInventory(itemToTake)) {
                    this.getCurrentRoom().removeContent(itemName);
                }
            }
        } else {
            UI.displayMessage("That isn't something in this room");
        }
    }

    /**
     * Executes drop command, removes item from appropriate place if it exists there, adds back to current room
     * @param itemName Item to drop
     */
    public void executeDropCommand(String itemName) {
        if (inventoryContains(itemName)) {
            GameItem item = getItem(getInventory(), itemName);
            this.getCurrentRoom().addItem(item);
            this.getInventory().remove(item);
            UI.displayDropMessage(itemName);
        } else if (spellbookHolderContains(itemName)) {
            Spell spell = getSpell(spellbookHolder, itemName);
            this.getCurrentRoom().addItem(spell);
            this.getSpellbookHolder().remove(spell);
            UI.displayDropMessage(itemName);
        } else if (toolBeltContains(itemName)) {
            Tool tool = getTool(toolBelt, itemName);
            this.getCurrentRoom().addItem(tool);
            this.getToolBelt().remove(tool);
            UI.displayDropMessage(itemName);
        } else {
            UI.displayMessage("You do not have a " + itemName + " to drop");
        }
    }

    /**
     * Returns a list of surrounding coordinates in a clockwise fashion starting with North
     * @param playerLocation Players current coordinates
     * @return Surrounding coordinates
     */
    public List<short[]> getSurroundingCoordinates(short[] playerLocation) {

        short[] northDirection = new short[] {playerLocation[0], (short) (playerLocation[1] + 1)};
        short[] eastDirection = new short[] {(short) (playerLocation[0] + 1), playerLocation[1] };
        short[] southDirection = new short[] {playerLocation[0], (short) (playerLocation[1] - 1)};
        short[] westDirection = new short[] {(short) (playerLocation[0] - 1), playerLocation[1]};

        ArrayList<short[]> listOfCoordinates = new ArrayList<>(Arrays.asList(new short[4][]));
        listOfCoordinates.set(0, northDirection);
        listOfCoordinates.set(1, eastDirection);
        listOfCoordinates.set(2, southDirection);
        listOfCoordinates.set(3, westDirection);
        return listOfCoordinates;
    }

    /**
     * Checks whether coordinate exists on the level
     * @param level Level object to check on
     * @param coordinates Coordinates to check for
     * @return True for valid
     */
    public boolean isValidCoordinate(Level level, short[] coordinates) {
        if (!(coordinates[0] > level.getLevelWidth() - 1) && !(coordinates[1] > level.getLevelHeight() - 1) && !(coordinates[0] < 0) && !(coordinates[1] < 0)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Executes spell command, checks if spell requires target and removes if disabled by spell, or if teleport spell requiring level object
     * @param level Level object for teleportation spell values
     * @param spellName Input spell name to use
     */
    public void executeSpellCommand(Level level, String spellName) {
        spellName = InputValidation.capitalise(spellName);
        if (this.spellbookHolderContains(spellName)) {
            Spell spell = this.getSpell(this.getSpellbookHolder(), spellName);
            if (spell.getRequiresTarget()) {
                GameEntity target = getTarget();
                if (target != null) {
                    spell.use(target);
                    // isActive used to check whether obstacle removed or not, some targets may remain in the room on expansion
                    if (!target.isActive()) {
                        this.getCurrentRoom().removeContent(target.getName());
                    }
                }
            } else if (spell instanceof TeleportSpell) {
                spell.use(level, this);
            } else {
                spell.use();
            }
        } else {
            UI.displayMessage("You don't have that in your spellbook holder");
        }
    }

    /**
     * Executes potion command, removing from inventory on use
     * @param level Level object for values if potion interacts with level i.e. X-ray
     * @param potionName Name of potion to use
     */
    public void executePotionCommand(Level level, String potionName) {
        potionName = InputValidation.capitalise(potionName);
        if (inventoryContains(potionName)) {
            Potion potion = (Potion) this.getItem(this.getInventory(), potionName);
            if (potion instanceof XrayPotion) {
                // If the player enters incorrect input for X-ray potion effect it should not be consumed
                if (potion.use(level, this)) {
                    getInventory().remove(potion);
                }
            } else if (potion instanceof SleepingPotion) {
                // Sleeping potion is consumed regardless of any factors
                potion.use(this);
                getInventory().remove(potion);
            } else {
                // A placeholder default for now
                potion.use();
                getInventory().remove(potion);
            }
        } else {
            UI.displayMessage("You don't have that in your inventory");
        }
    }

    /**
     * Executes food command, removing from inventory on use
     * @param foodName Name of food to use
     */
    public void executeFoodCommand(String foodName) {
        foodName = InputValidation.capitalise(foodName);
        if (this.inventoryContains(foodName)) {
            Food food = (Food) this.getItem(getInventory(), foodName);
            food.use(this);
            getInventory().remove(food);
        } else {
            UI.displayMessage("You don't have that in your inventory");
        }
    }

    /**
     * Executes tool command, if spanner the use checks if box present and returns game item, else checks for target and removes entity if disabled by tool
     * @param toolName Name of tool to use
     */
     public void executeToolCommand(String toolName) {
        toolName = InputValidation.capitalise(toolName);
        if (this.toolBeltContains(toolName)) {
            Tool tool = this.getTool(getToolBelt(), toolName);
            if (tool instanceof Spanner) {
                GameItem gameItem = tool.use(this.getCurrentRoom());
                if (gameItem instanceof Spell) {
                    this.addToSpellbookHolder((Spell) gameItem);
                } else {
                    this.addToInventory(gameItem);
                }
            } else if (tool.getRequiresTarget()) {
                GameEntity target = getTarget();
                if (target != null) {
                    tool.use(target);
                    // isActive used to check whether obstacle removed or not, some targets may remain in the room on expansion
                    if (!target.isActive()) {
                        this.getCurrentRoom().removeContent(target.getName());
                    }
                }
            } else {
                tool.use();
            }
        } else {
            UI.displayMessage("You don't have that in your toolbelt");
        }
     }

    /**
     * Triggers obstacle if encountered to hold player to the room while active, if player can not disable then deals damage upon moving through
     * @param obstacle Obstacle object encountered
     */
    public boolean triggerObstacle(Obstacle obstacle) {
        UI.displayMessage("The " + obstacle.getName() + " will cause damage if you try move around the room, would you like to proceed?");
        UI.displayMessage("Enter Y/N:");
        String playerYorN = scanner.nextLine().toUpperCase();
        if (playerYorN.equals("Y")) {
            obstacle.activate(this);
            this.getCurrentRoom().removeContent(obstacle.getName());
            return true;
        } else if (playerYorN.equals("N")) {
            UI.displayMessage("Returning to room instance...");
            return false;
        } else {
            UI.displayMessage("Please enter Y or N");
            return false;
        }
    }

    /**
     * Checks inventory for an item name
     * @param itemName Item name to check for
     * @return True for contains item
     */
    public boolean inventoryContains(String itemName) {
        for (GameItem item : this.getInventory()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks spellbook holder for spell name
     * @param spellName Spell name to check for
     * @return True if contains spell
     */
    public boolean spellbookHolderContains(String spellName) {
        for (Spell spell : this.getSpellbookHolder()) {
            if (spell.getName().equalsIgnoreCase(spellName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks toolbelt for a tool name
     * @param toolName Tool name to check for
     * @return True if contains tool
     */
    public boolean toolBeltContains(String toolName) {
        for (Tool tool : this.getToolBelt()) {
            if (tool.getName().equalsIgnoreCase(toolName)) {
                return true;
            }
        }
        return false;
    }

    public List<GameItem> getInventory() {
        return inventory;
    }
    public int getInventorySize() {
        return inventorySize;
    }
    public List<Spell> getSpellbookHolder() {
        return spellbookHolder;
    }

    public List<Tool> getToolBelt() {
        return toolBelt;
    }

    public short getSpellbookHolderMax() {
        return spellbookHolderMax;
    }

    public short getToolBeltMax() {
        return toolBeltMax;
    }
    public int getPowerPointsMax() {
        return powerPointsMax;
    }

    public void setPowerPointsMax(int powerPointsMax) {
        this.powerPointsMax = powerPointsMax;
    }

    /**
     * Returns an item from a list of GameItem
     * @param gameItems List of GameItem i.e. inventory
     * @param gameItemName Name of item to get
     * @return Game item if list contains game item name
     */
    public GameItem getItem(List<GameItem> gameItems, String gameItemName) {
        gameItemName = InputValidation.capitalise(gameItemName);
        for (GameItem gameItem: gameItems) {
            if (gameItem.getName().equals(gameItemName)) {
                return gameItem;
            }
        }
        return null;
    }

    /**
     * Returns a spell from a list of Spell
     * @param spells List of Spell i.e. spellbook holder
     * @param spellName Name of spell to get
     * @return Spell if list contains spell name
     */
    public Spell getSpell(List<Spell> spells, String spellName) {
        spellName = InputValidation.capitalise(spellName);
        for (Spell spell: spells) {
            if (spell.getName().equals(spellName)) {
                return spell;
            }
        }
        return null;
    }
    /**
     * Returns a tool from a list of Tool
     * @param tools List of Tool i.e. toolbelt
     * @param toolName Name of tool to get
     * @return Tool if list contains tool name
     */
    public Tool getTool(List<Tool> tools, String toolName) {
        toolName = InputValidation.capitalise(toolName);
        for (Tool tool: tools) {
            if (tool.getName().equals(toolName)) {
                return tool;
            }
        }
        return null;
    }

    /**
     * Returns room with same coordinates as player current coordinates
     * @return Room player currently on
     */
    public Room getCurrentRoom() {
        for (Room room: this.getCurrentLevel().getLevelRooms()) {
            if (Arrays.equals(room.getRoomCoordinate(), this.getCurrentCoordinates())) {
                return room;
            }
        }
        throw new IllegalArgumentException("Room coordinates not found matching player location at " + Arrays.toString(this.getCurrentCoordinates()));
    }

    public void setCurrentLevel(Level currentLevel) {
        this.currentLevel = currentLevel;
    }
    public Level getCurrentLevel() {
        return currentLevel;
    }
    public void takesDamage(int damageTaken) {
        this.setPowerPoints(this.getPowerPoints() - damageTaken);
    }

    /**
     * Print inventory contents names
     */
    public void openInventory() {
        System.out.print("Inventory: [");
        int itemCounter = 0;
        for (GameItem item: this.getInventory()) {
            if (itemCounter == 0) {
                System.out.print(item.getName());
                itemCounter++;
            } else {
                System.out.print("," + item.getName());
            }
        }
        System.out.print("]\n");
    }

    /**
     * Adds a game item to inventory, returns true if successful
     * @param gameItem Game item to add to inventory
     * @return True if item added
     */
    public boolean addToInventory(GameItem gameItem) {
        if (gameItem != null) {
            if (this.getInventory().size() < this.getInventorySize()) {
                this.getInventory().add(gameItem);
                UI.displayTakeMessage(gameItem.getName());
                return true;
            } else {
                UI.displayMessage("Inventory full");
                return false;
            }
        } else {
            UI.displayMessage("Game item is null");
            return false;
        }
    }
    /**
     * Adds a spell to spellbook holder, returns true if successful
     * @param spell Spell to add to spellbook holder
     * @return True if spell added
     */
    public boolean addToSpellbookHolder(Spell spell) {
        if (spell != null) {
            if (this.getSpellbookHolder().size() < this.getSpellbookHolderMax()) {
                this.getSpellbookHolder().add(spell);
                UI.displayTakeMessage(spell.getName());
                return true;
            } else {
                UI.displayMessage("Spellbook holder full");
                return false;
            }
        } else {
            UI.displayMessage("Spell is null");
            return false;
        }
    }
    /**
     * Adds a tool to toolbelt, returns true if successful
     * @param tool Tool to add to toolbelt
     * @return True if tool added
     */
    public boolean addToToolBelt(Tool tool) {
        if (tool != null) {
            if (this.getToolBelt().size() < this.getToolBeltMax()) {
                this.getToolBelt().add(tool);
                UI.displayTakeMessage(tool.getName());
                return true;
            } else {
                UI.displayMessage("Toolbelt at capacity");
                return false;
            }
        } else {
            UI.displayMessage("Tool is null");
            return false;
        }
    }

    public void executeHelpCommand() {
        UI.displayHelpMessage();
    }


}
