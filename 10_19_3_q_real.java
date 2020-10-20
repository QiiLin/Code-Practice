// Given a singly linked list, determine if it is a palindrome
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public boolean isPalindrome(ListNode head) {
        // check if a linked list is palindrome 
        //  having a slow and fast pointer
        // when fast ends the slow will be at middle 
        // then I will reverse the first part of the linked list
        // and compare the value of two breaked linked list
        // lets code it out 
        ListNode slow = head;
        ListNode fast = head;
        ListNode prevs = null;
        ListNode prevf = null;
        while(fast != null && fast.next != null) {
            prevf = fast.next;
            fast = fast.next.next;
            prevs = slow;
            slow = slow.next;
        }
        // if prevf .next == null it is even linked list
        // if prevf.next != null it is odd
        ListNode prev = null;
        fast = slow;
        while(fast != null) {
            ListNode temp = fast.next;
            fast.next = prev;
            prev = fast;
            fast = temp;
        }
            
        while (prev != null &&  head != null) {
            if (prev.val != head.val) {
                return false;
            }
            prev = prev.next;
            head = head.next;
        }
        return true;
    }
}
// Given a string, sort it in decreasing order based on the frequency of characters.

class Pair {
    Character v;
    int c;
    public Pair(Character v, int c) {
        this.v = v;
        this.c = c;
    }
}
class Solution {
    public String frequencySort(String s) {
        // sorted base on frequency of character
        // inituive approach
        // will be using the 26 count memo
        // and build a size of 26 heap
        // and then build the return string
        // besure to use string builder to save time
        Map<Character, Integer> freqMap = new HashMap<>();
        for(char task : s.toCharArray()) {
            freqMap.put(task, freqMap.getOrDefault(task, 0)+1);
        }
        PriorityQueue<Pair> pq = new PriorityQueue<Pair>((a,b) -> b.c - a.c);
        for (Character c : freqMap.keySet()) {
            if (freqMap.get(c) > 0) {
                pq.add(new Pair(c,freqMap.get(c) ));
            }
        }
        StringBuilder sb = new StringBuilder();
        while(!pq.isEmpty()) {
            Pair currnet = pq.poll();
            String temp = "" + currnet.v;
            for (int i = 0; i < currnet.c; i++) {
                sb.append(temp); 
            }
        }
        return sb.toString();
        
    }
}


/**
 * You are given a doubly linked list which in addition to the next and previous pointers, it could have a child pointer, 
 * which may or may not point to a separate doubly linked list. These child lists may have one or more children of their own, 
 * and so on, to produce a multilevel data structure, as shown in the example below.
 * Flatten the list so that all the nodes appear in a single-level, doubly linked list. You are given the head of the first level of the list.
 */
/*
// Definition for a Node.
class Node {
    public int val;
    public Node prev;
    public Node next;
    public Node child;
};
*/

class Solution {
    public Node flatten(Node head) {
        // based on the given example, it is cleear a dfs 
        if (head == null) {
            return null;
        }
        dfs_return(head);
        return head;
    
    }
    public Node dfs_return(Node head) {
        Node child = null;
        Node next = null;
        if (head.next != null) {
            // last node in the next 
            next = dfs_return(head.next);
        }
        if (head.child != null) {
            // last node in the child
            child = dfs_return(head.child);
        }
        if (child != null) {
            // have child
            if (head.next != null) {
                head.next.prev = child;
                child.next = head.next;
            }
            head.next = head.child;
            head.child.prev = head;
        }
        head.child = null;
        return next == null ?(child == null ? head: child) : next;
    }
}
