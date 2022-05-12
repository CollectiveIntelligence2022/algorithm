import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class 다시_boj_g1_23290_마법사상어와복제 {

    static class Fish {
        int r, c, d;
        Fish(int r, int c, int d){
            this.r = r;
            this.c = c;
            this.d = d;
        }
    }

    final static int MAX = 4;
    static ArrayList<Integer>[][] grid;
    static Deque<Integer>[][] smell;
    static ArrayList<Fish> fishList;
    static boolean[][] visited;
    static int[] dist;
    // ←, ↖, ↑, ↗, →, ↘, ↓, ↙
    static int[][] dir = {{0,-1},{-1,-1},{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1}};
    static int M, S, sx, sy, max, maxPriority;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken()); // 물고기의 수
        S = Integer.parseInt(st.nextToken()); // 마법 연습한 횟수

        grid = new ArrayList[MAX][MAX];
        smell = new LinkedList[MAX][MAX];
        for(int i=0; i<MAX; i++) {
            for(int j=0; j<MAX; j++) {
                grid[i][j] = new ArrayList<>();
                smell[i][j] = new LinkedList<>();
            }
        }

        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());

            int fx = Integer.parseInt(st.nextToken()) - 1;
            int fy = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken()) - 1;

            grid[fx][fy].add(d);
        }

        st = new StringTokenizer(br.readLine());
        sx = Integer.parseInt(st.nextToken()) - 1;
        sy = Integer.parseInt(st.nextToken()) - 1;

        fishList = new ArrayList<Fish>();
        for(int s=0; s<S; s++) {
            copyFish();
            moveFish();
            visited = new boolean[MAX][MAX];
            dist = new int[3];
            moveShark(0, sx, sy, 0, 0, new int[3]);
            System.out.println(Arrays.toString(dist));
        }

        br.close();
    }

    private static int getDir(int p) {
        switch (p) {
            //상은 1, 좌는 2, 하는 3, 우는 4
            case 1: return 2;
            case 2: return 0;
            case 3: return 6;
            case 4: return 4;
        }
        return 0;
    }

    private static void moveShark(int cnt, int r, int c, int val, int priority, int[] moving) {
        if(cnt == 3) {
            if(max < val) {
                max = val;
                maxPriority = priority;
                sx = r;
                sy = c;
                dist = moving;
            }

            return;
        }

        visited[r][c] = true;

        for(int p=1; p<=MAX; p++) {
            int d = getDir(p);
            int nr = r + dir[d][0];
            int nc = c + dir[d][1];

            if(nr < 0 || nr >= MAX || nc < 0 || nc >= MAX) continue;

            int v = (!visited[nr][nc]) ? grid[nr][nc].size() : 0;
            moving[cnt] = d;
            moveShark(cnt+1, nr, nc, val + v, priority * 10 + p, moving);
        }
    }

    private static void moveFish() {
        ArrayList<Integer>[][] tempGrid = new ArrayList[4][4];
        for(int r=0; r<4; r++) {
            for(int c=0; c<4; c++) {
                tempGrid[r][c] = new ArrayList<Integer>();
            }
        }

        for(int r=0; r<4; r++) {
            for(int c=0; c<4; c++) {
                for(int d:grid[r][c]) {
                    int nr = r, nc = c;

                    boolean isMoved = false;
                    for(int i=0; i<8; i++) {
                        int nd = ((d-i) + 8) % 8;
                        nr = r + dir[nd][0];
                        nc = c + dir[nd][1];

                        if(nr < 0 || nr >= MAX || nc < 0 || nc >= MAX) continue;
                        if(smell[nr][nc].size() != 0) continue;
                        if(nr == sx && nc == sy) continue;

                        tempGrid[nr][nc].add(nd);
                        isMoved = true;
                        break;
                    }
                    if(!isMoved) tempGrid[r][c].add(d);
                }
            }
        }

        grid = tempGrid;
    }

    private static void copyFish() {
        fishList.clear();
        for(int i=0; i<MAX; i++) {
            for(int j=0; j<MAX; j++) {
                for(int d : grid[i][j]) fishList.add(new Fish(i, j, d));
            }
        }
    }

}
/*

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class boj_g1_23290_마법사상어와복제 {

    final static int MAX = 4;

    static Stack<int[]> eat;
    static Queue<int[]> clone;
    static ArrayList<int[]> fishes;
    // ←, ↖, ↑, ↗, →, ↘, ↓, ↙
    static int[][] dir = {{0,-1},{-1,-1},{-1,0},{-1,1},{0,1},{1,1},{1,0},{1,-1}};
    static int[][] smell;
    static int M, S, sx, sy, max, maxPriority;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        M = Integer.parseInt(st.nextToken()); // 물고기의 수
        S = Integer.parseInt(st.nextToken()); // 마법 연습한 횟수

        smell = new int[MAX][MAX];
        clone = new ArrayDeque<>();
        fishes = new ArrayList<>();
        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());

            int fx = Integer.parseInt(st.nextToken()) - 1;
            int fy = Integer.parseInt(st.nextToken()) - 1;
            int d = Integer.parseInt(st.nextToken()) - 1;

            fishes.add(new int[]{fx, fy, d});
        }

        st = new StringTokenizer(br.readLine());
        sx = Integer.parseInt(st.nextToken()) - 1;
        sy = Integer.parseInt(st.nextToken()) - 1;

        while (S-- > 0) {

            copy();
            moveFish();
            max = maxPriority = 0;

            for(int i=0; i<MAX; i++) {
                for(int j=0; j<MAX; j++) if(smell[i][j] > 0) smell[i][j]--;
            }

            eat = new Stack<>();
            moveShark(0, sx, sy, 0, 0, new Stack<>(), fishes);

            while (!eat.isEmpty()) {
                int[] cur = eat.pop();
                for(int i=fishes.size()-1; i>=0; i--) {
                    if(fishes.get(i)[0] == cur[0] && fishes.get(i)[1] == cur[1]) {
                        // 상어가 물고기가 있는 같은 칸으로 이동하게 된다면,
                        // 그 칸에 있는 모든 물고기는 격자에서 제외되며, 제외되는 모든 물고기는 물고기 냄새를 남긴다.
                        smell[cur[0]][cur[1]] = 2;
                        fishes.remove(i);
                    }
                }
            }

            // 두 번 전 연습에서 생긴 물고기의 냄새가 격자에서 사라진다.
            // 모든 복제된 물고기는 1에서의 위치와 방향을 그대로 갖게 된다.
            while (!clone.isEmpty()) fishes.add(clone.poll());

        }

        System.out.println(fishes.size());
        br.close();
    }

    //←, ↖, ↑, ↗, →, ↘, ↓, ↙
    private static String aa(int d) {
        switch (d) {
            case 0: return "←";
            case 1: return "↖";
            case 2: return "↑";
            case 3: return "↗";
            case 4: return "→";
            case 5: return "↘";
            case 6: return "↓";
            case 7: return "↙";
        }
        return "";
    }

    private static void copy() {
        for(int[] f : fishes) clone.add(new int[]{f[0], f[1], f[2]});
    }

    private static boolean rangeCheck(int r, int c) {
        return r < 0 || r >= MAX || c < 0 || c >= MAX;
    }

    private static void moveFish() {
        // 모든 물고기가 한 칸 이동한다. -> 이동할 수 있는 칸을 향할 때까지 45도 반시계 회전
        for(int i=0; i<fishes.size(); i++) {

            int nd = fishes.get(i)[2];
            for(int d=0; d<MAX*2-1; d++) { // 한바퀴 다돌아도 못가면 이동X 7번만 돌려본다
                int nr = fishes.get(i)[0] + dir[nd][0];
                int nc = fishes.get(i)[1] + dir[nd][1];

                // 상어가 있는 칸, 물고기의 냄새가 있는 칸, 격자의 범위를 벗어나는 칸으로는 이동할 수 없다.
                if(rangeCheck(nr, nc) || smell[nr][nc] != 0 || (nr == sx && nc == sy)) {
                    nd = (nd == 0) ? 7 : ((nd - 1) % 8);
                    continue;
                }

                fishes.get(i)[0] = nr;
                fishes.get(i)[1] = nc;
                fishes.get(i)[2] = nd;
                break;
            }
            // 만약, 이동할 수 있는 칸이 없으면 이동을 하지 않는다.
            // for문 안에서 값이 바뀌지 않았다면 이동하지 않은것
        }
    }

    private static int findFish(int r, int c, ArrayList<int[]> temp) {
        int cnt = 0;
        for(int i=temp.size()-1; i>=0; i--) {
            if(temp.get(i)[0] == r && temp.get(i)[1] == c) {
                cnt++;
                temp.remove(i);
            }
        }

        return cnt;
    }

    private static int getPriority(int d) {
        switch (d) {
            case 0: return 2;
            case 2: return 1;
            case 4: return 4;
            case 6: return 3;
        }
        return 0;
    }

    private static void moveShark(int cnt, int r, int c, int val, int priority, Stack<int[]> sharkDist, ArrayList<int[]> tFishes) {
        if(cnt == 3) {
            // 제외되는 물고기의 수가 가장 많은 방법으로 이동하며,
            // 그러한 방법이 여러가지인 경우 사전 순으로 가장 앞서는 방법을 이용한다.
            if(max < val) {
                eat = sharkDist;
                max = val;
                maxPriority = priority;
                sx = r;
                sy = c;
            }else if((max == val && priority < maxPriority)) {
                eat = sharkDist;
                max = val;
                maxPriority = priority;
                sx = r;
                sy = c;
            }

            return;
        }

        for(int d=0; d<8; d+=2) {
            int nr = r + dir[d][0];
            int nc = c + dir[d][1];

            // 상어 연속 3칸 이동(상하좌우) - 범위 못넘어감
            if(rangeCheck(nr, nc)) continue;


            Stack<int[]> tempS = new Stack<>();
            for(int[] s : sharkDist) tempS.push(new int[]{s[0], s[1]});

            ArrayList<int[]> tempF = new ArrayList<>();
            for(int[] f : tFishes) tempF.add(new int[]{f[0], f[1], f[2]});

            int value = findFish(nr, nc, tempF);

            if(value > 0) tempS.push(new int[]{nr, nc});
            int p = getPriority(d);

            moveShark(cnt+1, nr, nc, val + value,priority * 10 + p, tempS, tempF);
        }
    }
}



System.out.println();
        String[][] ss = new String[MAX][MAX];

        for(int[] f : fishes) {
            ss[f[0]][f[1]] += aa(f[2]);
        }

        for(int i=0; i<MAX; i++) {
            for(int j=0; j<MAX; j++) System.out.print(ss[i][j]+" \t");
            System.out.println();
        }
        System.out.println((sx+1)+" "+(sy+1));
        for(int i=0; i<MAX; i++) {
            for(int j=0; j<MAX; j++) System.out.print(smell[i][j]+" \t");
            System.out.println();
        }
 */