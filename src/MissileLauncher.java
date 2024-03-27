import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;
public class MissileLauncher extends Entity{

    public MissileLauncher(){
        super(100, 75, 75);
    }
    @Override
    public void action(DLList allyTeam, DLList enemyTeam) {
       int index = allyTeam.findIndex(this);
       int length = allyTeam.length();
       try {
           if (index >= 1 && index < length - 1) {
               DoubleNode location = allyTeam.get(index);
               if(location.getPrev().getValue() instanceof Spy || location.getNext().getValue() instanceof Spy) {//checks if adjacent nodes have an Entity that is a Spy
                   Class<?> candidate1 = location.getPrev().getValue().getClass();
                   Class<?> candidate2 = location.getNext().getValue().getClass();
                   if (candidate1 == Spy.class) candidate1 = candidate2;//favor non Spy entity
                   enemyTeam.missileMethod(candidate1);//deals 75 damage to all Entities in enemy army that are instances of candidate1
               }
           }else if(index < enemyTeam.length()){
               int enemyHealth = enemyTeam.get(index).getValue().getHealth();
               enemyTeam.get(index).getValue().setHealth(enemyHealth - getPower());//does 75 damage to enemy Entity that shares the same index position as current MissileLauncher
           }else{
               int enemyLength = enemyTeam.length();
               if(enemyLength != 0){
                   int enemyHealth = enemyTeam.get(enemyLength - 1).getValue().getHealth();
                   enemyTeam.get(enemyLength - 1).getValue().setHealth(enemyHealth - getPower());//does 75 damage to enemy Entity at the end of enemy army Linked List
               }
           }
       }catch(Exception exception){
           System.out.println(exception);
       }
    }

    public void draw(PApplet sketch, int x, int y, boolean flipped) {
        sketch.imageMode(PConstants.CENTER);
        PImage og = sketch.loadImage("MissileLauncher.png");

        sketch.image(og, x, y, 50, 50);
    }
    @Override
    public String getDescription() {
        return "Damage: " + getPower() + " | Health: " + getHealth() + " | Cost: " + getCost() + "\nSpecial Combo: When adjacent to a spy, will target all enemy units that share same type as its other adjacent unit\nAttacks target at same positon as it";
    }

    public String toString(){
        return "|Missile " + "Health: " + getHealth() + " " + "Power: " + getPower() + "|";
    }
}
