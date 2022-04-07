import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class boj_g4_12886_돌그룹 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        int stoneWeight = a + b + c;

        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visitied = new boolean[1501][1501];
        queue.offer(new int[] {a,b,c});
        visitied[a][b] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            Arrays.sort(cur);

            if(cur[0] == cur[1] && cur[0] == cur[2]) {
                sb.append(1);
                break;
            }
            for(int i=0; i<3; i++) {
                for(int j=i+1; j<3; j++) {
                    if(cur[i] < cur[j]) {
                        int x = cur[i] + cur[i];
                        int y = cur[j] - cur[i];

                        if(visitied[x][y]) continue;

                        queue.offer(new int[] {x, y, stoneWeight - x - y});
                        visitied[x][y] = true;
                    }
                }
            }
        }
        System.out.println((sb.length() == 0) ? 0 : sb.toString());
        br.close();
    }
}