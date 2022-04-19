import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class boj_g5_컨베이어벨트위의로봇 {

    static class Belt {
        boolean robot; // 로봇 존재 여부
        int cnt; // 내구도

        public Belt(int cnt) {
            this.cnt = cnt;
        }
    }

    static Belt[] belts;
    static int N,K;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken()); // 2*N 벨트 칸
        K = Integer.parseInt(st.nextToken()); // 내구도 0인 칸의 개수

        belts = new Belt[2*N+1];
        st = new StringTokenizer(br.readLine());
        for(int i=1; i<=2*N; i++) { // 벨트 내구도
            belts[i] = new Belt(Integer.parseInt(st.nextToken()));
        }

        int result = 0;

        while(cntCheck()) { // 4. 내구도가 0인 칸의 개수가 K개 이상이라면 과정을 종료한다. 그렇지 않다면 1번으로 돌아간다.
            result++;
            // 1. 벨트가 각 칸 위에 있는 로봇과 함께 한 칸 회전한다.
            rotateBelt();

            // 2. 가장 먼저 벨트에 올라간 로봇부터, 벨트가 회전하는 방향으로 한 칸 이동할 수 있다면 이동한다.
            //      만약 이동할 수 없다면 가만히 있는다.
            //      로봇이 이동하기 위해서는 이동하려는 칸에 로봇이 없으며, 그 칸의 내구도가 1 이상 남아 있어야 한다.
            moveRobot();

            // 3. 올리는 위치에 있는 칸의 내구도가 0이 아니면 올리는 위치에 로봇을 올린다.
            if(belts[1].cnt != 0) {
                belts[1].robot = true;
                belts[1].cnt--;
            }
        }

        System.out.println(result);
        br.close();
    }

    static boolean cntCheck() { // 내구도 0인 칸의 개수 구하기
        int cnt = 0;
        for(int i=1; i<=2*N; i++) {
            if(belts[i].cnt == 0) cnt++;
        }
        return cnt < K;
    }

    static void rotateBelt() {
        Belt tmp = belts[2*N];
        for(int i=2*N-1; i>=1; i--) {
            if(i == N-1) belts[i].robot = false; // 한칸뒤에 내리는 위치이므로
            belts[i+1] = belts[i];
        }
        belts[1] = tmp;
    }

    static void moveRobot() {
        for(int i=N-1; i>1; i--) { // 올리는 위치에서 로봇을 올리므로 N에 가까울수록 가장 먼저 벨트에 올라간 로봇
            if(belts[i].robot && !belts[i+1].robot && belts[i+1].cnt != 0) { // 로봇존재, 다음칸 로봇X, 내구도 있을때
                // 해당 칸 로봇 false, 다음칸에 로봇 true, 내구도 1 감소
                belts[i].robot = false;
                belts[i+1].robot = i + 1 != N; // N 일때는 내리는 위치이므로 바로 내려준다.
                belts[i+1].cnt--;
            }
        }
    }
}
