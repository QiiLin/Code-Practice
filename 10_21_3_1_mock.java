
// 205. Isomorphic Strings
class Solution {
    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        // init idea: having a fix 26 char mapping 
        // and when iterat through it 
        // check if the currrent char has map 
        // if so, then check the mapping char is the same as the other one
        // else the checking 
        // I forget about the character that is not lower case
        // need to expand the record mapping size
        // int [] s_map = new int [256];
        // int [] v_map = new int [256];
        // for(int i = 0; i < s.length(); i++) {
        //     char curr = s.charAt(i);
        //     char target = t.charAt(i);
        //     // if mapping does not exist, then set up the mapping
        //     if (s_map[curr] == 0 && v_map[target] == 0  ) {
        //         System.out.println(i + " " + curr + " "+  target);
        //         s_map[curr] = (target - 'a') + 1;
        //         v_map[target] = (curr - 'a') + 1;
        //     // if else that means the mapping exist just need to check if the mapping match
        //     } else if (((s_map[curr] != 0) && (target - 'a') != s_map[curr] - 1) || (v_map[target] != 0 && (curr - 'a') != v_map[target] - 1 )) {
        //         return false;
        //     }
        // }
        // return true;
        // ---------------- below is a clean up version of solution
        int[] m1 = new int[256];
        int[] m2 = new int[256];
        int n = s.length();
        for (int i = 0; i < n; i++) {
            if (m1[s.charAt(i)] != m2[t.charAt(i)]) return false;
            m1[s.charAt(i)] = i + 1;
            m2[t.charAt(i)] = i + 1;
        }
        return true;
    }
}

// 855. Exam Room
class ExamRoom {
    // N seat .. max distance between between each person 
    // if tie then seat on lower numbered seat
    // init idea: need to having a correct location for the further sperate gap location
    //    if tie, take the gap with the lower number.
    //    key: we need to some how to keep tracking of the status of each sit and their distance between there neighbours
    // target: find make both function constant time 
    //    proity queue where each element has its left_right distance 
    //       issue here, how to update leave ?  having another hashmap? but for what?
    //       so having another hashmap that takes the seat and maps to the element in the queue ?
    //         but we need to update the queue by pop out the p's neighbours first and then push back with new distance data
    // seems like a doable solution, the running time will be log n for seat but for leave will be n since I will 
    // have to iterated it and then find its neighor and do the update.
    
    // got inspired by a friend, I should use treeMap to handle this 
    // since treeMap have higher and lower which can be used to find the neighbour in logn time
    // this will ensure the run time of both will be log n
    
    
    // ... the simplest way will be just find the biggest gap in each seat and leave call
    // this will makes it linear time for both function.
    // too lazy to code it out..
    // so layout idea here
    // 1, base can for the init seat (we don't insert gap in the proity queue, when 0 or n-1 is being seat ....)
    //    this will requrie me to handle some edge case as well..be sure to add base case [-1, n] so that we can 
    //    handle this edge case with simple if statement. 
    // 2. for seat, if the size is zero, its init case, just do nothing and return 0
    //    else do a poll to get the largest gap and find the mid, and then we will put two gap inside [left, mid] [mid, right]
    // 3. iteratied through the queue and find the interval that contains p as left or right... and then remove it from queue and the add back with
    //    [left1 , right2]...
    // that should be it
    
    // will do the treeMap version 
    // bascially the same idea, except that we are using higher, lower fucntion in the tree to reduce the running time
    TreeSet<Integer> seats = new TreeSet();
    PriorityQueue<Pair> pq;
    int N;
    public ExamRoom(int N) {
        this.N = N;
        pq = new PriorityQueue<>((a, b) -> {
            if (a.gap != b.gap) {
                return b.gap - a.gap;
            } else {
                return a.left - b.left;
            }
        });
        pq.add(new Pair(-1, N));
        
    }
    
    // returning an int representing what seat the student sat in
    public int seat() {
        int result = 0;
        Pair gap = pq.poll();
        // if left is -1,,, next pos will be 0 for sure
        if (gap.left == -1) {
            result = 0;
        // if right is N.... next pos will be N-1 for sure
        } else if (gap.right == N) {
            result = N - 1 ;
        } else {
            // else it has to be the middle value
            result = gap.left  + (gap.right - gap.left ) / 2;
        }
        pq.add(new Pair(gap.left, result));
        pq.add(new Pair(result, gap.right)); 
        seats.add(result);
        return result;
    }
    // representing that the student in seat number p now leaves the room.
    //  guaranteed that any calls to ExamRoom.leave(p) have a student sitting in seat p
    public void leave(int p) {
        Integer left = seats.lower(p);
        Integer right = seats.higher(p);
        left = left == null ? -1 : left;
        right = right == null ? N : right;
        seats.remove(p);

        pq.remove(new Pair(left, p));
        pq.remove(new Pair(p, right));
        pq.add(new Pair(left, right));
    }
    
    private class Pair{
        int left, right, gap;
        public Pair(int left, int right) {
            this.left = left;
            this.right = right;
            if(left == -1) {
                gap = right;
            } else if(right == N) {
                gap = right - left - 1;
            } else {
                gap = Math.abs(right - left) /2;
            }
        }   
        
        // equals is need to remove the element from the TreeSet
        @Override
        public boolean equals(Object obj) {
            Pair temp = (Pair) obj;
            if(left == temp.left && right == temp.right) {
                return true;
            } else {
                return false;
        }
    }
  }
}

// 394. Decode String
class Solution {
    public String decodeString(String s) {
        // idea is simple, all we need to do is to use stack to find the pattern
        // and based from that we can do a check 
        String res = "";
        Stack<Integer> countStack = new Stack<>();
        Stack<String> resStack = new Stack<>();
        int idx = 0;
        while (idx < s.length()) {
            if (Character.isDigit(s.charAt(idx))) {
                int count = 0;
                while (Character.isDigit(s.charAt(idx))) {
                    count = 10 * count + (s.charAt(idx) - '0');
                    idx++;
                }
                countStack.push(count);
            }
            else if (s.charAt(idx) == '[') {
                resStack.push(res);
                res = "";
                idx++;
            }
            else if (s.charAt(idx) == ']') {
                StringBuilder temp = new StringBuilder (resStack.pop());
                int repeatTimes = countStack.pop();
                for (int i = 0; i < repeatTimes; i++) {
                    temp.append(res);
                }
                res = temp.toString();
                idx++;
            }
            else {
                res += s.charAt(idx++);
            }
        }
        return res;
    }
}
