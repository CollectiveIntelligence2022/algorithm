import java.util.*;

class Solution_lv2_단체사진찍기 {
	Map<Character, Integer> map = new HashMap<>();
	int[] idx = new int[8];
	boolean[] isUsed = new boolean[8];
	int res;

	public int solution(int n, String[] data) {
		char[] kf = { 'A', 'C', 'F', 'J', 'M', 'N', 'R', 'T' };
		for (int i = 0; i < 8; i++) {
			map.put(kf[i], i);
		}
		perm(0, data);
		return res;
	}

	// 조건이 하나가 아닐 수 있기 때문에 순서를 고려해야 한다
    // -> 순열
    // 8! * n(최대 100) = 4032000
    // -> 시간 충분
    // 문자로 주어졌기 때문에 인덱싱 어떻게?
    // -> key: 프렌즈(문자), value: 인덱스 로 가지는 map을 하나 선언
    // -> 8개의 인덱스 순열을 뽑고 해당 배열의 인덱스를 map.get(프렌즈)로 접근
	public void perm(int cnt, String[] data) {
		if (cnt == 8) {
			for (String s : data) {
				// 하나라도 만족 못하면 세지 않는다
				if (!isSatisfied(map, s)) {
					return;
				}
			}

			res++;
			return;
		}

		for (int i = 0; i < 8; i++) {
			if (!isUsed[i]) {
				idx[cnt] = i;
				isUsed[i] = true;
				perm(cnt + 1, data);
				isUsed[i] = false;
			}
		}
	}

	/* 만족 여부를 검사 */
	public boolean isSatisfied(Map<Character, Integer> map, String s) {
		// 두 프렌즈간 거리
		int distance = Math.abs(idx[map.get(s.charAt(0))] - idx[map.get(s.charAt(2))]);
		// 비교 칸 수
		// -> 바로 옆일 경우 0인데 인덱스상으론 1 차이 나기 때문에 1 더해줘야 함
		int comp = s.charAt(4) - '0' + 1;

		switch (s.charAt(3)) {
		case '=':
			if (distance == comp) {
				return true;
			}
			break;
		case '<':
			if (distance < comp) {
				return true;
			}
			break;
		case '>':
			if (distance > comp) {
				return true;
			}
			break;
		}

		return false;
	}
}