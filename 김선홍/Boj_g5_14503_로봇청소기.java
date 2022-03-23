import java.io.*;
import java.util.*;

public class Boj_g5_14503_로봇청소기 {
	static int[][] map;
	static int[] dx = { -1, 0, 1, 0 };// 북동남서
	static int[] dy = { 0, 1, 0, -1 };
	static int N, M, rx, ry, rd;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine(), " ");
		rx = Integer.parseInt(st.nextToken());
		ry = Integer.parseInt(st.nextToken());
		rd = Integer.parseInt(st.nextToken());

		map = new int[N][M];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		System.out.println(robotCleaner());
		br.close();
	}

	private static int robotCleaner() {
		boolean[][] isClean = new boolean[N][M];
		isClean[rx][ry] = true;

		int res = 1;
		while (true) {
			int d = rd;
			// 청소 여부를 판단
			boolean isMove = false;

			// 현재 방향의 왼쪽부터 -> 현재 방향에서 반시계 한칸
			for (int i = 0; i < 4; i++) {
				int nd = (d + 3) % 4;
				int nx = rx + dx[nd];
				int ny = ry + dy[nd];

				// 왼쪽 방향에 아직 청소하지 않은 공간이 존재한다면, 그 방향으로 회전한 다음 한 칸을 전진하고 1번부터 진행한다.
				if (!isClean[nx][ny] && map[nx][ny] == 0) {
					res++;
					isMove = true;
					isClean[nx][ny] = true;
					rx = nx;
					ry = ny;
					rd = nd;
					break;
				}

				// 왼쪽 방향에 청소할 공간이 없다면, 그 방향으로 회전하고 2번으로 돌아간다.
				d = nd;
			}

			if (!isMove) {
				int nx = rx - dx[rd];
				int ny = ry - dy[rd];

				// 네 방향 모두 청소가 이미 되어있거나 벽이면서, 뒤쪽 방향이 벽이라 후진도 할 수 없는 경우에는 작동을 멈춘다.
				if (map[nx][ny] == 1) {
					break;
				}

				// 네 방향 모두 청소가 이미 되어있거나 벽인 경우에는, 바라보는 방향을 유지한 채로 한 칸 후진을 하고 2번으로 돌아간다.
				rx = nx;
				ry = ny;
			}
		}

		return res;
	}
}