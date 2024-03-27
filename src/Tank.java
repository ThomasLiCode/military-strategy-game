import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Tank extends Entity {

    public Tank (){
        super(300, 50, 50);
    }
    @Override
    public void action(DLList allyTeam, DLList enemyTeam) { //attacks enemy Entity at the front of enemy army list
        if(enemyTeam.get(0) == null)
            return;
        int enemyHealth = enemyTeam.get(0).getValue().getHealth();
        enemyTeam.get(0).getValue().setHealth(enemyHealth - getPower());
    }


    public void draw(PApplet sketch, int x, int y, boolean flipped) {
        sketch.imageMode(PConstants.CENTER);
        PImage og = sketch.loadImage("tank.png");

        sketch.image(og, x, y, 50, 50);
    }

    @Override
    public String getDescription() {
        return "Damage: " + getPower() + " | Health: " + getHealth() + " | Cost: " + getCost() + "\nAttacks enemy unit at the front";
    }

    public String toString(){
        return "|Tank " + "Health: " + getHealth() + " " + "Power: " + getPower() + "|";
    }
}
