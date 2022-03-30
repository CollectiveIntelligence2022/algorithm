import java.util.*;

class sol_lv3_순위 {
    
    ArrayList<ArrayList<Integer>> order;
    ArrayList<ArrayList<Integer>> reverse;
    int[] cnt;
    
    public int solution(int n, int[][] results) {
        order = new ArrayList<>();
        reverse = new ArrayList<>();
        
        for(int i=0; i<n; i++) {
            order.add(new ArrayList<>());
            reverse.add(new ArrayList<>());
        }
        cnt = new int[n];
        
        for(int[] result : results) {
            int from = result[0] - 1;
            int to = result[1] - 1;
            order.get(from).add(to);
            reverse.get(to).add(from);
        }
        
        for(int i=0; i<n; i++) {
            dfs(i, new boolean[n], order);
            dfs(i, new boolean[n], reverse);
        }
        
        int answer = 0;
        
        for(int v : cnt) {
            if(v == n-1) answer++;
        }
        
        return answer;
    }
    
    private void dfs(int n, boolean[] visited, ArrayList<ArrayList<Integer>> list) {
        visited[n] = true;
        
        for(int next : list.get(n)) {
            if(visited[next]) continue;
            cnt[next]++;
            dfs(next, visited, list);
        }
    }
}
