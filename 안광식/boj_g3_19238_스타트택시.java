package problems;

import java.io.*;
import java.util.*;

public class boj_g3_19238_스타트택시 {
	static int N, M, fuel, taxiR, taxiC;
	static int[] di = { -1, 0, 1, 0 };
	static int[] dj = { 0, 1, 0, -1 };
	static int[][] map;
	static ArrayList<int[]> goalList;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine()," ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		fuel = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 1)
					map[i][j] = -1;
			}
		}
		
		st = new StringTokenizer(br.readLine(), " ");
		taxiR = Integer.parseInt(st.nextToken()) - 1;
		taxiC = Integer.parseInt(st.nextToken()) - 1;
		
		goalList = new ArrayList<>();
		goalList.add(new int[] { -1, -1 });
		for (int i = 1; i <= M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int startR = Integer.parseInt(st.nextToken()) - 1;
			int startC = Integer.parseInt(st.nextToken()) - 1;
			int goalR = Integer.parseInt(st.nextToken()) - 1;
			int goalC = Integer.parseInt(st.nextToken()) - 1;
			
			map[startR][startC] = i;
			goalList.add(new int[] { goalR, goalC });
		}
		
		Solution();
		System.out.println(fuel);
		
		br.close();
	}
	
	private static void Solution() {
		int dist;
		while (M-- > 0) {
			dist = moveToNearest();
			
			if (dist == -1 || fuel < dist) {
				fuel = -1;
				return;
			}
			fuel -= dist;
			
			
			dist = moveToGoal();

			if (dist == -1 || fuel < dist) {
				fuel = -1;
				return;
			}
			fuel -= dist;
			fuel += dist * 2;
		}
	}

	private static int moveToNearest() {
		boolean[][] visited = new boolean[N][N];
		ArrayDeque<int[]> dq = new ArrayDeque<>();
		
		visited[taxiR][taxiC] = true;
		dq.offer(new int[] { taxiR, taxiC });
		
		int nearR = Integer.MAX_VALUE;
		int nearC = Integer.MAX_VALUE;
		int dist = 0;
		boolean isFind = false;
		while (!dq.isEmpty()) {
			int size = dq.size();
			
			for (int i = 0; i < size; i++) {
				int[] cur = dq.poll();
				
				if (map[cur[0]][cur[1]] > 0) {
				
					isFind = true;
					
					if (nearR > cur[0]) {
						nearR = cur[0];
						nearC = cur[1];
					}
					
					else if (nearR == cur[0]) {
						if (nearC > cur[1]) {
							nearR = cur[0];
							nearC = cur[1];
						}
					}
				}
				
				for (int d = 0; d < 4; d++) {
					int nr = cur[0] + di[d];
					int nc = cur[1] + dj[d];
					
					if (isOut(nr, nc) || visited[nr][nc] || map[nr][nc] < 0)
						continue;
					
					visited[nr][nc] = true;
					dq.offer(new int[] { nr, nc });
				}
			}
			
			// 찾았으면 그만
			if (isFind) {
				taxiR = nearR;
				taxiC = nearC;
				return dist;
			}
			
			dist++;
		}
		
		return -1;
	}

	private static int moveToGoal() {
		int[] goal = goalList.get(map[taxiR][taxiC]);
		map[taxiR][taxiC] = 0;

		boolean[][] visited = new boolean[N][N];
		ArrayDeque<int[]> dq = new ArrayDeque<>();
		
		visited[taxiR][taxiC] = true;
		dq.offer(new int[] { taxiR, taxiC });
		
		int dist = 0;
		while (!dq.isEmpty()) {
			int size = dq.size();
			
			for (int i = 0; i < size; i++) {
				int[] cur = dq.poll();
				
				if (goal[0] == cur[0] && goal[1] == cur[1]) {
					taxiR = cur[0];
					taxiC = cur[1];
					return dist;
				}
				
				for (int d = 0; d < 4; d++) {
					int nr = cur[0] + di[d];
					int nc = cur[1] + dj[d];
					
					if (isOut(nr, nc) || visited[nr][nc] || map[nr][nc] < 0)
						continue;
					
					visited[nr][nc] = true;
					dq.offer(new int[] { nr, nc });
				}
			}
			
			// 연료
			dist++;
		}
		
		return -1;
	}

	private static boolean isOut(int nr, int nc) {
		return nr < 0 || nr >= N || nc < 0 || nc >= N;
	}
}

// 승객 M명
// N*N 빈 칸, 벽
// 상하좌우로 이동, 항상 최단거리로 이동
// 여러명이면 가장 위, 가장 왼쪽 승객을 고름
// 한 칸 이동시 연료 1 소모
// 승객 태워서 도착지까지 데려다주면서 소모한 연료 2배만큼 충전됨
// 이동중에 연료 바닥나면 끝, 이동과 동시에 바닥나면 ㄱㅊ