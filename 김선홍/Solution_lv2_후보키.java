import java.util.*;

class Solution_lv2_후보키 {
    List<String> candidateKey = new ArrayList<>();
    boolean[] visit;
    int R, C;
    
    public int solution(String[][] relation) {
        R = relation.length;// 튜플 수
        C = relation[0].length;// 속성 수
        visit = new boolean[C];
        subset(0, relation);
        
        return candidateKey.size();
    }
    
    /* 부분집합으로 모든 속성 조합을 뽑아서 후보키를 검사한다 */
    public void subset(int idx, String[][] data) {
        if (idx == C) {
            checkCandidateKey(data);
            return;
        }
        
        visit[idx] = true;
        subset(idx + 1, data);
        visit[idx] = false;
        subset(idx + 1, data);
    }
    
    /* 후보키가 되는지 검사 */
    public void checkCandidateKey(String[][] data) {
        Set<String> set = new HashSet<>();
        String str = "";// 속성의 인덱스들을 저장할 문자열
        
        for (int i = 0; i < C; i++) {
            // 선택안한 속성
            if (visit[i]) {
                continue;
            }
            
            // 속성 인덱스를 이어 붙인다
            // ex) 01, 023, 3 ...
            str += Integer.toString(i);
        }
        
        // 유일성 검사
        // 튜플마다 검사해볼 속성들을 모두 이어 붙인다
        // set을 사용하기 때문에 모두 문자열로 이어 붙여서 검사
        for (int i = 0; i < R; i++) {
            StringBuilder sb = new StringBuilder();
            
            for (int j = 0; j < C; j++) {
                if (visit[j]) {
                    continue;
                }

                sb.append(data[i][j]).append(j);
            }
            
            // 합친 문자열을 set에 넣음
            set.add(sb.toString());
        }
        
        // set 크기가 튜플 수랑 다르다
        // -> 유일성 만족 못한다
        if (set.size() != R) {
            return;
        }
        
        // 최소성 검사
        for (String s : candidateKey) {
            int cnt = 0;
            
            // 모든 문자 비교해서 개수 셈
            for (int i = 0; i < s.length(); i++) {
                for (int j = 0; j < str.length(); j++) {
                    if (s.charAt(i) == str.charAt(j)) {
                        cnt++;
                    }
                }
            }
            
            // 이미 최소성을 만족중
            if (s.length() == cnt) {
                return;
            }
            
            // contains 메소드를 사용할 경우 속성 판별 못한다
            // ex) 012 02 -> false를 반환해서 판별 불가!!
            // if (str.contains(s)) {
            //     return false;
            // }
        }
        
        // 후보키 등록
        candidateKey.add(str);
    }
}