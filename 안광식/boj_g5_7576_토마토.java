package problems;

import java.io.*;
import java.util.*;

public class boj_g5_7576_토마토 {
	static int M, N;
	static int[] di = { -1, 0, 1, 0 };
	static int[] dj = { 0, 1, 0, -1 };
	static int[][] map;
	static ArrayDeque<int[]> tomato;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine(), " ");
		M = Integer.parseInt(st.nextToken()); // 가로
		N = Integer.parseInt(st.nextToken()); // 세로
		
		map = new int[N][M];
		tomato = new ArrayDeque<>();
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 1) {
					tomato.offer(new int[] { i, j });
				}
			}
		}
		
		System.out.println(tomato());
		
		br.close();
	}
	
	private static int tomato() {
		int days = 0;
		
		while (!tomato.isEmpty()) {
			int size = tomato.size();
			
			for (int i = 0; i < size; i++) {
				int[] cur = tomato.poll();
				
				for (int d = 0; d < 4; d++) {
					int nx = cur[0] + di[d];
					int ny = cur[1] + dj[d];
					
					if (nx < 0 || nx >= N || ny < 0 || ny >= M || map[nx][ny] != 0)
						continue;
					
					map[nx][ny] = 1;
					tomato.offer(new int[] { nx, ny });
				}
			}
			
			days++;
		}
		
		
		if (check()) 
			days = 0;
		
		return days - 1;
	}

	private static boolean check() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == 0)
					return true;
			}
		}
		
		return false;
	}
}

// 익은거, 안익은거 있음
// 하루가 지나면 익은거 인접 안익은 애들은 익게됨
// 인접은 상하좌우
// 며칠이 지나면 토마토가 다 익을까?
// 1 : 익은거
// 0 : 안익은거
// -1 : 빈 칸

// bfs의 depth 체크..?
// 끝까지 다 돌고 만약 0이 남아있다면 -1, 아니라면 depth 출력
