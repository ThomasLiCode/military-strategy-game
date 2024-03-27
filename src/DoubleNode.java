import processing.core.PApplet;
public class DoubleNode {
    private Entity value;
    private DoubleNode next;
    private DoubleNode prev;

    public DoubleNode(Entity value, DoubleNode prev, DoubleNode next){
        this.value = value;
        this.prev = prev;
        this.next = next;
    }

    public Entity getValue(){ return value; }
    public DoubleNode getPrev(){ return prev; }
    public DoubleNode getNext(){ return next; }
    public void setPrev(DoubleNode node){ prev = node; }
    public void setNext(DoubleNode node){ next = node; }
}
