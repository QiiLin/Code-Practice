/**
 * Given the root of a Binary Search Tree and a target number k, return true if there exist two elements in the BST such that their sum is equal to the given target.
 * 
 */

 /**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public boolean findTarget(TreeNode root, int k) {
        // warm up question: BST: sol find the two element such that its sum up to the given target
        // init idea: flaten it to linear strcuture and find the sum by having two pointer
        // space: n  time: n
        // second idea: using hashset to verify the existen of wanted sum same space and same run time
        HashSet<Integer> hd = new HashSet<Integer>();
        return helper(root, k, hd);
    }
    public boolean helper(TreeNode root, int k, HashSet<Integer> hd){
        if (root != null) {
            if (hd.contains(k - root.val))
                return true;
            hd.add(root.val);
            return helper(root.left, k, hd) || helper(root.right, k, hd); 
        } else {
            return false;
        }
    }
}


/**
 * Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.

A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
 */

class Solution {
    public List<String> letterCombinations(String digits) {
        //this is the process of building strings combination 
        // ideally string builder will be a better usage here
        // init thoughts
        // at max it will have 4 digits and they are all 2-9
        // we can construct a fix maping by the 
        // simplest idea will be having  a b c 
        // [ad ae af bd be bf cd ce cf] [g h i]
        // feels like backtracking 
        // but it just looks like brute
        // DP? no
        // Greedy no
        // backtracking? yes
        // divide and conquee? it could be done in such way 
        List<String> ans = new ArrayList<String>();
        if(digits==null||digits.length()==0) return ans;
        char[][] map = new char[8][];
        map[0]="abc".toCharArray();
        map[1]="def".toCharArray();
        map[2]="ghi".toCharArray();
        map[3]="jkl".toCharArray();
        map[4]="mno".toCharArray();
        map[5]="pqrs".toCharArray();
        map[6]="tuv".toCharArray();
        map[7]="wxyz".toCharArray();
        char[] input = digits.toCharArray();
        ans.add("");
        for(char c:input)
            ans=expand(ans,map[c-'2']);
        return ans;
    }
    private List<String> expand(List<String> l,char[] arr) {
        List<String> next = new ArrayList<String>();
        for(String s:l)
            for(char c:arr)
                next.add(s+c);
        return next;
    }
}

// Copy List with Random Pointer
/*
// Definition for a Node.
class Node {
    int val;
    Node next;
    Node random;

    public Node(int val) {
        this.val = val;
        this.next = null;
        this.random = null;
    }
}
*/

class Solution {
    public Node copyRandomList(Node head) {
        // so it is a process of makeing a deep copy.  
        // init issue: when node is pointing to something we made a copy of it
        // the how can we form the same relation as before
        // also the duplicate is allowed, how can we verify which is which
        // simplest way will be using node maping 
        // so when we make copy 
        // init idea will work: the space will be O(n) the time compexilty will be O(n) but this is a two pass algorithm
        // Question: is there a way for use to use constant space
        // here is my idea: make the deep copy once without handling the random pointer
        // how about this ... we make the deep copy and we change the orginal random pointer of the orignal list to its copy
        // and in the next pass 
        // we go throug the deep copy linked list and do the following
        //    - 1.swap the randome pointer value of orignal and deep copy
        //        issue: we are modifiing the orginal list 
        // ...is there any other place we can keep the relation between the copy and original node
        //  we can having the copy follows orginal node.
        if (head == null) return null;
        Node cur = head;
        while (cur != null) {
            Node next = cur.next;
            cur.next = new Node(cur.val, next, null);
            cur = next;
        }
        cur = head;
        while (cur != null) {
            if (cur.random != null)
                cur.next.random = cur.random.next;
            cur = cur.next.next;
        }
        cur = head;
        Node copyHead = head.next;
        while (cur != null) {
            Node next = cur.next.next;
            Node copy = cur.next;
            cur.next = next;
            if (next != null)
                copy.next = next.next;
            cur = next;
        }
        return copyHead;
    }
}

/**
 * Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it can trap after raining.
 */
class Solution {
    public int trap(int[] height) {
        if (height == null || height.length < 2) return 0;
        Stack<Integer> stack = new Stack<>(); // so that when we found the valley... we keep poping and caluate water block
        int water = 0, i = 0;
        while (i < height.length) {
            if (stack.isEmpty() || height[i] <= height[stack.peek()]) {
                stack.push(i++);
            } else {
                int curr = stack.pop();
                if (!stack.isEmpty()) {
                    // find the smaller height between the left bar and right bar
                    int minHeight = Math.min(height[stack.peek()], height[i]);
                    // calculate the area of water block
                    water += (minHeight - height[curr]) * (i - stack.peek() - 1);
                }
            }
        }
        // copy from the solution this is constant space 
/**
 * public class Solution {
    public int trap(int[] height) {
        int left = 0, right = height.length - 1;
        int res = 0, maxleft = 0, maxright = 0;
        while (left <= right) {
            if (height[left] <= height[right]) {
                if (height[left] >= maxleft) {
                    maxleft = height[left];
                } else {
                    res += maxleft - height[left];
                }
                left++;
            }
            else {
               if (height[right] >= maxright) {
                   maxright = height[right];
               } else {
                   res += maxright - height[right];
               }
                right--;
            }
        }
        return res;
    }
}
 * 
 * 
 */
        return water;
    }
}