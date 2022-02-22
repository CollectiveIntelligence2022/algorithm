import java.util.*;

class Solution {
    public int solution(int[] people, int limit) {
        int answer = 0;
        
        Arrays.sort(people);
        int min = 0;
        int max = people.length - 1;
        
        while (true) {
            answer++;
            int sum = people[max--];
            
            while(sum + people[min] <= limit) {
                sum += people[min++];
            }
            
            if (min > max)
                break;
        }
        
        return answer;
    }
}