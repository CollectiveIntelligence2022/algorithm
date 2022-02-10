import java.util.*;

class Solution {
    static HashMap<String, Integer> map;
    static ArrayList<Integer> courses;
    static String[] orderArr;
    static int[] numbers;
    public String[] solution(String[] orders, int[] course) {
        ArrayList<String> arrList = new ArrayList<>();
        
        courses = new ArrayList<>();
        for (int i = 0; i < course.length; i++) {
            courses.add(course[i]);
        }
        
        map = new HashMap<>();
        for (String order : orders) {
            numbers = new int[order.length()];
            orderArr = order.split("");
            comb(0, 0);
        }
        
        HashMap<Integer, Integer> maxCnt = new HashMap<>();
        for (int n : course) {
            maxCnt.put(n, 0);
        }
        for (String key : map.keySet()) {
            if (map.get(key) < 2)
                continue;
            
            // max보다 작으면 패스
            if (maxCnt.get(key.length()) > map.get(key))
                continue;
            
            // 만약 크다면 key.length와 길이 같은 애들 싹 빼주기
            if (maxCnt.get(key.length()) < map.get(key)) {
                ArrayList<String> tmp = new ArrayList<>();
                for (String s : arrList) {
                    if (s.length() != key.length()) {
                        tmp.add(s);
                    }
                }
                arrList = tmp;
                maxCnt.put(key.length(), map.get(key));
            }
            
            arrList.add(key);
        }
        
        
        Collections.sort(arrList);
        String[] answer = new String[arrList.size()];
        for (int i = 0; i < arrList.size(); i++) {
            answer[i] = arrList.get(i);
        }        
        return answer;
    }
    
    static private void comb(int cnt, int start) {
        if (courses.contains(cnt)) {
            String[] arr = new String[cnt];
            for (int i = 0; i < cnt; i++) {
                arr[i] = orderArr[numbers[i]];
            }
            Arrays.sort(arr);
            String str = String.join("", arr);
            
            int n = 1;
            if (map.containsKey(str)) {
                n = map.get(str) + 1;
            }
            map.put(str, n);
        }
        
        for (int i = start; i < orderArr.length; i++) {
            numbers[cnt] = i;
            comb(cnt + 1, i + 1);
        }
    }
}

// 각 주문별로 조합 구해서 course 수만큼 만족하면 그 때 맵에 저장?
// 다 저장하고 마지막에 맵 쫙 돌면서 2회 이상 주문된 애들만 배열에 저장
// 정렬해서 출력