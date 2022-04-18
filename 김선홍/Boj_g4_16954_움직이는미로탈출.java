import java.io.*;
import java.util.*;

public class Boj_g4_16954_움직이는미로탈출 {
	static ArrayDeque<int[]> wall = new ArrayDeque<>();
	static char[][] map;
	static int[] dx = { 0, -1, -1, 0, 1, 1, 1, 0, -1 };
	static int[] dy = { 0, 0, 1, 1, 1, 0, -1, -1, -1 };

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		map = new char[8][8];

		for (int i = 0; i < 8; i++) {
			map[i] = br.readLine().toCharArray();
		}

		// 행 내림차순으로 탐색하면서 벽 좌표를 저장
		for (int i = 7; i >= 0; i--) {
			for (int j = 0; j < 8; j++) {
				if (map[i][j] == '#') {
					wall.offer(new int[] { i, j });
				}
			}
		}

		System.out.println(bfs());
		br.close();
	}

	/*
	 * 8방향 + 제자리 가능
	 * 최단거리를 구하는 것이 아니라 초마다 움직일 수 있는 곳 다 가봐야 함
	 * -> 이전 시간에 방문했던 칸을 다시 갈 수 있음
	 * 이동을 먼저하고 그다음 벽을 한칸씩 내려줘야 함
	 */
	private static int bfs() {
		ArrayDeque<int[]> q = new ArrayDeque<>();
		q.offer(new int[] { 7, 0 });

		while (!q.isEmpty()) {
			boolean[][] visit = new boolean[8][8];
			int size = q.size();

			for (int i = 0; i < size; i++) {
				int[] cur = q.poll();
				// 현재 위치에 벽이 내려왔으면 이동 불가
				if (map[cur[0]][cur[1]] == '#') {
					continue;
				}
				// 도착
				if (cur[0] == 0 && cur[1] == 7) {
					return 1;
				}

				for (int d = 0; d < 9; d++) {
					int nx = cur[0] + dx[d];
					int ny = cur[1] + dy[d];

					if (isCheck(nx, ny) && !visit[nx][ny] && map[nx][ny] == '.') {
						visit[nx][ny] = true;
						q.offer(new int[] { nx, ny });
					}
				}
			}
			// 모든 이동이 끝났다면 벽을 내린다
			downWall();
		}

		return 0;
	}

	/* 벽을 한 칸 내린다 */
	private static void downWall() {
		int size = wall.size();
		for (int i = 0; i < size; i++) {
			int[] cur = wall.poll();
			if (cur[0] + 1 < 8) {
				map[cur[0]++][cur[1]] = '.';
				map[cur[0]][cur[1]] = '#';
				wall.offer(cur);
			}
		}
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < 8 && 0 <= y && y < 8;
	}
}