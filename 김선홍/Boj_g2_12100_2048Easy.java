import java.io.*;
import java.util.*;

public class Boj_g2_12100_2048Easy {
	static int N, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		int[][] map = new int[N][N];

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		brute(map, 0);
		System.out.println(res);
		br.close();
	}

	/* 순서에 영향을 받고, 중복 방향 사용 가능 -> 중복순열 */
	private static void brute(int[][] map, int cnt) {
		if (cnt == 5) {
			res = Math.max(res, getMaxNum(map));
			return;
		}

		for (int i = 0; i < 4; i++) {
			int[][] tmap = copyArray(map);
			switch (i) {
			case 0:
				moveUp(tmap);
				break;
			case 1:
				moveDown(tmap);
				break;
			case 2:
				moveLeft(tmap);
				break;
			case 3:
				moveRight(tmap);
				break;
			}
			brute(tmap, cnt + 1);
		}
	}

	/* 블록을 위로 모은다 */
	private static void moveUp(int[][] map) {
		for (int j = 0; j < N; j++) {
			int pre = 0;

			for (int i = 1; i < N; i++) {
				// 이동할 블록 없음
				if (map[i][j] == 0) {
					continue;
				}

				// 이동하려는 곳이 빈칸
				if (map[pre][j] == 0) {
					map[pre][j] = map[i][j];
					map[i][j] = 0;
				} else {
					// 합쳐지려는 두 블록의 값이 같다면 합친다
					if (map[pre][j] == map[i][j]) {
						map[pre++][j] *= 2;
						map[i][j] = 0;
					} else {
						// 이동 가능하다면 이동시키고 그렇지 않다면 이전 좌표만 한칸 땡긴다
						if (pre++ < i - 1) {
							map[pre][j] = map[i][j];
							map[i][j] = 0;
						}
					}
				}
			}
		}
	}

	/* 블록을 아래로 모은다 */
	private static void moveDown(int[][] map) {
		for (int j = 0; j < N; j++) {
			int pre = N - 1;

			for (int i = N - 2; i >= 0; i--) {
				// 이동할 블록 없음
				if (map[i][j] == 0) {
					continue;
				}

				// 이동하려는 곳이 빈칸
				if (map[pre][j] == 0) {
					map[pre][j] = map[i][j];
					map[i][j] = 0;
				} else {
					// 합쳐지려는 두 블록의 값이 같다면 합친다
					if (map[pre][j] == map[i][j]) {
						map[pre--][j] *= 2;
						map[i][j] = 0;
					} else {
						// 이동 가능하다면 이동시키고 그렇지 않다면 이전 좌표만 한칸 땡긴다
						if (pre-- > i + 1) {
							map[pre][j] = map[i][j];
							map[i][j] = 0;
						}
					}
				}
			}
		}
	}

	/* 블록을 왼쪽으로 모은다 */
	private static void moveLeft(int[][] map) {
		for (int i = 0; i < N; i++) {
			int pre = 0;

			for (int j = 1; j < N; j++) {
				// 이동할 블록 없음
				if (map[i][j] == 0) {
					continue;
				}

				// 이동하려는 곳이 빈칸
				if (map[i][pre] == 0) {
					map[i][pre] = map[i][j];
					map[i][j] = 0;
				} else {
					// 합쳐지려는 두 블록의 값이 같다면 합친다
					if (map[i][pre] == map[i][j]) {
						map[i][pre++] *= 2;
						map[i][j] = 0;
					} else {
						// 이동 가능하다면 이동시키고 그렇지 않다면 이전 좌표만 한칸 땡긴다
						if (pre++ < j - 1) {
							map[i][pre] = map[i][j];
							map[i][j] = 0;
						}
					}
				}
			}
		}
	}

	/* 블록을 오른쪽으로 모은다 */
	private static void moveRight(int[][] map) {
		for (int i = 0; i < N; i++) {
			int pre = N - 1;

			for (int j = N - 2; j >= 0; j--) {
				// 이동할 블록 없음
				if (map[i][j] == 0) {
					continue;
				}

				// 이동하려는 곳이 빈칸
				if (map[i][pre] == 0) {
					map[i][pre] = map[i][j];
					map[i][j] = 0;
				} else {
					// 합쳐지려는 두 블록의 값이 같다면 합친다
					if (map[i][pre] == map[i][j]) {
						map[i][pre--] *= 2;
						map[i][j] = 0;
					} else {
						// 이동 가능하다면 이동시키고 그렇지 않다면 이전 좌표만 한칸 땡긴다
						if (pre-- > j + 1) {
							map[i][pre] = map[i][j];
							map[i][j] = 0;
						}
					}
				}
			}
		}
	}

	/* 원본 배열 복사 */
	private static int[][] copyArray(int[][] map) {
		int[][] tmap = new int[N][N];
		for (int i = 0; i < N; i++) {
			System.arraycopy(map[i], 0, tmap[i], 0, N);
		}
		return tmap;
	}

	/* 가장 큰 숫자를 찾는다 */
	private static int getMaxNum(int[][] map) {
		int max = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				max = Math.max(max, map[i][j]);
			}
		}
		return max;
	}
}