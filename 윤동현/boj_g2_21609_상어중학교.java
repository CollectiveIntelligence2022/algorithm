import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class boj_g2_21609_상어중학교 {
    // |r1 - r2| + |c1 - c2| = 1을 만족하는 두 칸 (r1, c1)과 (r2, c2)를 인접한 칸 (4방향)
    static int[][] dir = {{1,0},{-1,0},{0,1},{0,-1}};
    static int[][] map;
    static boolean[][] visited;
    static int N, M, er, ec, rainbow, MAX;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken()); // 크기
        M = Integer.parseInt(st.nextToken()); // 색상의 개수

        map = new int[N][N];
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        int result = 0;

        while (true) {
            MAX = Integer.MIN_VALUE;
            er = ec = rainbow = -1; // erase row,col
            visited = new boolean[N][N];
            for(int i=0; i<N; i++) {
                for(int j=0; j<N; j++) {
                    if(map[i][j] <= 0 || visited[i][j]) continue;

                    bfs(i, j, map[i][j]);
                }
            }

            if(MAX == Integer.MIN_VALUE) break;
            result += MAX*MAX;

            visited = new boolean[N][N];
            removeBlock(er, ec, map[er][ec]);
            gravity();
            rotate();
            gravity();

        }

        System.out.println(result);
        br.close();
    }

    private static void rotate() {
        int[][] temp = new int[N][N];

        int jj = N-1;
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                temp[i][j] = map[j][jj];
            }
            jj--;
        }
        map = temp;
    }

    // 격자에 중력이 작용하면 검은색 블록을 제외한 모든 블록이 행의 번호가 큰 칸으로 이동한다.
    // 이동은 다른 블록이나 격자의 경계를 만나기 전까지 계속 된다.
    private static void gravity() {
        for(int j=0; j<N; j++) {
            for(int i=N-2; i>=0; i--) {
                if(map[i][j] > -1) {
                    int k = i;
                    while (k+1 < N) {
                        if(map[k+1][j] != -2) break;
                        k++;
                    }

                    if(k != i) {
                        map[k][j] = map[i][j];
                        map[i][j] = -2;
                    }
                }

            }
        }
    }

    private static void removeBlock(int r, int c, int v) {
        visited[r][c] = true;
        map[r][c] = -2;

        for(int d=0; d<4; d++) {
            int nr = r + dir[d][0];
            int nc = c + dir[d][1];

            // 검은색 블록은 포함 X, 무지개는 노상관
            if(!rangeCheck(nr, nc) && !visited[nr][nc] && (map[nr][nc] == 0 || map[nr][nc] == v)) {
                removeBlock(nr, nc, v);
            }
        }

    }

    private static boolean rangeCheck(int r, int c) {
        return r < 0 || r >= N || c < 0 || c >= N;
    }

    private static void bfs(int r, int c, int v) {
        Queue<int[]> queue = new ArrayDeque<>();
        int cnt = 1;
        int rainbowCnt = 0;

        queue.add(new int[]{r, c});
        visited[r][c] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            for(int d=0; d<4; d++) {
                int nr = cur[0] + dir[d][0];
                int nc = cur[1] + dir[d][1];

                // 검은색 블록은 포함 X, 무지개는 노상관
                if(!rangeCheck(nr, nc) && !visited[nr][nc] && (map[nr][nc] == 0 || map[nr][nc] == v)) {
                    if(map[nr][nc] == 0) rainbowCnt++;
                    queue.offer(new int[]{nr, nc});
                    visited[nr][nc] = true;
                    cnt++;
                }
            }
        }

        // 블록 그룹은 적어도 일반블록 1개이상 && 일반블록 색은 모두 같아야함
        if(cnt < 2) return;

        // 블록 그룹의 기준 블록은 무지개 블록이 아닌 블록 중에서
        if(MAX < cnt) {
            er = r;
            ec = c;
            MAX = cnt;
            rainbow = rainbowCnt;
        }else if(MAX == cnt) {
            if(rainbow < rainbowCnt) {
                er = r;
                ec = c;
                rainbow = rainbowCnt;
            }else if(rainbow == rainbowCnt) {
                if(er < r) { // 행의 번호가 가장 작은 블록,
                    er = r;
                    ec = c;
                }else if(r == er) {
                    if(ec < c) { // 그러한 블록이 여러개면 열의 번호가 가장 작은 블록이다.
                        er = r;
                        ec = c;
                    }
                }
            }
        }

        // 다음 순서에서 0인것을 사용하기 위해
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                if(map[i][j] == 0) visited[i][j] = false;
            }
        }
    }


}