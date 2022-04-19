import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class boj_g4_미세먼지안녕 {

    static int[][] map;
    static int R, C, T, top[], down[];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        map = new int[R][C];
        for(int i=0; i<R; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<C; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] == -1) {
                    if(top == null) {
                        top = new int[]{i,j};
                    }else {
                        down = new int[]{i,j};
                    }
                }
            }
        }

        while(T-- > 0) {
            spreadDust();
            operateCleaner();
        }

        System.out.println(cntDust());
        br.close();
    }

    static boolean rangeCheck(int r, int c) {
        return r < 0 || r >= R || c < 0 || c >= C;
    }

    static void spreadDust() {
        int[][] dir = {{1,0},{-1,0},{0,1},{0,-1}};
        int[][] temp = new int[R][C];
        temp[top[0]][top[1]] = -1;
        temp[down[0]][down[1]] = -1;

        for(int i=0; i<R; i++) {
            for(int j=0; j<C; j++) {
                if(map[i][j] < 1) continue;

                // (r, c)에 있는 미세먼지는 인접한 네 방향으로 확산된다.
                int cnt = 0;

                // 확산되는 양은 Ar,c/5이고 소수점은 버린다.
                int volume = map[i][j] / 5;

                for(int d=0; d<4; d++) {
                    int nr = i + dir[d][0];
                    int nc = j + dir[d][1];

                    // 인접한 방향에 공기청정기가 있거나, 칸이 없으면 그 방향으로는 확산이 일어나지 않는다.
                    if(rangeCheck(nr,nc) || map[nr][nc] == -1) continue;

                    temp[nr][nc] += volume;
                    cnt++;
                }

                // (r, c)에 남은 미세먼지의 양은 Ar,c - (Ar,c/5)×(확산된 방향의 개수) 이다.
                temp[i][j] += map[i][j] - (volume * cnt);
            }
        }

        map = temp;
    }

    static void operateCleaner() {
        int[][] temp = new int[R][C];
        for(int i=0; i<R; i++) {
            temp[i] = map[i].clone();
        }
        // top - 반시계
        int r = top[0];
        int c = top[1] + 1; // 청소기 우측에서 시작
        temp[r][c] = 0; // 청소기가 밀어내므로 무조건 0
        while(c+1 < C) temp[r][c+1] = map[r][c++]; // 우
        while(r-1 > -1) temp[r-1][c] = map[r--][c]; // 상
        while(c-1 > -1) temp[r][c-1] = map[r][c--]; // 좌
        while(r+1 < top[0]) temp[r+1][c] = map[r++][c]; // 하

        // down - 시계 방향
        r = down[0];
        c = down[1] + 1;
        temp[r][c] = 0; // 청소기 우측에서 시작
        while(c+1 < C) temp[r][c+1] = map[r][c++]; // 우
        while(r+1 < R) temp[r+1][c] = map[r++][c]; // 하
        while(c-1 > -1) temp[r][c-1] = map[r][c--]; // 좌
        while(r-1 > down[0]) temp[r-1][c] = map[r--][c]; // 상

        map = temp;
    }

    static int cntDust() {
        int cnt = 0;
        for(int i=0; i<R; i++) {
            for(int j=0; j<C; j++) {
                if(map[i][j] <= 0) continue;
                cnt += map[i][j];
            }
        }
        return cnt;
    }
}
