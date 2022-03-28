import java.util.*;

class sol_lv3_가장먼노드 {
    public int solution(int n, int[][] edge) {
        int answer = 0;
        
        ArrayList<ArrayList<Integer>> al = new ArrayList<>();
        for(int i=0; i<n; i++) al.add(new ArrayList<>()); // 초기화
        
        for(int[] e : edge) {
            int from = e[0] - 1;
            int to = e[1] - 1;
            al.get(from).add(to);
            al.get(to).add(from);
        }
        
        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[n];
        queue.offer(0); // 0번 노드부터 시작 (편의를 위해 0부터 시작)
        visited[0] = true;
        
        while(!queue.isEmpty()) {
            answer = queue.size(); // 너비 우선 접근이므로 level별로 순차 접근하므로 당시 큐크기가 가장 먼 노드들의 개수와 똑같다.
            
            for(int i=0; i<answer; i++) {
                int cur = queue.poll();
                
                for(int next : al.get(cur)) { // 해당 하는 노드의 사이즈 만큼 돈다.
                    if(!visited[next]) {
                        visited[next] = true;
                        queue.offer(next);
                    }
                }
            }
        }
        
        return answer;
    }
}
