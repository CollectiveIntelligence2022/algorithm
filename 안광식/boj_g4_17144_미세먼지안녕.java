package problems;

import java.io.*;
import java.util.*;

public class boj_g4_17144_미세먼지안녕 {
	static int R, C, T, answer, r1, c1, r2, c2;
	static int[] di = { -1, 0, 1, 0 };
	static int[] dj = { 0, 1, 0, -1 };
	static int[][] map;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine(), " ");
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		
		map = new int[R][C];
		r1 = c1 = r2 = c2 = -1;
		for (int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < C; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == -1) {
					if (r1 == -1 && c1 == -1) {
						r1 = i;
						c1 = j;
						continue;
					}
					r2 = i;
					c2 = j;
				}
			}
		}
		
		Solution();
		System.out.println(answer);
		
		br.close();
	}
	
	private static void Solution() {
		while (T --> 0) {
			spread();
			airClean1();
			airClean2();
		}
		getAnswer();
	}

	private static void spread() {
		// 1.미세먼지 확산, 모든 칸에서 동시에 발생
		//  - 인접 네 방향으로 확산
		//  - 인접 칸에 공기청정기가 있거나 맵 밖으로 나가면 확산 일어나지 않음
		//  - 확산되는 양은 A(r,c)/5 소수점 버림
		//  - 기존칸에 남는 양은 A(r,c) - (A(r,c)/5) * 확산된 방향의 개수
		ArrayDeque<int[]> dq = new ArrayDeque<>();
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (map[i][j] <= 0)
					continue;
				
				int amount = map[i][j] / 5;
				int cnt = 0;
				for (int d = 0; d < 4; d++) {
					int nx = i + di[d];
					int ny = j + dj[d];
					
					if (nx < 0 || nx >= R || ny < 0 || ny >= C || map[nx][ny] == -1)
						continue;
					
					dq.offer(new int[] { nx, ny, amount });
					cnt++;
				}
				
				dq.offer(new int[] { i, j, map[i][j] - (amount * cnt) });
				map[i][j] = 0;
			}
		}
		
		while (!dq.isEmpty()) {
			int[] cur = dq.poll();
			map[cur[0]][cur[1]] += cur[2];
		}
	}

	// 위쪽 공기청정기, 반시계
	private static void airClean1() {
		int r = r1;
		int c = c1;
		int prev = 0;
		// 우
		while (++c < C) {
			int tmp = map[r][c];
			map[r][c] = prev;
			prev = tmp;
		}
		c--;
		
		// 상
		while (--r >= 0) {
			int tmp = map[r][c];
			map[r][c] = prev;
			prev = tmp;
		}
		r++;
		
		// 좌
		while (--c >= 0) {
			int tmp = map[r][c];
			map[r][c] = prev;
			prev = tmp;
		}
		c++;
		
		// 하
		while (++r < r1) {
			int tmp = map[r][c];
			map[r][c] = prev;
			prev = tmp;
		}
	}

	// 아래쪽 공기청정기, 시계
	private static void airClean2() {
		int r = r2;
		int c = c2;
		int prev = 0;
		// 우
		while (++c < C) {
			int tmp = map[r][c];
			map[r][c] = prev;
			prev = tmp;
		}
		c--;

		// 하
		while (++r < R) {
			int tmp = map[r][c];
			map[r][c] = prev;
			prev = tmp;
		}
		r--;

		// 좌
		while (--c >= 0) {
			int tmp = map[r][c];
			map[r][c] = prev;
			prev = tmp;
		}
		c++;
		
		// 상
		while (--r > r2) {
			int tmp = map[r][c];
			map[r][c] = prev;
			prev = tmp;
		}
	}

	private static void getAnswer() {
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (map[i][j] > 0)
					answer += map[i][j];
			}
		}
	} 
}

// R*C
// 1.미세먼지 확산, 모든 칸에서 동시에 발생
//  - 인접 네 방향으로 확산
//  - 인접 칸에 공기청정기가 있거나 맵 밖으로 나가면 확산 일어나지 않음
//  - 확산되는 양은 A(r,c)/5 소수점 버림
//  - 기존칸에 남는 양은 A(r,c) - (A(r,c)/5) * 확산된 방향의 개수
// 2.공기청정기 작동
//  - 바람이 나옴
//  - 위쪽 공기청정기 바람은 반시계로 순환, 아래쪽은 시계방향 순환
//  - 바람이 불면 미세먼지가 바람 방향대로 한 칸씩 이동
//  - 공기청정기로 들어오는 먼지는 모두 정화됨











