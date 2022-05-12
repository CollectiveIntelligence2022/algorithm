import java.util.*;

class Solution_lv2_영어끝말잇기 {
    public int[] solution(int n, String[] words) {
        int[] answer = new int[2];
        Set<String> set = new HashSet<>();
        int num = 1, turn = 1;
        set.add(words[0]);
        
        for (int i = 1; i < words.length; i++) {
            // 이미 말한 단어 or 끝말잇기 성립 안될 경우
            if (set.contains(words[i]) || !isAble(words[i - 1], words[i])) {
                answer[0] = num + 1;
                answer[1] = turn;
                break;
            } else {
                set.add(words[i]);
                if (++num % n == 0) {
                    num = 0;
                    turn++;
                }
            }
        }
        
        return answer;
    }
    
    public boolean isAble(String before, String cur) {
        return before.charAt(before.length() - 1) == cur.charAt(0);
    }
}