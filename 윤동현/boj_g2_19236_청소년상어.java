import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class boj_g2_19236_청소년상어 {

    static class Fish {
        int r,c,d;
        boolean alive;

        public Fish(int r, int c, int d, boolean alive) {
            this.r = r;
            this.c = c;
            this.d = d;
            this.alive = alive;
        }
    }

    final static int MAX = 4;

    // ↑, ↖, ←, ↙, ↓, ↘, →, ↗
    static int[][] dir = {{-1,0},{-1,-1},{0,-1},{1,-1},{1,0},{1,1},{0,1},{-1,1}};
    static int result, max;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        Fish[] fish = new Fish[MAX*MAX + 1];
        int[][] map = new int[MAX][MAX];
        int[] shark = null;

        for(int i=0; i<MAX; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < MAX; j++) {
                int num = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken()) - 1;
                fish[num] = new Fish(i,j,d,true);
                map[i][j] = num;
            }
        }

        shark = new int[]{0,0,fish[map[0][0]].d,map[0][0]}; // 좌표, 방향, 먹은값
        fish[map[0][0]].alive = false;
        map[0][0] = -1;

        dfs(shark, map, fish);
        System.out.println(result);

        br.close();
    }

    static boolean rangeCheck(int n, int r) {
        return n < 0 || n >= MAX || r < 0 || r >= MAX;
    }

    static boolean sharkCheck(int[] shark, int[][] map) {
        boolean isCheck = false;
        int n = shark[0];
        int c = shark[1];
        int d = shark[2];

        for(int i=0; i<MAX-1; i++) {
            n = n + dir[d][0];
            c = c + dir[d][1];

            if(rangeCheck(n,c) || map[n][c] == -1) continue;

            isCheck = true;
        }

        return isCheck;
    }

    static void dfs(int[] shark, int[][] map, Fish[] fish) {
        result = Math.max(result, shark[3]);

        // 물고기 이동
        for(int i=1; i<fish.length; i++) {
            if(!fish[i].alive) continue; //죽었으면 체크 X

            int r = fish[i].r;
            int c = fish[i].c;
            int d = fish[i].d;

            for(int k=0; k<8; k++) {
                int nr = r + dir[d][0];
                int nc = c + dir[d][1];

                if(rangeCheck(nr, nc) || map[nr][nc] == -1) {
                    d = (d + 1) % 8;
                    continue;
                }

                if(map[nr][nc] == 0) {
                    map[fish[i].r][fish[i].c] = 0; //기존위치 빈칸처리
                    fish[i].r = nr;
                    fish[i].c = nc;
                    fish[i].d = d;
                    map[nr][nc] = i;
                }else {
                    int to = map[nr][nc]; //바뀔 물고기 숫자
                    fish[to].r = fish[i].r;
                    fish[to].c = fish[i].c;
                    map[fish[to].r][fish[to].c] = to;

                    fish[i].r = nr;
                    fish[i].c = nc;
                    fish[i].d = d;
                    map[nr][nc] = i;
                }
                break;
            }
        }

        // 상어 이동
        int sr = shark[0];
        int sc = shark[1];
        int sd = shark[2];
        int eat = shark[3];

        for(int d=1; d<MAX; d++) {
            int nr = sr + dir[sd][0] * d;
            int nc = sc + dir[sd][1] * d;

            if(!rangeCheck(nr,nc) && map[nr][nc] != 0) {
                int[][] tmap = new int[MAX][MAX];
                for(int i=0; i<MAX; i++) tmap[i] = map[i].clone(); // 깊은 복사

                Fish[] newFish = new Fish[MAX*MAX + 1];
                for(int i=1; i<newFish.length; i++) {
                    newFish[i] = new Fish(fish[i].r, fish[i].c, fish[i].d, fish[i].alive);
                }

                tmap[sr][sc] = 0;
                newFish[tmap[nr][nc]].alive = false;
                int[] newShark = new int[]{nr, nc, newFish[tmap[nr][nc]].d, eat+tmap[nr][nc]};
                tmap[nr][nc] = -1;
                dfs(newShark, tmap, newFish);
            }
        }
    }

}