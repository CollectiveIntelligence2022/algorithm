import java.io.*;
import java.util.*;

public class Boj_g3_1726_로봇 {
	static class Robot {
		int x, y, d, cnt;

		public Robot(int x, int y, int d, int cnt) {
			super();
			this.x = x;
			this.y = y;
			this.d = d;
			this.cnt = cnt;
		}
	}

	static int[][] map;
	static boolean[][][] visit;
	static int[] dx = { -1, 0, 1, 0 };// 북동남서
	static int[] dy = { 0, 1, 0, -1 };
	static int N, M, sx, sy, sd, ex, ey, ed;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		map = new int[N][M];
		visit = new boolean[4][N][M];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		st = new StringTokenizer(br.readLine(), " ");
		sx = Integer.parseInt(st.nextToken()) - 1;
		sy = Integer.parseInt(st.nextToken()) - 1;
		sd = Integer.parseInt(st.nextToken()) - 1;
		if (sd == 0) {
			sd = 1;
		} else if (sd == 1) {
			sd = 3;
		} else if (sd == 3) {
			sd = 0;
		}

		st = new StringTokenizer(br.readLine(), " ");
		ex = Integer.parseInt(st.nextToken()) - 1;
		ey = Integer.parseInt(st.nextToken()) - 1;
		ed = Integer.parseInt(st.nextToken()) - 1;
		if (ed == 0) {
			ed = 1;
		} else if (ed == 1) {
			ed = 3;
		} else if (ed == 3) {
			ed = 0;
		}

		System.out.println(bfs());
		br.close();
	}

	private static int bfs() {
		ArrayDeque<Robot> q = new ArrayDeque<>();
		visit[sd][sx][sy] = true;
		q.offer(new Robot(sx, sy, sd, 0));
		int res = 0;

		while (!q.isEmpty()) {
			Robot r = q.poll();
			// System.out.println(r.x + " " + r.y + " " + r.d + " " + r.cnt);

			// 도착
			if (r.x == ex && r.y == ey && r.d == ed) {
				res = r.cnt;
				break;
			}

			for (int k = 1; k <= 3; k++) {
				int nx = r.x + dx[r.d] * k;
				int ny = r.y + dy[r.d] * k;

				// 배열 밖 or 벽만나면 더이상 탐색 X
				// 방문을 한 지점이라도 3칸까지 안갔다면 탐색은 계속한다
				if (!isCheck(nx, ny) || map[nx][ny] == 1) {
					break;
				}

				if (!visit[r.d][nx][ny]) {
					visit[r.d][nx][ny] = true;
					q.offer(new Robot(nx, ny, r.d, r.cnt + 1));
				}
			}

			// 시계방향
			int nd = (r.d + 1) % 4;
			if (!visit[nd][r.x][r.y]) {
				visit[nd][r.x][r.y] = true;
				q.offer(new Robot(r.x, r.y, nd, r.cnt + 1));
			}

			// 반시계방향
			nd = (r.d + 3) % 4;
			if (!visit[nd][r.x][r.y]) {
				visit[nd][r.x][r.y] = true;
				q.offer(new Robot(r.x, r.y, nd, r.cnt + 1));
			}

			// 180도 -> 명령 2번 사용
			nd = (r.d + 2) % 4;
			if (!visit[nd][r.x][r.y]) {
				visit[nd][r.x][r.y] = true;
				q.offer(new Robot(r.x, r.y, nd, r.cnt + 2));
			}
		}

		return res;
	}

	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < M;
	}
}