Based on dynamic programming.

class Solution {
    public String longestPalindrome(String s) {
        int len = s.length();

        if (len == 0) {
            return "";
        }

        char[] sChars = s.toCharArray();

        /*
         * length of common string found
         * set dp[i][j] to 0 if s[i - 1] != s[j];
         */
        int[][] dp = new int[2][len + 1];
        int currRow = 0;
        int maxLen = 0;
        int start = 0;

        /*
         * find longest common string between s and s' (reversed s)
         * for orginal string, from sChars[0] to sChars[len - 1]
         * for reversed view, from sChars[len - 1] to sChars[0];
         */
        for (int i = 0; i <= len; i++) {
            for (int j = len; j >= 0; j--) {
                if (i == 0 || j == len) {
                    dp[currRow][j] = 0;
                } else if (sChars[i - 1] == sChars[j]) {
                    dp[currRow][j] = dp[1 - currRow][j + 1] + 1;
                    int currLen = dp[currRow][j];

                    /*
                     * 1. longest common substring length
                     * 2. skip those common substrings doesn't "produced" by reverse.
                     */
                    if (i - 1 == j + currLen - 1 && i - currLen == j && currLen > maxLen) {
                        start = j;
                        maxLen = currLen;
                    }
                } else {
                    dp[currRow][j] = 0;
                }
            }

            // * switch the working row of dp[][] to the next row (i)
            currRow = 1 - currRow;
        }

        return s.substring(start, start + maxLen);
    }
}
Space Complexity: O(n)

Approach 2: Brute Force (962ms)
class Solution {
    public String longestPalindrome(String s) {
        char[] charArray = s.toCharArray();
        int start = 0;
        int maxLen = 0;
        for (int i = 0; i < charArray.length; i++) {
            for (int len = 0; i + len < charArray.length; len++) {
                if (isPalindrome(charArray, i, len) && len + 1 > maxLen) {
                    maxLen = len + 1;
                    start = i;
                }
            }
        }

        return s.substring(start, start + maxLen);
    }

    public boolean isPalindrome(char[] charArray, int start, int len) {
        while (len > 0) {
            if (charArray[start] != charArray[start + len]) {
                return false;
            }

            start++;
            len -= 2;
        }

        return true;
    }
}
Approach 3: Dynamic Programming
image

image

class Solution {
    public String longestPalindrome(String s) {
        char[] sChars = s.toCharArray();

        int m = s.length();

        // * dp[i][len + 1] means substring starting from i with length of len;
        boolean[][] dp = new boolean[m][2];
        int currCol = 0;

        int maxLen = 0;
        int ans = 0; // record start index of s

        for (int len = 0; len < m; len++) {
            for (int start = 0; start + len < m; start++) {
                int end = start + len;
                if (len == 0) {
                    dp[start][currCol] = true;
                } else if (len == 1) {
                    dp[start][currCol] = (sChars[start] == sChars[end]);
                } else {
                    dp[start][currCol] = (sChars[start] == sChars[end] && dp[start + 1][currCol]);
                }

                if (dp[start][currCol] && len + 1 > maxLen) {
                    ans = start;
                    maxLen = len + 1;
                }
            }

            currCol = 1 - currCol;
        }

        return maxLen == 0 ? "" : s.substring(ans, ans + maxLen);
    }
}
Space complexity: O(n). It uses O(2n) space to store the table.

Approach 4: Expand Around Center
class Solution {
    public String longestPalindrome(String s) {
        if (s.length() == 0) {
            return "";
        }

        char[] sChars = s.toCharArray();

        int start = 0;
        int end = 0;

        for (int i = 0; i < sChars.length; i++) {
            int len = Math.max(expand(sChars, i, i), expand(sChars, i, i + 1));

            if (len > end - start + 1) {
                start = i - (len - 1) / 2;
                end = i + len / 2;
            }
        }

        return s.substring(start, end + 1);
    }

    public int expand(char[] sChars, int i, int j) {
        while (i >= 0 && j < sChars.length && sChars[i] == sChars[j]) {
            i--;
            j++;
        }
        return j - i - 1;
    }
}
Approach 5: Manacher's Algorithm
!!!Based on the solution video on leetcode-cn.com
The copyright belongs to the original author
In Approach 4, we do lots of comparisons which is unnecessary. Manacher's algorithm achieves the time complexity O(n) by skip the unnecessary comparisons.
e.g.
image

For indice 8 which is a, we don't need to do the expandAroundCenter since we have learned from indice 2, that the expand length is 0. And to make it true, the indice 8 and indice 2 should be in the same palindrome. Meanwhile they have the same distance to the center of the palindrome.

We could divide the problem into several cases.
p[i] means the expand length of substring with the center of indice i .
[a, a, b, c, b, a, d] => p[3] = 2

1. i + p[mirror] < maxRight
image
2. i + p[mirror] == maxRight
image
3. i + p[mirror] > maxRight
image

if i >= maxRight
    expand the substring with the center of i
else if i + p[mirror] < maxRight
    p[i] = p[mirror]
else if i + p[mirror] == maxRight
    p[i] = p[mirror] = maxRight - i
    expand the substring with the center of i, beginning from maxRight
else
    p[i] = maxRight - i
We actually could make the code more concise:

if i < maxRight
    p[i] = min(p[mirror], maxRight - i)
    
expand substring with center of i, beginning from i + p[i]
The algorithm only solve the string with odd size, so we add '#' to the string to make the size of string always be odd.

class Solution {
    public String longestPalindrome(String s) {

        // code line +8 to line +15
        int strLen = 2 * s.length() + 3;
        char[] sChars = new char[strLen];

        /*
         * To ignore special cases at the beginning and end of the array
         * "abc" -> @ # a # b # c # $
         * "" -> @#$
         * "a" -> @ # a # $
         */
        sChars[0] = '@';
        sChars[strLen - 1] = '$';
        int t = 1;
        for (char c : s.toCharArray()) {
            sChars[t++] = '#';
            sChars[t++] = c;
        }
        sChars[t] = '#';

        int maxLen = 0;
        int start = 0;
        int maxRight = 0;
        int center = 0;
        int[] p = new int[strLen]; // i's radius, which not includes i
        for (int i = 1; i < strLen - 1; i++) {
            if (i < maxRight) {
                p[i] = Math.min(maxRight - i, p[2 * center - i]);
            }

            // expand center
            while (sChars[i + p[i] + 1] == sChars[i - p[i] - 1]) {
                p[i]++;
            }

            // update center and its bound
            if (i + p[i] > maxRight) {
                center = i;
                maxRight = i + p[i];
            }

            // update ans
            if (p[i] > maxLen) {
                start = (i - p[i] - 1) / 2;
                maxLen = p[i];
            }
        }

        return s.substring(start, start + maxLen);
    }
}
Time complexity: O(n)
Space complexity: O(n) We need O(n) space to form p[i].
