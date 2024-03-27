import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.util.Random;
public class Artillery extends Entity {
    public Artillery(){
        super(100, 75, 100);
    }
    @Override
    public void action(DLList allyTeam, DLList enemyTeam) {//Randomly attacks an enemy Entity
        Random r = new Random();
        int length = enemyTeam.length();
        if(length <= 0)
            return;
        int target = r.nextInt(length);
        int enemyHealth = enemyTeam.get(target).getValue().getHealth();
        enemyTeam.get(target).getValue().setHealth(enemyHealth - getPower());
    }


    public void draw(PApplet sketch, int x, int y, boolean flipped) {
        sketch.imageMode(PConstants.CENTER);
        PImage og = sketch.loadImage("artillery.png");

        sketch.image(og, x, y, 50, 50);
    }

    @Override
    public String getDescription() {
        return "Damage: " + getPower() + " | Health: " + getHealth() + " | Cost: " + getCost() + "\nAttacks a random target in enemy army";
    }

    public String toString(){
        return "|Artillery " + "Health: " + getHealth() + " " + "Power: " + getPower() + "|";
    }
}
