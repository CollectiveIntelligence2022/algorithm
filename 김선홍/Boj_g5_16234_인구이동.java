import java.io.*;
import java.util.*;

public class Boj_g5_16234_인구이동 {
	static ArrayDeque<int[]> q = new ArrayDeque<>();
	static int[][] map;
	static boolean[][] visit;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, L, R, sum;
	static boolean isAble;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());

		map = new int[N][N];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		System.out.println(solution());
		br.close();
	}

	//	1. 국경선을 공유하는 두 나라의 인구 차이가 L명 이상, R명 이하라면, 두 나라가 공유하는 국경선을 오늘 하루 동안 연다.
	//	2. 위의 조건에 의해 열어야하는 국경선이 모두 열렸다면, 인구 이동을 시작한다.
	//	3. 국경선이 열려있어 인접한 칸만을 이용해 이동할 수 있으면, 그 나라를 오늘 하루 동안은 연합이라고 한다.
	//	4. 연합을 이루고 있는 각 칸의 인구수는 (연합의 인구수) / (연합을 이루고 있는 칸의 개수)가 된다. 편의상 소수점은 버린다.
	//	5. 연합을 해체하고, 모든 국경선을 닫는다.
	private static int solution() {
		int res = 0;

		while (true) {
			isAble = false;
			visit = new boolean[N][N];

			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (!visit[i][j]) {
						sum = map[i][j];
						q.offer(new int[] { i, j });

						dfs(i, j);
						move();
					}
				}
			}

			// 더 이상 이동이 불가능하면 종료
			if (!isAble) {
				break;
			}

			res++;
		}

		return res;
	}

	// 인구 이동이 불가능 할 때까지 dfs로 계속 탐색
	private static void dfs(int x, int y) {
		visit[x][y] = true;

		for (int d = 0; d < 4; d++) {
			int nx = x + dx[d];
			int ny = y + dy[d];

			if (!check(nx, ny) || visit[nx][ny]) {
				continue;
			}

			int diff = Math.abs(map[x][y] - map[nx][ny]);

			if (L <= diff && diff <= R) {
				isAble = true;
				q.offer(new int[] { nx, ny });
				sum += map[nx][ny];
				dfs(nx, ny);
			}
		}
	}

	private static void move() {
		int size = q.size();

		while (!q.isEmpty()) {
			int[] cur = q.poll();
			map[cur[0]][cur[1]] = sum / size;
		}
	}

	private static boolean check(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}
}
