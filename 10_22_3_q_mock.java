/**
 * Given two integer arrays of equal length target and arr.

In one step, you can select any non-empty sub-array of arr and reverse it. You are allowed to make any number of steps.

Return True if you can make arr equal to target, or False otherwise.
 */
class Solution {
    public boolean canBeEqual(int[] target, int[] arr) {
        // check if you can reverse sub string in the target
        // so this is not really a check function as long as 
        // the two array has the same amount of char then .... it will always be true
        int[] f = new int[1001];
        for(int i = 0; i < arr.length; i++) {
            f[target[i]]++;
            f[arr[i]]--;
        }
        
        for(int i = 0 ; i < 1001; i++)
            if(f[i] != 0)
                return false;
        return  true;
    }
}

/**
 * Given a string s of '(' , ')' and lowercase English characters. 

Your task is to remove the minimum number of parentheses ( '(' or ')', in any positions ) so that the resulting parentheses string is valid and return any valid string.

Formally, a parentheses string is valid if and only if:

It is the empty string, contains only lowercase characters, or
It can be written as AB (A concatenated with B), where A and B are valid strings, or
It can be written as (A), where A is a valid string.
 */

class Solution {
    public String minRemoveToMakeValid(String s) {
        // simple idea of stack 
        // stack of
        // if during the process sees ) while stack is empty don't include the ) in the res
        // if in the end the stack is not empty start removing the first stack.size() element.
        StringBuilder sb = new StringBuilder();
        int count = 0;
        Stack<Character> stack = new Stack();
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == '(') {
                count++;
                sb.append(c);
            } else if (c == ')') {
                if (count > 0) {
                    count--;
                    sb.append(c);
                }
            } else sb.append(c);
        }
        // System.out.println(sb.toString());
        int index = sb.length()-1;
        while (count > 0 && index >= 0) {
            if (sb.charAt(index) == '(') {
                sb.deleteCharAt(index);
                count--;
                // index++;
            }
            index--;
        }
        return sb.toString();
    }
}
/**
 * You have a grid of size n x 3 and you want to paint each cell of the grid with exactly one of the three colours: Red, Yellow or Green while making sure that no two adjacent cells have the same colour (i.e no two cells that share vertical or horizontal sides have the same colour).

You are given n the number of rows of the grid.

Return the number of ways you can paint this grid. As the answer may grow large, the answer must be computed modulo 10^9 + 7.

    
 * 
 */

class Solution {
    public int numOfWays(int n) {
        // small problem first: three cols ...
        // dp question 
        // ok only two possible pattern of a block
        // G Y G,  => has 6 version
        // R Y G,  => has 6 version
        //  consider burtue force
        // the vertical (block below has )
        // R Y G => Y R G, G Y R, G Y G, G R G
        // G Y G => R G R, Y G Y, Y R Y, R G Y, Y G R.
        // noting that is n by 3
        // we can comput row by row
        // varation of first row will be either 3 distinct = 6 , 2 same = 6

        long three_c = 6;
        long two_c = 6;
        long mod = (long)1e9 + 7;
        for (int i = 1; i < n; i ++) {
            long three_temp = (three_c*2 + two_c *2);
            long two_temp = three_c*2 + two_c *3;
            three_c = three_temp % mod;
            two_c = two_temp % mod;
        }
        // add two case: start with 3 distinct, start with 2 distinct 
        return (int)((two_c + three_c) % mod);
        
    }
}