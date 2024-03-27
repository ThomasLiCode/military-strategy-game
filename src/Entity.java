import processing.core.PApplet;

public abstract class Entity {
    private int health;
    private int power;
    private int cost;
    public Entity(int health, int power, int cost){
        this.health = health;
        this.power = power;
        this.cost = cost;
    }
    public int getHealth(){ return health; }
    public int getPower(){ return power; }
    public int getCost(){ return cost; }
    public void setHealth(int health) {
        this.health = health;
    }
    public boolean isDestroyed(){
        if(health<=0){
            return true;
        }
        return false;
    }
    public abstract void action(DLList allyTeam, DLList enemyTeam);
    public void draw(PApplet sketch, int x, int y) {
        draw(sketch, x, y, false);
    }
    public abstract void draw(PApplet sketch, int x, int y, boolean flipped);
    public abstract String getDescription();
    public boolean mouseHits(int x, int y, int mouseX, int mouseY) {//if mouse is within a certain range of icon, returns true, else false
        return (x + 25) >= mouseX && (x - 25) <= mouseX && (y + 25) >= mouseY && (y - 25) <= mouseY;
    }
    public String toString(){
        return "Health: " + getHealth() + " " + "Power: " + getPower();
    }
}
