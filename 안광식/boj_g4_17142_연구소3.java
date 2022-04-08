package problems;

import java.io.*;
import java.util.*;

public class boj_g4_17142_연구소3 {
	static int N, M, emptyCnt, minTime;
	static int[] di = { -1, 0, 1, 0 };
	static int[] dj = { 0, 1, 0, -1 };
	static int[] numbers;
	static int[][] map;
	static ArrayList<int[]> virusList;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		virusList = new ArrayList<>();
		emptyCnt = 0;
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				
				if (map[i][j] == 2) 
					virusList.add(new int[] { i, j });
				
				if (map[i][j] == 0)
					emptyCnt++;
			}
		}
		
		minTime = Integer.MAX_VALUE;
		numbers = new int[M];
		comb(0, 0);
		System.out.println(minTime == Integer.MAX_VALUE ? -1 : minTime);
		
		br.close();
	}
	
	private static void comb(int cnt, int start) {
		if (cnt == M) {
			bfs();
			
			return;
		}
		
		for (int i = start; i < virusList.size(); i++) {
			numbers[cnt] = i;
			comb(cnt + 1, i + 1);
		}
	}

	private static void bfs() {
		int cnt = 0;
		int time = 0;
		
		ArrayDeque<int[]> dq = new ArrayDeque<>();
		boolean[][] visited = new boolean[N][N];
		
		for (int i = 0; i < M; i++) {
			int[] cur = virusList.get(numbers[i]);
			
			dq.offer(new int[] { cur[0], cur[1] });
			visited[cur[0]][cur[1]] = true;
		}
		
		while (!dq.isEmpty()) {
			int size = dq.size();
			
			for (int i = 0; i < size; i++) {
				int[] cur = dq.poll();
				
				if (map[cur[0]][cur[1]] == 0)
					cnt++;

				if (cnt == emptyCnt) {
					minTime = Math.min(minTime, time);
					return;
				}
				
				for (int d = 0; d < 4; d++) {
					int nx = cur[0] + di[d];
					int ny = cur[1] + dj[d];
					
					if (isOut(nx, ny) || visited[nx][ny] || map[nx][ny] == 1)
						continue;
					
					dq.offer(new int[] { nx, ny });
					visited[nx][ny] = true;
				}
			}
			
			time++;
		}
	}

	private static boolean isOut(int nx, int ny) {
		return nx < 0 || nx >= N || ny < 0 || ny >= N;
	}
}
