class Solution {
    
    final static int MAX = 11;
    static int maxScore;
    static int[] answer = {-1};
    
    public int[] solution(int n, int[] info) {
        
        maxScore = 0;
        subset(n, 0, info, new int[MAX]);
        
        return answer;
    }
    
    private void subset(int n, int i, int[] info, int[] ryan) {
        if(n == 0) { // 종료
            findRyan(maxScore(info, ryan), ryan);
            return;
        }
        
        if(i == (MAX-1)) { // 종료
            ryan[MAX-1] = n;
            findRyan(maxScore(info, ryan), ryan);
            ryan[MAX-1] = 0;
            return;
        }
        
        // 선택안하거나 선택하거나
        if(n > info[i]) {
            ryan[i] = (info[i] + 1);        
            subset(n - (info[i] + 1), i+1, info, ryan);        
            ryan[i] = 0;
        }
        subset(n, i+1, info, ryan);
    }
    
    private void findRyan(int score, int[] ryan) {
        if(maxScore > score || score == 0) return;
        
        if(maxScore == score) {
            for(int i=(MAX-1); i>=0; i--) {
                if(ryan[i] == answer[i]) continue;
                if(answer[i] > ryan[i]) return;
                else break;
            }
        }
        
        maxScore = score;
        answer = ryan.clone();
        return;
    }
    
    private int maxScore(int[] info, int[] ryan) {
        int a = 0; // 어피치
        int r = 0; // 라이언
        for(int i=0; i<MAX; i++) {
            if(info[i] == 0 && ryan[i] == 0) continue;
            
            if(info[i] >= ryan[i]) {
                a += (MAX - 1 - i);
            }else {
                r += (MAX - 1 - i);
            }
        }
        
        return r - a;
    }
}
