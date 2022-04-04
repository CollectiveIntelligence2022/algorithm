import java.util.*;

class Solution {
    static int maxCnt;
    static int[] Info;
    static ArrayList<Integer>[] nodes;
    public int solution(int[] info, int[][] edges) {
        Info = info;
        nodes = new ArrayList[Info.length];
        for (int[] e : edges) {
            // e[0] : 부모
            // e[1] : 자식
            if (nodes[e[0]] == null)
                nodes[e[0]] = new ArrayList<>();
            nodes[e[0]].add(e[1]);
        }
        
        List<Integer> list = new ArrayList<>();
        list.add(0);
        dfs(0, 0, 0, list);
        
        return maxCnt;
    }
    
    private void dfs(int idx, int sheepCnt, int wolfCnt, List<Integer> nextList) {
        // 양 늑대 카운트
        if (Info[idx] == 0)
            sheepCnt++;
        else
            wolfCnt++;
        
        // 종료 조건
        if (wolfCnt >= sheepCnt)
            return;
        
        maxCnt = Math.max(maxCnt, sheepCnt);
        
        // 다음 탐색 인덱스 갱신
        List<Integer> list = new ArrayList<>();
        list.addAll(nextList);
        
        list.remove(Integer.valueOf(idx));
        if (nodes[idx] != null) {
            for (int next : nodes[idx])
                list.add(next);
        }
        
        // dfs
        for (int next : list) {
            dfs(next, sheepCnt, wolfCnt, list);
        }
    }
}

// 하나 방문할 때마다 다음 방문 가능한 노드 갱신하면서 재귀.. dfs?