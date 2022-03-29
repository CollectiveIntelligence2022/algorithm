import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class boj_g4_20058_마법사상어와파이어스톰 {
    static int cnt = 0;
    static int[][] dir = {{0,1}, {0,-1}, {1,0}, {-1,0}};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        int size = 1<<N;
        int[][] A = new int[size][size];
        int[] L = new int[Q];

        for(int i=0; i<size; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<size; j++) {
                A[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        for(int i=0; i<Q; i++) L[i] = Integer.parseInt(st.nextToken());

        for(int l : L) {
            A = rotate(1<<l, size, A);
            A = meltIce(size, A);
        }

        int sum = sumIce(A);
        System.out.println(sum);

        int ice = 0;
        boolean[][] visited = new boolean[size][size];
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                if(visited[i][j] || A[i][j] == 0) continue;
                cnt = 0;
                dfs(i,j,A,visited);
                ice = Math.max(ice, cnt);
            }
        }
        System.out.println(ice);

    }

    private static void dfs(int r, int c, int[][] A, boolean[][] visited) {
        visited[r][c] = true;
        cnt++;

        for(int d=0; d<4; d++) {
            int nr = r + dir[d][0];
            int nc = c + dir[d][1];

            if(rangeCheck(nr, nc, A.length) || visited[nr][nc] || A[nr][nc] == 0) continue;

            dfs(nr, nc, A, visited);
        }
        return;
    }

    private static int sumIce(int[][] A) {
        int s = 0;
        for(int[] aa : A) {
            for(int a : aa) {
                s += a;
            }
        }
        return s;
    }

    private static int[][] meltIce(int size, int[][] A) {
        int[][] temp = new int[size][size];
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                temp[i][j] = A[i][j];
            }
        }

        int r,c,cnt;

        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                if(A[i][j] == 0) continue; // -생길수더있다.

                cnt = 0;
                for(int d=0; d<4; d++) {
                    r = i + dir[d][0];
                    c = j + dir[d][1];

                    if(rangeCheck(r,c,size) || A[r][c] == 0) continue;

                    cnt++;
                }
                if(cnt < 3) temp[i][j]--;
            }
        }

        return temp;
    }

    private static boolean rangeCheck(int r, int c, int size) {
        return (r < 0 || r >= size || c < 0 || c >= size);
    }

    private static int[][] rotate(int l, int size, int[][] A) {
        int[][] temp = new int[size][size];

        for(int i=0; i<size; i+=l) {
            for(int j=0; j<size; j+=l) {

                //rotate 90
                int nr = i;
                int nc = j+l-1;
                for(int r=i; r<i+l; r++) {
                    for(int c=j; c<j+l; c++) {
                        temp[nr++][nc] = A[r][c];
                    }
                    nc--;
                    nr = i;
                }

            }
        }

        return temp;
    }
}
