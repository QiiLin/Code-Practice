/**
 * 
 * A matrix is Toeplitz if every diagonal from top-left to bottom-right has the same element.

Now given an M x N matrix, return True if and only if the matrix is Toeplitz.
 */

class Solution {
    public boolean isToeplitzMatrix(int[][] matrix) {
        /*
        i
        */
        for (int r = 0; r < matrix.length; ++r)
            for (int c = 0; c < matrix[0].length; ++c)
                if (r > 0 && c > 0 && matrix[r-1][c-1] != matrix[r][c])
                    return false;
        return true;
    }
}

/**
 * You are playing the Bulls and Cows game with your friend.

You write down a secret number and ask your friend to guess what the number is. When your friend makes a guess, you provide a hint with the following info:

The number of "bulls", which are digits in the guess that are in the correct position.
The number of "cows", which are digits in the guess that are in your secret number but are located in the wrong position. Specifically, the non-bull digits in the guess that could be rearranged such that they become bulls.
Given the secret number secret and your friend's guess guess, return the hint for your friend's guess.

The hint should be formatted as "xAyB", where x is the number of bulls and y is the number of cows. Note that both secret and guess may contain duplicate digits.
 */
class Solution {
    public String getHint(String secret, String guess) {
        // just record the occurence of digits 
        int bull = 0;
        int cow = 0;
        int [] record = new int [10];
        for (int i = 0; i < secret.length(); i++) {
            int s = Character.getNumericValue(secret.charAt(i));
            int g = Character.getNumericValue(guess.charAt(i));
            if (s == g) {
                bull++;
            } else {
                if (record[s] < 0) cow++;
                if (record[g] > 0) cow++;
                record[s] ++;
                record[g] --;
            }
        }
        return bull + "A" + cow + "B";
    }
}
//Evaluate Division .... I later found this is union find... but the dfs works here as well and has better run time..

class Solution {
    public double[] calcEquation(List<List<String>> eq, double[] vals, List<List<String>> q) {
        // first thought.
        // need to process the input so that we can access in constant time
        // ideally find all possible way to
        // each target ...[a ,b] = > a/b   => b/a
        // maybe do it speratly 
        // a => [b-v]
        // b => [c-v]
        // a - c  =  a/b and b/c = a/c
        // a/b  b/c  c/e  want a/e
        // up a => [b]  b=>[c] c=>[e]
        // a/b * b/c * c/e =final value  => a/c will be found and will be recorded a = > [b, c, e]
        // issue b over e will
        // code it out
        // covert input to graph
        // below can be treat as 2d array as well...
        Map<String, Map<String, Double>> m = new HashMap<>();
        for (int i = 0; i < vals.length; i++) {
            // put both 1/v and v into the graph
            m.putIfAbsent(eq.get(i).get(0), new HashMap<>());
            m.putIfAbsent(eq.get(i).get(1), new HashMap<>());
            m.get(eq.get(i).get(0)).put(eq.get(i).get(1), vals[i]);
            m.get(eq.get(i).get(1)).put(eq.get(i).get(0), 1 / vals[i]);
        }
        double[] r = new double[q. size() ];
        // then for each query we do the dfs check, seen here is need to check cycle
        for (int i = 0; i < q.size() ; i++)
            r[i] = dfs(q.get(i).get(0), q.get(i).get(1), 1, m, new HashSet<>());
        return r;
    }
    // the code sample of dfs is getting reference from wiki.
    double dfs(String s, String t, double r, Map<String, Map<String, Double>> m, Set<String> seen) {
        if (!m.containsKey(s) || !seen.add(s)) return -1;
        if (s.equals(t)) return r;
        Map<String, Double> next = m.get(s);
        for (String c : next.keySet()) {
            double result = dfs(c, t, r * next.get(c), m, seen);
            if (result != -1) return result;
        }
        return -1;
    }
}


// Minimum Cost to Hire K Workers

class Solution {
    public double mincostToHireWorkers(int[] quality, int[] wage, int K) {
        // key factor: ratio... we want to use lowest ratio that hold both 
        // condition
        // so just sort the workers by ratio and take the k and maintain a the max ans
        // that will it
        int N = quality.length;
        Worker[] workers = new Worker[N];
        for (int i = 0; i < N; ++i)
            workers[i] = new Worker(quality[i], wage[i]);
        Arrays.sort(workers);

        double ans = 1e9;
        int sumq = 0;
        PriorityQueue<Integer> pool = new PriorityQueue();
        for (Worker worker: workers) {
            pool.offer(-worker.quality);
            sumq += worker.quality;
            if (pool.size() > K)
                sumq += pool.poll();
            if (pool.size() == K)
                ans = Math.min(ans, sumq * worker.ratio());
        }

        return ans;
    }
}

class Worker implements Comparable<Worker> {
    public int quality, wage;
    public Worker(int q, int w) {
        quality = q;
        wage = w;
    }

    public double ratio() {
        return (double) wage / quality;
    }

    public int compareTo(Worker other) {
        return Double.compare(ratio(), other.ratio());
    }
}