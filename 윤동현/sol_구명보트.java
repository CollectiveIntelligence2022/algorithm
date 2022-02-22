import java.util.*;

class Solution {
    public int solution(int[] people, int limit) {
        int answer = 0;
        
        Arrays.sort(people);
        //최소값 두명이 limit보다 크면 무조건 사람 한명당 1보트.
        if(people.length>1 && people[0] + people[1] > limit) {
            return people.length;
        }
        
        int max = people.length - 1;
        for(int min = 0; min <= max; max--) {
            // 두명을 태울 수 있다면 태우고 min++;
            if(people[min] + people[max] <= limit) min++;
            answer++;
        }
        
        return answer;
    }
}
// 최소 값 부터 채워보자.
// 30,60 30,65가 있으면 30,65를 선택하는것이 이득
// 그러니깐 최소값과 큰놈이랑 짝지어주자.
