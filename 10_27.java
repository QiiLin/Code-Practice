// power of four classical bit question 

class Solution {
    public boolean isPowerOfFour(int num) {
        //note we can check power of two by n
        return num > 0 && (num & (num - 1)) ==0  && (num & 0x55555555) !=0;
    }
}


// Exclusive Time of Functions
class Solution {
    public int[] exclusiveTime(int n, List<String> logs) {
        // single thread
        // n functions... each function has id lable from 0 to n-1
        // using stack to store function call 
        // call starts ... id push into stack when ends the id is pop off the stack
        // top of stack is the currrent function being executed .
        // whne id being pop.. we need to know its start time and end time and time stamp
        // {function_id}:{"start" | "end"}:{timestamp}
        // Note: function can be called more than one time and possibit recursively 
        // find the array such that i-th index of it represents the total excutation time
        // .... make thing clear
        // worth noting: it is a sinlge thread
        // so when 0 starts if the next is not 0... time of 0 will be next.time - curr.time
        // if it is zero...and end - start + 1 only works between elment with same id
        // if 0 start  meet 1 start => later - before
        // if 0 start meets 0 end => later - before + 1;
        // if 0 end meets 1 end => later -before
        // if end than start => start - 1 - end 
        // since we only keep element start in the stack....
        // above can be generalized .. if current is start and prev has to be start.===>its simple later - before
        // if current end, and stack is not empty => it has to be later - before + 1;
        // Note: when function ends.... its top of stack has to be its self. since this is a single thread
        // 
        // Now what about recursive call ... it is the same idea....
        // 
        // noting this is for the start..
        // if the number are different 
        // |0|1|2|3|4|5|6|7|8|
        // init idea: using stack... if it ends ...pop the top element...
        // if it is the end ..
        Stack < Integer > stack = new Stack < > ();
        int[] res = new int[n];
        String[] s = logs.get(0).split(":");
        stack.push(Integer.parseInt(s[0]));
        int i = 1, prev = Integer.parseInt(s[2]);
        while (i < logs.size()) {
            s = logs.get(i).split(":");
            if (s[1].equals("start")) {
                if (!stack.isEmpty())
                    res[stack.peek()] += Integer.parseInt(s[2]) - prev;
                stack.push(Integer.parseInt(s[0]));
                prev = Integer.parseInt(s[2]);
            } else {
                res[stack.peek()] += Integer.parseInt(s[2]) - prev + 1;
                stack.pop();
                prev = Integer.parseInt(s[2]) + 1;
            }
            i++;
        }
        return res;
        
    }
}


// Text Justification
class Solution {
    public List<String> fullJustify(String[] words, int maxWidth) {
        // idea: as it stated.
        // so just greedy apporach 
        // and using string builder to at space need ..
        // and that will be it...
        // 
        // Note: I copy the code... once I found the idea is already given 
        // it is the matter of handle edge cases for the space justfid space
        List<String> lines = new ArrayList<String>();
        int index = 0;
        while (index < words.length) {
            int count = words[index].length();
            int last = index + 1;
            while (last < words.length) {
                if (words[last].length() + count + 1 > maxWidth) break;
                //plus one for the space, if its a perfect fit it will fit
                count += 1 + words[last].length();
                last++;
            }
            StringBuilder builder = new StringBuilder();
            builder.append(words[index]);
            int diff = last - index - 1;
           // if last line or number of words in the line is 1, left-justified
            if (last == words.length || diff == 0) {
                for (int i = index+1; i < last; i++) {
                    builder.append(" ");
                    builder.append(words[i]);
                }
                for (int i = builder.length(); i < maxWidth; i++) {
                    builder.append(" ");
                }
            } else {
                // middle justified
                int spaces = (maxWidth - count) / diff;
                int r = (maxWidth - count) % diff;
                for (int i = index+1; i < last; i++) {
                    for(int k=spaces; k > 0; k--) {
                        builder.append(" ");
                    }
                    if(r > 0) {
                        builder.append(" ");
                        r--;
                    }
                    builder.append(" ");
                    builder.append(words[i]);
                }
            }
            lines.add(builder.toString());
            index = last;
        }
        return lines;
    }
}