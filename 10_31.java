

/* The isBadVersion API is defined in the parent class VersionControl.
      boolean isBadVersion(int version); */

    public class Solution extends VersionControl {
        public int firstBadVersion(int n) {
            // it is 1, 2, 3 4 5 
            // binary search
            int left = 1;
            int right = n;
            while(left <= right) {
                int mid = left + ((right - left) /2);
                // if mid is bad then the first one is one the left side else right 
                if (isBadVersion(mid)) {
                    right = mid - 1;
                } else {
                    System.out.println(left);
                    left = mid + 1;
                }
            }
            return left;
        }
    }

/**
 * Design a data structure that supports adding new words and finding if a string matches any previously added string.

Implement the WordDictionary class:

WordDictionary() Initializes the object.
void addWord(word) Adds word to the data structure, it can be matched later.
bool search(word) Returns true if there is any string in the data structure that matches word or false otherwise. word may contain dots '.' where dots can be matched with any letter.
 */



    class WordDictionary {
        // this will be the trie structure issue: with the regex notation of .
        // so how to handle it in search
        // think about the key factor here
        // init case handle it in the addword case 
        // so basically we have 27 list
        // and for each char we also update the . repr wordict as well 
        // this will result in the duplicate 
        //  another idea will be do in the search  
        // lets code it out 
        boolean isEnd;
        WordDictionary [] hd;
        
        /** Initialize your data structure here. */
        public WordDictionary() {
            this.hd = new WordDictionary[26];
            for (int i = 0; i < 26; i++) {
                this.hd[i] = null;
            }
            this.isEnd = false;
        }
        
        /** Adds a word into the data structure. */
        public void addWord(String word) {
            int n = word.length();
            WordDictionary[] temp = this.hd; 
            for (int i = 0; i < n; i++) {
                char c = word.charAt(i);
                int index = c - 'a';
                // if it does not exist create one 
                if (temp[index] == null) {
                    // init it 
                    temp[index] = new WordDictionary();
                }
                // if it is the last char mark the the node to be the end 
                if (i == n - 1) {
                    temp[index].isEnd = true;
                }
                // set the temp to be the current node for char.
                temp = temp[index].hd;
            }
        }
        
        /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
        public boolean search(String word) {
            // need to handle the . case 
            // modified version of trie structure
            return helper(word, 0, this);
        }
        private boolean helper(String word, int index , WordDictionary current) {
            if (index == word.length()) {
                // we have found the worddictionary that match the last char need to check if it a the end?
                return current.isEnd;
            } else {
                char c = word.charAt(index);
                if (c != '.') {
                    int i = c - 'a';
                    if (current.hd[i] == null ) {
                        return false;
                    } else {
                        return helper(word, index + 1, current.hd[i]);
                    }
                }
                // try each one of the list, if it can be found that one of the next element is End
                for (int i = 0; i < current.hd.length; i++) {
                    if (current.hd[i] != null && helper(word, index + 1, current.hd[i])) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
    
    /**
     * Your WordDictionary object will be instantiated and called as such:
     * WordDictionary obj = new WordDictionary();
     * obj.addWord(word);
     * boolean param_2 = obj.search(word);
     */

/**Given an m x n 2d grid map of '1's (land) and '0's (water), return the number of islands */

    class Solution {
        public int numIslands(char[][] grid) {
            /*condition of island here:
             1. its horzontal and verical are all surround by water
             Note : there isn't any shape of island .
             so by obsevation
             // 1. backtracking with memo ... memo that lable the visited island
             //     try all 4 direction goes for the one that has island and keep expending. 
             //       idea: a for loop and do the flooding and chagne the visited grid to '2'
             //             increase counter and go through the grid when see lable 1 do the same operatio again
             //  since we are using grid it will be cosntant space and n^2 time 
             // 2. dp emmm.since the island does not have shape ...
             
                3. strong connected component here again... so union find works here as well 
                but I forget the exact impelemtation of it ....do the union find later 
            lets code it out
            */ 
            // first try: brute force or so call depth first 
            int m = grid.length;
            int n = grid[0].length;
            int count = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == '1') {
                        flood(grid, i, j, m , n);
                        count ++;
                    }
                }
            }
            return count;
        }
        private void flood(char[][] grid, int i , int j, int m, int n) {
            if (i>=0 && j>=0 && i<grid.length && j<grid[0].length&&grid[i][j]=='1') {
                grid[i][j]='0';
                flood(grid, i + 1, j, m ,n);
                flood(grid, i - 1, j, m ,n);
                flood(grid, i, j + 1, m ,n);
                flood(grid, i, j - 1, m ,n);
            }
        }
    }