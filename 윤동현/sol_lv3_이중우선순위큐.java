import java.util.*;

public class sol_lv3_이중우선순위큐 {
    public int[] solution(String[] operations) {
        // 최소힙, 최대힙 사용
        PriorityQueue<Integer> min = new PriorityQueue<>();
        PriorityQueue<Integer> max = new PriorityQueue<>(Collections.reverseOrder());

        for(String s : operations) {
            String[] operation = s.split(" ");

            if(min.size() < 1 && operation[0].equals("D")) continue;

            int val = Integer.parseInt(operation[1]);
            if(operation[0].equals("I")) {
                // 삽입시에는 최소,최대 둘다 삽입
                min.offer(val);
                max.offer(val);
                continue;
            }

            if(val == -1) {
                // 최솟값 삭제시 최소힙에서 꺼내주고 최대힙에도 해당 값 삭제
                max.remove(min.poll());
            }else {
                // 최댓값 삭제시 최대힙에서 꺼내주고 최소힙에도 해당 값 삭제
                min.remove(max.poll());
            }
        }

        int[] answer = new int[2];
        if(!min.isEmpty()) {
            answer[0] = max.poll();
            answer[1] = min.poll();
        }

        return answer;
    }
}
