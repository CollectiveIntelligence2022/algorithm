package problems;

import java.util.*;
import java.io.*;

public class boj_g4_마법사상어와파이어스톰 {
	static int N, Q, SIZE, totalSum, maxGroupCnt;
	static int[] di = { -1, 0, 1, 0 };
	static int[] dj = { 0, 1, 0, -1 };
	static int[][] map;
	static boolean[][] visited;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		SIZE = (int) Math.pow(2, N);
		
		map = new int[SIZE][SIZE];
		for (int i = 0; i < SIZE; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < SIZE; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < Q; i++) {
			int L = Integer.parseInt(st.nextToken());
			fireStorm(L);
		}
		
		getAnswer();
		
		System.out.println(totalSum);
		System.out.println(maxGroupCnt);
		
		br.close();
	}
	
	private static void fireStorm(int L) {
		int size = (int) Math.pow(2, L);
		for (int i = 0; i < SIZE; i += size) {
			for (int j = 0; j < SIZE; j += size) {
				rotate(i, j, size);
			}
		}
		
		melt();
	}

	private static void rotate(int startX, int startY, int size) {
		if (size < 2)
			return;
		
		for (int i = 0; i < size - 1; i++) {
			int tmp = map[startX][startY + i];
			map[startX][startY + i] = map[startX + size - 1 - i][startY];
			map[startX + size - 1 - i][startY] = map[startX + size - 1][startY + size - 1 - i];
			map[startX + size - 1][startY + size - 1 - i] = map[startX + i][startY + size - 1];
			map[startX + i][startY + size - 1] = tmp;
		}

		rotate(startX + 1, startY + 1, size - 2);
	}

	private static void melt() {
		ArrayDeque<int[]> dq = new ArrayDeque<>();
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (map[i][j] == 0)
					continue;
				
				int cnt = 0;
				
				for (int d = 0; d < 4; d++) {
					int nx = i + di[d];
					int ny = j + dj[d];
					
					if (nx < 0 || nx >= SIZE || ny < 0 || ny >= SIZE || map[nx][ny] == 0)
						continue;
					
					cnt++;
				}
				
				if (cnt < 3)
					dq.offer(new int[] { i, j });
			}
		}
		
		while (!dq.isEmpty()) {
			int[] cur = dq.poll();
			map[cur[0]][cur[1]]--;
		}
	}
	
	private static void getAnswer() {
		totalSum = 0;
		maxGroupCnt = 0;
		visited = new boolean[SIZE][SIZE];
		
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (visited[i][j] || map[i][j] == 0)
					continue;
				bfs(i, j);
			}
		}
	}

	private static void bfs(int r, int c) {
		ArrayDeque<int[]> dq = new ArrayDeque<>();
		
		dq.offer(new int[] { r, c });
		visited[r][c] = true;
		totalSum += map[r][c];
		
		int cnt = 1;
		while (!dq.isEmpty()) {
			int[] cur = dq.poll();
			
			for (int d = 0; d < 4; d++) {
				int nx = cur[0] + di[d];
				int ny = cur[1] + dj[d];
				
				if (nx < 0 || nx >= SIZE || ny < 0 || ny >= SIZE || visited[nx][ny] || map[nx][ny] == 0)
					continue;
				
				dq.offer(new int[] { nx, ny });
				visited[nx][ny] = true;
				totalSum += map[nx][ny];
				cnt++;
			}
		}
		
		maxGroupCnt = Integer.max(maxGroupCnt, cnt);
	}
}

// 파이어스톰 시전시 단계 L 설정 => 2^L * 2^L 크기 부분 격자로 나눠서 격자별로 90도 회전
// 회전 후 최소 3개 이상의 얼음칸과 인접해있지 않다면 얼음 양 1 줄어든다.
// Q번 시전 후 답 출력
// 1.남아있는 얼음 A[r][c]의 합
// 2.남아있는 얼음 중 가장 큰 덩어리가 차지하는 칸의 개수
// bfs 돌면서 가장 큰 덩어리 구하고 하는김에 얼음 합도 구하기

