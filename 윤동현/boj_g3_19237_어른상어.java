import java.io.*;
import java.util.*;

public class boj_g3_19237_어른상어 {

    static class Smell {
        int idx, cnt;

        public Smell(int idx, int cnt) {
            this.idx = idx;
            this.cnt = cnt;
        }
    }

    static Smell[][] sMap;
    static int[][][] dirPriority;
    // 방향 위,아래,왼쪽,오른쪽
    static int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static int[][] map;
    static int N, M, k, sharkCnt, sharkDir[];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        map = new int[N][N]; // 실제 map 과 상어 이동경로 idx로 관리
        sMap = new Smell[N][N];
        sharkDir = new int[M + 1]; // 상어의 현재 방향 (idx 로 관리하므로 +1)
        dirPriority = new int[M + 1][4][4]; // 상어의 방향에대한 우선순위 (idx로 관리하므로 +1)
        sharkCnt = M;

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] != 0) {
                    // 상어 생성
                    sMap[i][j] = new Smell(map[i][j], k);
                }
            }
        }

        // 상어의 방향 상태
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= M; i++) {
            sharkDir[i] = Integer.parseInt(st.nextToken()) - 1;
        }

        // 상어마다 우선순위 입력
        for (int i = 1; i <= M; i++) {
            for (int j = 0; j < 4; j++) {
                st = new StringTokenizer(br.readLine(), " ");
                for (int k = 0; k < 4; k++) {
                    dirPriority[i][j][k] = Integer.parseInt(st.nextToken()) - 1;
                }
            }
        }

        int answer = 0;
        while (++answer <= 1000) {
            moveShark();
            smellDown();

            if (sharkCnt == 1) break;
        }
        System.out.println((answer > 1000) ? -1 : answer);
        br.close();
    }

    private static void smellDown() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (sMap[i][j] == null) continue;
                if (--sMap[i][j].cnt == 0)
                    sMap[i][j] = null;
            }
        }
    }

    private static boolean rangeCheck(int r, int c) {
        return (r < 0 || r >= N || c < 0 || c >= N);
    }

    private static void moveShark() {
        Queue<int[]> queue = new ArrayDeque<>();

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (map[i][j] == 0) continue;

                int nr, nc, nd;
                boolean noSmell = false;
                int s = map[i][j];

                for (int d = 0; d < 4; d++) {
                    nd = dirPriority[s][sharkDir[s]][d];
                    nr = i + dir[nd][0];
                    nc = j + dir[nd][1];

                    if (rangeCheck(nr, nc) || sMap[nr][nc] != null) continue;

                    noSmell = true;
                    queue.offer(new int[]{nr, nc, nd, s});
                    map[i][j] = 0;
                    break;
                }

                if (noSmell) continue;

                for (int d = 0; d < 4; d++) {
                    nd = dirPriority[s][sharkDir[s]][d];
                    nr = i + dir[nd][0];
                    nc = j + dir[nd][1];

                    if (rangeCheck(nr, nc) || sMap[nr][nc].idx != s) continue;

                    map[i][j] = 0;
                    queue.offer(new int[]{nr, nc, nd, s});
                    break;
                }

            }
        }

        while (!queue.isEmpty()) {
            int[] temp = queue.poll();

            int r = temp[0];
            int c = temp[1];
            int d = temp[2];
            int s = temp[3];

            if (map[r][c] > 0) {
                sharkCnt--;
                if (map[r][c] < s) continue;
            }

            map[r][c] = s;
            sharkDir[s] = d;
            sMap[r][c] = new Smell(map[r][c], k + 1);
        }
    }
}