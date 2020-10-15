
// 190


public class Solution_a {
    // you need treat n as an unsigned value
    public int reverseBits(int n) {
        int ret = 0;
        for (int i = 0; i < 32; i++) {
            ret <<= 1;
            ret |= (n & 1);
            n >>>= 1;
        }
        return ret;
    }
}



// 62

public class Solution_b {
    public int uniquePaths(int m, int n) {
        // int[][] grid = new int[m][n];
        // for(int i = 0; i<m; i++){
        //     for(int j = 0; j<n; j++){
        //         if(i==0||j==0)
        //             grid[i][j] = 1;
        //         else
        //             grid[i][j] = grid[i][j-1] + grid[i-1][j];
        //     }
        // }
        // return grid[m-1][n-1];
        // we only need 1 d space here
        int[] dp = new int[n+1];
        dp[1]=1;
        for(int i=1;i<=m;i++)
            for(int j=1;j<=n;j++){
                dp[j]=dp[j]+dp[j-1]; 
            }
        return dp[n];
    }
}

// 149

class Solution_c {
    public int maxPoints(int[][] points) {
        // must solve it in linear time
        // idea: brute force first
        // for each possible slop 
        // this will run in square time and square space
        // is there duplication?
        // greet ... or dp 
        // I think it is dp 
        if(points == null) return 0;
        int n = points.length, result = 0;
        if(n <= 2) return n;
        HashMap<String,Integer> map = new HashMap<>();
        for(int i = 0 ; i < n ; i++){
            map.clear();
            int overlap = 0, max = 0;
            for(int j = i + 1 ; j < n ; j++){
                int x=points[j][0] - points[i][0];
        	    int y=points[j][1] - points[i][1];
        	    if (x==0 && y==0){
        	        overlap++;
                    continue;
        	    }
       		    int gcd = gcd(x,y); // gcd will never be zero.
    		    x/=gcd;
  		        y/=gcd;
    		    String key = x + ":" + y;
    		    if (map.containsKey(key)) 
                    map.put(key,map.get(key) + 1);
    		    else 
                    map.put(key, 1);
                max = Math.max(max, map.get(key));
            }
            result = Math.max(result, max+overlap+1);
        }
        return result;
    }
    public int gcd(int a, int b)
    {
        if (b == 0)
            return a;
        return gcd(b, a%b);
    }
}