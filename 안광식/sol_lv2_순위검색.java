import java.util.*;

class Solution {
    static HashMap<String, List<Integer>> map;
    
    public int[] solution(String[] info, String[] query) {
        int[] answer = new int[query.length];
        map = new HashMap<String, List<Integer>>();
        
        for (String str : info) {
            String[] strArr = str.split(" ");
            comb(strArr, "", 0);
        }
 
        for (String key : map.keySet())
            Collections.sort(map.get(key));
        
       for (int i = 0; i < query.length; i++) {
            query[i] = query[i].replaceAll(" and ", "");
            String[] q = query[i].split(" ");
            answer[i] = map.containsKey(q[0]) ? binarySearch(q[0], Integer.parseInt(q[1])) : 0;
            // answer[i] = map.containsKey(q[0]) ? search(q[0], Integer.parseInt(q[1])) : 0;
        }
        
        return answer;
    }
    
    private static int search(String key, int score) {
        List<Integer> list = map.get(key);
        int idx = 0;
        while (true) {
            if (idx >= list.size())
                break;
            if (list.get(idx) < score)
                idx++;
            else
                break;
        }
        
        return list.size() - idx;
    }
    
    private static int binarySearch(String key, int score) {
        List<Integer> list = map.get(key);
        int start = 0, end = list.size() - 1;
 
        while (start <= end) {
            int mid = (start + end) / 2;
            if (list.get(mid) < score)
                start = mid + 1;
            else
                end = mid - 1;
        }
 
        return list.size() - start;
    }
    
    private static void comb(String[] strArr, String str, int cnt) {
        if (cnt == 4) {
            if (!map.containsKey(str)) {
                List<Integer> list = new ArrayList<Integer>();
                map.put(str, list);
            }
            map.get(str).add(Integer.parseInt(strArr[4]));
            return;
        }
        comb(strArr, str + "-", cnt + 1);
        comb(strArr, str + strArr[cnt], cnt + 1);
    }
}