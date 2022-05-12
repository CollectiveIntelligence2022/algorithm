import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class boj_g2_17825_주사위윷놀이 {

    static class Node {
        int to;
        int fast;
        boolean fastCheck;

        public Node(int to) {
            this.to = to;
            this.fastCheck = false;
        }
    }

    static Node[] map;
    static int[] order, horse, now;
    static boolean[] visited;
    static int result;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        order = new int[10];
        horse = new int[10];
        for(int i=0; i<10; i++) order[i] = Integer.parseInt(st.nextToken());

        nodeInit();
        perm(0);
        System.out.println(result);
        br.close();
    }

    static void perm(int cnt) {
        if(cnt == 10) {
            now = new int[4];
            visited = new boolean[42];
            move();
            return;
        }

        for(int i=0; i<4; i++) {
            horse[i] = i;
            perm(cnt+1);
        }
    }

    static boolean fastChk(int f) {
        return f == 10 || f == 20 || f == 25 || f == 30;
    }

    static void move() {
        int score = 0;
        for(int i=0; i<10; i++) {
            int to = horseMove(horse[i], order[i]);
            if (to == -1) return;
            now[horse[i]] = to;
            score += getScore(to);
        }

        result = Math.max(result, score);
    }

    private static int horseMove(int h, int cmd) {
        int cur = now[h];

        for(int i=0; i<cmd; i++) {
            if(i==0 && fastChk(cur)) {
                cur = map[cur].fast;
            }else cur = map[cur].to;
        }

        if(cur <=40 && visited[cur]) return -1;

        visited[now[h]] = false;
        visited[cur] = true;
        return cur;
    }

    /*
    static int move() {
        int[] h = {0,0,0,0};
        boolean[] visited = new boolean[42];

        int score = 0;
        for(int i=0; i<10; i++) {
            int cur = h[horse[i]];

            for(int j=0; j<order[i]; j++) {
                if(j == 0 && fastChk(cur)) {
                    cur = map[cur].fast;
                }else {
                    cur = map[cur].to;
                }
            }

            h[horse[i]] = cur;
            if(visited[cur] && cur != 41) {
                score = -1;
                break;
            }else {
                visited[h[horse[i]]] = false;
                visited[cur] = true;
                score += getScore(cur);
            }
        }

        return score;
    }
*/
    static int getScore(int s) {
        if(s == 17) return 16;
        if(s == 21) return 22;
        if(s == 27) return 24;
        if(s == 33) return 30;
        if(s == 37) return 35;
        if(s == 29) return 28;
        if(s == 23) return 26;
        if(s == 41) return 0;
        return s;
    }

    static void nodeInit() {
        map = new Node[42];
        for(int i=0; i<=40; i++) map[i] = new Node(i+2);

        map[41] = new Node(0);
        map[40].to = 41;
        map[10].fastCheck = map[20].fastCheck = map[30].fastCheck = true;

        map[10].fast = 13;
        map[13] = new Node(17);
        map[17] = new Node(19);
        map[19] = new Node(25);

        map[20].fast = 21;
        map[21] = new Node(27);
        map[27] = new Node(25);

        map[25] = new Node(33);
        map[33] = new Node(37); // 스코어 35취급해준다
        map[37] = new Node(40);

        map[30].fast = 29; // 스코어 28취급
        map[29] = new Node(27);
        map[27] = new Node(23); // 스코어 26 취급
        map[23] = new Node(25);
    }
}
