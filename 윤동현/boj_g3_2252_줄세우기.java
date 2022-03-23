import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

// https://bcp0109.tistory.com/21 위상정렬 참고
public class boj_g3_2252_줄세우기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[] indegree = new int[N+1]; // 자기 자신을 가리키는 간선 갯수
        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        for(int i=0; i<N+1; i++) {
            graph.add(new ArrayList<Integer>());
        }

        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            graph.get(from).add(to);
            indegree[to]++;
        }

        // 위상정렬
        Queue<Integer> queue = new ArrayDeque<>();
        Queue<Integer> result = new ArrayDeque<>();

        for(int i=1; i<=N; i++) {
            if(indegree[i] == 0) queue.offer(i);
        }

        while(!queue.isEmpty()) {
            int cur = queue.poll();
            result.offer(cur);

            for(int v : graph.get(cur)) {
                indegree[v]--;

                if(indegree[v] == 0) queue.offer(v);
            }
        }

        while(!result.isEmpty()) System.out.print(result.poll()+" ");

        br.close();
    }
}
