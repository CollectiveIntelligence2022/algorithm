import java.util.*;

class Solution {
    public int[] solution(int n, String[] words) {
        int[] answer = { 0, 0 };
        HashSet<String> set = new HashSet<>();
        
        int cnt = 1;
        char prev = words[0].charAt(0);
        for (String word : words) {
            if (set.contains(word) || word.charAt(0) != prev) {
                answer[0] = cnt % n == 0 ? n : cnt % n;
                answer[1] = cnt % n == 0 ? cnt / n : cnt / n + 1;
                break;
            }
            set.add(word);
            prev = word.charAt(word.length() - 1);
            cnt++;
        }

        return answer;
    }
}