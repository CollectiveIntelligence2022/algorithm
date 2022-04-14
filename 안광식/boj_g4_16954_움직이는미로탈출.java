package problems;

import java.io.*;
import java.util.*;

public class boj_g4_16954_움직이는미로탈출 {
	static int wallCnt;
	static int[] di = { -1, -1, 0, 1, 1, 1, 0, -1 }; // 상 우상 우 우하 하 좌하 좌 좌상
	static int[] dj = { 0, 1, 1, 1, 0, -1, -1, -1 };
	static int[][] map;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		map = new int[8][8];
		wallCnt = 0;
		
		for (int i = 0; i < 8; i++) {
			String input = br.readLine();
			for (int j = 0; j < 8; j++) {
				if (input.charAt(j) == '#') {
					map[i][j] = 1;
					wallCnt++;
				}
			}
		}
		
		System.out.println(Solution());
		
		br.close();
	}
	
	private static int Solution() {
		ArrayDeque<int[]> dq = new ArrayDeque<>();
		boolean[][] visited;
		
		dq.offer(new int[] { 7, 0 });
		
		while (!dq.isEmpty()) {
			int size = dq.size();
			visited = new boolean[8][8];
			for (int i = 0; i < size; i++) {
				int[] cur = dq.poll();
				
				dq.offer(cur);
				visited[cur[0]][cur[1]] = true;
				
				for (int d = 0; d < 8; d++) {
					int nx = cur[0] + di[d];
					int ny = cur[1] + dj[d];
					
					if (isOut(nx, ny) || map[nx][ny] == 1 || visited[nx][ny])
						continue;
					
					dq.offer(new int[] { nx, ny });
					visited[nx][ny] = true;
				}
			}
			
			wallMove();
			
			if (wallCnt == 0)
				return 1;
			
			size = dq.size();
			for (int i = 0; i < size; i++) {
				int[] cur = dq.poll();
				if (map[cur[0]][cur[1]] == 1)
					continue;
				dq.offer(cur);
			}
		}
		
		return 0;
	}

	private static void wallMove() {
		for (int i = 7; i >= 0; i--) {
			for (int j = 0; j < 8; j++) {
				if (map[i][j] == 0)
					continue;

				map[i][j] = 0;
				
				if (i == 7) {
					wallCnt--;
					continue;
				}
				
				map[i + 1][j] = 1;
			}
		}
	}

	private static boolean isOut(int nx, int ny) {
		return nx < 0 || nx >= 8 || ny < 0 || ny >= 8;
	}
}

// (7,0) 시작, (0,7)까지 가야함
// 이동은 8방향 + 가만히 있기 가능
// 캐릭터 먼저 이동.. 현재 위치에서 갈 수 있는 두 곳 큐에 넣기
// 벽 이동.. 한 칸씩 내려주고 캐릭터 위치 큐 쭉 꺼내서 소멸 안하면 다시 큐에 넣기
// 흠 무한루프 돌거같은데.. visited 하면 안되니까.. 아
// 그냥 무한반복 하고 벽 개수 카운트하자.. 벽 개수가 0개 되면 1 출력













