import java.util.*;

class Solution {
    public int[] solution(String[] id_list, String[] report, int k) {
        int[] answer = new int[id_list.length];
        
        HashMap<String, HashSet<String>> map = new HashMap<>();
        
        // 신고 당한놈(key) : 신고 한 놈(value) 저장
        for (String r : report) {
            String[] str = r.split(" ");
            HashSet<String> set = new HashSet<>();
            if (map.containsKey(str[1])) {
                set = map.get(str[1]);
            }
            set.add(str[0]);
            map.put(str[1], set);
        }
        
        // 유저 이름별 인덱스 저장하기
        HashMap<String, Integer> idxMap = new HashMap<>();
        for (int i = 0; i < id_list.length; i++) {
            idxMap.put(id_list[i], i);
        }
        
        // 모두 돌면서 k번 이상 신고당한놈 신고한 사람들 횟수 +1
        for (String key : map.keySet()) {
            HashSet<String> set = map.get(key);
            if (set.size() >= k) {
                for (String user : set) {
                    answer[idxMap.get(user)]++;
                }
            }
        }
        
        return answer;
    }
}

// 해시맵<id,해시셋>
// 신고당한놈 id를 key로 해당 set에 신고한놈 id 추가 (set으로 중복 제거)
// 마지막에 맵 전부 돌면서 만약 해시셋 길이가 k 이상이면 해당 유저 메일 +1
// 유저별 인덱스를 맵으로 따로 저장해둘까.....?