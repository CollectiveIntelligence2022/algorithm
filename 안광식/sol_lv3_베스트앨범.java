import java.util.*;

class Solution {
    public ArrayList<Integer> solution(String[] genres, int[] plays) {
        ArrayList<Integer> answer = new ArrayList<>();
        int size = genres.length;
        
        // 장르별 총 재생횟수 계산
        HashMap<String, Integer> totalPlays = new HashMap<>();
        for (int i = 0; i < size; i++) {
            String key = genres[i];
            int play = plays[i];
            
            if (totalPlays.containsKey(key)) {
                play += totalPlays.get(key);
            }
            
            totalPlays.put(key, play);
        }
        
        // 재생횟수 기준 장르별 내림차순 정렬
        ArrayList<String> keys = new ArrayList<>();
        ArrayList<Integer> values = new ArrayList<>();
        for (String key : totalPlays.keySet()) {
            int value = totalPlays.get(key);
            
            if (keys.size() == 0) {
                keys.add(key);
                values.add(value);
                continue;
            }
            
            for (int i = 0; i < values.size(); i++) {
                if (values.get(i) < value) {
                    values.add(i, value);
                    keys.add(i, key);
                    break;
                }
                else if (i == values.size() - 1) {
                    values.add(i + 1, value);
                    keys.add(i + 1, key);
                    break;
                }
            }
        }
        
        
        for (String key : keys) {
            int maxPlay = 0;
            int maxIdx = -1;
            int maxPlay2 = 0;
            int maxIdx2 = -1;
            for (int i = 0; i < size; i++) {
                if (genres[i].equals(key)) {
                    if (maxPlay < plays[i]) {
                        maxPlay2 = maxPlay;
                        maxIdx2 = maxIdx;
                        
                        maxPlay = plays[i];
                        maxIdx = i;
                    }
                    else if (maxPlay == plays[i] &&
                             maxPlay2 < plays[i]) {
                        maxPlay2 = plays[i];
                        maxIdx2 = i;
                    }
                    else if (maxPlay2 < plays[i]) {
                        maxPlay2 = plays[i];
                        maxIdx2 = i;
                    }
                }
            }
            
            answer.add(maxIdx);
            if (maxIdx2 > -1)
                answer.add(maxIdx2);
        }
        
        
            
        
        return answer;
    }
}

// 장르 별로 가장 많이 재생된 노래 두 개씩 선택
// 장르에 속한 곡이 하나라면, 하나의 곡만 선택

// 총 재생수 많은 장르 > 장르 내에서 재생수 많은거 > 고유번호 낮은거

// 1.속한 노래가 많이 재생된 장르 (장르별 재생수 합산하기,,)
// 2.장르 내에서 많이 재생된 노래
// 3.장르 내에서 재생 횟수가 같은 노래 중에서는 고유번호 오름차순