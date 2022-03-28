package problems;

import java.io.*;
import java.util.*;

public class boj_g4_15684_사다리조작 {
	static int N, M, H, answer;
	static int[][] map;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		
		map = new int[H][N];
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			
			map[a][b] = 1;
		}
		
		answer = Integer.MAX_VALUE;
		setLine(0, 0, 0);
		
		System.out.println(answer > 3 ? -1 : answer);
		
		br.close();
	}
	
	private static void setLine(int cnt, int startX, int startY) {
		// i번 출발, i번 도착 체크
		if (checkSadari()) 
			answer = Integer.min(answer, cnt);
		
		// 다 놔본 경우면 그만
		if (cnt == 3 || answer <= cnt) 
			return;
		
		for (int i = startX; i < H; i++) {
			for (int j = 0; j < N - 1; j++) {
				if (i == startX && j < startY)
					j = startY;
				
				if (map[i][j] == 1)
					continue;
				
				map[i][j] = 1;
				if (isAvailable(i, j))
					setLine(cnt + 1, i, j + 1);
				map[i][j] = 0;
			}
		}
	}

	private static boolean checkSadari() {
		for (int start = 0; start < N; start++) {
			int idx = start;
			int h = 0;
			
			while (h < H) {
				// 본인 인덱스가 1이라면 우측으로 이동
				if (map[h][idx] == 1) {
					idx++;
				}
				
				// 본인 인덱스 -1이 1이라면 좌측으로 이동 (인덱스 범위 체크)
				else if (idx > 0 && map[h][idx - 1] == 1) {
					idx--;
				}
				
				h++;
			}
			
			if (idx != start)
				return false;
		}
		
		return true;
	}
	
	private static boolean isAvailable(int i, int j) {
		if (j == 0) 
			return map[i][j + 1] == 0;
		
		return map[i][j - 1] == 0 && map[i][j + 1] == 0;
	}
}

// N개 세로선, M개 가로선
// (a,b) : b, b+1열을 a행에서 연결했다는 뜻
// map은 같은 행에서 연속으로 1 불가.. 놓을 때 본인 왼쪽, 본인 오른쪽 확인 (둘 중 하나라도 1이면 놓지 못함)
// 가로선 놓을 수 있는 경우 전부 체크,, 브루트포스
// 다 놓고 나서 i번 출발 - i번 도착 하는지 싹 확인
// 가능하면 바로 리턴