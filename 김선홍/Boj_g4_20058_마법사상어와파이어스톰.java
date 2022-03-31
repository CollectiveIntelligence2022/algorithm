import java.io.*;
import java.util.*;

public class Boj_g4_20058_마법사상어와파이어스톰 {
	static int[][] map, tmp;
	static boolean[][] visit;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int[] level;
	static int N, Q, max;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = 1 << Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());

		map = new int[N][N];
		tmp = new int[N][N];
		level = new int[Q];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < Q; i++) {
			level[i] = Integer.parseInt(st.nextToken());
		}

		solution();
		br.close();
	}

	// 90도 회전
	// 0: 1*1(변화 x), 1: 2*2, 2: 4*4, ..., n: N*N
	private static void solution() {
		for (int i = 0; i < Q; i++) {
			// 1. 주어진 단계만큼 파이어스톰 시전
			if (level[i] > 0) {
				setFireStorm(1 << level[i]);
			}
			// 2. 칸 체크해서 얼음 양 줄이기
			decreaseIce();
		}
		// Q번 시전 완료했다면
		// 3. 남아있는 얼음의 총합
		// 4. 남아있는 얼음 중 가장 큰 덩어리가 차지하는 칸의 개수
		StringBuilder sb = new StringBuilder();
		getMaxCount();
		sb.append(getSum()).append("\n").append(max);
		System.out.print(sb);
	}

	/* s*s 구간으로 나눈다 */
	private static void setFireStorm(int s) {
		for (int i = 0; i < N; i += s) {
			for (int j = 0; j < N; j += s) {
				rotate(i, j, s);
			}
		}
	}

	/* 시계방향으로 90도 회전 */
	private static void rotate(int x, int y, int s) {
		// 전역에 한번만 초기화해줘도 해당 구간만 사용하기 때문에 매번 초기화할 필요 X
		// int[][] tmp = new int[s][s];
		
		for (int i = x; i < x + s; i++) {
			for (int j = y; j < y + s; j++) {
				tmp[j - y][x + s - 1 - i] = map[i][j];
			}
		}

		for (int i = x; i < x + s; i++) {
			for (int j = y; j < y + s; j++) {
				map[i][j] = tmp[i - x][j - y];
			}
		}
	}

	/* 인접한 칸에 있는 얼음 칸 개수가 3개 미만이면 1 감소 */
	private static void decreaseIce() {
		boolean[][] checked = new boolean[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] == 0) {
					continue;
				}

				int cnt = 0;
				for (int d = 0; d < 4; d++) {
					int nx = i + dx[d];
					int ny = j + dy[d];

					if (isCheck(nx, ny) && map[nx][ny] > 0) {
						cnt++;
					}
				}

				if (cnt < 3) {
					checked[i][j] = true;
				}
			}
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (checked[i][j]) {
					map[i][j]--;
				}
			}
		}
	}

	/* 얼음의 총합 */
	private static int getSum() {
		int sum = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				sum += map[i][j];
			}
		}
		return sum;
	}

	/* 남아있는 얼음 중 가장 큰 덩어리가 차지하는 칸의 개수 */
	private static void getMaxCount() {
		visit = new boolean[N][N];

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (!visit[i][j] && map[i][j] > 0) {
					max = Math.max(max, dfs(i, j));
				}
			}
		}
	}

	/* dfs로 얼음 덩어리 칸의 개수 카운팅 */
	private static int dfs(int x, int y) {
		int cnt = 1;
		visit[x][y] = true;

		for (int d = 0; d < 4; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];

			if (isCheck(nx, ny) && !visit[nx][ny] && map[nx][ny] > 0) {
				cnt += dfs(nx, ny);
			}
		}

		return cnt;
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}
}