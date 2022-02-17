package problems;

import java.io.*;
import java.util.*;

public class boj_g3_14890_경사로 {
	static int N, L, answer;
	static int[][] map;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		answer = 0;
		
		// 행
		for (int i = 0; i < N; i++) {
			ArrayList<Integer> road = new ArrayList<>();
			for (int j = 0; j < N; j++) {
				road.add(map[i][j]);
			}
			Solution(road);
		}
		
		// 열
		for (int i = 0; i < N; i++) {
			ArrayList<Integer> road = new ArrayList<>();
			for (int j = 0; j < N; j++) {
				road.add(map[j][i]);
			}
			Solution(road);
		}
		
		
		System.out.println(answer);
		
		br.close();
	}
	
	private static void Solution(ArrayList<Integer> road) {
		boolean[] isUsed = new boolean[road.size()];
		
		// 정방향 보면서 경사로 놓기
		for (int i = 0; i < road.size() - 1; i++) {
			// 같거나 역방향에서 놓아야 하는건 패스
			if (road.get(i) <= road.get(i + 1))
				continue;
			
			// 높이 차이가 1보다 크면 경사로 불가능
			if (road.get(i) - road.get(i + 1) > 1) 
				return;
			
			// 경사로 놓을 칸이 L개 안남았다면
			if (road.size() - i <= L)
				return;
			
			// L개 연속으로 낮은칸 연속 안되면 + 이미 경사로 놓은 곳이면 경사로 불가능
			int h = road.get(i + 1);
			for (int j = i + 1; j <= i + L; j++) {
				if (road.get(j) != h || isUsed[j])
					return;
				// 가능하면 경사로 놓기
				isUsed[j] = true;
			}
		}
		
		// 역방향 보면서 경사로 놓기
		for (int i = road.size() - 1; i > 0; i--) {
			// 같으면 패스
			if (road.get(i) <= road.get(i - 1))
				continue;
			
			// 높이 차이 1보다 크면 불가능
			if (road.get(i) - road.get(i - 1) > 1)
				return;
			
			// 경사로 놓을 칸이 L개 안남았다면
			if (i - L < 0)
				return;
			
			// L개 연속 낮은칸 연속 안됨 + 이미 경사로 놓았다면 리턴
			int h = road.get(i - 1);
			for (int j = i - 1; j >= i - L; j--) {
				if (road.get(j) != h || isUsed[j])
					return;
				isUsed[j] = true;
			}
		}
		
		// 만약 경사로 놓다가 놓을 수 없는 경우가 보이면 걍 리턴
		
		answer++;
	}
}

// 지나가려면 모든 칸의 높이가 같아야 함
// 칸이 다르면 경사로 놓아서 길 만들 수 있음
// 경사로는 높이 1, 길이 L, 개수는 무제한

// 경사로 놓을 수 있는 조건
// - 경사로는 낮은 칸에 놓음. L개의 연속된 칸에 경사로의 바닥이 모두 접해야 함
// - 낮은 칸, 높은 칸 높이 차이는 1
// - 경사로를 놓을 낮은 칸의 높이는 모두 같아야 하고, L개의 칸이 연속되어 있어야 함

// 경사로 놓을 수 없는 경우
// - 경사로를 놓은 곳에 또 경사로를 놓는 경우
// - 낮은 칸, 높은 칸의 높이 차이가 1이 아닌 경우
// - 낮은 지점의 칸 높이가 모두 같지 않거나, L개가 연속되지 않은 경우
// - 경사로 놓다가 범위 벗어난 경우









