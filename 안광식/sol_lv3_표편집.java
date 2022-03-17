import java.util.*;

class Solution {
    public String solution(int n, int curIdx, String[] cmd) {
        StringBuilder answer = new StringBuilder("O".repeat(n));
        
        Stack<int[]> deleteInfo = new Stack<>();
        int[] prev = new int[n];
        int[] next = new int[n];
        
        for (int i = 0; i < n; i++) {
            prev[i] = i - 1;
            next[i] = i + 1;
        }
        next[n - 1] = -1;
        
        for (String command : cmd) {
            String[] input = command.split(" ");
            switch (input[0]) {
                case "U":
                    int num = Integer.parseInt(input[1]);
                    for (int i = 0; i < num; i++)
                        curIdx = prev[curIdx];
                    
                    break;
                    
                case "D":
                    num = Integer.parseInt(input[1]);
                    for (int i = 0; i < num; i++)
                        curIdx = next[curIdx];
                    
                    break;
                    
                case "C":
                    deleteInfo.add(new int[] { prev[curIdx], curIdx, next[curIdx] });
                    
                    if (prev[curIdx] != -1) next[prev[curIdx]] = next[curIdx];
                    if (next[curIdx] != -1) prev[next[curIdx]] = prev[curIdx];
                    
                    if (next[curIdx] != -1) curIdx = next[curIdx];
                    else curIdx = prev[curIdx];
                    
                    break;
                    
                case "Z":
                    int[] resume = deleteInfo.pop();
                    
                    // resume
                    // 0 : prev
                    // 1 : 본인
                    // 2 : next
                    
                    if (resume[0] != -1) next[resume[0]] = resume[1];
                    if (resume[2] != -1) prev[resume[2]] = resume[1];
                    
                    break;
                    
                default:
                    break;
            }
        }
        
        while (!deleteInfo.isEmpty()) 
            answer.setCharAt(deleteInfo.pop()[1], 'X');
        
        return answer.toString();
    }
}

// import java.util.*;

// class Solution {
//     public String solution(int n, int curIdx, String[] cmd) {
//         StringBuilder answer = new StringBuilder();
        
//         Stack<int[]> deleteInfo = new Stack<>();
//         List<Integer> table = new ArrayList<>();
//         for (int i = 0; i < n; i++) {
//             table.add(i); 
//             answer.append('O');
//         }
        
//         for (String command : cmd) {
//             String[] input = command.split(" ");
//             switch (input[0]) {
//                 case "U":
//                     curIdx -= Integer.parseInt(input[1]);
//                     if (curIdx < 0)
//                         curIdx = 0;
//                     break;
                    
//                 case "D":
//                     curIdx += Integer.parseInt(input[1]);
//                     if (curIdx >= table.size())
//                         curIdx = table.size() - 1;
//                     break;
                    
//                 case "C":
//                     deleteInfo.add(new int[] { curIdx, table.get(curIdx) });
                    
//                     table.remove(curIdx);
//                     if (curIdx >= table.size())
//                         curIdx = table.size() - 1;
//                     break;
                    
//                 case "Z":
//                     int[] resume = deleteInfo.pop();
//                     table.add(resume[0], resume[1]);
                    
//                     if (resume[0] <= curIdx)
//                         curIdx++;
//                     break;
                    
//                 default:
//                     break;
//             }
//         }
        
//         while (!deleteInfo.isEmpty()) 
//             answer.setCharAt(deleteInfo.pop()[1], 'X');
        
//         return answer.toString();
//     }
// }

// U x : 현재 선택된 행에서 x칸 위에 있는 행 선택
// D x : 현재 선택된 행에서 x칸 아래에 있는 행 선택
// C : 현재 선택된 행 삭제 후 바로 아래 행 선택 (단, 삭제된 행이 마지막 행이라면 바로 윗 행 선택)
// Z : 가장 최근 삭제된 행을 원래대로 복구 (단, 현재 선택된 행은 바뀌지 않음) => 복구할거 없는데 이 명령어 나오는 경우는 없음

// 어레리에 표 정보 저장
// 직전 삭제 정보 저장용 스택
// 어레리 인덱스는 위아래 구분용으로 쓰고, 어레리에 들어갈 밸류를 Integer로 젤 처음 주어지는 행번호 저장하기
// 마지막까지 명령 수행 다 하고 처음부터 끝까지 돌면서 없는 번호일때 X 넣어주기