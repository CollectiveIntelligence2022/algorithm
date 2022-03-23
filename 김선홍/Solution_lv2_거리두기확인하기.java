import java.util.*;

class Solution {
    public int[] solution(String[][] places) {
        int N = places.length;
        int[] answer = new int[N];
        
        for (int i = 0; i < N; i++) {
            if (bfs(places[i], N)) {
                answer[i] = 1;
            } else {
                answer[i] = 0;
            }
        }
        
        return answer;
    }
    
    public boolean bfs(String[] map, int N) {
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        
        boolean[][] visit = new boolean[N][N];
        ArrayDeque<int[]> q = new ArrayDeque<>();
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i].charAt(j) == 'P' && !visit[i][j]) {
                    visit[i][j] = true;
                    q.offer(new int[] {i, j});
                    int len = 0;
                    
                    while (!q.isEmpty() && ++len <= 2) {
                        int size = q.size();
                        
                        for (int k = 0; k < size; k++) {
                            int[] cur = q.poll();
                            
                            for (int d = 0; d < 4; d++) {
                                int nx = cur[0] + dx[d];
                                int ny = cur[1] + dy[d];
                                
                                if (!isCheck(nx, ny) || visit[nx][ny] || map[nx].charAt(ny) == 'X') {
                                    continue;
                                }
                                
                                if (map[nx].charAt(ny) == 'P') {
                                    return false;
                                }
                                
                                visit[nx][ny] = true;
                                q.offer(new int[] {nx, ny});
                            }
                        }
                    }
                    
                    // 다음 탐색을 위해 큐 비움
                    q.clear();
                }
            }
        }
        
        return true;
    }
    
    public boolean isCheck(int x, int y) {
        return 0 <= x && x < 5 && 0 <= y && y < 5;
    }
}