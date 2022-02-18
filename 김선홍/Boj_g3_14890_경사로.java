import java.io.*;
import java.util.*;

public class Boj_g3_14890_경사로 {
	static int N, L, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());

		int[][] rMap = new int[N][N];
		int[][] cMap = new int[N][N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				rMap[i][j] = cMap[j][i] = Integer.parseInt(st.nextToken());
			}
		}

		for (int i = 0; i < N; i++) {
			buildSlope(rMap[i]);
			buildSlope(cMap[i]);
		}

		System.out.println(res);
		br.close();
	}

	// N*N
	// 길 2N개
	// 경사로 높이 차 1
	// L개의 연속된 칸(최소 L개 만족)
	// 상향, 하향 나누어서 생각
	private static void buildSlope(int[] map) {
		int i = 1, curHeight = map[0], len = 1;

		while (i < N) {
			if (Math.abs(curHeight - map[i]) > 1) {
				return;
			}

			// 오르막 경사로 건설 가능
			if (curHeight < map[i]) {
				// 길이 조건 불충족
				if (len < L) {
					return;
				}

				i++;
				curHeight++;
				len = 1;
			} else if (curHeight > map[i]) {// 내리막 경사로 건설 가능
				// 현재 경사로 지점으로부터 또 다른 경사로가 나타날 때까지 땅 너비를 카운팅
				int cnt = 0;

				for (int k = i; k < N; k++) {
					// 또 다른 경사로가 나타남
					if (map[k] != curHeight - 1) {
						break;
					}

					// 경사로 건설 완료
					if (++cnt == L) {
						break;
					}
				}

				// 경사로 건설 불가
				if (cnt < L) {
					return;
				}

				// 다음 탐색 시 경사로 건설 다음 부분부터
				i += L;
				curHeight--;
				len = 0;

			} else {// 땅 높이가 같음
				i++;
				len++;
			}
		}

		res++;
	}
}
