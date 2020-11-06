
/*
International Morse Code defines a standard encoding where each letter is mapped to a series of dots and dashes, as follows: "a" maps to ".-", "b" maps to "-...", "c" maps to "-.-.", and so on.
*/
class Solution {
    public int uniqueMorseRepresentations(String[] words) {
        int m = words.length;
        String [] hd = new String[]{".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};
        HashSet<String> check = new HashSet<String>();
        for (int i = 0; i < m; i++) {
            StringBuilder temp = new StringBuilder();
            for (char c : words[i].toCharArray()) {
                temp.append(hd[c - 'a']);
            }
            check.add(temp.toString());
        }
        return check.size();
    }
}

/**
 * Given an Iterator class interface with methods: next() and hasNext(), design and implement a PeekingIterator that support the peek() operation -- it essentially peek() at the element that will be returned by the next call to next().
 */

 // Java Iterator interface reference:
// https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html

import java.util.NoSuchElementException;
class PeekingIterator implements Iterator<Integer> {
    Integer next;
    Iterator<Integer> iter;
    boolean noSuchElement;

    public PeekingIterator(Iterator<Integer> iterator) {
	    // initialize any member here.
	    iter = iterator;
        advanceIter();
    }

    // Returns the next element in the iteration without advancing the iterator.
    public Integer peek() {
        // you should confirm with interviewer what to return/throw
        // if there are no more values
        return next;
    }

    // hasNext() and next() should behave the same as in the Iterator interface.
    // Override them if needed.
    @Override
    public Integer next() {
        if (noSuchElement)
            throw new NoSuchElementException();
        Integer res = next;
        advanceIter();
        return res;
    }

    @Override 
    public boolean hasNext() {
        return !noSuchElement;
    }
    
    private void advanceIter() {
        if (iter.hasNext()) {
            next = iter.next();
        } else {
            noSuchElement = true;
        }
    }
}


/** The Skyline Problem */

/** I speed more than a hour working on this .... and still couldn't figure
 *  It did the question in my own way using the stack...but I forget about the 
 *  the coutner case which is that higher height that overlap should be handle first...
 *  I made it working its just a bit messay...
 */

class Solution {
    public List<List<Integer>> getSkyline(int[][] buildings) {
        /** ideally this is a sorting question... feels like greedy or dp 
        / Good news: given list is already sort in asending by Li
        / can I do in one pass 
        / main issue, getting interception points 
        / dry run
        / 1. take the left top corrder when it is possible else take the right bottom one
        /     condition for the left top cor
        /       1. if the current top left has higher height and it is with in the prev right bound
                2. if the element is not with ine the bound take the prev right most element's r value as ground value and 
                   add current top left is a new starting point and update the right most element 
         for each current element find the element on the left side with right most bound and higher height than current
         for each current element find the element on the right side with left most bound and higher height than current
         // base on those two values we can setting up two point into the result 
         // time complexit n^2 space n for the result. 
         // Can we do better here? we need to store the find the left most element that overlap with current one 
         // dp question 
         // init idea:
           dp[i] = the index of xxxx 
           dp[i+1] = 
           
           by observation, if the height is increasing then we just take the top left 
           if the height is decreasing ... complex case need to be handle 
           using stack here 
           // if it is always increasing then 
           // the ending point will be pop out of stack if the next .r < curr.r stop we have reach the end 
           // else keep computing the intercept between two rectange 
           // the above also happens until the next element has higher heigth and overlap with the top of stack 
           // then we excaute to the next element
           
           // in the case it is decreasing:
              ... 1. we keep pop element from the stack until the a overlap and higher height element is found 
                   then we compute the coordate and add the current element back in


                   I should use queue instead of stack to handle the heigth.
         
        */
        // this will be use to store the element index
            List<List<Integer>> results = new ArrayList<>();

            //PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.<int[]>comparingInt(e -> e[2]).reversed().thenComparing(e -> e[1]));
            PriorityQueue<int[]> queue = new PriorityQueue<>((a, b) -> a[2] == b[2] ? a[1] - b[1] : b[2] - a[2]);
            int[] prev = new int[] { -1, Integer.MAX_VALUE, 0 };

            for (int[] curr : buildings) {
                while (!queue.isEmpty() && curr[0] > prev[1]) {
                    int[] next = queue.poll();
                    if (next[1] <= prev[1])
                        continue;
                    results.add(List.of(prev[1], next[2]));
                    prev = next;
                }
                if (curr[2] > prev[2]) {
                    if (curr[0] == prev[0]) {
                        results.remove(results.size() - 1);
                    }
                    results.add(List.of(curr[0], curr[2]));
                    if (curr[1] < prev[1])
                        queue.offer(prev);
                    prev = curr;
                } else if (curr[1] > prev[1]) {
                    if (curr[2] == prev[2]) {
                        prev[1] = curr[1];
                    } else {
                        queue.offer(curr);
                    }
                }
            }

            while (!queue.isEmpty()) {
                int[] next = queue.poll();
                if (next[1] <= prev[1])
                    continue;
                results.add(List.of(prev[1], next[2]));
                prev = next;
            }
            if (prev[2] == Integer.MAX_VALUE) {
                results.add(List.of(prev[1], 0));
            }
            return results;
    }
}