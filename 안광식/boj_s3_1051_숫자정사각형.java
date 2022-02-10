package bj.silver;

import java.io.*;
import java.util.*;

public class bj_1051_숫자정사각형 {
	static int N, M, max;
	static int[][] map;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		max = 1;
		
		map = new int[N][M];
		for (int i = 0; i < N; i++) {
			String input = br.readLine();
			for (int j = 0; j < M; j++) {
				map[i][j] = input.charAt(j) - '0';
			}
		}
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				getMaxLen(i, j);
			}
		}
		
		System.out.println(max*max);
		
		br.close();
	}
	
	private static void getMaxLen(int r, int c) {
		// 가장 길게 잡을 수 있는 변의 길이 구해서 가장 큰 정사각형부터 확인
		int len = Integer.min(N - r, M - c) - 1;
		
		while (true) {
			// 젤 작은 사각형 보면 스톱
			if (len == 0)
				break;
			
			// 가지치기
			if (len < max)
				break;
			
			if (map[r][c] == map[r + len][c] &&
				map[r][c] == map[r][c + len] &&
				map[r][c] == map[r + len][c + len]) {
				max = len + 1;
				break;
			}
			
			len--;
		}
	}
}

// 전부 다 보기?
// 0,0부터 쭉 돌면서 정사각형 보기 (마름모형태 정사각형은 안됨)
// 효율 챙기려면 가장 작은 정사각형부터 X 가장 큰 정사각형부터 체크
// 현재 가장 큰 정사각형 한 변 길이(max) 기록해놓고 이것보다 변 길이 같거나 짧아지면 가지치기
// max*max 출력

