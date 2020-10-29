
/**
 * Input: s = "?zs"
Output: "azs"
Explanation: There are 25 solutions for this problem. From "azs" to "yzs", all are valid. Only "z" is an invalid modification as the string will consist of consecutive repeating characters in "zzs".
 */
public String modifyString(String s) {
    // we always has a solution
    // simple question 
    int n = s.length();
    if (n == 0) {
        return "";
    }
    StringBuilder st = new StringBuilder();
    char prev = '1';
    for(int i = 0; i < n; i++) {
        char c = s.charAt(i);
        if (c == '?') {
            int counter = -1;
            if (prev != '1') {
                counter = prev - 'a';
            }
            int next_counter = -1;
            if ( i + 1 < n) {
                next_counter =  s.charAt(i+1) - 'a';
            }
            // recall its lower case
            char target = 'a';
            for (int j = 0; j < 26; j++) {
                if (j  != counter && j != next_counter) {
                    target =(char)(97 + j);
                }
            }
            st.append(target);
            prev = target;
        } else {
            st.append(c);
            prev = c;
        }
    }
    return st.toString();
}

/**
 * Bulb Switcher III
 */

class Solution {
    public int numTimesAllBlue(int[] light) {
        // room with n bulbs and are number 1 to n 
        // init case all off
        // at moment k we turn on the light[k]
        // color of light will be blue if all previous are on 
        // find the number of case where all turnon is blue
        // key factor/ restrcition 
        //  1. bulbs will on turn blue if its prev are on
        // init idea brtue force will be 
        // trying out all possible way
        // all light blue => all the turn on light are consecutive and on of it is the init light bulb is on
        // 2 condition to check
        // make it clear here if the total number of append element is the same as the largest index that means ...a blue light happens
        // since we know it will start from 1 for sure
        // 1, 
        // ie as following:
        /**
        try  track of state
        1. try to update state one by one.. noting update on light will require up all others 
        st: 1 2 3 4 5 
        m   0 1 2 3 4  
        on: 2 
        // for every turn on check its prev state if it is on and is blue we increase count
        // if the prev state is off... don;t increase the count
        // so two state to keep track here...on or off and color 
        // run time will be n^2 and space will be linear as well
        // can we do better ? heap, hashmap, 
        */
        int n = light.length;
        int count = 0;
        int max = -1;
        int res = 0;
        for (int i = 0; i < n; i++) {
            max = Math.max(light[i], max);
            count += 1;
            if (max == count) {
                res ++;
            }
        }
        return res;
    }
}



// SKIPLIST

class Skiplist {
    // keep track of a list of head 
    // and default is coin flap so it will be 0.5
    // idea is simple. it just takes time to code it out
    class Node {
        int val;
        Node prev, next, up, down;
        Node(int val){
            this.val = val;
        }
    }
    Node head;
    Node tail;
    Random rand = new Random();
    public Skiplist() {
        head = new Node(Integer.MIN_VALUE);
        tail = new Node(Integer.MAX_VALUE);
        head.next = tail;
        tail.prev= head;
        
    }
    private Node getSmallerOrEqual(int val, Node start){
        if(start.next.val<=val){
            return getSmallerOrEqual(val, start.next);
        }else{
            if(start.down!=null) return getSmallerOrEqual(val, start.down);
            else return start;
        }
    }
    public boolean search(int target) {
        return target == getSmallerOrEqual(target, head).val;
    }
    
    public void add(int num) {
        Node prev = getSmallerOrEqual(num, head);
        Node cur = new Node(num);
        insert(prev, cur);
        levelUp(cur);
    }
    private boolean flip(){
        return rand.nextInt(2)==1;
    }
    private void levelUp(Node cur){
        if(flip()){
            Node prev = cur.prev;
            while(prev!=null&&prev.up==null)
                prev = prev.prev;
            if(prev==null){
                Node newhead = new Node(Integer.MIN_VALUE);
                Node newtail = new Node(Integer.MAX_VALUE);
                newhead.down = head;
                newhead.next = newtail;
                head.up = newhead;
                newtail.down = tail;
                tail.up = newtail;
                newtail.prev = newhead;
                head =newhead;
                tail=newtail;
                prev=head.down;
            }
            
            Node upcur = new Node(cur.val);
            upcur.down = cur;
            cur.up = upcur;
            insert(prev.up, upcur);
            levelUp(upcur);
        }
    }
    private void insert(Node prev, Node cur){
        Node next = prev.next;
        prev.next = cur;
        cur.next = next;
        cur.prev = prev;
        next.prev = cur;
    }
    private void remove(Node node){
        if(node==null) return;
        Node prev = node.prev;
        Node next = node.next;
        prev.next = next;
        next.prev = prev;
        remove(node.up);
    }
    public boolean erase(int num) {
        Node prev = getSmallerOrEqual(num, head);
        if(prev.val != num) return false;
        remove(prev);
        return true;
    }
}