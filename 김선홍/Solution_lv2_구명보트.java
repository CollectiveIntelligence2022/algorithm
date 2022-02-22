import java.util.Arrays;

class Solution_lv2_구명보트 {
	// 정렬 후 인덱스를 2개로 두자 -> 양 옆 사람들 태우기
	// 인덱스 역순으로 탐색
	public int solution(int[] people, int limit) {
		int answer = 0;
		Arrays.sort(people);

		for (int i = 0, j = people.length - 1; j >= i; j--) {
			// 둘 다 탈 수 있다면 둘다 태움
			// 무게 제한을 초과한다면 무거운 사람만 태움
			if (people[i] + people[j] <= limit) {
				i++;
			}
			answer++;
		}

		return answer;
	}
}