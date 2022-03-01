import java.util.*;

class sol_lv2_더맵게 {
    public int solution(int[] scoville, int K) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        
        for(int s : scoville) {
            pq.offer(s);
        }
        
        int answer = 0;
        
        // pq가 1보다 커야하는 이유는 스코빌 지수에 필요한 지수가 2개이므로
        // 그리고 pq이기 때문에 가장 낮은 지수가 k이상이면 모든 음식 스코빌 지수를 만족
        while(pq.size() > 1 && pq.peek() < K) {
            pq.offer(pq.poll() + (pq.poll() * 2));
            answer++;
        }
        
        // peek가 k보다 작으면 스코빌 지수를 만들수 없기때문
        return (pq.peek() < K) ? -1 : answer;
    }
}
