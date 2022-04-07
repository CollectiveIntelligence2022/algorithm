import java.io.*;
import java.util.*;

public class Boj_g4_12886_돌그룹 {
	static int A, B, C;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		A = Integer.parseInt(st.nextToken());
		B = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		System.out.println(bfs());
		br.close();
	}

	/*
	 * 수정 후 코드
	 * A, B, C에 대해 각각 나타내자
	 * -> 총 3개의 숫자, A+B+C 최댓값 1500이므로 3*1500크기의 2차원 배열로 표현
	 */
	private static int bfs() {
		boolean[][] visit = new boolean[3][1501];
		ArrayDeque<int[]> q = new ArrayDeque<>();
		visit[0][A] = true;
		visit[1][B] = true;
		visit[2][C] = true;
		q.offer(new int[] { A, B, C });

		while (!q.isEmpty()) {
			int[] cur = q.poll();
			System.out.println(cur[0] + " " + cur[1] + " " + cur[2]);
			
			if (cur[0] == cur[1] && cur[1] == cur[2]) {
				return 1;
			}
			
			for (int i = 0; i < 3; i++) {
				int ni = (i + 1) % 3;
				if (cur[i] != cur[ni]) {
					if (cur[i] > cur[ni]) {
						cur[i] -= cur[ni];
						cur[ni] *= 2;
					} else {
						cur[ni] -= cur[i];
						cur[i] *= 2;
					}

					// 이전에 만든 형태라면 넘어가자
					if (visit[0][cur[0]] && visit[1][cur[1]] && visit[2][cur[2]]) {
						continue;
					}

					visit[0][cur[0]] = true;
					visit[1][cur[1]] = true;
					visit[2][cur[2]] = true;
					q.offer(cur);
				}
			}
		}
		
		return 0;
	}

	/* 
	 * 수정 전 코드 
	 * String으로 상태 표현
	 * why? 500*500*500 visit은 MLE뜰듯?
	 * 그대로 붙여서?
	 * -> 99 9 999와 999 9 99는 다른데 그냥 붙이면 둘다 999999임
	 * set에 숫자 간 공백을 주고 문자열로 저장
	 */
	private static int bfs2() {
		Set<String> set = new HashSet<>();
		ArrayDeque<String> q = new ArrayDeque<>();
		String str = Integer.toString(A) + " " + Integer.toString(B) + " " + Integer.toString(C);
		set.add(str);
		q.offer(str);

		while (!q.isEmpty()) {
			String cur = q.poll();
			String[] s = cur.split(" ");
			int[] arr = new int[3];

			for (int i = 0; i < 3; i++) {
				arr[i] = Integer.parseInt(s[i]);
			}

			if (arr[0] == arr[1] && arr[1] == arr[2]) {
				return 1;
			}

			for (int i = 0; i < 3; i++) {
				if (arr[i] != arr[(i + 1) % 3]) {
					if (arr[i] > arr[(i + 1) % 3]) {
						arr[i] -= arr[(i + 1) % 3];
						arr[(i + 1) % 3] *= 2;
					} else {
						arr[(i + 1) % 3] -= arr[i];
						arr[i] *= 2;
					}

					String nxt = Integer.toString(arr[0]) + " " + Integer.toString(arr[1]) + " " + Integer.toString(arr[2]);
					if (!set.contains(nxt)) {
						set.add(nxt);
						q.offer(nxt);
					}
				}
			}

		}

		return 0;
	}
}