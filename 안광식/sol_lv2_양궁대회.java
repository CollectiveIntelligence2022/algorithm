class Solution {
    static int max;
    static int[] apeachScore, answer, ryanScore;
    public int[] solution(int n, int[] info) {
        answer = new int[11];
        ryanScore = new int[11];
        apeachScore = info;
        
        max = 0;
        recul(0, n);
        
        if (max == 0) {
            answer = new int[1];
            answer[0] = -1;
        }
        
        return answer;
    }
    
    private void recul(int cnt, int leftArrow) {
        // 화살 다 쓴 경우
        if (leftArrow == 0) {
            updateAnswer(getScoreGap());
            return;
        }
        
        // 끝까지 다 내려온 경우 (0점 과녁 빼고)
        if (cnt == 10) {
            ryanScore[10] = leftArrow;
            updateAnswer(getScoreGap());
            ryanScore[10] = 0;
            return;
        }
        
        // 현재 화살 선택하는 경우
        if (apeachScore[cnt] < leftArrow) {
            ryanScore[cnt] = apeachScore[cnt] + 1;
            recul(cnt + 1, leftArrow - (apeachScore[cnt] + 1));
            ryanScore[cnt] = 0;
        }
            
        // 안하는 경우
        recul(cnt + 1, leftArrow);
    }
    
    private void updateAnswer(int gap) {
        if (max > gap || gap == 0)
            return;
        
        if (max == gap) {
            for (int i = 10; i >= 0; i--) {
                if (answer[i] == ryanScore[i])
                    continue;
                if (answer[i] > ryanScore[i])
                    return;
                else
                    break;
            }
        }
        
        max = gap;
        answer = new int[11];
        for (int i = 0; i < 11; i++) {
            answer[i] = ryanScore[i];
        }
    }
    
    private int getScoreGap() {
        int apeach = 0;
        int ryan = 0;
        
        for (int i = 0; i < 11; i++) {
            if (apeachScore[i] == 0 && ryanScore[i] == 0)
                continue;
            
            if (apeachScore[i] < ryanScore[i]) 
                ryan += 10 - i;
            
            else 
                apeach += 10 - i;
        }
        
        return ryan - apeach;
    }
}

// 1.도전자 n발 쏜 후 챔피언 n발 쏨
// 2.점수 계산
//  1) 10점 ~ 0점
//  2) k (1 ~ 10) 점수에 대해 다음과 같이 점수 지급
//   - k점에 대해 도전자가 a발, 챔피언이 b발 맞출 경우 더 많이 맞춘놈이 k점 가져감
//   - 단, a == b 라면 도전자가 k점 가져감 (여러발이라도 k점만 가져가는점 주의, 둘 다 0점 맞춘거면 점수 못가져감)
//  3) 모든 과녁 점수에 대해 각 선수의 최종 점수 계산
// 3.최종 점수가 더 높은놈이 우승하고 둘 점수가 같다면 도전자 우승

// 현재는 도전자 화살 다 쏜 상태, 챔피언이 쏠 차례
// 도전자를 가장 큰 점수차로 이기려면 n발의 화살을 어떤 과녁 점수에 맞혀야 하는가 ?
// 가장 큰 점수차로 이길 수 있는 방법이 여러 가지 일 경우, 가장 낮은 점수를 더 많이 맞힌 경우를 return
// 어떻게 쏴도 점수가 낮거나 같으면 [-1] 리턴

// 제일 큰 수부터 확인..
// 재귀? 10점부터 0점까지 쭉 내려오면 최댓값 갱신
// 어피치보다 1발 더 쏘도록 함
// 1점짜리 과녁까지 왔는데 화살이 남았다면 몽땅 0점 과녁에 넣기