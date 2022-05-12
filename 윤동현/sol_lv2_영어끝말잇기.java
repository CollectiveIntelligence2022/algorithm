import java.util.*;

class sol_lv2_영어끝말잇기 {
    public int[] solution(int n, String[] words) {
        int[] answer = {0, 0};

        HashSet<String> hs = new HashSet<>();
        int[] person = new int[n];

        int i = 1;
        hs.add(words[0]);
        person[0]++;

        for(int k=1; k<words.length; k++) {
            if(hs.contains(words[k]) || words[k].charAt(0) != words[k-1].charAt(words[k-1].length()-1)) {
                answer[0] = i+1;
                answer[1] = person[i]+1;
                break;
            }

            hs.add(words[k]);
            person[i]++;
            i = (i+1) % n;
        }

        return answer;
    }
}
