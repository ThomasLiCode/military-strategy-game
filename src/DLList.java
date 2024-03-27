import processing.core.PApplet;

public class DLList {
    private DoubleNode head;
    private DoubleNode tail;
    public DLList(){
        head = null;
        tail = null;
    }
    public DLList(Entity ...entities) {//take in as many Entity objects wanted
        head = null;
        tail = null;

        for (Entity e : entities) {//adds each Entity into the Linked List
            addLast(e);
        }
    }

    public DoubleNode getHead() {
        return head;
    }

    public void allAction(DLList allyList, DLList enemyList){//performs .action() on all Entities within a Linked List
        System.out.println();
        if(head == null)
            return;
        DoubleNode iter = head;
        while(iter != null){
            System.out.print(", " + iter.getValue().getHealth() + ", ");
            iter.getValue().action(allyList, enemyList);
            iter = iter.getNext();
        }
    }
    public void refreshList(){//updates Linked List to eliminate nodes with Entities that are <=0 health
        if(head == null)
            return;
        DoubleNode iter = head;
        while(iter != null){
            if(iter.getValue().isDestroyed()){//is true when Entity health less than one
                Entity temp = iter.getValue();
                iter = iter.getNext();
                delete(temp);
            }else{
                iter = iter.getNext();
            }
        }
    }
    public void drawAll(PApplet sketch){//draw all Entities
        if(head == null)
            return;
        DoubleNode n = head;
        int i = 0;
        while(n != null){
            n.getValue().draw(sketch, i * 50, 100);
            n = n.getNext();
        }
    }
    public void delete(Entity entity) {//Delete Entity from list
        DoubleNode node = head;
        while (node != null && node.getValue() != entity) {
            node = node.getNext();
        }

        if (node == null) return;

        DoubleNode prev = node.getPrev();
        DoubleNode next = node.getNext();

        if (prev != null) prev.setNext(next);//care for cases of where entity is head
        else head = next;
        if (next != null) next.setPrev(prev);//care for cases of where entity is tail
        else tail = prev;
    }

    public DoubleNode get(int index){//Get Entity at Index
        if(head == null){
            return null;
        }else{
            DoubleNode current = head;
            int count = 0;
            while(current != null){
                if(count == index){
                    return current;
                }
                count++;
                current = current.getNext();
            }
            return null;
        }
    }
    public DoubleNode getTail(){
        if(head == null){
            return null;
        }
        return tail;
    }
    public void addFirst(Entity e){
        if(head == null){
            DoubleNode newNode = new DoubleNode(e, null, null);
            head = newNode;
            tail = newNode;
        }else{
            DoubleNode newNode = new DoubleNode(e, null, head);
            head.setPrev(newNode);
            head = newNode;
        }
    }

    public void addLast(Entity e){
        if(length() >= 7){
            return;
        }
        //if Linked List is empty, set head and tail to the new node
        if(head == null){
            DoubleNode newNode = new DoubleNode(e, null, null);
            head = newNode;
            tail = newNode;
        }else{
            DoubleNode newNode = new DoubleNode(e, tail, null);
            tail.setNext(newNode);
            tail = newNode;
        }
    }

    public void missileMethod(Class<?> target){//Deals 75 damage to all Entities that are instances of target
        if(head == null)
            return;
        DoubleNode curr = head;
        while(curr != null){
            if(curr.getValue().getClass() == target){//check if the Class is equal to our target class
                int health = curr.getValue().getHealth();
                curr.getValue().setHealth(health - 75);
                System.out.println("health: " + curr.getValue().getHealth());
            }
            curr = curr.getNext();
        }
    }



    public void insert(int index, Entity e){//Create a new DoubleNode with Entity value at index within Linked List
        if(head == null || index == length()) {
            addLast(e);
        } else if(index == 0){
            DoubleNode newNode = new DoubleNode(e, null, head);
            head.setPrev(newNode);
            head = newNode;
        }else{
            DoubleNode current = head;
            int count = 0;
            while(current != null){
                if(count == index){//when we reach the desired index, we add a new DoubleNode
                    DoubleNode newNode = new DoubleNode(e, current.getPrev(), current);
                    current.getPrev().setNext(newNode);
                    current.setPrev(newNode);
                }
                count++;//increment count so we can track where we are
                current = current.getNext();//increment current so we can progress deeper into the Linked List
            }
        }
    }
    public int findIndex(Entity e){//finds index of Entity within Linked List
        return findIndexR(e, head, 0);
    }
    public int findIndexR(Entity e, DoubleNode curr, int index){//recursive helper method
        if(curr == null)//if e can not be found
            return -1;
        if(curr.getValue().equals(e)){//if we find e we return
            return index;
        }
        return findIndexR(e, curr.getNext(), index  + 1);//Same thing as curr = curr.getNext()
    }
    public int length(){//returns length of Linked List
        if(head == null){
            return 0;
        }
        int count = 1;
        DoubleNode current = head;
        while(current.getNext() != null){
            count++;
            current = current.getNext();
        }
        return count;
    }

    public DoubleNode reverse(){//Reverses Doubly Linked List
        if(head == null || head.getNext() == null)
            return null;
        tail = head;
        DoubleNode ptr1 = head;
        DoubleNode ptr2 = head.getNext();
        ptr1.setNext(null);
        ptr1.setPrev(ptr2);
        while(ptr2 != null){
            ptr2.setPrev(ptr2.getNext());
            ptr2.setNext(ptr1);
            ptr1 = ptr2;
            ptr2 = ptr2.getPrev();//we iterate using .getPrev() because ptr2.getPrev() is ptr2's original .getNext()
        }
        head = ptr1;
        return head;
    }

    public String toString(){//Neatly returns Entities within a list
        if(head == null){
            return null;
        }
        String result = "";
        DoubleNode current = head;
        while(current != null){
            result += current.getValue() + " ";
            current = current.getNext();
        }
        return result;
    }
}
