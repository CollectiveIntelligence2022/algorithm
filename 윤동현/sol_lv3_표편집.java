import java.util.Stack;

public class sol_lv3_표편집 {
    public StringBuilder solution(int n, int k, String[] cmd) {
        StringBuilder answer = new StringBuilder("O".repeat(n));
        Stack<int[]> revert = new Stack<>();
        int[] prev = new int[n];
        int[] next = new int[n];

        for(int i=0; i<n; i++) {
            prev[i] = i-1;
            next[i] = i+1;
        }

        int cnt = 0;
        for(String str : cmd) {
            String[] c = str.split(" ");

            if(c[0].equals("U")) {
                cnt = Integer.parseInt(c[1]);
                for(;cnt>0; cnt--) {
                    k = prev[k];
                }
            }else if(c[0].equals("D")) {
                cnt = Integer.parseInt(c[1]);
                for(;cnt>0; cnt--) {
                    k = next[k];
                }
            }else if(c[0].equals("C")) {
                revert.push(new int[] {prev[k], k, next[k]});

                if(prev[k] != -1) next[prev[k]] = next[k];
                if(next[k] != n) prev[next[k]] = prev[k];

                if(next[k] != n) k = next[k];
                else k = prev[k];
            }else if(c[0].equals("Z")) {
                int[] cur = revert.pop();
                // 이전 인덱스의 다음 인덱스를 현재 번호로
                if(cur[0] != -1) next[cur[0]] = cur[1];
                // 다음 인덱스의 이전 인덱스를 현재 번호로
                if(cur[2] != n) prev[cur[2]] = cur[1];
            }
        }

        while(!revert.isEmpty()) {
            answer.setCharAt(revert.pop()[1], 'X');
        }

        return answer;
    }
}

/* Hash 사용 -> 효율성 6번부터 시간초과
public StringBuilder solution(int n, int k, String[] cmd) {
        StringBuilder answer = new StringBuilder();
        Stack<Integer> revert = new Stack<>();
        HashSet<Integer> set = new HashSet<>();
        for(int i=0; i<n; i++) {
            set.add(i);
            answer.append("O");
        }

        int cnt = 0;
        for(String str : cmd) {
            String[] c = str.split(" ");

            if(c[0].equals("U")) {
                cnt = Integer.parseInt(c[1]);
                // k가 삭제된 값이면 존재하는 인덱스값에서 cnt값만큼 인덱스 조절
                for(; cnt>0;) {
                    if(set.contains(--k)) cnt--;
                }
            }else if(c[0].equals("D")) {
                cnt = Integer.parseInt(c[1]);
                // k가 삭제된 값이면 존재하는 인덱스값에서 cnt값만큼 인덱스 조절
                for(; cnt>0;) {
                    if(set.contains(++k)) cnt--;
                }
            }else if(c[0].equals("C")) {
                set.remove(k); // 현재 행 삭제
                revert.push(k); // 복구를 위해 스택에 삽입

                // 바로 밑에 존재하는 인덱스 찾기
                for(cnt = k+1; cnt<n; cnt++) {
                    if(set.contains(cnt)) break;
                }

                if(cnt < n) {
                    k = cnt;
                }else { // 삭제된 행이 마지막이므로 바로 윗행을 가르킨다.
                    for(cnt = k-1; cnt>=0; cnt--) {
                        if(set.contains(cnt)) break;
                    }
                    k = cnt;
                }

            }else if(c[0].equals("Z")) {
                set.add(revert.pop());
            }
        }

        while(!revert.isEmpty()) {
            answer.setCharAt(revert.pop(), 'X');
        }

        return answer;
    }
*/