import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class boj_g1_23290_마법사상어와복제 {

    private final static int MAX = 4;

    static class Map {
        int cnt;
        Queue<Integer> fish;

        public Map(int cnt, Queue<Integer> fish) {
            this.cnt = cnt;
            this.fish = fish;
        }
    }

    static Queue<int[]> copy;
    static Map[][] maps;
    // ←, ↖, ↑, ↗, →, ↘, ↓, ↙
    static int[][] dir = {{0,-1},{-1,-1},{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1}};
    static int[][] sDir = {{0,0},{-1,0},{0,-1},{1,0},{0,1}}; // ↑, ←, ↓, →
    static boolean[][] visited;
    static int[] shark, dist, perm;
    static int M, S, eatCnt, priority;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());

        maps = new Map[MAX][MAX];
        for(int i=0; i<MAX; i++) {
            for(int j=0; j<MAX; j++) maps[i][j] = new Map(0, new ArrayDeque<>());
        }

        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            int fx = Integer.parseInt(st.nextToken()) - 1;
            int fy = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken()) - 1;
            maps[fx][fy].fish.add(d);
        }

        st = new StringTokenizer(br.readLine());
        shark = new int[]{Integer.parseInt(st.nextToken()) - 1, Integer.parseInt(st.nextToken()) - 1};

        copy = new ArrayDeque<>();

        while (S-- > 0) {
            setCopy();
            moveFish();

            dist = new int[3];
            perm = new int[3];
            visited = new boolean[MAX][MAX];
            eatCnt = 0;
            priority = Integer.MAX_VALUE;
            findMoveShark(0, shark[0], shark[1], 0, 0);
            moveShark(shark[0], shark[1]);
            removeSmell();
            getCopy();
        }

        System.out.println(getFishCnt());
        br.close();
    }

    //복제 마법을 시전한다.
    private static void setCopy() {
        copy.clear();
        for(int i=0; i<MAX; i++) {
            for(int j=0; j<MAX; j++) {
                for(int d : maps[i][j].fish) {
                    copy.add(new int[]{i,j,d});
                }
            }
        }
    }

    private static boolean rangeCheck(int r, int c) {
        return r < 0 || r >= MAX || c < 0 || c >= MAX;
    }

    private static void moveFish() {
        Map[][] temp = new Map[MAX][MAX];
        for(int i=0; i<MAX; i++) {
            for(int j=0; j<MAX; j++) {
                temp[i][j] = new Map(maps[i][j].cnt, new ArrayDeque<>());
            }
        }

        for(int i=0; i<MAX; i++) {
            for(int j=0; j<MAX; j++) {
                // 현재 좌표의 물고기들을 꺼낸다
                for(int d : maps[i][j].fish) {
                    boolean isCheck = false;

                    for(int k=0; k<MAX*2; k++) {
                        // 이동할 수 있는 칸을 향할 때까지 방향을 45도 반시계 회전시킨다.
                        int nd = ((d-k) + 8) % 8;
                        int nr = i + dir[nd][0];
                        int nc = j + dir[nd][1];

                        // 상어가 있는 칸, 물고기의 냄새가 있는 칸, 격자의 범위를 벗어나는 칸으로는 이동할 수 없다.
                        if(rangeCheck(nr, nc) || maps[nr][nc].cnt != 0 || (nr == shark[0] && nc == shark[1])) continue;

                        temp[nr][nc].fish.add(nd);
                        isCheck = true;
                        break;
                    }

                    // 만약, 이동할 수 있는 칸이 없으면 이동을 하지 않는다.
                    if(!isCheck) temp[i][j].fish.add(d);
                }
            }
        }

        maps = temp;
    }

    private static void findMoveShark(int cnt, int r, int c, int eat, int p) {
        if(cnt == 3) {
            if(eatCnt < eat) {
                eatCnt = eat;
                priority = p;
                dist = perm.clone();
            }else if(eatCnt == eat && p < priority) {
                priority = p;
                dist = perm.clone();
            }

            return;
        }

        for(int d=1; d<=MAX; d++) {
            int nr = r + sDir[d][0];
            int nc = c + sDir[d][1];

            if(rangeCheck(nr, nc)) continue;

            perm[cnt] = d;
            if(visited[nr][nc]) {
                findMoveShark(cnt+1, nr, nc, eat, p * 10 + d);
            }else {
                visited[nr][nc] = true;
                findMoveShark(cnt+1, nr, nc, eat + maps[nr][nc].fish.size(), p * 10 + d);
                visited[nr][nc] = false;
            }
        }
    }

    private static void moveShark(int r, int c) {
        for(int d : dist) {
            r += sDir[d][0];
            c += sDir[d][1];

            // findMove로 범위 벗어나는거 체크했으므로 바로
            if(maps[r][c].fish.size() != 0) {
                maps[r][c].fish.clear(); // 해당 칸 물고기 없애기
                maps[r][c].cnt = 3; // 물고기 냄새 남기기
            }

            // 상어 위치 조정
            shark[0] = r;
            shark[1] = c;
        }
    }

    private static void removeSmell() {
        for(int i=0; i<MAX; i++) {
            for(int j=0; j<MAX; j++) if(maps[i][j].cnt != 0) maps[i][j].cnt--;
        }
    }

    private static void getCopy() {
        while (!copy.isEmpty()) {
            int[] c = copy.poll();
            maps[c[0]][c[1]].fish.add(c[2]);
        }
    }

    private static int getFishCnt() {
        int fish = 0;
        for(int i=0; i<MAX; i++) {
            for(int j=0; j<MAX; j++) fish += maps[i][j].fish.size();
        }
        return fish;
    }
}
