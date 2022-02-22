import java.util.*;

class Solution {
    
    static class Best implements Comparable<Best> {
        int index;
        int play;
        
        Best(int index, int play) {
            this.index = index;
            this.play = play;
        }
        
        public int compareTo(Best o) { 
            return -Integer.compare(this.play, o.play);
        }
    }
    
    public ArrayList<Integer> solution(String[] genres, int[] plays) {
        // 같은 키값을 가지는 total, Best를 각각 선언
        // pq를 사용한것은 어차피 최다play 2번 꺼내야하니깐 자동 정렬되므로!
        HashMap<String, Integer> total = new HashMap<>();
        HashMap<String, PriorityQueue<Best>> map = new HashMap<>();
        
        // 입력받기
        for(int i=0; i<genres.length; i++) {
            if(total.containsKey(genres[i])) {
                total.put(genres[i], total.get(genres[i]) + plays[i]);
            }else {
                total.put(genres[i], plays[i]);
            }
            
            PriorityQueue<Best> pq;
            if(map.containsKey(genres[i])) {
                pq = map.get(genres[i]);
            }else {
                pq = new PriorityQueue<>();
            }
            pq.add(new Best(i, plays[i]));
            map.put(genres[i], pq);
        }
        
        // total값 정렬 내림차순 정렬
        List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(total.entrySet());
        Collections.sort(entries, (o1, o2) -> -o1.getValue().compareTo(o2.getValue()));
        
        // 정렬된 결과를 가지고 해당 키값 뽑아서 최다 play 두번 뽑기 (재생곡하나라면 한개만!!)
        ArrayList<Integer> answer = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : entries) {
            PriorityQueue<Best> pq = map.get(entry.getKey());
            if(pq.size() > 1) {
                answer.add(pq.peek().index);
                pq.poll();
                answer.add(pq.peek().index);
            }else {
                answer.add(pq.peek().index);
            }
        }
        
        return answer;
    }
}
