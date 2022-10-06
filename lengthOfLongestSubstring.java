class Solution {
    public int lengthOfLongestSubstring(String s) {
        if(s.length() <= 1){
            return s.length();
        }
        Map<Character,Integer> map = new HashMap<Character,Integer>();
        int start = 0,max = 0;
        for(int i = 0 ; i < s.length() ;i++){
            Character ch = s.charAt(i);
            Integer dupIdx = map.get(ch);
            if(dupIdx != null && dupIdx >= start){
                max = Math.max(max,(i-start));
                start = dupIdx + 1;
            }
            map.put(ch,i);
        }
         return Math.max(max, s.length() - start);
        
    }
}
