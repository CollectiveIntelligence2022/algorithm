package problems;

import java.io.*;
import java.util.*;

public class boj_g5_15683_감시 {
	static int N, M, min;
	static int[] di = { -1, 0, 1, 0 };
	static int[] dj = { 0, 1, 0, -1 };
	static int[][] originalMap;
	static ArrayList<int[]> cctvs;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		originalMap = new int[N][M];
		cctvs = new ArrayList<>();
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				originalMap[i][j] = Integer.parseInt(st.nextToken());
				if (originalMap[i][j] > 0 && originalMap[i][j] < 6) {
					cctvs.add(new int[] { i, j });
				}
			}
		}
		
		min = Integer.MAX_VALUE;
		Solution(0, originalMap);
		
		System.out.println(min);
		
		br.close();
	}
	
	private static void Solution(int idx, int[][] map) {
		// 모든 cctv 확인
		if (idx == cctvs.size()) {
			getMin(map);
			return;
		}
		
		int cur[] = cctvs.get(idx);
		int x = cur[0];
		int y = cur[1];
		int cctv = map[x][y];
		
		switch (cctv) {
		// 앞
		case 1:
			// 4가지 경우 다 보기
			for (int d = 0; d < 4; d++) {
				int[][] copyMap = deepCopy(map);
				
				set(x, y, d, copyMap);
				
				Solution(idx + 1, copyMap);
			}
			
			break;
		
		// 양옆
		case 2:
			// 가로 or 세로
			for (int d = 0; d < 2; d++) {
				int[][] copyMap = deepCopy(map);
				
				set(x, y, d, copyMap);
				set(x, y, (d + 2) % 4, copyMap);
				
				Solution(idx + 1, copyMap);
			}
			
			break;
			
		// ㄱ 모양
		case 3:
			// 4가지 경우 다 보기
			for (int d = 0; d < 4; d++) {
				int[][] copyMap = deepCopy(map);
				
				set(x, y, d, copyMap);
				set(x, y, (d + 1) % 4, copyMap);
				
				Solution(idx + 1, copyMap);
			}
			
			break;
		
		// 3방향
		case 4:
			// 4가지 경우 다 보기
			for (int d = 0; d < 4; d++) {
				int[][] copyMap = deepCopy(map);
				
				set(x, y, d, copyMap);
				set(x, y, (d + 1) % 4, copyMap);
				set(x, y, (d + 2) % 4, copyMap);
				
				Solution(idx + 1, copyMap);
			}
			
			break;
		
		// 4방향
		case 5:
			int[][] copyMap = deepCopy(map);
			
			set(x, y, 0, copyMap);
			set(x, y, 1, copyMap);
			set(x, y, 2, copyMap);
			set(x, y, 3, copyMap);
			
			Solution(idx + 1, copyMap);
			
			break;

		default:
			break;
		}
		
	}
	
	// 특정 방향으로 '#' 깔기
	private static void set(int x, int y, int d, int[][] map) {
		while (true) {
			x += di[d];
			y += dj[d];
			
			// 끝까지 다 깔았거나 벽 만나면 스톱
			if (x < 0 || x >= N || y < 0 || y >= M || map[x][y] == 6)
				break;
			
			// 카메라는 넘어가기
			if (map[x][y] > 0 && map[x][y] < 6)
				continue;
			
			map[x][y] = '#';
		}
	}
	
	private static int[][] deepCopy(int[][] map) {
		int[][] copyMap = new int[N][M];
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				copyMap[i][j] = map[i][j];
			}
		}
		
		return copyMap;
	}

	private static void getMin(int[][] map) {
		int cnt = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == 0) {
					cnt++;
				}
			}
		}
		
		min = Integer.min(min, cnt);
	}
}

// cctv
// 1 : 앞
// 2 : 양옆
// 3 : 앞,옆 (직각)
// 4 : 3방향
// 5 : 4방향
// 6은 벽

// 사각지대가 최소가 되는 경우 사각지대 크기 구하라
// 벽 통과 불가능, CCTV끼린 통과 가능