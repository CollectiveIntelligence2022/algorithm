class Solution_lv2_피로도 {
    int answer = 0;
    
    public int solution(int k, int[][] dungeons) {
        boolean[] visit = new boolean[dungeons.length];
        brute(k, 0, dungeons, visit);
        return answer;
    }
    
    // 최대한 많이 돌아야 함
    // 그리디하게?
    // -> 최소 필요 피로도 순 or 소모 피로도 순 정렬해도 예외 케이스 존재
    // sol) 완전 탐색으로 모든 경우를 검사하자.
    public void brute(int hp, int cnt, int[][] dg, boolean[] visit) {
        answer = Math.max(answer, cnt);
        
        for (int i = 0; i < dg.length; i++) {
            // 이미 방문한 던전 or 현재 던전을 못 도는 경우 다음 던전을 탐색
            if (visit[i] || hp < dg[i][0]) {
                continue;
            }
            
            visit[i] = true;
            brute(hp - dg[i][1], cnt + 1, dg, visit);
            visit[i] = false;
        }
    }
}