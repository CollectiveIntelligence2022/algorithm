import java.util.*;

class sol_기능개발 {
    public ArrayList<Integer> solution(int[] progresses, int[] speeds) {
        ArrayList<Integer> answer = new ArrayList<>();
        
        Queue<int[]> queue = new ArrayDeque<>();
        
        // 큐에 모든 값 집어넣기..!
        for(int i=0; i<progresses.length; i++) {
            queue.offer(new int[]{progresses[i], speeds[i]});
        }
        
        // 모든 작업이 끝나면 종료
        int time = 1;
        int count = 0;
        int deploy = 0;
        while(!queue.isEmpty()) {
            
            // 해당 시간에 배포 완료된 것을 젤 앞 기준으로 뽑음
            while(!queue.isEmpty() && (deploy = queue.peek()[0] + queue.peek()[1] * time) >= 100) {
                queue.poll();
                count++;
            }
            if(count != 0) {
                answer.add(count);
                count = 0;
            }
            time++;
        }
        
        return answer;
    }
}
