import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class boj_g3_2533_사회망서비스 {

    final static int MAX = 1000000;
    static int N, dp[][];
    static boolean[] visited;
    static ArrayList<Integer>[] graph;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        dp = new int[2][MAX+1];
        visited = new boolean[MAX+1];
        graph = new ArrayList[MAX+1];
        for(int i=0; i<=MAX; i++) graph[i] = new ArrayList<>();

        N = Integer.parseInt(br.readLine());
        for(int i=0; i<N-1; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            graph[from].add(to);
            graph[to].add(from);
        }

        findEarlyAdopter(1);
        System.out.println(Math.min(dp[0][1], dp[1][1]));

        br.close();
    }

    static void findEarlyAdopter(int n) {
        visited[n] = true;
        dp[0][n] = 0; // 얼리어 답터 아님
        dp[1][n] = 1; // 얼리어 답터인 경우

        for(int child : graph[n]) {
            if(visited[child]) continue;
            findEarlyAdopter(child);
            dp[0][n] += dp[1][child]; // 얼리어 답터가 아닌경우 무조건 자식이 얼리어답터여야하니깐
            dp[1][n] += Math.min(dp[0][child], dp[1][child]); // 얼리어답터인경우 자식이 얼리어답터 일수도 아닐수도 있기때문
        }
    }
}
