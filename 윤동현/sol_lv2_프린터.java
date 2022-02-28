import java.util.*;

class Solution {
    public int solution(int[] priorities, int location) {
        // max 값 구하기
        int max = Arrays.stream(priorities).max().getAsInt();
        
        // queue에 인덱스와 우선순위를 넣어준다.
        Queue<int[]> queue = new ArrayDeque<>();
        for(int i=0; i<priorities.length; i++) {
            queue.offer(new int[]{i, priorities[i]});
        }
        
        int answer = 0;
        while(!queue.isEmpty()) {
            // max값이 아니라면 뒤로 넣기
            if(queue.peek()[1] != max) {
                queue.offer(queue.poll());
            }else {
                answer++;
                // 만약 location이라면 끝내기
                if(queue.peek()[0] == location) {
                    break;
                }
                // location이 아니라면 priorities 배열에서 값을 0으로 만들기.
                priorities[queue.peek()[0]] = 0;
                queue.poll();
            }
            
            // max 값 갱신을 위해 max값이 있는지 체크
            boolean isCheck = false;
            for(int[] val : queue) {
                if(val[1] == max) {
                    isCheck = true;
                    break;
                }
            }
            
            if(!isCheck) { // max 값이 없으므로 max값 priorities배열에서 갱신 아까 poll한 것은 0으로 만들어줬기떄문에 다음 최대값을 구함
                max = Arrays.stream(priorities).max().getAsInt();
            }
        }
        
        return answer;
    }
}
