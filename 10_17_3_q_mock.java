
// 1478. Allocate Mailboxes
class Solution {
    public int minDistance(int[] houses, int K) {
        // consider edge case: if k == 1, the best location of mailbox will be at the middle of street
        // brute force way, will be trying to place mailbox at the middle between eahc interval 
        // idea is simple: try to place mail box 
        // dp[i][j][k] = min distance up to i-th house to j-th house with placing k mailbox
        // base case ...we know the distance form i - j  in array with 1 mailbox
        // what about the dp[i][j][k+1] = min(for all s < j && s > i, dp[i][s][k] + dp[s][j][1])
        // so the possible location of the mailbox will be always infront of a house
        // since placing in the middle will not reduce the distance 
        // this seems a big dp formual that work but require too many unneccary data
        // the dp[s][j][1] can be reduce to just equal to  distance(houses[s + (i-s/2)])
        // so the solution will be dp[i][k] = the min distance up to i-th house with placing k mailbox
        // dp[i][k + 1] = min(for all s < i && s >= 0, dp[s][k] +   distance(houses[s + (i-s/2)]))
        // this now feels more really and fessible to resolve 
        // but can we do better?
        // I realized that when computing dp[i][k + 1] we only need dp[s][k]
        // hence 2d memo is not need for this case
        // we could reduce the space to linear
        // space cost will be O(n)
        // how run time: n^2
        Arrays.sort(houses);
        int n = houses.length;
        int [] dp = new int[n];
        int[][] costs = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int t = i; t <= j; t++) {
                    costs[i][j] += Math.abs(houses[(i + j) / 2] - houses[t]);         
                }
                dp[j] = costs[0][j];
            }
        }
        // update the dp by dp[i][k + 1] = min(for all s < i && s >= 0, dp[s][k] +  distance(houses[s + (i-s/2)]))
        for (int m = 2; m <= K; m++) {

            int [] dp_t = new int[n];
            for(int v = 0; v < n; v++) {
                dp_t[v] = dp[v];
            }
            // update by k 
            for (int i = 0; i < n; i++) {
                int inf = 100 * 10000;
                for(int s = 1; s <= i; s++) {
                    // need to find the cost of having 1 mailbox at houses[i:j] issue: we need to make sure dp[s] is still the data from prev row
                    inf = Math.min(inf,(dp[s-1] == 100 * 10000 ? 0: dp[s-1]) + costs[s][i]); 
                }
                dp_t[i] = inf;
            }
            for(int v = 0; v < n; v++) {
                dp[v] = dp_t[v];
            }
        }
        
        // for (int k = 1; k <= K; ++k) {
        //     for (int j = n - 1; j > k - 2; --j) {
        //         for (int i = k - 2; i < j; ++i) {
        //             dp[j] = Math.min(dp[j], (i >= 0 ? dp[i] : 0) + costs[i][j]);
        //         }
        //     }
        // }
        return dp[n-1];
        // this is a fast solution... It has a better way to compute the distance.....
        // ref: https://leetcode.com/problems/allocate-mailboxes/discuss/685403/JavaC%2B%2BPython-DP-Solution
        // int n = A.length, B[] = new int[n+1], dp[] = new int[n];
        // for (int i = 0; i < n; ++i) {
        //     B[i + 1] = B[i] + A[i];
        //     dp[i] = (int)1e6;
        // }
        // for (int k = 1; k <= K; ++k) {
        //     for (int j = n - 1; j > k - 2; --j) {
        //         for (int i = k - 2; i < j; ++i) {
        //             int m1 =  (i + j + 1) / 2, m2 = (i + j + 2) / 2;
        //             int last = (B[j + 1] - B[m2]) - (B[m1 + 1] - B[i + 1]);
        //             dp[j] = Math.min(dp[j], (i >= 0 ? dp[i] : 0) + last);
        //         }
        //     }
        // }
        // return dp[n - 1];
        
        
        
//  another soltuion, here it utilize the 2d instead one d
//         Arrays.sort(houses);
//         int size = houses.length;
//         int[][] distance = new int[size][size];
        
//         for (int i = 0; i < size; ++i) {
//             for (int j = i + 1; j < size; ++j) {
//                 distance[i][j] = distance[i][j - 1] + houses[j] - houses[(i + j) / 2]; 
//             }
//         }
        
//         int[][] counts = new int[k][size];
        
//         for (int i = 0; i < size - k + 1; ++i) {
//             counts[0][i] = distance[0][i];
//         }
        
//         for (int i = 1; i < k; ++i) {
//             for (int j = i + 1; j < size + i - (k - 1); ++j) {
//                 int min = Integer.MAX_VALUE;
                
//                 for (int x = i - 1; x < j; ++x) {
//                     min = Math.min(min, counts[i - 1][x] + distance[x + 1][j]);
//                 }
                
//                 counts[i][j] = min;
//             }
//         }
        
//         return counts[k - 1][size - 1];
    }
}

// 1382. Balance a Binary Search Tree
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
    public TreeNode balanceBST(TreeNode root) {
        // so this is a tree function 
        // if the depth of left and right is differ by k where k > 1
        // case 1:
        // this will be to complicate such the different maybe k which will requir are balance after another reblance
        // maybe we can build it from sretch 
        // we can do a left mid right traveal 
        // and save the node in a list
        // build the new tree from the middle 
        // this will takArrayList<TreeNode> es O(n) time and cosntant space 
        ArrayList<TreeNode> res = new ArrayList<TreeNode> ();
        helper(root, res);
        return constrcut_tree(res, 0,  res.size() - 1);
    }
    
    public void helper(TreeNode root, ArrayList<TreeNode> res) {
        if (root != null) {
            helper(root.left, res);
            res.add(root);
            helper(root.right,res);
        }
    }
    public TreeNode constrcut_tree(ArrayList<TreeNode> res, int start, int end) {
        if (start > end) return null;
        int mid = (start + end) / 2;
        TreeNode root = res.get(mid);
        root.left = constrcut_tree(res, start, mid - 1);
        root.right = constrcut_tree(res, mid + 1, end);
        return root;
    }

}


// Count Negative Numbers in a Sorted Matrix
class Solution {
    public int countNegatives(int[][] grid) {
        // key factor it is sorted in decreasing order in both way 
        // go through the first row and first col of it
        // if the first row of has 2 negative  = 2*4 negative 
        // if the first row does not have negative and the frist col does not have negative => 0
        // so if there are 2 3 element of the row are negative 
        // and  1 2 3 of the elements are are negative on the row 
        // this will be linear time and linear space 
        // check 4 sides of the grid 
        // they are all positive we done else 
        // but this does not work
        // we need to do some search
        // since it is sorted,, using BS is ideal 
        // check first row and first col and find the first index of it where the value is negative 
        // the question is being convert to find a index on row and index of col
        // such that it first become negative
        // emmmm it seems like it is stair shape.. so just go over the stair one by one
        // and add up the row will give the solution as well. 
        // the code below is 
        int m = grid.length;
        int n = grid[0].length;
        int row = m - 1;
        int col = 0;
        int res = 0;
        while (row >= 0 && col < n) {
            if (grid[row][col] < 0) {
                res += n - col; // there are n - c negative numbers in current row.
                row--;
            }else {
                col++;
            }
        }
        return res;
        
    }
}