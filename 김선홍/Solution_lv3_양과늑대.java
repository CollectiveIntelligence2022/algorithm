import java.util.*;

class Solution_lv3_양과늑대 {
    List<Integer>[] tree;
    // [현재 위치에서 데리고 있는][양의 수][늑대 수] = 방문 여부
    boolean[][][] visit = new boolean[17][17][17];
    int max;
    
    @SuppressWarnings("unchecked")
	public int solution(int[] info, int[][] edges) {
        int size = info.length;
        tree = new ArrayList[size];
        
        for (int i = 0; i < size; i++) {
            tree[i] = new ArrayList<>();
        }
        
        for (int i = 0; i < edges.length; i++) {
            tree[edges[i][0]].add(edges[i][1]);
            tree[edges[i][1]].add(edges[i][0]);
        }

        dfs(info, 0, 1, 0);
        return max;
    }
    
    // dfs로 탐색
    // info[x] = 0(양), 1(늑대), 2(양 또는 늑대 카운팅완료)
    public void dfs(int[] info, int cur, int sheepCnt, int wolfCnt) {
        if (sheepCnt <= wolfCnt) {
            return;
        }
        
        // 돌아올 때마다 최댓값 갱신
        if (cur == 0) {
            max = Math.max(max, sheepCnt);
        }
        
        int pre = info[cur];
        info[cur] = 2;
        visit[cur][sheepCnt][wolfCnt] = true;
        
        for (int nxt : tree[cur]) {
            // 양이 있는 노드
            if (info[nxt] == 0 && !visit[nxt][sheepCnt + 1][wolfCnt]) {
                dfs(info, nxt, sheepCnt + 1, wolfCnt);
                
                // 늑대가 있는 노드
            } else if (info[nxt] == 1 && !visit[nxt][sheepCnt][wolfCnt + 1]) {
                dfs(info, nxt, sheepCnt, wolfCnt + 1);
                
                // 이미 카운팅한 노드
            } else if (info[nxt] == 2 && !visit[nxt][sheepCnt][wolfCnt]) {
                dfs(info, nxt, sheepCnt, wolfCnt);
            }
        }
        
        info[cur] = pre;
        visit[cur][sheepCnt][wolfCnt] = false;
    }
}