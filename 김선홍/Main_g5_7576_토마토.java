import java.io.*;
import java.util.*;

public class Main_g5_7576_토마토 {
	static ArrayDeque<int[]> q = new ArrayDeque<>();
	static int[][] map;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		map = new int[N][M];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 1) {// 익은 토마토를 미리 큐에 넣음
					q.offer(new int[] { i, j });
				}
			}
		}

		System.out.println(solution());
		br.close();
	}

	// bfs를 통해 토마토를 익게 확장
	private static int solution() {
		int res = -1;// 다 익고 나서도 카운팅이 되므로 -1부터 시작
		
		while (!q.isEmpty()) {
			res++;
			int size = q.size();

			for (int i = 0; i < size; i++) {
				int[] cur = q.poll();

				for (int d = 0; d < 4; d++) {
					int nx = cur[0] + dx[d];
					int ny = cur[1] + dy[d];

					if (check(nx, ny) && map[nx][ny] == 0) {
						map[nx][ny] = 1;
						q.offer(new int[] { nx, ny });
					}
				}
			}
		}

		if (!isFull()) {// 안 익은 토마토가 있다면 -1
			return -1;
		}

		return res;
	}

	// 토마토가 모두 익었는지 확인
	private static boolean isFull() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == 0) {// 안 익은 토마토 발견
					return false;
				}
			}
		}

		return true;
	}

	// 배열 범위 체크
	private static boolean check(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < M;
	}
}