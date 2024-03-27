import processing.core.PApplet;
import processing.core.PConstants;
import processing.event.KeyEvent;
import processing.sound.*;
import processing.core.PImage;

public class Main extends PApplet{
    final int INSTRUCTIONS = 0;
    final int PREGAME = 1;
    final int GAMEPLAY = 2;
    final int GAME_WIN = 3;
    final int GAME_END = 4;
    int gameState = INSTRUCTIONS;
    int delay = 100;
    int lastPress = 0;
    int resources = 1000;
    int nextPress = 0;
    int attemptsLeft = 2;
    SoundFile button;
    SoundFile backGroundMusic;
    PImage introBackground;
    DLList allyList;
    DLList enemyList;
    Entity[] shopList = {new Tank(), new Artillery(), new Mechanic(), new Spy(), new Soldier(), new MissileLauncher()};//List of Entity used to draw shop icons and interact with user
    public void settings(){

        size(1000, 500);
        pixelDensity(displayDensity());
    }
    //List of preset Linked List Levels. Every time a level is beaten we set enemyList to the next index of the enemyLevels array
    DLList[] enemyLevels = {
            new DLList(new Tank(), new Soldier(), new Soldier(), new Soldier(), new Soldier(), new Artillery(), new Artillery()),
            new DLList(new Tank(), new Mechanic(), new Artillery(), new Soldier(), new Artillery(), new Soldier(), new Artillery()),
            new DLList(new Tank(), new Mechanic(), new Artillery(), new Spy(), new MissileLauncher(), new Tank(), new Artillery()),
            new DLList(new Tank(), new Artillery(), new Artillery(), new Artillery(), new Artillery(), new Spy(), new Soldier()),
            new DLList(new Tank(), new Artillery(), new Mechanic(), new Artillery(), new MissileLauncher(), new Spy(), new Artillery())
    };
    private int currentLevel = 0;
    public void setup(){
        button = new SoundFile(this, "buttonSound.mp3");
        backGroundMusic = new SoundFile(this, "backgroundMusic.mp3");
        //backGroundMusic.play();
        frameRate(60);
        allyList = new DLList();
        enemyList = new DLList();
        allyList.drawAll(this);
    }
    public void mousePressed(){
        if(gameState == INSTRUCTIONS){
            gameState = PREGAME;
        }
        if(gameState == PREGAME){
            if (mouseX > 425 && mouseX < 575 && mouseY > 225 && mouseY < 275 && millis() - lastPress >= delay) {//if press "Reveal Enemy" button
                button.play();
                if(allyList.length() == 0){
                    if (attemptsLeft <= 0){//if no attempts left, end game
                        gameState = GAME_END;
                        return;
                    }
                    if (currentLevel >= enemyLevels.length) {//if no levels left, win game
                        gameState = GAME_WIN;
                        return;
                    }
                    if (resources < 25){//if resources depleted, end game
                        gameState = GAME_END;
                        return;
                    }
                    push();
                    textSize(32);
                    textAlign(CENTER);
                    fill(250, 0, 0);
                    text("Build a team first!", width / 2.0f, height / 2.0f -50);//warning to prevent users from going to the next level without having any units in their army
                    pop();
                }else{
                      gameState = GAMEPLAY;
                    enemyList = enemyLevels[currentLevel];//set new level
                    currentLevel++;
                    DoubleNode allyHead = allyList.getHead();
                    DoubleNode enemyHead = enemyList.getHead();
                    //reverses opposing team list at start of battle if instance of Spy is found in Linked List
                    spyAction(allyHead, allyList);
                    spyAction(enemyHead, enemyList);
                }
            }
            lastPress = millis();
        } else if (gameState == GAMEPLAY) {
            if (mouseX > 425 && mouseX < 575 && mouseY > 225 && mouseY < 275 && millis() - lastPress >= delay) {//if the "fight" button is pressed
                button.play();
                //traverses Linked List and performs .action() method on each Entity traversed
                allyList.allAction(allyList, enemyList);
                enemyList.allAction(enemyList, allyList);
                //update both Linked List, removing nodes that have an Entity <= 0 health
                allyList.refreshList();
                enemyList.refreshList();
                if(enemyList.length() == 0){//if enemy is defeated increases resources and move to next pregame
                    resources += 500;
                    gameState = PREGAME;
                }
                if(allyList.length() == 0){//if ally team is dead lose an attempt and move to next pregame
                    attemptsLeft--;
                    gameState = PREGAME;
                }
            }
            lastPress = millis();
        }
    }
    public void spyAction(DoubleNode head, DLList list){//Checks if there is a Spy in list and if so, reverses Linked List
        DLList otherList;
        if(list == allyList) otherList = enemyList;
        else otherList = allyList;

        while (head != null) {
            if (head.getValue() instanceof Spy) {
                otherList.reverse();
            }
            head = head.getNext();
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {//insert Entity at desired position
        if (selectingEntityPos == null) return;

        int index = event.getKeyCode() - 49;
        if (index < 0 || index > allyList.length()) return;//calculates index based on keyCode
        if(allyList.length() < 7) {
            allyList.insert(index, selectingEntityPos);
            resources -= selectingEntityPos.getCost();
        }
        selectingEntityPos = null;
         /*Referring to program above: selectingEntityPos becomes an instance of the Entity that the user clicked on the shop.
            We then deduct the cost from our total resources. We then set selectingEntityPos to null so this code does not run whenever
            a key event occurs because if selectingEntityPos is null we return
            */
    }

    public void draw() {//draws each stage of game
        if(gameState != PREGAME){
            selectingEntityPos = null;
        }
        if(gameState == INSTRUCTIONS) {
            drawInstruction();
        } else if(gameState == GAME_END){
            background(100);
            push();
            fill(0);
            textSize(20);
            textAlign(CENTER);
            text("You lose!", width / 2.0f, height / 2.0f + 8);
            pop();
        }else if (gameState == GAME_WIN) {
            background(100);
            push();
            fill(0);
            textSize(20);
            textAlign(CENTER);
            text("You Win", width / 2.0f, height / 2.0f + 8);
            pop();

        } else if( gameState == PREGAME){
            drawGamePlay();
            drawShop();
            if (selectingEntityPos != null && allyList.length() < 7)  {//prompt user for keyboard input of positon
                push();
                fill(0);
                rect(0, 0, width, height);
                textAlign(CENTER);
                fill(255);
                text("Input a position from 1-" + (allyList.length() + 1), width / 2.0f, height / 2.0f + 8);
                pop();
            }
        } else {
            drawGamePlay();
        }
    }

    public void drawGamePlay(){
        background(100);
        stroke(230);
        line(0, 300, 1000, 300);

        DoubleNode node = allyList.getTail();
        int length = allyList.length();
        int x = 0;

        while (node != null) {


            Entity e = node.getValue();

            int xPos = 450 + (length - x) * -55;
            int yPos = 250;
            e.draw(this, xPos, yPos);

            if (e.mouseHits(xPos, yPos, mouseX, mouseY)) {//hover over Entity and get description
                push();
                textSize(20);
                text(e.getClass().getName(), 100, 330);
                textSize(12);
                text(e.getDescription(), 100, 350);
                pop();
            }

            x++;
            node = node.getPrev();
        }


        node = enemyList.getTail();
        length = enemyList.length();
        x = 0;
        while (node != null) {
            node.getValue().draw(this, 550 + (length - x) * 55, 250, true);
            x++;
            node = node.getPrev();
        }
        rect(425, 225, 150, 50);

        push();
        fill(0);
        textSize(20);
        textAlign(CENTER);
        text(gameState == PREGAME ? "Reveal Enemy" : "Fight", width / 2.0f, height / 2.0f + 8);
        pop();

        push();
        textAlign(CENTER);
        textSize(20);
        text("Form your team buy purchasing units!", 500, 100);
        text("Click icons to purchase units", 500, 330);
        pop();

        push();
        textSize(20);
        text("Resources: " + resources, 15, 30);
        text("Attempts left: " + attemptsLeft, 850, 30);
        pop();
    }

    public Entity selectingEntityPos = null;

    public void drawShop(){
        imageMode(PConstants.CENTER);
        int xPos = 210;
        int yPos = 400;
        for(int i = 0; i < shopList.length; i++){//iterate through array to draw individual icon
            Entity e = shopList[i];
            e.draw(this, xPos, yPos);
            if (e.mouseHits(xPos, yPos, mouseX, mouseY)) {
                push();
                textSize(20);
                text(e.getClass().getName(), 100, 330);
                textSize(12);
                text(e.getDescription(), 100, 350);
                pop();
                if(mousePressed && millis() >= nextPress){
                    button.play();
                    nextPress = millis() + 200;
                    try {
                        if(resources - e.getCost() >= 0) {
                            selectingEntityPos = e.getClass().getConstructor().newInstance();/*selectingEntityPos becomes an instance of the specific Entity it hovered over
                            this is important for the insertion and keyboard inputs as we must know what to add to the LinkedList. */
                        }

                    }catch(Exception exception){
                        System.out.print("Could not find constructor");
                    }
                }
            }
            xPos += 120;
        }
    }

    public void drawInstruction(){
        background(100);
        introBackground = loadImage("INTROBACKGROUND.png");
        image(introBackground, 0, 0);
        push();
        textAlign(CENTER);
        textSize(32);
        text("Welcome to Army Simulator!", 500, 100);
        pop();

        push();
        textAlign(CENTER);
        textSize(20);
        text("In this simulator you will play as an army commander and buy units to form the strongest army possible.\nCarefully read the description of each unit and use that information to create the optimal team composition.\n From there, click the REVEAL ENEMY button to see the enemy army and then spam the FIGHT button\n until only one army is left. You only have two tries to defeat five armies.\nGood Luck!\n\nMOUSE CLICK TO CONTINUE", 500, 200);
        pop();


    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}