import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class sol_lv2_거리두기확인하기 {
    private static final int MAX = 5;

    public ArrayList<Integer> solution(String[][] places) {
        ArrayList<Integer> answer = new ArrayList<>();

        for(String[] place : places) {
            char[][] p = getMatrix(place);
            ArrayList<int[]> persons = findPerson(p);
            boolean isChk = false;
            for(int[] person : persons) {
                if(checkDistance(p, person)) {
                    isChk = true;
                    break;
                }
            }
            answer.add((isChk) ? 0 : 1);
        }

        return answer;
    }

    private static char[][] getMatrix(String[] place) {
        char[][] temp = new char[MAX][MAX];

        int idx = 0;
        for(String s : place) {
            temp[idx++] = s.toCharArray();
        }

        return temp;
    }

    private static ArrayList<int[]> findPerson(char[][] place) {
        ArrayList<int[]> person = new ArrayList<>();

        for(int i=0; i<MAX; i++) {
            for(int j=0; j<MAX; j++) {
                if(place[i][j] == 'P') {
                    person.add(new int[] {i,j});
                }
            }
        }

        return person;
    }

    private static boolean rangeCheck(int r, int c) {
        return (r < 0 || r >= MAX || c < 0 || c >= MAX);
    }

    private static boolean checkDistance(char[][] place, int[] start) {
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[MAX][MAX];
        int[][] dir = {{1,0},{-1,0},{0,1},{0,-1}};

        visited[start[0]][start[1]] = true;
        queue.offer(new int[]{start[0], start[1], 0});
        boolean isCheck = false;
        int[] cur = null;
        while(!queue.isEmpty()) {
            cur = queue.poll();

            if(cur[2] >= 2) continue;

            for(int d=0; d<4; d++) {
                int nr = cur[0] + dir[d][0];
                int nc = cur[1] + dir[d][1];

                if(rangeCheck(nr,nc) || visited[nr][nc] || place[nr][nc] == 'X') continue;
                if(place[nr][nc] == 'P') {
                    isCheck = true;
                    break;
                }

                visited[nr][nc] = true;
                queue.offer(new int[]{nr,nc,cur[2]+1});
            }
        }

        return isCheck;
    }
}
