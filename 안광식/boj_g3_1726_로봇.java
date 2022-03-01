package problems;

import java.io.*;
import java.util.*;

public class boj_g3_1726_로봇 {
	static class Robot {
		int x, y, d, cnt;
		public Robot(int x, int y, int d, int cnt) {
			this.x = x;
			this.y = y;
			this.d = d;
			this.cnt = cnt;
		}
	}
	
	static int M, N, answer;
	static Robot start, goal;
	static int[][] map;
	
	static int di[] = { -1, 0, 1, 0 }; // 상 우 하 좌
	static int dj[] = { 0, 1, 0, -1 };
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine(), " ");
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		
		map = new int[M][N];
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		st = new StringTokenizer(br.readLine(), " ");
		int x = Integer.parseInt(st.nextToken()) - 1;
		int y = Integer.parseInt(st.nextToken()) - 1;
		int d = Integer.parseInt(st.nextToken());
		// 동 -> 우
		if (d == 2) // 서 -> 좌
			d = 3;
		else if (d == 3) // 남 -> 하
			d = 2;
		else if (d == 4) // 북 -> 상
			d = 0;
		start = new Robot(x, y, d, 0);
		
		st = new StringTokenizer(br.readLine(), " ");
		x = Integer.parseInt(st.nextToken()) - 1;
		y = Integer.parseInt(st.nextToken()) - 1;
		d = Integer.parseInt(st.nextToken());
		if (d == 2)
			d = 3;
		else if (d == 3)
			d = 2;
		else if (d == 4)
			d = 0;
		goal = new Robot(x, y, d, 0);
		
		answer = 0;
		Solution();
		System.out.println(answer);
		
		br.close();
	}
	
	private static void Solution() {
		ArrayDeque<Robot> dq = new ArrayDeque<>();
		boolean[][][] visited = new boolean[M][N][4];
		
		dq.offer(start);
		visited[start.x][start.y][start.d] = true;
		
		while (!dq.isEmpty()) {
			Robot cur = dq.poll();
			
			// 목표 지점 도착시 스톱
			if (cur.x == goal.x && cur.y == goal.y && cur.d == goal.d) {
				answer = cur.cnt;
				return;
			}
			
			// 명령1 - 현재 위치, 현재 방향에서 갈 수 있는 경우들 체크
			for (int k = 1; k <= 3; k++) {
				int nx = cur.x + (di[cur.d] * k);
				int ny = cur.y + (dj[cur.d] * k);
				
				// 1칸 못가면 2, 3칸 볼거 없음
				if (nx < 0 || nx >= M || ny < 0 || ny >= N || map[nx][ny] == 1) {
					break;
				}
				
				// 방문여부....
				if (visited[nx][ny][cur.d]) {
					continue;
				}
				
				dq.offer(new Robot(nx, ny, cur.d, cur.cnt + 1));
				visited[nx][ny][cur.d] = true;
			}
			
			// 명령2 - 현재 위치에서 갈 수 있는 방향 체크 (left, right)
			
			// 우
			int nd = (cur.d + 1) % 4;
			if (!visited[cur.x][cur.y][nd]) {
				dq.offer(new Robot(cur.x, cur.y, nd, cur.cnt + 1));
				visited[cur.x][cur.y][nd] = true;
			}
			
			// 좌
			nd = (cur.d - 1 + 4) % 4;
			if (!visited[cur.x][cur.y][nd]) {
				dq.offer(new Robot(cur.x, cur.y, nd, cur.cnt + 1));
				visited[cur.x][cur.y][nd] = true;
			}
		}
	}
}

// op
// Go k => 현재 방향으로 k칸 이동 (k는 1~3)
// Turn dir => dir은 left 또는 right로 90도 회전

// map
// 0 : 이동 가능
// 1 : 이동 불가능