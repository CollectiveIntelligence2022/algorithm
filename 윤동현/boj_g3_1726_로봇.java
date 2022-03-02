import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class boj_g3_1726_로봇 {

    static int[][] map;
    // 0 동쪽, 1 서쪽, 2 남쪽, 3 북쪽
    static int[][] dir = {{0,1}, {0,-1}, {1,0}, {-1,0}};
    static int[] start, end;
    static int N,M;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = parseInt(st.nextToken());
        M = parseInt(st.nextToken());

        map = new int[N][M];
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<M; j++) {
                map[i][j] = parseInt(st.nextToken());
            }
        }

        start = new int[4];
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<3; i++) {
            start[i] = parseInt(st.nextToken()) - 1;
        }
        start[3] = 0; // bfs에서 바로 쓰기 위해

        end = new int[3];
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<3; i++) {
            end[i] = parseInt(st.nextToken()) - 1;
        }

        bfs();

        br.close();
    }

    private static int parseInt(String s) {
        return Integer.parseInt(s);
    }

    private static boolean arriveCheck(int[] cur) {
        return (cur[0] == end[0] && cur[1] == end[1] && cur[2] == end[2]);
    }

    private static boolean rangeCheck(int r, int c) {
        return (r < 0 || r >= N || c < 0 || c >= M);
    }

    // 좌표 및 방향까지 고려하여야 하니깐 3차원 visited 배열로 관리하자
    private static void bfs() {
// 거리 기준 queue이므로 최소값인것을 보장 못함.. 같은 count값일때 거리값 가까운거 기준으로 헀으면 됬을꺼같다.
//        PriorityQueue<int[]> queue = new PriorityQueue<>(new Comparator<int[]>() {
//            @Override
//            public int compare(int[] o1, int[] o2) {
//                int dis = Math.abs(o1[0]-end[0]) + Math.abs(o1[1]-end[1]);
//                int dis2 = Math.abs(o2[0]-end[0]) + Math.abs(o2[1]-end[1]);
//                // 거리 도착지점과의 끝점과 가까운것을 먼저 하겠다.
//                return Integer.compare(dis, dis2);
//            }
//        });
        Queue<int[]> queue = new ArrayDeque<>();

        boolean[][][] visited = new boolean[N][M][4];
        queue.offer(start);
        visited[start[0]][start[1]][start[2]] = true;

        while(!queue.isEmpty()) {
            int[] cur = queue.poll();
            int d = cur[2];

            if(arriveCheck(cur)) {
                System.out.println(cur[3]);
                break;
            }

            // k는 1, 2 또는 3일 수 있다. 현재 향하고 있는 방향으로 k칸 만큼 움직인다.
            for(int k=1; k<=3; k++) {
                int nr = cur[0] + dir[d][0] * k;
                int nc = cur[1] + dir[d][1] * k;

                if(rangeCheck(nr, nc) || map[nr][nc] == 1) break;
                if(visited[nr][nc][d]) continue;

                visited[nr][nc][d] = true;
                queue.offer(new int[]{nr, nc, d, cur[3] + 1});
            }

            // 왼쪽 또는 오른쪽으로 90° 회전
            if(d < 2) { // 동쪽 서쪽일때는 무조건 남북으로만 회전함
                if(!visited[cur[0]][cur[1]][2]) {
                    visited[cur[0]][cur[1]][2] = true;
                    queue.offer(new int[]{cur[0], cur[1], 2, cur[3] + 1});
                }
                if(!visited[cur[0]][cur[1]][3]) {
                    visited[cur[0]][cur[1]][3] = true;
                    queue.offer(new int[]{cur[0], cur[1], 3, cur[3] + 1});
                }
            }else { // 남북일때는 동서로만 회전
                if(!visited[cur[0]][cur[1]][0]) {
                    visited[cur[0]][cur[1]][0] = true;
                    queue.offer(new int[]{cur[0], cur[1], 0, cur[3] + 1});
                }
                if(!visited[cur[0]][cur[1]][1]) {
                    visited[cur[0]][cur[1]][1] = true;
                    queue.offer(new int[]{cur[0], cur[1], 1, cur[3] + 1});
                }
            }
        }
    }
}