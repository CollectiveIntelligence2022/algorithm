package problems;

import java.io.*;
import java.util.*;

public class boj_g5_16234_인구이동 {
	static int N, L, R, days;
	static int[] di = { -1, 0, 1, 0 };
	static int[] dj = { 0, 1, 0, -1 };
	static int[][] map;
	static boolean[][] visited;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		
		for (int i = 0 ; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		Solution();
		
		System.out.println(days);
		
		br.close();
	}
	
	private static void Solution() {
		days = 0;
		
		while (true) {
			int groupCnt = 0;
			
			// 인접 나라끼리 인구 차이가 L 이상 R 이하라면 연합
			// 모든 칸 돌면서 각 칸에 대해 bfs 진행, 이때 이어지는 애들은 모두 연합 (이미 연합한 곳은 visited로 관리)
			visited = new boolean[N][N];
			
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					if (!visited[i][j]) {
						bfs(i, j);
						groupCnt++;
					}
				}
			}
			
			if (groupCnt == N*N)
				return;
			
			days++;
		}
	}

	private static void bfs(int i, int j) {
		int sum = map[i][j];
		int cnt = 1;
		
		ArrayDeque<int[]> dq = new ArrayDeque<>();
		ArrayList<int[]> group = new ArrayList<>();
		dq.offer(new int[] { i, j });
		group.add(new int[] { i, j });
		visited[i][j] = true;
		
		while (!dq.isEmpty()) {
			int[] cur = dq.poll();
			
			for (int d = 0; d < 4; d++) {
				int nx = cur[0] + di[d];
				int ny = cur[1] + dj[d];
				
				if (nx < 0 || nx >= N || ny < 0 || ny >= N || visited[nx][ny])
					continue;
				
				if (Math.abs(map[cur[0]][cur[1]] - map[nx][ny]) < L ||
					Math.abs(map[cur[0]][cur[1]] - map[nx][ny]) > R) {
					continue;
				}
				
				sum += map[nx][ny];
				cnt++;
				dq.offer(new int[] { nx, ny });
				group.add(new int[] { nx, ny });
				visited[nx][ny] = true;
			}
		}
		
		// 연합 완료,, 인구 이동
		int pop = sum / cnt;
		for (int[] cur : group)
			map[cur[0]][cur[1]] = pop;
	}
}

// N*N
// map에 해당 칸 인구 저장
// 하루 동안 인구이동 발생
// 1.국경선 공유하는 나라의 인구 차이가 L 이상 R 이하라면, 둘의 국경선 하루 동안 연다.
// 2.위 조건으로 열어야 하는거 전부 열면 인구이동 시작
// 3.인접 칸으로만 이동 가능, 하루 동안 그 나라는 연합 => 이어져 있으면 하나의 연합,, 다 더해서 개수로 나눠서 전부 저장
// 4.연합을 이루는 칸의 인구 수는 (연합 인구 수)/(연합 이루는 칸의 개수) 소수점 버림
// 5.연합 해체, 모든 국경선 닫음
