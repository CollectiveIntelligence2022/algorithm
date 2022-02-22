import java.util.*;

class Solution {
    public int solution(int[] people, int limit) {
        int answer = 0;
        
        // 몸무게 오름차순 정렬
        Arrays.sort(people);
        
        // 알고리즘 처리
        answer = process(people, limit);
        
        return answer;
    }
    
    int process(int[] people, int limit) {
        int maxIdx = people.length-1, boat = 0;
        
        // 무한반복문 (기저조건은 min, max가 만날 때)
        for(int minIdx = 0; minIdx <= maxIdx; maxIdx--) {
            if(people[minIdx] + people[maxIdx] <= limit) {
                minIdx++;
            }
            boat++;
        }
        
        return boat;
    }
}
