import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class boj_g5_연구소 {

    static int[][] dir = {{1,0},{-1,0},{0,1},{0,-1}};
    static int N, M, result;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        int[][] map = new int[N][M];
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        result = Integer.MIN_VALUE;
        perm(0, map);

        System.out.println(result);
        br.close();
    }

    private static void perm(int cnt, int[][] map) {
        if(cnt == 3) {
            result = Math.max(result, safeArea(map));
            return;
        }

        for(int i = 0; i<N*M; i++) {
            int r = i / M;
            int c = i % M;

            if(map[r][c] == 0) {
                map[r][c] = 1;
                perm(cnt+1, map);
                map[r][c] = 0;
            }
        }
    }

    private static int safeArea(int[][] map) {
        Queue<int[]> queue = new ArrayDeque<>();
        int[][] tmap = new int[N][M];

        for(int i=0; i<N; i++) {
            for(int j=0; j<M; j++) {
                tmap[i][j] = map[i][j];
                if(tmap[i][j] == 2) {
                    queue.add(new int[]{i,j});
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            for(int d=0; d<4; d++) {
                int nr = cur[0] + dir[d][0];
                int nc = cur[1] + dir[d][1];

                if(rangeCheck(nr, nc) || tmap[nr][nc] != 0) continue;

                tmap[nr][nc] = 2;
                queue.add(new int[]{nr, nc});
            }
        }

        int cnt = 0;
        for(int i=0; i<N*M; i++) {
            int r = i / M;
            int c = i % M;

            if(tmap[r][c] == 0) cnt++;
        }

        return cnt;
    }

    private static boolean rangeCheck(int nr, int nc) {
        return nr < 0 || nr >= N || nc < 0 || nc >=M;
    }
}
