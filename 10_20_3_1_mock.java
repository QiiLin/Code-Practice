

/**
 * 
 * Given the root of a binary tree, each node in the tree has a distinct value.

After deleting all nodes with a value in to_delete, we are left with a forest (a disjoint union of trees).

Return the roots of the trees in the remaining forest.  You may return the result in any order.
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
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        // case to be consider in this case:
        // when delete one node... its left and right child will a tree 
        // so init idea: will be when ever delete node... we add TreeNode's child into the 
        // the List<TreeNode>
        // now the issue is that the child node may exist in the to_delete list
        // 1. using hashmap to check if the child is to be delete if not it will be sure in the remove list
        // 2. think about how we should handle delete operation.
        // by brute force... we are going to do bst search for each value in the tree.
        // this will take mlogn times which is unwanted
        // 3. one way we can fix this issue will be just loop through the root with the min value and max value of to_delete 
        // in mind so that we can avoid some unnecceeary travsal.. but the runing time will be O(n)
        // space since we using hashtable => O(n) as well
        // step 1. copnver to delete to hash_Set
        // step 2. start travsal the tree 
        // return the res;
        List<TreeNode> res = new ArrayList<TreeNode>();
        if (root == null) {
            return res; 
        }
        HashSet<Integer> check = new HashSet<Integer>();
        for (int i = 0; i < to_delete.length; i++) {
            System.out.println(to_delete[i] + " ");
            check.add(to_delete[i]);
        }
        // base case check root
        if (!check.contains(root.val)) {
             res.add(root);   
        }
        helper(root, null, false, res, check);
        return res;
        
    }
    private void helper(TreeNode root, TreeNode p, boolean flag, List<TreeNode> res, HashSet<Integer> check) {
        if (root != null) {

            if (check.contains(root.val)) {
                if (p != null) {
                    if (!flag) {
                        p.left = null;
                    } else {
                        p.right = null;
                    }                    
                }
                if (root.left != null && !check.contains(root.left.val)) {
                    System.out.println(root.left.val + " xx" + check.contains(root.left.val));
                    res.add(root.left);
                }
                if (root.right != null && !check.contains(root.right.val)) {
                    System.out.println(root.right.val + " xx" + check.contains(root.right.val) );
                    res.add(root.right);
                }
            }
            // the left side could have node to delete
            helper(root.left, root, false, res, check);
            // the right side could have node to delete
            helper(root.right, root, true, res, check);   
        }
    }
}
//Find in Mountain Array
/**
 * // This is MountainArray's API interface.
 * // You should not implement it, or speculate about its implementation
 * interface MountainArray {
 *     public int get(int index) {}
 *     public int length() {}
 * }
 */
 
class Solution {
    public int findInMountainArray(int target, MountainArray mountainArr) {
        // two pointer question 
        // need to end earilier 
        // need to binary search first
        // 
        
        int i = 0;
        int size =  mountainArr.length() - 1;
        int n = size;
        int peek = -1;
        // find the peak 
        while(i <= n) {
            int mid = i + ((n - i)/2);
            int v = mountainArr.get(mid);
            int v_n = mid + 1 > size ? v : mountainArr.get(mid+1);
            int v_p = mid - 1 < 0 ? v : mountainArr.get(mid-1);
            if (v >= v_n && v_p <= v) {
                peek = mid;
                break;
            }
            if (v >= v_n && v_p >= v) {
                // we are on the right side
                n = mid - 1;
            }
            if (v  <=  v_n && v_p  <=  v){
                // we are on the left side
                i = mid + 1;
            }
        }
        System.out.println(peek);
        // do binary on both side
        // now think about a bit
        int left = 0;
        int right = peek;
        while(left <= right) {
            int mid = left + (right - left)/2;
            int v = mountainArr.get(mid);
            if (v == target) {
                return mid;
            } else if (v < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        left = peek;
        right = size;
        while(left <= right) {
            int mid = left + (right - left)/2;
            System.out.println(left + " " + right + " " + mid);
            int v = mountainArr.get(mid);
            if (v == target) {
                return mid;
            } else if (v < target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }
}
// 852. Peak Index in a Mountain Array
class Solution {
    public int peakIndexInMountainArray(int[] arr) {
        // at least length of 3
        // there are some breaking point such that 
        // increase and then decreasing 
        // return any i such that it is a the peak of montain 
        // so we want to record the prev index when increasing become decreasing 
        // simple question code it out  this will be linear time
        int i = 0;
        int n = arr.length;
        int flag = 0;
        int prev = -1;
        int res = -1;
        while(i < n) {
            if(prev < arr[i]) {
                flag = 1;
            } else if (flag == 1 && prev > arr[i]){
                res = i - 1;
                break;
            }
            prev = arr[i];
            i++;
        }
        return res == -1 ? 0 : res;
    }
}