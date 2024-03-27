import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

public class Mechanic extends Entity{

    private static PImage img;

    public Mechanic(){
        super(100, 0, 100);
    }
    @Override
    public void action(DLList allyTeam, DLList enemyTeam) {//heals nearby Artillery and Tanks by 75
        int index = allyTeam.findIndex(this);
        DoubleNode mechanic = allyTeam.get(index);
        if(index >= 1){
            if(mechanic.getPrev() != null) {
                Entity previous = mechanic.getPrev().getValue();
                if (previous instanceof Tank || previous instanceof Artillery) {//check if nearby is Tank or Artillery
                    int health1 = previous.getHealth();
                    previous.setHealth(health1 + 75);
                }
            }
        }
        if(index <= allyTeam.length() - 2){
            if(mechanic.getNext() != null) {
                Entity next = mechanic.getNext().getValue();
                if (next instanceof Tank || next instanceof Artillery) {//check if nearby is Tank or Artillery
                    int health2 = next.getHealth();
                    next.setHealth(health2 + 75);
                }
            }
        }
//        if (getHealth() <= 0)
//            allyTeam.delete(this);
    }


    public void draw(PApplet sketch, int x, int y, boolean flipped) {
        sketch.imageMode(PConstants.CENTER);
        PImage og = sketch.loadImage("mechanic.png");

        sketch.image(og, x, y, 50, 50);
    }

    @Override
    public String getDescription() {
        return "Damage: " + getPower() + " | Health: " + getHealth() + " | Cost: " + getCost() + "\nRepairs adjacent Artillery & Tank";
    }

    public String toString(){
        return "|Mechanic " + "Health: " + getHealth() + " " + "Power: " + getPower() + "|";
    }
}
