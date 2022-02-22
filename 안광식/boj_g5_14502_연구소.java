package problems;

import java.io.*;
import java.util.*;

public class boj_g5_14502_연구소 {
	static int N, M, answer;
	static int[] di = { -1, 0, 1, 0 };
	static int[] dj = { 0, 1, 0, -1 };
	static int[][] mapOrigin;
	static ArrayDeque<int[]> virusOrigin;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		mapOrigin = new int[N][M];
		virusOrigin = new ArrayDeque<>();
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				mapOrigin[i][j] = Integer.parseInt(st.nextToken());
				if (mapOrigin[i][j] == 2)
					virusOrigin.offer(new int[] { i, j });
			}
		}
		
		answer = 0;
		Solution();
		
		System.out.println(answer);
		
		br.close();
	}
	
	private static void Solution() {
		// 벽 세우기,, 3개,, dfs..?
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (mapOrigin[i][j] == 0) {
					mapOrigin[i][j] = 1;
					dfs(i, j, 1);
					mapOrigin[i][j] = 0;
				}
			}
		}
	}

	private static void dfs(int x, int y, int wallCnt) {
		if (wallCnt == 3) {
//			printmap(mapOrigin);
			getSafeZone();
			return;
		}
		
		for (int i = x; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (mapOrigin[i][j] == 0) {
					mapOrigin[i][j] = 1;
					dfs(i, j, wallCnt + 1);
					mapOrigin[i][j] = 0;
				}
			}
		}
	}

	private static void printmap(int[][] map) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				System.out.print(map[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println();
	}

	private static void getSafeZone() {
		ArrayDeque<int[]> virus = virusOrigin.clone();
		
		int[][] map = deepCopy();
		
		while(!virus.isEmpty()) {
			int[] cur = virus.poll();
			
			for (int d = 0; d < 4; d++) {
				int nx = cur[0] + di[d];
				int ny = cur[1] + dj[d];
				
				if (nx < 0 || nx >= N || ny < 0 || ny >= M || map[nx][ny] != 0)
					continue;
				
				map[nx][ny] = 2;
				virus.offer(new int[] { nx, ny });
			}
		}
		
		int safeZoneCnt = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == 0)
					safeZoneCnt++;
			}
		}
		
		answer = Math.max(answer, safeZoneCnt);
	}

	private static int[][] deepCopy() {
		int[][] map = new int[N][M];
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				map[i][j] = mapOrigin[i][j];
			}
		}
		
		return map;
	}
}

// 바이러스는 상하좌우 인접 칸으로 퍼져나감
// 벽 3개만 세워서 최대한 막기
// 오리지널 맵 따로 기억해둬야할듯.. 바이러스 퍼뜨리는 맵은 딥카피로 사용
// 안전 영역 최댓값 구하기



