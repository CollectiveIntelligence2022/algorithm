import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class 다음에_boj_g1_21611_마법사상어와블리자드 {

    static ArrayList<Integer> al;
    static int[][] map;
    static int[][] dir = {{0,-1},{1,0},{0,1},{-1,0}};
    static int[][] sDir = {{-1,0},{1,0},{0,-1},{0,1}};
    static int[] boomCnt;
    static int N, M, sr, sc;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        for(int i=0; i<N; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        boomCnt = new int[4];
        sr = sc = N/2;
        for(int i=0; i<M; i++) {
            st = new StringTokenizer(br.readLine());
            int d = Integer.parseInt(st.nextToken()) - 1;
            int s = Integer.parseInt(st.nextToken());

            dropIce(d, s);
            al = new ArrayList<>();
            move();
            int size = al.size();
            while (true) {
                int size2 = move2();
                if(size == size2) break;
                size = size2;
            }
            grouping();
        }

        System.out.println();
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }

        int sum = 0;
        for(int i=1; i<=3; i++) {
            sum += boomCnt[i] * i;
        }
        System.out.println(sum);
        br.close();
    }

    private static void dropIce(int d, int s) {
        int nr = sr;
        int nc = sc;
        for(int k=0; k<s; k++) {
            nr += sDir[d][0];
            nc += sDir[d][1];

            map[nr][nc] = 0;
        }
    }

    private static void move() {
        int nr = sr;
        int nc = sc;
        int moveCnt = 0;
        int size = 1;
        int nd = 0;
        while (true) {
            if(moveCnt == 2) {
                moveCnt = 0;
                size++;
            }

            boolean isCheck = false;
            for(int k=0; k<size; k++) {
                nr += dir[nd][0];
                nc += dir[nd][1];
                if(nr == 0 && nc == -1) {
                    isCheck = true;
                    break;
                }
                if(map[nr][nc] != 0) {
                    al.add(map[nr][nc]);
                    map[nr][nc] = 0;
                }
            }

            if(isCheck) break;

            nd = (nd + 1) % 4;
            moveCnt++;
        }


        for(int j=al.size()-1; j>=3; j--) {
            int cnt = 0;
            int index = j - 1;
            for(int k=0; k<j; k++) {
                if(al.get(j) != al.get(index)) break;
                cnt++; index--;
            }

            if(cnt < 3) continue;

            boomCnt[al.get(j)] += cnt+1;
            for(int k=0; k<cnt+1; k++) {
                al.remove(j--);
            }
            j++;
        }

        nr = sr;
        nc = sc;
        moveCnt = 0;
        size = 1;
        nd = 0;
        int idx = 0;

        while (true) {
            if(moveCnt == 2) {
                moveCnt = 0;
                size++;
            }

            boolean isCheck = false;

            for(int k=0; k<size; k++) {
                nr += dir[nd][0];
                nc += dir[nd][1];
                if(idx == al.size() || (nr == 0 && nc == -1)) {
                    isCheck = true;
                    break;
                }
                map[nr][nc] = al.get(idx++);
            }

            if(isCheck) break;

            nd = (nd + 1) % 4;
            moveCnt++;
        }
    }

    private static int move2() {
        al.clear();

        int nr = sr;
        int nc = sc;
        int moveCnt = 0;
        int size = 1;
        int nd = 0;
        while (true) {
            if(moveCnt == 2) {
                moveCnt = 0;
                size++;
            }

            boolean isCheck = false;
            for(int k=0; k<size; k++) {
                nr += dir[nd][0];
                nc += dir[nd][1];
                if(nr == 0 && nc == -1) {
                    isCheck = true;
                    break;
                }
                if(map[nr][nc] != 0) {
                    al.add(map[nr][nc]);
                    map[nr][nc] = 0;
                }
            }

            if(isCheck) break;

            nd = (nd + 1) % 4;
            moveCnt++;
        }

        for(int j=al.size()-1; j>=3; j--) {
            int cnt = 0;
            int index = j - 1;
            for(int k=0; k<j; k++) {
                if(al.get(j) != al.get(index)) break;
                cnt++; index--;
            }

            if(cnt < 3) continue;

            boomCnt[al.get(j)] += cnt+1;
            for(int k=0; k<cnt+1; k++) {
                al.remove(j--);
            }
            j++;
        }

        nr = sr;
        nc = sc;
        moveCnt = 0;
        size = 1;
        nd = 0;
        int idx = 0;

        while (true) {
            if(moveCnt == 2) {
                moveCnt = 0;
                size++;
            }

            boolean isCheck = false;

            for(int k=0; k<size; k++) {
                nr += dir[nd][0];
                nc += dir[nd][1];
                if(idx == al.size() || (nr == 0 && nc == -1)) {
                    isCheck = true;
                    break;
                }
                map[nr][nc] = al.get(idx++);
            }

            if(isCheck) break;

            nd = (nd + 1) % 4;
            moveCnt++;
        }

        return al.size();
    }

    private static void grouping() {
        al.clear();
        Queue<Integer> queue = new ArrayDeque<>();

        int nr = sr;
        int nc = sc;
        int moveCnt = 0;
        int size = 1;
        int nd = 0;
        while (true) {
            if(moveCnt == 2) {
                moveCnt = 0;
                size++;
            }

            boolean isCheck = false;
            for(int k=0; k<size; k++) {
                nr += dir[nd][0];
                nc += dir[nd][1];
                if(nr == 0 && nc == -1) {
                    isCheck = true;
                    break;
                }
                if(map[nr][nc] != 0) {
                    al.add(map[nr][nc]);
                    map[nr][nc] = 0;
                }
            }

            if(isCheck) break;

            nd = (nd + 1) % 4;
            moveCnt++;
        }

        for(int i=0; i<al.size(); i++) {
            int cnt = 0;
            for(int j = i+1; j<al.size(); j++) {
                if(al.get(i) != al.get(j)) break;
                cnt++;
            }
            queue.add(cnt+1);
            queue.add(al.get(i));
            i += cnt;
        }

        nr = sr;
        nc = sc;
        moveCnt = 0;
        size = 1;
        nd = 0;

        while (true) {
            if(moveCnt == 2) {
                moveCnt = 0;
                size++;
            }

            boolean isCheck = false;
            for(int k=0; k<size; k++) {
                nr += dir[nd][0];
                nc += dir[nd][1];
                if(queue.isEmpty() || (nr == 0 && nc == -1)) {
                    isCheck = true;
                    break;
                }
                map[nr][nc] = queue.poll();
            }

            if(isCheck) break;

            nd = (nd + 1) % 4;
            moveCnt++;
        }

    }
}