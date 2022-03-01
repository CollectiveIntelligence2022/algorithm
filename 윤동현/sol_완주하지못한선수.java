import java.util.*;

class sol_완주하지못한선수 {
    
    static String answer = "";
    
    public String solution(String[] participant, String[] completion) {
        HashMap<String, Integer> map = new HashMap<>();
        
        for(String people : participant) {
            if(map.containsKey(people)) {
                map.put(people, map.get(people) + 1);
            }else {
                map.put(people, 1);
            }
        }
        
        for(String people : completion) {
            if(map.get(people) == 1) {
                map.remove(people);
                continue;
            }
            map.put(people, map.get(people) - 1);
        }
        
        map.forEach((key, value) -> {
            answer = key;
        });
        
        return answer;
    }
}
