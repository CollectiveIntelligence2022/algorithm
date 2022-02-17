import java.io.*;
import java.util.*;

public class Main_s1_14888_연산자끼워넣기 {
	static int[] num, op;
	static int N, max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		num = new int[N];
		op = new int[4];

		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			num[i] = Integer.parseInt(st.nextToken());
		}

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < 4; i++) {
			op[i] = Integer.parseInt(st.nextToken());
		}

		perm(1, num[0]);
		System.out.println(max);
		System.out.println(min);
		br.close();
	}

	// 순열을 통해 모든 경우의 수를 연산하여 max, min 값을 갱신
	private static void perm(int idx, int res) {
		if (idx == N) {
			max = Math.max(max, res);
			min = Math.min(min, res);
			return;
		}

		for (int i = 0; i < 4; i++) {
			// 연산자 사용 횟수가 0 -> 가지치기
			if (op[i] == 0) {
				continue;
			}

			// 해당 연산자 사용
			op[i]--;
			
			switch (i) {
			case 0:
				perm(idx + 1, res + num[idx]);
				break;
			case 1:
				perm(idx + 1, res - num[idx]);
				break;
			case 2:
				perm(idx + 1, res * num[idx]);
				break;
			case 3:
				perm(idx + 1, res / num[idx]);
				break;
			}
			// 다음 탐색을 위해 복구
			op[i]++;
		}
	}
}