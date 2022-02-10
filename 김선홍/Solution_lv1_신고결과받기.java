import java.util.*;

class Solution {
    public int[] solution(String[] id_list, String[] report, int k) {
        int[] answer = new int[id_list.length];
        // key: 자기자신, value: 자신을 신고한 사람
        HashMap<String, HashSet<String>> hm = new HashMap<>();
        // key: 자기자신, value: 받은 메일 수
        HashMap<String, Integer> mail = new HashMap<>();
        
        for(int i=0;i<id_list.length;i++){
            hm.put(id_list[i], new HashSet<>());
            mail.put(id_list[i], 0);
        }
        
        
        for(int i=0;i<report.length;i++){
            String[] str = report[i].split(" ");
            
            // str[0]: 신고자, str[1]: 신고 당한 사람(자기자신, hm의 key)
            if(!hm.get(str[1]).contains(str[0])) {
                HashSet<String> set = hm.get(str[1]);
                set.add(str[0]);
                hm.put(str[1], set);
                // hm.put(str[1], hm.get(str[1]).add(str[0])) -> X
            }
        }
        
        for(int i=0;i<id_list.length;i++){
            
            // k번 이상 신고 당했다면 메일발송
            if(hm.get(id_list[i]).size()>=k){
                // 해시맵에 저장된 값 갯수만큼 메일 발송 갯수를 카운트
                for(String s:hm.get(id_list[i])){
                    mail.put(s,mail.get(s)+1);
                }
            }
        }
        
        // 결과 배열에 저장
        for(int i=0;i<id_list.length;i++){
            answer[i] = mail.get(id_list[i]);
        }
        
        return answer;
    }
}
