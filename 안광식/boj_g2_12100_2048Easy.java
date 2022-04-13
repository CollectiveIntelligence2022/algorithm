package problems;

import java.io.*;
import java.util.*;

public class boj_g2_12100_2048Easy {
	static int N, max;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		int[][] map = new int[N][N];
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " "); 
			for (int j = 0; j < N; j++ ) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		max = 0;
//		down(map);
//		for (int[] arr : map) {
//			System.out.println(Arrays.toString(arr));
//		}
		for (int d = 0; d < 4; d++)
			Solution(0, d, map);
		
		System.out.println(max);
		
		br.close();
	}
	
	private static void Solution(int cnt, int dir, int[][] map) {
		// 종료
		if (cnt == 5) {
			updateMaxBlock(map);
			return;
		}
		
		// 현재 방향으로 밀기
		int[][] copyMap = deepCopy(map);
		switch (dir) {
		case 0:
			up(copyMap);
			break;
		case 1:
			down(copyMap);
			break;
		case 2:
			right(copyMap);
			break;
		case 3:
			left(copyMap);
			break;
		}
		
		// 다음 네 방향
		for (int d = 0; d < 4; d++) 
			Solution(cnt + 1, d, copyMap);
	}

	private static void up(int[][] map) {
		ArrayDeque<Integer> dq = new ArrayDeque<>();
		for (int j = 0; j < N; j++) {
			for (int i = 0; i < N; i++) {
				if (map[i][j] == 0)
					continue;
				dq.offer(map[i][j]);
				map[i][j] = 0;
			}
			
			int idx = 0;
			while (!dq.isEmpty()) {
				int cur = dq.poll();
				
				if (map[idx][j] == 0) {
					map[idx][j] = cur;
					continue;
				}
				
				if (map[idx][j] == cur) {
					map[idx++][j] += cur;
					continue;
				}
				
				map[++idx][j] = cur;
			}
		}
	}

	private static void down(int[][] map) {
		ArrayDeque<Integer> dq = new ArrayDeque<>();
		for (int j = 0; j < N; j++) {
			for (int i = N - 1; i >= 0; i--) {
				if (map[i][j] == 0)
					continue;
				dq.offer(map[i][j]);
				map[i][j] = 0;
			}
			
			int idx = N - 1;
			while (!dq.isEmpty()) {
				int cur = dq.poll();
				
				if (map[idx][j] == 0) {
					map[idx][j] = cur;
					continue;
				}
				
				if (map[idx][j] == cur) {
					map[idx--][j] += cur;
					continue;
				}
				
				map[--idx][j] = cur;
			}
		}
	}

	private static void right(int[][] map) {
		ArrayDeque<Integer> dq = new ArrayDeque<>();
		for (int i = 0; i < N; i++) {
			for (int j = N - 1; j >= 0; j--) {
				if (map[i][j] == 0)
					continue;
				dq.offer(map[i][j]);
				map[i][j] = 0;
			}
			
			int idx = N - 1;
			while (!dq.isEmpty()) {
				int cur = dq.poll();
				
				if (map[i][idx] == 0) {
					map[i][idx] = cur;
					continue;
				}
				
				if (map[i][idx] == cur) {
					map[i][idx--] += cur;
					continue;
				}

				map[i][--idx] = cur;
			}
		}
	}

	private static void left(int[][] map) {
		ArrayDeque<Integer> dq = new ArrayDeque<>();
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] == 0)
					continue;
				dq.offer(map[i][j]);
				map[i][j] = 0;
			}
			
			int idx = 0;
			while (!dq.isEmpty()) {
				int cur = dq.poll();
				
				if (map[i][idx] == 0) {
					map[i][idx] = cur;
					continue;
				}
				
				if (map[i][idx] == cur) {
					map[i][idx++] += cur;
					continue;
				}
				
				map[i][++idx] = cur;
			}
		}
	}

	private static void updateMaxBlock(int[][] map) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				max = Math.max(max, map[i][j]);
			}
		}
	}

	private static int[][] deepCopy(int[][] map) {
		int[][] copyMap = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				copyMap[i][j] = map[i][j];
			}
		}
		
		return copyMap;
	}
}

// 상하좌우 dfs?
// 갈 때 마다 맵 카피해주기
// 