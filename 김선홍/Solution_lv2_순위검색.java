import java.util.*;

class Solution_lv2_순위검색 {
    Map<String, List<Integer>> map = new HashMap<>();
    
    // 최대 info 5만, query 10만 N^2 -> 시간초과
    // HashMap 사용 -> key: 모든 조건 문자열로 붙임, value: list(점수)
    public int[] solution(String[] info, String[] query) {
        int[] answer = new int[query.length];
        String[] lang = {"cpp", "java", "python", "-"};
        String[] group = {"backend", "frontend", "-"};
        String[] career = {"junior", "senior", "-"};
        String[] food = {"chicken", "pizza", "-"};
        
        // 모든 경우의 수 key 생성
        for (int l = 0; l < 4; l++) {
            for (int g = 0; g < 3; g++) {
                for (int c = 0; c < 3; c++) {
                    for (int f = 0; f < 3; f++) {
                        List<Integer> list = new ArrayList<>();
                        map.put(lang[l] + group[g] + career[c] + food[f], list);
                    }
                }
            }
        }
        
        // 2^4 * 50000
        for (int i = 0; i < info.length; i++) {
            String[] in = info[i].split(" ");
            subset(0, in, "");
        }
        
        for (String key : map.keySet()) {
            List<Integer> list = map.get(key);
            Collections.sort(list);
            map.put(key, list);
        }
        
        // 각 쿼리를 수행하면서 해당 key를 찾고 점수 리스트를 이분탐색
        for (int i = 0; i < query.length; i++) {
            String[] in = query[i].split(" and ");
            String[] foodAndScore = in[3].split(" ");
            String key = in[0] + in[1] + in[2] + foodAndScore[0];
            int score = Integer.parseInt(foodAndScore[1]);
            
            List<Integer> list = map.get(key);
            // 이분 탐색
            int left = 0, right = list.size() - 1;
            while (left <= right) {
                int middle = (left + right) / 2;
                if (list.get(middle) < score) {
                    left = middle + 1;
                } else {
                    right = middle - 1;
                }
            }
            
            // 해당 점수 이상 -> 전체 개수 - left 이하 개수
            answer[i] = list.size() - left;
        }
        
        return answer;
    }
    
    // info를 순회하면서 각 항목 선택 or 비선택 부분집합 구하기
    public void subset(int cnt, String[] in, String key) {
        if (cnt == 4) {
            List<Integer> list = map.get(key);
            list.add(Integer.parseInt(in[cnt]));
            map.put(key, list);
            return;
        }
        
        subset(cnt + 1, in, key + in[cnt]);
        subset(cnt + 1, in, key + "-");
    }
}