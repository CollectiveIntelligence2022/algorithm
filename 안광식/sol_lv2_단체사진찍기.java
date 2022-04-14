import java.util.*;

class Solution {
    static int N, answer;
    static String[] Data;
    static char[] friends = { 'A', 'C', 'F', 'J', 'M', 'N', 'R', 'T' };
    static int[] numbers;
    static boolean[] isSelected;
    public int solution(int n, String[] data) {
        N = 8;
        answer = 0;
        Data = data;
        
        numbers = new int[N];
        isSelected = new boolean[N];
        perm(0);
        
        return answer;
    }
    
    private static void perm(int cnt) {
        if (cnt == N) {
            if (checkFriends())
                answer++;
            return;
        }
        
        for (int i = 0; i < N; i++) {
            if (isSelected[i])
                continue;
            
            numbers[cnt] = i;
            
            isSelected[i] = true;
            perm(cnt + 1);
            isSelected[i] = false;
        }
    }
    
    private static boolean checkFriends() {
        for (int i = 0; i < Data.length; i++) {
            char A = Data[i].charAt(0);
            char B = Data[i].charAt(2);
            
            int cnt = -1;
            for (int j = 0; j < N; j++) {
                if (friends[numbers[j]] == A || friends[numbers[j]] == B) {
                    if (cnt >= 0) {
                        break;
                    }
                    
                    cnt = 0;
                }
                
                else if (cnt >= 0)
                    cnt++;
            }
            
            int gap = Data[i].charAt(4) - '0';
            switch(Data[i].charAt(3)) {
                case '=':
                    if (gap != cnt)
                        return false;
                    break;
                
                case '<':
                    if (cnt >= gap)
                        return false;
                    break;
                    
                case '>':
                    if (cnt <= gap)
                        return false;
                    break;
            }
        }
        
        return true;
    }
}

// 순열 구하고 각 결과마다 조건 만족하는지 체크
// 만족한다면 cnt++