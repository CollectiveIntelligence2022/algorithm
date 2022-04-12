class Solution_lv2_양궁대회 {
	// 1. k점을 둘 다 똑같은 개수 맞춤 -> 어피치가 k점 가져감
	// -> 그게 아니라면 더 많이 맞춘 사람이 k점 가져감
	// -> 둘다 못맞추면 둘다 점수 못 얻음
	// 2. 최종 점수가 더 높은 선수가 우승
	// -> 같으면 어피치 우승

	// ans) 라이언이 가장 큰 점수 차이로 우승하기 위한 과녁 분포 구하기
	// -> 우승할 수 없는 경우(짐 or 비김) -1
	// -> 가장 큰 점수 차이로 우승할 수 있는 방법이 여러 가지 일 경우, 가장 낮은 점수를 더 많이 맞힌 경우로
	// -> 가장 낮은 점수를 맞힌 개수가 같을 경우 계속해서 그다음으로 낮은 점수를 더 많이 맞힌 경우로

	int[] arrowCnt = new int[11];
	int[] answer = new int[11];
	int max = 0;
	boolean isWin = false;

	public int[] solution(int n, int[] info) {
		comb(0, 0, n, info);

		if (!isWin) {
			answer = new int[1];
			answer[0] = -1;
		}

		return answer;
	}

    // 같은 과녁에 중복해서 쏠 수 있다, 순서 상관 없다
    // -> 중복 조합
	public void comb(int start, int cnt, int n, int[] info) {
		if (cnt == n) {
			int lionScore = 0, apeachScore = 0;

			for (int i = 0; i < 11; i++) {
				if (arrowCnt[i] > info[i]) {
					lionScore += (10 - i);
				} else if (info[i] > 0) {
					apeachScore += (10 - i);
				}
			}

            int distance = lionScore - apeachScore;
            // 점수 같거나 작으면 못이김
			if (distance <= 0) {
				return;
			}
            
            // 최댓값보다 더 크다면 바로 복사
			if (distance > max) {
				isWin = true;
				max = distance;
				System.arraycopy(arrowCnt, 0, answer, 0, 11);
                // 최댓값과 같다면 점수가 낮은 과녁부터 오름차순으로 검사
			} else if (distance == max) {
				for (int i = 10; i >= 0; i--) {
                    // 가장 낮은 점수를 더 많이 맞춘 경우, 새로 갱신
					if (arrowCnt[i] > answer[i]) {
						System.arraycopy(arrowCnt, 0, answer, 0, 11);
						break;
                        // 더 적게 맞췄다면 더 볼 필요 X
					} else if(arrowCnt[i] < answer[i]) {
						break;
					}
				}
			}

			return;
		}

		for (int i = start; i < 11; i++) {
			arrowCnt[i]++;
			comb(i, cnt + 1, n, info);
			arrowCnt[i]--;
		}
	}
}