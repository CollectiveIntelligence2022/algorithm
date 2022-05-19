import java.io.*;
import java.util.*;

public class Main_lv2_전광판 {
	static int[] num, answer;
	// 0 ~ 10
	// 10: 유효한 자릿수에 0이 있을 경우
	static boolean[][] onSwitch = { { false, false, false, false, false, false, false }, { false, true, true, false, false, false, false },
			{ true, true, false, true, true, false, true }, { true, true, true, true, false, false, true }, { false, true, true, false, false, true, true },
			{ true, false, true, true, false, true, true }, { true, false, true, true, true, true, true }, { true, true, true, false, false, true, false },
			{ true, true, true, true, true, true, true }, { true, true, true, true, false, true, true }, { true, true, true, true, true, true, false } };
	static String A, B;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());

		while (T-- > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			A = st.nextToken();
			B = st.nextToken();

			sb.append(solution()).append("\n");
		}

		System.out.print(sb);
		br.close();
	}

	/** 
	 * 1. 자릿수마다 숫자를 저장
	 * 2. 켜거나 꺼야 하는 횟수를 셈
	 */
	private static int solution() {
		parseNumber();
		return getCount();
	}

	// 입력받은 문자열의 각 자릿수를 정수로 변환해서 저장
	private static void parseNumber() {
		num = new int[5];
		answer = new int[5];

		int idx = 4;
		for (int i = A.length() - 1; i >= 0; i--) {
			int n = A.charAt(i) - '0';
			if (n == 0) {
				n = 10;
			}
			
			num[idx--] = n;
		}

		idx = 4;
		for (int i = B.length() - 1; i >= 0; i--) {
			int n = B.charAt(i) - '0';
			if (n == 0) {
				n = 10;
			}
			
			answer[idx--] = n;
		}
	}

	// 횟수 카운팅
	private static int getCount() {
		int res = 0;

		for (int i = 0; i < 5; i++) {
			boolean[] onA = new boolean[7];
			boolean[] onB = new boolean[7];

			System.arraycopy(onSwitch[num[i]], 0, onA, 0, 7);
			System.arraycopy(onSwitch[answer[i]], 0, onB, 0, 7);

			for (int j = 0; j < 7; j++) {
				// 두 경우가 다르다면 횟수 증가
				if (onA[j] ^ onB[j]) {
					res++;
				}
			}
		}

		return res;
	}
}