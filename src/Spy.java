import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
public class Spy extends Entity{

    public Spy(){
        super(50, 0, 50);
    }
    @Override
    public void action(DLList allyTeam, DLList enemyTeam) {
    }

    public void draw(PApplet sketch, int x, int y, boolean flipped) {
        sketch.imageMode(PConstants.CENTER);
        PImage og = sketch.loadImage("spy.png");

        sketch.image(og, x, y, 50, 50);
    }

    @Override
    public String getDescription() {
        return "Damage: " + getPower() + " | Health: " + getHealth() + " | Cost: " + getCost() + "\nReverses enemy team composition";
    }

    public String toString(){
        return "|Spy " + "Health: " + getHealth() + " " + "Power: " + getPower() + "|";
    }
}

