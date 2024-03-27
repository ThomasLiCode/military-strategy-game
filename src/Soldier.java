import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.util.Random;
public class Soldier extends Entity{

    public Soldier(){
        super(100, 75, 25);
    }
    @Override
    public void action(DLList allyTeam, DLList enemyTeam) {//attacks enemy Entity at the front of enemy army list
        if(enemyTeam.get(0) == null)
            return;
        int enemyHealth = enemyTeam.get(0).getValue().getHealth();
        enemyTeam.get(0).getValue().setHealth(enemyHealth - getPower());
    }


    public void draw(PApplet sketch, int x, int y, boolean flipped) {
        sketch.imageMode(PConstants.CENTER);
        PImage og = sketch.loadImage("soldier.png");

        sketch.image(og, x, y, 50, 50);
    }
    @Override
    public String getDescription() {
        return "Damage: " + getPower() + " | Health: " + getHealth() + " | Cost: " + getCost() + "\nAttacks enemy unit at the front";
    }

    public String toString(){
        return "|Soldier " + "Health: " + getHealth() + " " + "Power: " + getPower() + "|";
    }
}
