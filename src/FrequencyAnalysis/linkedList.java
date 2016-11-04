package FrequencyAnalysis;

public class linkedList {
    
    class Node {
        char alphabet;
        int count;
        double frequency;
        Node next;
        
        public Node(char a) {
            alphabet = a;
            count = 1;
            frequency = 0;
            next = null;
        }
        
        public char getAlphabet() {
            return alphabet;
        }
        
        public int getCount() {
            return count;
        }
        
        public double getFrequency() {
            return frequency;
        }
        
        public void increment() {
            count++;
        }
        
        public void setNext(Node n) {
            next = n;
        }
        
        public Node getNext() {
            return next;
        }
    }
    
    Node head;
    int length;
    int totalCharCount;
    
    /**
     * Default constructor. An empty list.
     */
    public linkedList() {
        head = null;
        length = 0;
        totalCharCount = 0;
    }
    
    /**
     * The linked list receives a character and puts it to the right place.
     * @param a The character to add to the list.
     */
    public void add (char a) {
        if (head == null) {
            head = new Node(a);
            length++;
            totalCharCount++;
        } else {
            Node iterator = head;
            while (true) {
                if (iterator.getAlphabet() == a) {
                    iterator.increment();
                    totalCharCount++;
                    return;
                } else {
                    if (iterator.getNext() == null) {
                        break;
                    } else {
                        iterator = iterator.getNext();
                    }                    
                }
            } //end of while loop
            /**
             * At this point, the iterator is at the tail.
             * A new node should be appended after iterator.
             */
            Node newNode = new Node(a);
            iterator.setNext(newNode);
            length++;
            totalCharCount++;
        }
    }
    
    public void printRawCount() {
        if (head == null) {
            System.out.println("-");
            return;
        }        
        Node iterator = head;
        while (iterator != null) {
            System.out.println(iterator.getAlphabet() + " " + iterator.getCount());
            iterator = iterator.getNext();
        }        
    }
    
    public void printFrequency() {
        if (head == null) {
            System.out.println("-");
            return;
        }        
        Node iterator = head;
        while (iterator != null) {
            System.out.println(iterator.getAlphabet() + " " + (iterator.getFrequency() * 100) + "%");
            iterator = iterator.getNext();
        }        
    }
    
    public frequencyChart toChart() {
        frequencyChart fc = new frequencyChart(length);
        Node iterator = head;
        for (int i = 0; i < length; i++) {
            fc.set(i, iterator.getAlphabet(), iterator.getFrequency());
            iterator = iterator.getNext();
        }
        return fc;
    }
    
    public void calculateFrequency() {
        if (head != null) {
            Node iterator = head;
            while (iterator != null) {
                iterator.frequency = (double) iterator.getCount() / totalCharCount;
                iterator = iterator.getNext();
            }
        }
    }
    
    public void sort() {
        head = mergeSort(head);
    }
    
    private Node mergeSort(Node n) {
        if (n == null || n.next == null) {
            return n;
        }
        Node midNode = getMidNode(n);
        Node a = n; //or, left hand side
        Node b = midNode.getNext(); //or, the right hand side
        midNode.next = null;
        
        //mergeSort(a);
        //mergeSort(b);
        
        n = merge(mergeSort(a),mergeSort(b));
        return n;
    }
    
    private Node getMidNode(Node n) {
        if (n == null) {
            return n;
        }
        Node slow = n;
        Node fast = n;
        
        while(fast.next != null && fast.next.next != null) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }
        
        return slow;
    }
    
    private Node merge(Node a, Node b) {
        Node result = null;
        
        if (a == null) {
            return b;
        } else if (b == null) {
            return a;
        }
        
        if (a.getCount() >= b.getCount()) {
            result = a;
            result.next = merge(a.getNext(), b);
        } else {
            result = b;
            result.next = merge(a, b.getNext());
        }
        return result;
    }
    
    public String top8() {
        String s = "";
        Node iterator = head;
        for (int i = 0; i < 8; i++) {
            if (iterator == null) {
                break;
            } else {
                s += iterator.getAlphabet();
            }
            iterator = iterator.getNext();
        }
        return s;
    }
    
    public String bot4() {
        String s = "";
        Node iterator = head;
        for (int i = 0; i < 4; i++) {
            if (iterator == null) {
                break;
            } else {
                s += iterator.getAlphabet();
            }
            iterator = iterator.getNext();
        }
        while (iterator != null) {
            s = squeezeIn(s, iterator.getAlphabet());
            iterator = iterator.getNext();
        }
        return s;
    }
    
    private String squeezeIn(String s, char c) {
        String newS = "";
        for (int i = 0; i < s.length()-1; i++) {
            newS = newS + s.charAt(i+1);                    
        }
        newS = newS + c;
        return newS;
    }
}
