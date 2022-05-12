import java.util.*;
class Solution {
    static int answer, k, N;
    static int[] numbers;
    static boolean[] isSelected;
    static int[][] dungeons;
    public int solution(int kInput, int[][] dungeonsInput) {
        answer = 0;
        k = kInput;
        dungeons = dungeonsInput;
        N = dungeons.length;
        
        numbers = new int[N];
        isSelected = new boolean[N];
        
        perm(0);
        
        return answer;
    }
    
    private static void perm(int cnt) {
        if (cnt == N) {
            goDungeon();
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
    
    private static void goDungeon() {
        int energy = k;
        int cnt = 0;
        for (int n : numbers) {
            if (energy >= dungeons[n][0]) {
                energy -= dungeons[n][1];
                cnt++;
            }
        }
        
        answer = Math.max(answer, cnt);
    }
}

// 최소 피로도 이상 있어야하고, 거기 갔다오면 소모 피로도만큼만 까임
// 순열 구해서 순서대로 던전 탐험, 몇개 던전 탐색할 수 있는지 찾고 최댓값 갱신