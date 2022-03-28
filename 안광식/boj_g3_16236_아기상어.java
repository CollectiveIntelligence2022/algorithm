package problems;

import java.io.*;
import java.util.*;

public class boj_g3_16236_아기상어 {
	static class Fish implements Comparable<Fish> {
		int r, c, size;
		
		public Fish(int r, int c, int size) {
			this.r = r;
			this.c = c;
			this.size = size;
		}

		@Override
		public int compareTo(Fish obj) {
			if (this.r == obj.r)
				return Integer.compare(this.c, obj.c);
			return Integer.compare(this.r, obj.r);
		}
	}
	
	static class Shark extends Fish {
		int cnt;
		
		public Shark(int r, int c) {
			super(r, c, 2);
			cnt = 0;
		}
	}
	
	static int N, answer;
	static int[] di = { -1, 0, 1, 0 };
	static int[] dj = { 0, 1, 0, -1 };
	static int[][] map;
	static Shark shark;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				
				if (map[i][j] == 9) {
					map[i][j] = 0;
					shark = new Shark(i, j);
				}
			}
		}
		
		Solution();
		System.out.println(answer);
		
		br.close();
	}
	
	private static void Solution() {
		answer = 0;
		while (true) {
			// 먹을 수 있는 물고기 체크, 물고기 좌표랑 거리 리턴
			int[] fishInfo = bfs();
			
			// 없다면 엄마 상어에게 도움 요청 (종료)
			if (fishInfo[0] == -1)
				return;
			
			// 2.먹을 수 있는 물고기 1마리면 그거 먹으러 감
			// 3.먹을 수 있는 물고기가 1마리보다 많다면 가장 가까운 물고기를 먹으러 감
			//  3-1) 거리는 상어가 있는 칸에서 물고가 있는 칸으로 이동할 때 지나는 칸의 개수 최소값
			//  3-2) 거리가 가까운 물고기가 많다면 가장 위, 가장 왼쪽에 있는 물고기를 먹음
			
			// 우선순위는 다 처리해서 한 마리 정보만 나오기 때문에 상어 상태, 누적 시간, 맵 정보만 업데이트
			shark.r = fishInfo[0];
			shark.c = fishInfo[1];
			if (++shark.cnt == shark.size) {
				shark.size++;
				shark.cnt = 0;
			}
			map[shark.r][shark.c] = 0;
			answer += fishInfo[2];
		}
	}

	private static int[] bfs() {
		int[] fishInfo = { -1, -1, -1 };
		ArrayList<Fish> fishList = new ArrayList<>();
		int time = 0;
		
		ArrayDeque<int[]> dq = new ArrayDeque<>();
		boolean[][] visited = new boolean[N][N];
		
		dq.offer(new int[] { shark.r, shark.c });
		visited[shark.r][shark.c] = true;
		
		boolean flag = false;
		
		while (!dq.isEmpty() && !flag) {
			int size = dq.size();
			
			for (int i = 0; i < size; i++) {
				int[] cur = dq.poll();
				
				for (int d = 0; d < 4; d++) {
					int nx = cur[0] + di[d];
					int ny = cur[1] + dj[d];
					
					if (nx < 0 || nx >= N || ny < 0 || ny >= N || visited[nx][ny] || map[nx][ny] > shark.size)
						continue;
					
					dq.offer(new int[] { nx, ny });
					visited[nx][ny] = true;
					
					// 물고기인데 크기 작은 경우
					if (map[nx][ny] > 0 && map[nx][ny] < shark.size) {
						flag = true;
						fishList.add(new Fish(nx, ny, map[nx][ny]));
					}
				}
			}
			
			time++;
		}
		
		if (!fishList.isEmpty()) {
			Collections.sort(fishList);
			Fish f = fishList.get(0);
			fishInfo[0] = f.r;
			fishInfo[1] = f.c;
			fishInfo[2] = time;
		}
		
		return fishInfo;
	}
}

// N*N
// 물고기 M마리, 아기상어 1마리
// 얘네는 각각 크기를 가지고 있고 아기상어는 크기 2로 시작
// 아기상어는 1초에 상하좌우로 인접한 한 칸씩 이동

// 더 큰 물고기 칸 : 이동 불가
// 같은 물고기 칸 : 이동 가능, 먹지는 못함
// 작은 물고기 칸 : 이동 가능, 먹을 수 있음

// 1.먹을 수 있는 물고기 체크, 없다면 엄마 상어에게 도움 요청 (종료)
// 2.먹을 수 있는 물고기 1마리면 그거 먹으러 감
// 3.먹을 수 있는 물고기가 1마리보다 많다면 가장 가까운 물고기를 먹으러 감
//  3-1) 거리는 상어가 있는 칸에서 물고가 있는 칸으로 이동할 때 지나는 칸의 개수 최소값
//  3-2) 거리가 가까운 물고기가 많다면 가장 위, 가장 왼쪽에 있는 물고기를 먹음

// 이동은 1초, 먹는데 걸리는 시간은 없음 (이동과 동시에 섭취)
// 아기 상어 본인 크기와 같은 수의 물고기를 먹을 때 마다 크기 1 증가














