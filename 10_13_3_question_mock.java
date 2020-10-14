
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// takes 1 hour 30 minutes 56 seconds
// 438 solution 
class Solution_a {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        if (s.length() == 0 || p.length() == 0 || s.length() < p.length()) {
            return res;
        }
        
        int count[] = new int[26];
        for (int i = 0; i < p.length(); i++) {
            char ch = p.charAt(i);
            count[ch-'a']++;
        }
        
        int cnt[] = new int[26];
        for (int i = 0; i < p.length(); i++) {
            int pos = s.charAt(i) - 'a';
            cnt[pos]++;     
        }

        boolean flag = true;
        for (int i = 0; i < 26; i++) {
            if (count[i] != cnt[i]) {
                flag = false;
                break;
            }
        }
        if (flag) {
            res.add(0);
        }
        for (int i = p.length(); i < s.length(); i++) {
            int pos = s.charAt(i-p.length()) - 'a';
            cnt[s.charAt(i)-'a']++;
            cnt[pos]--;
            flag = true;
            for (int j = 0; j < 26; j++) {
                if (count[j] != cnt[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                res.add(i-p.length()+1);
            }
        } 
        return res;
    }
}
// 1123. Lowest Common Ancestor of Deepest Leaves
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
class Solution_b {
    int deepest = 0;
    TreeNode lca;

    public TreeNode lcaDeepestLeaves(TreeNode root) {
        helper(root, 0);
        return lca;
    }

    private int helper(TreeNode node, int depth) {
        deepest = Math.max(deepest, depth);
        if (node == null) {
            return depth;
        }
        int left = helper(node.left, depth + 1);
        int right = helper(node.right, depth + 1);
        if (left == deepest && right == deepest) {
            lca = node;
        }
        return Math.max(left, right);
    }
}

// 416. Partition Equal Subset Sum
class Solution {
    public boolean canPartition(int[] nums) {
        // only positive interger , find if it can split in two sub set
        // splite a array such that both sum are the same 
        // classical question 
        // go throug each index, we can try it 
        // what is the goal of the question
        // we are asking if it is possible to split it 
        // we are not asking to find it 
        // so can we generate result from smaller result
        // first of all
        // sum up the array
        // and the target sum we want to find will be have of that sum 
        // once we have the target sum we want to find a way to decide which side the current index 
        // should be place so that it can be split into tow sub set
        // so I place first index to the left 
        // [1, 5, 5, 6, 5] = 22
        // the next index 
        //                                    left [1] right []
        //       left [1 5] right []                              left [1] right [5]
        // left[1 5 5]  right[] or left[1 5] right [5]     left[1]  right[5 5] or left[1 5] right [5]   
        // this feels like knap snack question 
        // we want to use the value and tag from the list have max value with the limit of that half of sum 
        // so lets code it 
        int sum = 0;
    
        for (int num : nums) {
            sum += num;
        }
    
        if ((sum & 1) == 1) {
            return false;
        }
        sum /= 2;
    
        int n = nums.length;
        boolean[] dp = new boolean[sum+1];
        Arrays.fill(dp, false);
        dp[0] = true;
    
        for (int num : nums) {
            for (int i = sum; i > 0; i--) {
                if (i >= num) {
                    dp[i] = dp[i] || dp[i-num];
                }
            }
        }
    
        return dp[sum];
        
    }
}