import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class boj_g3_19238_스타트택시 {

    static class Client {
        int fr,fc,tr,tc; // f : from, t : to

        public Client(int fr, int fc, int tr, int tc) {
            this.fr = fr;
            this.fc = fc;
            this.tr = tr;
            this.tc = tc;
        }
    }

    static Client[] clients;
    static int[][] dir = {{1,0},{-1,0},{0,1},{0,-1}};
    static int[][] map;
    static int N, M, K, taxi[];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken()); // map크기
        M = Integer.parseInt(st.nextToken()); // 승객의 수
        K = Integer.parseInt(st.nextToken()); // 현재 연료의 양

        map = new int[N][N];
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        taxi = new int[]{Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1};

        clients = new Client[M];
        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            int fr = Integer.parseInt(st.nextToken()) - 1;
            int fc = Integer.parseInt(st.nextToken()) - 1;
            int tr = Integer.parseInt(st.nextToken()) - 1;
            int tc = Integer.parseInt(st.nextToken()) - 1;

            map[fr][fc] = -1;
            clients[i] = new Client(fr, fc, tr, tc);
        }

        while (M > 0) {
            int[] client = findClient();
            int clientIdx = getClientIdx(client[0], client[1]);

            if(K - client[2] < 0) { // 애초에 도착할 수 없으면 떙
                K = -1;
                break;
            }

            // 택시 위치 수정
            taxi[0] = client[0];
            taxi[1] = client[1];
            int tr = clients[clientIdx].tr;
            int tc = clients[clientIdx].tc;

            int to_dist = arrival(tr, tc); // 도착지까지의 거리 구하기

            if(K - client[2] - to_dist < 0 || to_dist == -1) {
                K = -1;
                break;
            }

            K = K - client[2] - to_dist + (to_dist * 2);
            map[clients[clientIdx].fr][clients[clientIdx].fc] = 0;
            taxi[0] = clients[clientIdx].tr;
            taxi[1] = clients[clientIdx].tc;
            M--;
            clients[clientIdx] = null;
        }

        System.out.println(K);
        br.close();
    }

    private static int arrival(int to_r, int to_c) {
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[N][N];

        queue.offer(new int[]{taxi[0], taxi[1], 0}); // 택시좌표, 길이 0
        visited[taxi[0]][taxi[1]] = true;

        int dist = -1;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            if(cur[0] == to_r && cur[1] == to_c) {
                dist = cur[2];
                break;
            }

            for(int d=0; d<4; d++) {
                int nr = cur[0] + dir[d][0];
                int nc = cur[1] + dir[d][1];

                if(rangeCheck(nr, nc) || visited[nr][nc] || map[nr][nc] == 1) continue;

                queue.offer(new int[]{nr, nc, cur[2] + 1});
                visited[nr][nc] = true;
            }
        }

        // 큐가 목적지 도착못했는데 못가는 경우 -1 리턴
        return dist;
    }

    private static int getClientIdx(int r, int c) {
        int i = 0;
        for(; i<clients.length; i++) {
            if(clients[i] == null) continue;
            if(clients[i].fr == r && clients[i].fc == c) {
                break;
            }
        }
        return i;
    }

    private static boolean rangeCheck(int r, int c) {
        return r < 0 || r >= N || c < 0 || c >= N;
    }

    private static int[] findClient() {
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[N][N];

        queue.offer(new int[]{taxi[0], taxi[1], 0}); // 택시좌표, 길이 0
        visited[taxi[0]][taxi[1]] = true;

        int cnt = 0;
        int min_r = N, min_c = N, min = Integer.MAX_VALUE;
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            if(map[cur[0]][cur[1]] == -1) {
                cnt++;
                if(cur[2] < min) { // 현재 위치에서 최단거리가 가장 짧은 승객을 고른다.
                    min = cur[2];
                    min_r = cur[0];
                    min_c = cur[1];
                } else if(cur[2] == min) {
                    if(cur[0] < min_r) { // 그런 승객이 여러 명이면 그중 행 번호가 가장 작은 승객을,
                        min_r = cur[0];
                        min_c = cur[1];
                    }else if(cur[0] == min_r) { // 그런 승객도 여러 명이면 그중 열 번호가 가장 작은 승객을 고른다.
                        if(cur[1] < min_c) {
                            min_r = cur[0];
                            min_c = cur[1];
                        }
                    }
                }

                if(cnt == M) break;
            }

            for(int d=0; d<4; d++) {
                int nr = cur[0] + dir[d][0];
                int nc = cur[1] + dir[d][1];

                if(rangeCheck(nr, nc) || visited[nr][nc] || map[nr][nc] == 1) continue;

                queue.offer(new int[]{nr, nc, cur[2] + 1});
                visited[nr][nc] = true;
            }
        }

        return new int[]{min_r, min_c, min}; // 제일 가까운 승객 좌표 + 최단거리까지
    }

}