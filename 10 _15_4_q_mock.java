// 1078
class Solution {
    public String[] findOcurrences(String text, String first, String second) {
        // pattern match problem
        ArrayList<String> hd = new ArrayList<String> ();
        int n = text.length();
        int i = 0;
        boolean f_f = false;
        boolean s_f = false;
        while(i < n) {
            // skip spaces 
            while(i < n && text.charAt(i) == ' ') {
                i++;
            }
            StringBuilder sb = new StringBuilder();
            while( i < n && text.charAt(i) != ' ') {
                sb.append(text.charAt(i));
                i++;
            }
            String target = sb.toString();
            if (sb.length() == 0) {
                break;
            }
            // if both define current is thrid
            if (f_f && s_f) {
                hd.add(target);
                s_f = false;
                f_f = target.equals(first) ? true:false;
            } else if (! f_f) {
                // if first is not found check it 
                f_f = target.equals(first) ? true:false;
            } else if (f_f && !s_f) {
                // if first is found and second is not found;
                if (target.equals(second)) {
                    s_f = true;
                } else {
                    f_f = target.equals(first) ? true:false;
                }
            }
        }
        String [] res = hd.toArray(new String[hd.size()]);
        return res;
    }
}
// 1016
class Solution {
    public boolean queryString(String S, int N) {
        for (int i = N; i > N / 2; --i)
            if (!S.contains(Integer.toBinaryString(i)))
                return false;
        return true;
    }
}
// 1090. Largest Values From Labels
class Solution {
    public int largestValsFromLabels(int[] values, int[] labels, int num_wanted, int use_limit) {
        // knap question
        // there is use limit for each item 
        // and there is number limit for overall 
        // and we still want to find the largest vals
        // recap ksnap
        // dp[i, weight - 1] 
        // dp [i-th label,  number] = 
        // this is a simpfy version
        // we shuld start from the largest value 
        // in this case we will have nlogn times and n space 
        Map<Integer, Integer> limit = new HashMap<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        for (int i = 0; i < values.length; i++) {
            if (!limit.containsKey(labels[i])) limit.put(labels[i], 0);
            pq.offer(new int[]{values[i], labels[i]});
        }
        int max = 0, count = 0;
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            if (limit.get(cur[1]) < use_limit) {
                max += cur[0];
                limit.put(cur[1], limit.get(cur[1]) + 1);
                if (++count == num_wanted) break;
            }
        }
        return max;
    }
}

// 1105. Filling Bookcase Shelves

class Solution {
    public int minHeightShelves(int[][] books, int shelf_width) {
        // dp question
        // order of place maters here
        // try to place first book then 
        // the second book as two choice 
        // one  stay on the same shelf 
        // two place it either horzhon or vercial on the second shelf
        // I guess the we can only place the book vertically 
        // dp[i] = heigth for placing books upto i-th book
        // dp [i] = for each book try to fit as many books from previous shelf 
        // and in the process of doing so, we many encounter the optimize case
        // and this will be recorded as  dp[i] = Math.min(dp[i], dp[j-1] + height);
        int[] dp = new int[books.length + 1];
        dp[0] = 0;
        for (int i = 1; i <= books.length; ++i) {
            int width = books[i-1][0];
            int height = books[i-1][1];
            dp[i] = dp[i-1] + height;
            for (int j = i - 1; j > 0 && width + books[j-1][0] <= shelf_width; --j) {
                height = Math.max(height, books[j-1][1]);
                width += books[j-1][0];
                dp[i] = Math.min(dp[i], dp[j-1] + height);
            }
        }
        return dp[books.length];
            
    }
}