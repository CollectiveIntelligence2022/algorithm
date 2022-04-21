import java.util.*;

class Solution_lv2_빛의경로사이클 {
    List<Integer> answer = new ArrayList<>();
    boolean[][][] isCycle;// [방향][행][열]
    int[] dx = { 1, 0, -1, 0 };// 남서북동(시계방향)
    int[] dy = { 0, -1, 0, 1 };
    int R, C;
    
    public List<Integer> solution(String[] grid) {
        R = grid.length;
        C = grid[0].length();
        isCycle = new boolean[4][R][C];
        
        // 모든 좌표에서 4방향을 모두 확인
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                for (int d = 0; d < 4; d++) {
                    // 사이클이 확인되지 않은 방향의 좌표만 사이클을 찾는다
                    if (!isCycle[d][i][j]) {
                        findCycle(grid, i, j, d);
                    }
                }
            }
        }
        
        // 오름차순
        Collections.sort(answer);
        return answer;
    }
    
    /* 사이클의 길이를 구해서 리스트에 저장 */
    public void findCycle(String[] map, int x, int y, int d) {
        int cnt = 0;
        
        while (true) {
            // 사이클 형성이 되었으므로 탐색 완료
            if (isCycle[d][x][y]) {
                break;
            }
            
            cnt++;
            isCycle[d][x][y] = true;
            // 배열 밖으로 나감
            // -> 반대편으로 순환하는 구조(원형처럼)
            x = (x + dx[d] + R) % R;
            y = (y + dy[d] + C) % C;
            
            // R(오른쪽): 시계 방향
            if (map[x].charAt(y) == 'R') {
                d = (d + 1) % 4;
                // L(왼쪽): 반시계 방향
            } else if (map[x].charAt(y) == 'L') {
                d = (d + 3) % 4;
            }
        }
        
        answer.add(cnt);
    }
}