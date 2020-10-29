/** Middle of the Linked List
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
    public ListNode middleNode(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while(fast != null && fast.next != null) {
            
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}

//jump game
/*
Given an array of non-negative integers, you are initially positioned at the first index of the array.

Each element in the array represents your maximum jump length at that position.

Determine if you are able to reach the last index.
*/

class Solution {
    public boolean canJump(int[] nums) {
        // condition ot reach last 
        //  if there is an 0 
        // init idea:
        // for each zero in the list that is not the last index
        //   we go from left to right
        // 
        // how abot this we keep track of max possible reach distance 
        // if it is not the last index value 
        // forget about the edge case
        // think again 
        // what if there is not enough step to reach 
        // that means the jump on the index does not help at all 
        int max = -1;
        int n = nums.length;
        for (int i = 0; i < n -1; i++) {
            // if current index is reachable or its init case
            if (max >= i || max == -1) {
                max = Math.max(max, nums[i] + i);
            }

        }
        return n == 1 ||  max >= n-1;
    }
}


/**
 * Under a grammar given below, strings can represent a set of lowercase words.  Let's use R(expr) to denote the set of words the expression represents.
 */

class Solution {
    public List<String> braceExpansionII(String expression) {
        // recursive structure
        // , => union of possibility 
        // feels like recusive call
        // {  when we see } 
        
        Queue<String> queue = new LinkedList<>();
        queue.offer(expression);
        Set<String> set = new HashSet<>();
        
        while (!queue.isEmpty()) {
            String str = queue.poll();
            // 
            if (str.indexOf('{') == -1) {
                set.add(str);
                continue;
            }
            // find the most inner {}
            int i = 0, l = 0, r = 0;
            while (str.charAt(i) != '}') {
                if (str.charAt(i) == '{') 
                    l = i; 
                i++;
            }
            r = i;
            // break its 
            String before = str.substring(0, l);
            String after = str.substring(r+1);
            // spilt the inner most by , 
            String[] strs = str.substring(l+1, r).split(",");
            // for each possible value we append it back so that it will be handle again.
            // { {a,b}, a } =?  {a,a} and { b, a} will push in 
            // in the end ... we will go through all possible ways to compute it ..
            // since it does not allow duplicate..
            // using set to handle this.
            StringBuilder sb = new StringBuilder();
            for (String ss : strs) {
                sb.setLength(0);
                queue.offer(sb.append(before).append(ss).append(after).toString());
            }
        }
        // covert set to return type... and that's it
        List<String> ans = new ArrayList<>(set);
        Collections.sort(ans);
        return ans;
        /**
         *  the above work. it is not the most optimal solution
         *  when we poll in new string back to handle it take more time to run it... we just keep removing element..
         *  the below is just the normal recursive call and it work better than above since it does not check combination one by one
         * 
         * class Solution {
    public List<String> braceExpansionII(String expression) {
        List<String> res = new ArrayList<>();
        if (expression.length() <= 1) {
            res.add(expression);
            return res;
        }
        if (expression.charAt(0) == '{') {
            int cnt = 0;
            int idx = 0;
            for (; idx < expression.length(); idx++) {
                if (expression.charAt(idx) == '{') cnt += 1;
                if (expression.charAt(idx) == '}') cnt -= 1;
                if (cnt == 0) break;
            }
            List<String> strs = helper(expression.substring(1, idx));
            HashSet<String> set = new HashSet<>();
            for (String str : strs) {
                List<String> tmp = braceExpansionII(str);
                set.addAll(tmp);
            }
            List<String> rest = braceExpansionII(expression.substring(idx + 1));
            for (String str1 : set) {
                for (String str2 : rest) {
                    res.add(str1 + str2);
                }
            }
        }
        else {
            String prev = expression.charAt(0) + "";
            int idx = 0;
            List<String> rest = braceExpansionII(expression.substring(1));
            for (String s : rest) res.add(prev + s);
        }
        Collections.sort(res);
        return res;
    }
    
    public List<String> helper(String s) {
        List<String> res = new ArrayList<>();
        int cnt = 0;
        int i = 0;
        for (int j = 0; j < s.length(); j++) {
            if (s.charAt(j) == ',') {
                if (cnt == 0) {
                    res.add(s.substring(i, j));
                    i = j + 1;
                }
            }
            else if (s.charAt(j) == '{') cnt += 1;
            else if (s.charAt(j) == '}') cnt -= 1;
        }
        res.add(s.substring(i));
        return res;
    }
}
         * 
         */
    }
}