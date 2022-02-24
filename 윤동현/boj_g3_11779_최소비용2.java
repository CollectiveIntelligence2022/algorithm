import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class boj_g3_11779_최소비용2 {

    static final int INF = Integer.MAX_VALUE;
    // 1000*1000은 충분히 배열로 가능할꺼라 생각
    static int[][] map;
    static int[] dist, path;
    static boolean[] visited;
    static int N,M,start,end;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        N = parseInt(br.readLine());
        M = parseInt(br.readLine());

        map = new int[N][N];
        // 그래프 초기화
        for(int[] val : map) {
            Arrays.fill(val, INF);
        }

        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            int from = parseInt(st.nextToken()) - 1;
            int to = parseInt(st.nextToken()) - 1;
            int cost = parseInt(st.nextToken());
            if(map[from][to] != INF) {
                map[from][to] = Math.min(cost, map[from][to]);
                continue;
            }
            map[from][to] = cost;
        }

        st = new StringTokenizer(br.readLine());
        start = parseInt(st.nextToken()) - 1;
        end = parseInt(st.nextToken()) - 1;

        dijkstra();
        br.close();
    }

    private static int parseInt(String s) { return Integer.parseInt(s); }

    private static void dijkstra() {
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return Integer.compare(o1[1], o2[1]);
            }
        });

        dist = new int[N];
        path = new int[N];
        visited = new boolean[N];

        // 초기화 및 시작지점 초기화
        Arrays.fill(dist, INF);
        dist[start] = 0;
        path[start] = start;
        pq.offer(new int[]{start, 0});

        while(!pq.isEmpty()) {
            int[] cur = pq.poll();
            int minIdx = cur[0];
            int min = cur[1];

            if(visited[minIdx]) continue;
            if(minIdx == end) break;

            visited[minIdx] = true;

            for(int i=0; i<N; i++) {
                if(!visited[i] && map[minIdx][i] != INF
                        && (min + map[minIdx][i] < dist[i])) {
                    dist[i] = min + map[minIdx][i];
                    path[i] = minIdx;
                    pq.offer(new int[]{i, dist[i]});
                }
            }
        }
        System.out.println(dist[end]);

        Stack<Integer> stack = new Stack<>();
        stack.push(end);
        int preIdx = path[end];
        while(start != preIdx) {
            stack.push(preIdx);
            preIdx = path[preIdx];
        }
        stack.push(preIdx);
        System.out.println(stack.size());
        while(!stack.isEmpty()) System.out.print((stack.pop()+1)+" ");
    }
}
