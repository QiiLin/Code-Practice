class Solution {
    public void setZeroes(int[][] matrix) {
        // if element are zero set all row and col to zero
        // simple. for each zero element mark its start to be zero
        // ie: 1 1 1 1  1
        //     1 1 1 0  1
        // => 
        /**
           want it to be constant then we need to mark the node
           note: the value can be negative
           so how can we mark it so that in the later pass we can realize 
           this row and col need to be change to all zero
           think think think 
           reverse ur mind... maybe just update it right the way
           ..this raise a new issue how to distinction the updated zero and 
           orignial zeros 
           idea: 0 at i,j ... first make 0 - i and 0 - j of the value to be zero.
           then keep going 
           after that is done tresaval backward
           ... still mark the node mark the first row and first col 
           // in this way ...we are only change the previous node 
           The idea is simple, just for loop row by row and update its first cell and first col
           then iteratie it again to change the all the value in the row and col to zero.
        */
        boolean first_row = false;
        boolean first_col = false;
        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j] == 0) {
                    if(i == 0) first_row = true;
                    if(j == 0) first_col = true;
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
            }
        }
        for(int i = 1; i < matrix.length; i++) {
            for(int j = 1; j < matrix[0].length; j++) {
                if(matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        if(first_row) {
            for(int j = 0; j < matrix[0].length; j++) {
                matrix[0][j] = 0;
            }
        }
        if(first_col) {
            for(int i = 0; i < matrix.length; i++) {
                matrix[i][0] = 0;
            }
        }
    
    }
}



class Solution {
    public int myAtoi(String str) {
        /** few condition to check here
        1. ignore the whitespace
        2. +/- is only valid as the first character
        3. after first character keep going until you hit a not digit or not space 
           Note if the valid digit length is more than one and the first digit is zero return 0;
        4. store the start index and end indx
        5. iterate from the end to the start and comput the value and return it 
        6. remember about the edge case
        */
        int i = 0;
        int sign = 1;
        int result = 0;
        if (str.length() == 0) return 0;

        //Discard whitespaces in the beginning
        while (i < str.length() && str.charAt(i) == ' ')
            i++;

        // Check if optional sign if it exists
        if (i < str.length() && (str.charAt(i) == '+' || str.charAt(i) == '-'))
            sign = (str.charAt(i++) == '-') ? -1 : 1;

        // Build the result and check for overflow/underflow condition
        while (i < str.length() && str.charAt(i) >= '0' && str.charAt(i) <= '9') {
            if (result > Integer.MAX_VALUE / 10 ||
                    (result == Integer.MAX_VALUE / 10 && str.charAt(i) - '0' > Integer.MAX_VALUE % 10)) {
                return (sign == 1) ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            result = result * 10 + (str.charAt(i++) - '0');
        }
        return result * sign;
      
    }
}

class Solution {
    public int countBattleships(char[][] board) {
        // rule: eith up down left right
        // count how many with constant memo and without modify the board
        // think about the condtio nthat hold for battle ship
        // 1 by N or N by 1
        // for get about one pass solve the problem first
        //  xx.xx.x.x.xx
        //  with above row I can say there must exist 3 ship here..2 possible overcount
        //  so mark those counted one with Y
        // and then do a col check we can be sure there to be 3 ship 
        // x . . x x .
        // x . x . . x
        // . x . . x .
        // x . x x . x 
        // . x . . . x
        // x . x x x .
        // simple idea: but how to not changing the board
        // just iterate it row by row 
        // we only record the ship when its bottom left is empty or boundary
        // this is because the natrue of the 1 by n and n by there will always be one cell its down and left is empty or boundary
        
        int n = board.length;
        int m = board[0].length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                boolean down = ((j + 1  < m && board[i][j+1] == '.') || j + 1 >= m );
                boolean left = ((i + 1  < n && board[i+1][j] == '.') || i + 1 >= n );
                if(board[i][j] == 'X' && down && left){
                    count ++;
                }
            }
        }
        return count;
        
    }
}


class Solution {
    public boolean isMatch(String s, String p) {
        // * is what needs to be handle here it can be use either one page a head and keep 
        // this is a classical tree map 
        // basically we want to avoid recomputation 
        // dp[i][j] just shows the status if s[:i] p[:j] match
        // a 2d map will do .. but mostly we only need one d since 
        // we only care about the status of i-1 or j-1;
        // recall regex match question, it is similar but even easier 
        // * can be use to replace any length of character
        // if the current == * two case 1. either check [i-1][j] (consider current char being include in *) 
        // or [i] [j-1] (consider * = "")
        // I feel like it could be improve again
        // since there is a simpler case
        // maybe even constant time
        // init idea: having two indexer for s and p
        // if both match increase
        // if there are * we first try taking it to represent one char
        // if in the later run it does not match
        // consider it match two char and so on
        // kind of df but with backtracking
        // seems doable
        
        if(s == null || p == null) return false;
        int sLen = s.length();
        int pLen = p.length();
        boolean[][] dp = new boolean[sLen + 1][pLen + 1];
        
        // Base cases:
        dp[0][0] = true;
        for(int i = 1; i <= sLen; i++){
            dp[i][0] = false;
        }       
        for(int j = 1; j <= pLen; j++){
            if(p.charAt(j-1) == '*'){
                dp[0][j] = dp[0][j-1];
            }            
        }
        
        // Recursion:
        for(int i = 1; i <= sLen; i++){
            for(int j = 1; j <= pLen; j++){
                if((s.charAt(i-1) == p.charAt(j-1) || p.charAt(j-1) == '?') && dp[i-1][j-1])
                    dp[i][j] = true;
                else if (p.charAt(j-1) == '*' && (dp[i-1][j] || dp[i][j-1]))
                    dp[i][j] = true;
            }
        }
        return dp[sLen][pLen];
        // this could be 
    }
}