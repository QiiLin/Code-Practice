// Longest Common Prefix

class Solution {
    public String longestCommonPrefix(String[] strs) {
        // longest => dp
        // consider brute force 
        // 1. the max length of longest prefix will be the shortest length
        // brute foruce will be for each char in the shortest work
        // compare it with rest of str
        // this is will be n^2 running time and constant space 
        // can we do better?
        // reduce it to linear time and space
        // those all possible prefix in a hashmap
        // and then for each possible prefix of each word
        // check if it is in it 
        // emmm but the time for getting such sub string is linear
        int n = strs.length;
        if (n == 0) return "";
        int m = Integer.MAX_VALUE;
        String target = "";
        for (int i = 0; i< n; i++) {
            int curr = strs[i].length();
            if (m > curr ) {
                m = curr;
                target = strs[i];
            }
        }
        int k = target.length();
        int fin = -1;
        for (int i = 0; i < k; i++ ) {
            for (int j = 0; j < n; j++) {
                if (strs[j].charAt(i) != target.charAt(i)) {
                    fin = i;
                    break;
                }
            }
            if (fin == i) {
                break;
            }
        }
        fin = fin == -1 ? k : fin;
        return target.substring(0, fin);
    } 
}

// rotate matrix by 90

class Solution {
    public void rotate(int[][] matrix) {
        // doing swap here inplace
        // swap clockwise
        int n = matrix.length;
        // iterate half of row
        for(int i = 0; i < n/2; i++) {
            // for each col internall do the swap
            for(int j = i; j < n-i-1; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n-j-1][i];
                matrix[n-j-1][i] = matrix[n-i-1][n-j-1];
                matrix[n-i-1][n-j-1] = matrix[j][n-i-1];
                matrix[j][n-i-1] = temp;
            }
        }
    }
}

// contain most water

class Solution {
    public int maxArea(int[] height) {
        // defn of most water will have having the min(left and right)
        // having the max value horztonally 
        // using stack
        // so when we saw a increasing trend
        // then we pop value that are small then first increasing value and 
        // count their area
        // emm horzontial distance is an issue
        // greedy problem
        // targe = horzaontal and vertical
        // if max horzontal 
        // and short horzontal to find the vertical 
        //
        int i = 0;
        int j = height.length - 1;
        int max = Integer.MIN_VALUE;
        while(i < j) {
            max = Math.max(max, (j - i) * Math.min(height[i], height[j]));
            if (height[i] < height[j]) {
                i++;
            } else {
                j--;
            }
        }
        return max;
    }
}