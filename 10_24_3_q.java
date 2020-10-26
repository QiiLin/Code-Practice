
/**
 * Given an array of citations (each citation is a non-negative integer) of a researcher, write a function to compute the researcher's h-index.

According to the definition of h-index on Wikipedia: "A scientist has index h if h of his/her N papers have at least h citations each, and the other N − h papers have no more than h citations each."
 */
class Solution {
    public int hIndex(int[] citations) {
        // so h is a value such that there are only N - h of his paper has less than h citiation 
        // put in another word, there are only h of his paper has equal or more than h number of citation 
        // so what do we need to varifiy here
        // 1. we don't know h so has to use loop to identify it go through n 
        // 2. for each possible number we can find it 
        //    Notice: the properity .. so we need to keep track of recod
        // 3. condition to check here: so for each citation we check the number of that are greating than the value 
        //    without any thinking, that will be takes n^2 times and linear space
        // question is can we do better.. noting here we are iterating the list storage to do the update. 
        // 4. is there a more efficient way to approach this?
        // data strcutrue to rm: heap? propity queue? so nop finding the element will take too long
        //                                              wait .... I can use treeMap to make it logn instead of n 
        // so find the element and place it back the queue
        // this will makes it nlogn ...
        // can we do better...
        // is there a way for use to build it one base on another
        // if 3 counts has 3 than 1 count will have 4
        // but this requires us to do iterate it in sort varsion 
        // that will again be nlogn 
        // can we do better think ? there is O(n) so you need to come up a solution for it
        // key of this question will be count the occrance of element that are greater than value
        // reverse your mind, you can just count exact occurence of element that has the same value as you 
        // and than we can build problem one by one
        // don't bound you mind with the key factor... think out side the box
        // acheive the solution in a different direction
        int n = citations.length;
        int [] d = new int[n + 1];
        for (int i = 0; i < n; i++) {
            if (citations[i] >= n) {
                d[n] ++;
            } else {
                d[citations[i]]++;
            }
        }
        int acc = 0;
        for (int i = n; i >= 0; i --) {
            acc += d[i];
            if (acc >= i) {
                return i;
            }
        }
        return 0;
        // there is a way in the do it without space ....
        /*
    // Here, we fill the input array with counts, where
    // (-citations[i] - 1) is exactly the number
    // of papers having i publications.
    // Negative because we need to distinguish it from
    // the citation counts that we haven't processed yet.
    // Note that we'll just throw away any counts >= citations.length,
    // but we'll never need those.
    for (int i = 0; i < citations.length; ++i) {
        int count = citations[i];
        if (count < 0)
            continue; // already processed
        citations[i] = -1; // the count starts with 0
        for (int nextCount; count < citations.length &&
                            (nextCount = citations[count]) >= 0; ) {
            // We haven't got enough space to count those
            // >= citations.length, but neither we need them.
            citations[count] = -2; // we've just seen one
            count = nextCount;
        }
        // The loop above could have terminated either
        // 1) because count >= citations.length (we don't count those) or
        // 2) because we hit an element that already stores a count.
        // In the second case we need to increment that count since
        // we've just encountered another element with the same value.
        if (count < citations.length) {
            --citations[count];
        }
    }
    for (int h = 0, less = 0; h < citations.length; ++h) {
        int count = -citations[h] - 1;
        // Logically, the loop below must have this condition:
        // citations.length - less >= h && less + count >= citations.length - h,
        // but the first of these is really redundant. Indeed, it is obviously
        // true on the first iteration, and it follows that if it was true for
        // some "h", then it would be true for "h + 1". Indeed, the "less" variable
        // on this iteration is what "less + count" was on the previous one, so 
        // the (citations.length - less >= h) condition, in terms of
        // the previous-iteration values, is nothing but really
        // (citations.length - (less + count) >= h + 1),
        // which is exactly the same as (citations.length - (h + 1) >= (less + count))
        // or (citations.length - h > (less + count)), but if that was false, then
        // (less + count >= citations.length - h) would be true on the previous
        // iteration, and the whole thing would have terminated earlier.
        if (less + count >= citations.length - h)
            return h;
        less += count;
    }
    return citations.length;
        */
        
        
    }
}


/**
 * A self-dividing number is a number that is divisible by every digit it contains.

For example, 128 is a self-dividing number because 128 % 1 == 0, 128 % 2 == 0, and 128 % 8 == 0.

Also, a self-dividing number is not allowed to contain the digit zero.

Given a lower and upper number bound, output a list of every possible self dividing number, including the bounds if possible.
 */

class Solution {
    public List<Integer> selfDividingNumbers(int left, int right) {
        // simple idea : just check each value is self Dividing ... that's it
        // a for loop and a while loop check will be enough...
        List<Integer> list = new ArrayList<>();
        for(int i=left ; i<= right;i++)
            if(isDivide(i))
                list.add(i);
        return list;

    }

    boolean isDivide(int x )
    {
        int temp = x;
        while(x!=0)
        {
            int rem = x%10;
            if(rem == 0 || temp%rem!=0)
                return false;
            x /=10;
        }
        return true;
    }
}


/**
In a N x N grid representing a field of cherries, each cell is one of three possible integers.

 

0 means the cell is empty, so you can pass through;
1 means the cell contains a cherry, that you can pick up and pass through;
-1 means the cell contains a thorn that blocks your way.
 

Your task is to collect maximum number of cherries possible by following the rules below:

 

Starting at the position (0, 0) and reaching (N-1, N-1) by moving right or down through valid path cells (cells with value 0 or 1);
After reaching (N-1, N-1), returning to (0, 0) by moving left or up through valid path cells;
When passing through a path cell containing a cherry, you pick it up and the cell becomes an empty cell (0);
If there is no valid path between (0, 0) and (N-1, N-1), then no cherries can be collected.
 
*/


class Solution {
    public int cherryPickup(int[][] grid) {
        // 3 states of each cell
        // 0 and 1 can pass -1 can't pass
        // max number of cheery 1 has cherry
        // only right down with valid path
        // after reaching the n-1 n-1 goes back by left or up throught valid path 
        // notice: when pick up, we need to update the grid so that cheery become 0
        // if there isn't such apth return 0;
        // classical dp 
        // lets break it down a bit
        // how do we know the optimal path from 00 to n-1 n- with right and down
        // for [i][j] we check if the accumlate value at its [i-1] [j] or [i][j-1] which is bigger
        // simple dp[i][j] = max [i-1][j]  [i][j-1] ... we only need one d storage for this 
        // Note: in this case ... we may need  2d for this problem
        // since I can reuse the result and goes back
        // issue: we may pick up duplicate cherry that is alread being taken 
        // so need to find a effective way to either update the dp result... which maybe an issue
        // or to identitfy the chosen path....but how to find the path
        // target run time will be n^2 since we have to go through the list
        // forget about the space usage issue... solve the problem first before optimize it
        // ok ... brute idea:
        // do the cherry pick twice 
        // on the second run ...update the grid
        // and then recompute it again
        // esitmate run time n^2 + n^2 + n^2 + n^2 with space of n^2
        // shit...... this has a counter case:
        // it will take the best path one way.. but does not ensure the optimal path for both run ...
        // damn it
        // so think about it again 
        // consider two path from the start 
        // but how 
        // 4 d .... array to handle it 
        // [i][j][s][k] when solving two path one the same map... maybe a good idea to put them in one dp probelm
        //  [i][j][s][k] = max([i-1][j][s-1][k] , [i][j-1][s-1][k],   [i][j-1][s][k-1] , [i-1][j][s][k-1]) + current value of ij and sk
        // so prev state of i-j up or left and s-k up or left.........such pain
        // may have over count issue...no really just the edge case where s = i and j = k
        // fk this is a 4 d problem ... we only care about the 
        // ...really 
        // I don't want to code it ...
        // I check the solution....we can do it with less space... just copy and paste here...
        // idea is the same here... its that we are has the fact that  i+j = t = s+k
        int N = grid.length;
        int[][] dp = new int[N][N];
        for (int[] row: dp) Arrays.fill(row, Integer.MIN_VALUE);
        dp[0][0] = grid[0][0];

        for (int t = 1; t <= 2*N - 2; ++t) {
            // so here is a temp 2d array for storage
            int[][] dp2 = new int[N][N];
            for (int[] row: dp2) Arrays.fill(row, Integer.MIN_VALUE);
            // path one value value its worth noting: it is Math.max(0, t-(N-1)) to Math.min(N-1, t)
            for (int i = Math.max(0, t-(N-1)); i <= Math.min(N-1, t); ++i) {
                // path two value
                for (int j = Math.max(0, t-(N-1)); j <= Math.min(N-1, t); ++j) {
                    // if any of it is -1 that means it is not possible ..
                    if (grid[i][t-i] == -1 || grid[j][t-j] == -1) continue;
                    int val = grid[i][t-i]; // value for current path 1
                    if (i != j) val += grid[j][t-j]; // value for current path 2.....smart way here...
                    // the below are just  [i][j][s][k] = max([i-1][j][s-1][k] , [i][j-1][s-1][k],   [i][j-1][s][k-1] , [i-1][j][s][k-1])  + current value of ij and sk
                    for (int pi = i-1; pi <= i; ++pi)
                        for (int pj = j-1; pj <= j; ++pj)
                            if (pi >= 0 && pj >= 0)
                                dp2[i][j] = Math.max(dp2[i][j], dp[pi][pj] + val);
                }
            }
            dp = dp2;
        }
        return Math.max(0, dp[N-1][N-1]);
    }
}