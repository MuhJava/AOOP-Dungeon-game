public abstract class GameEntity {
    private String name;
    private String description;
    private boolean isActive = true;
    public GameEntity() {}
    public GameEntity(String name) {
        this.setName(name);
    }

    public GameEntity(String name, String description) {
        this.setName(name);
        this.setDescription(description);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public void onFreeze() {
        UI.displayMessage("Freezing that won't do much good");
    }
    public void onHammer() {
        UI.displayMessage("I can't do anything useful to that with a hammer");
    }
    public boolean isActive() {
        return isActive;
    }
    public void setIsActive(boolean active) {
        isActive = active;
    }
}
