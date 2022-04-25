import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class boj_g3_23288_주사위굴리기2 {

    static int[][] dir = {{0,1},{1,0},{0,-1},{-1,0}};
    static int[][] map;
    static int[] dice;
    static int N, M, dr, dc, d;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // [ 위,dice[0] ] || [ 북,dice[1] ] || [ 동,dice[2] ]
        // [ 서,dice[3] ] || [ 남,dice[4] ] || [ 아래,dice[5] ]
        dice = new int[6];
        for(int i=1; i<=6; i++) dice[i-1] = i;

        // 가장 처음에 주사위의 이동 방향은 동쪽이다.
        dr = dc = d = 0;

        int result = 0;

        while (K-- > 0) {
            // 이동 방향으로 한 칸 굴러간다.
            int nr = dr + dir[d][0];
            int nc = dc + dir[d][1];

            if(rangeCheck(nr,nc)) { // 만약, 이동 방향에 칸이 없다면, 이동 방향을 반대로 한 다음 한 칸 굴러간다.
                d = (d + 2) % 4;
                nr = dr + dir[d][0];
                nc = dc + dir[d][1];
            }

            switch (d) {
                case 0:
                    rotateEast();
                    break;
                case 1:
                    rotateSouth();
                    break;
                case 2:
                    rotateWest();
                    break;
                case 3:
                    rotateNorth();
                    break;
                default: break;
            }

            dr = nr;
            dc = nc;
            int v = map[dr][dc];
            result += bfs(dr, dc, v); //주사위가 도착한 칸 (x, y)에 대한 점수를 획득

            // 아랫면에 있는 정수 A와 주사위가 있는 칸 (x, y)에 있는 정수 B를 비교
            // A > B인 경우 이동 방향을 90도 시계 방향
            // A < B인 경우 이동 방향을 90도 반시계 방향
            // A = B인 경우 이동 방향에 변화는 없다
            if (dice[5] > v) d = (d + 1) % 4;
            else if (dice[5] < v) {
                if (d == 0) d = 3;
                else d = ( d - 1 ) % 4;
            }
        }

        System.out.println(result);
        br.close();
    }

    static int bfs(int r, int c, int v) {
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[N][M];

        queue.offer(new int[]{r,c});
        visited[r][c] = true;

        int cnt = 1;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            for(int d=0; d<4; d++) {
                int nr = cur[0] + dir[d][0];
                int nc = cur[1] + dir[d][1];

                if(rangeCheck(nr, nc) || visited[nr][nc] || map[nr][nc] != v) continue;

                queue.offer(new int[]{nr, nc});
                visited[nr][nc] = true;
                cnt++;
            }
        }

        return cnt * v;
    }

    static boolean rangeCheck(int r, int c) {
        return r < 0 || r >= N || c < 0 || c >= M;
    }

    static void rotateEast() {
        int tmp = dice[0];
        dice[0] = dice[3];
        dice[3] = dice[5];
        dice[5] = dice[2];
        dice[2] = tmp;
    }

    static void rotateWest() {
        int tmp = dice[0];
        dice[0] = dice[2];
        dice[2] = dice[5];
        dice[5] = dice[3];
        dice[3] = tmp;
    }

    static void rotateSouth() {
        int tmp = dice[0];
        dice[0] = dice[1];
        dice[1] = dice[5];
        dice[5] = dice[4];
        dice[4] = tmp;
    }

    static void rotateNorth() {
        int tmp = dice[0];
        dice[0] = dice[4];
        dice[4] = dice[5];
        dice[5] = dice[1];
        dice[1] = tmp;
    }

}