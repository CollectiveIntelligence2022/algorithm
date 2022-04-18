package problems;

import java.io.*;
import java.util.*;

public class Main_17779_게리맨더링2 {
	static int N, totalPop, minGap;
	static int[][] popMap;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		popMap = new int[N][N];
		totalPop = 0;
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				popMap[i][j] = Integer.parseInt(st.nextToken());
				totalPop += popMap[i][j];
			}
		}
		
		minGap = Integer.MAX_VALUE;
		Solution();
		
		System.out.println(minGap);
		
		br.close();
	}
	
	private static void Solution() {
		for (int x = 0; x < N; x++) {
			for (int y = 0; y < N; y++) {
				for (int d1 = 1; d1 < N; d1++) {
					for (int d2 = 1; d2 < N; d2++) {
						grmdr(x, y, d1, d2);
					}
				}
			}
		}
	}

	private static void grmdr(int x, int y, int d1, int d2) {
		// 범위 체크
		if (x + d1 + d2 >= N)
			return;
		
		if (y - d1 < 0 || y + d2 >= N)
			return;
		
		boolean[][] sectionMap = new boolean[N][N];
		
		// 경계선 1,4
		for (int i = 0; i <= d1; i++) {
			sectionMap[x + i][y - i] = true;
			sectionMap[x + d2 + i][y + d2 - i] = true;
		}
		
		// 경계션 2,3
		for (int i = 0; i <= d2; i++) {
			sectionMap[x + i][y + i] = true;
			sectionMap[x + d1 + i][y - d1 + i] = true;
		}
		
		int[] popSum = new int[5];
		
		// 1번 구역
		for (int i = 0; i < x + d1; i++) {
			for (int j = 0; j <= y; j++) {
				if (sectionMap[i][j])
					break;
				popSum[0] += popMap[i][j];
			}
		}
		
		// 2번
		for (int i = 0; i <= x + d2; i++) {
			for (int j = N - 1; j > y; j--) {
				if (sectionMap[i][j])
					break;
				popSum[1] += popMap[i][j];
			}
		}
		
		// 3번
		for (int i = x + d1; i < N; i++) {
			for (int j = 0; j < y - d1 + d2; j++) {
				if (sectionMap[i][j])
					break;
				popSum[2] += popMap[i][j];
			}
		}
		
		// 4번
		for (int i = x + d2 + 1; i < N; i++) {
			for (int j = N - 1; j >= y - d1 + d2; j--) {
				if(sectionMap[i][j])
					break;
				popSum[3] += popMap[i][j];
			}
		}
		
		// 5번
		popSum[4] = totalPop - popSum[0] - popSum[1] - popSum[2] - popSum[3];
		
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < 5; i++) {
			min = Math.min(min, popSum[i]);
			max = Math.max(max, popSum[i]);
		}
		
		minGap = Math.min(minGap, max - min);
	}
}

// N*N
// 총 5개의 선거구로 나눈다
// 같은 선거구는 인접해 있어야 함
// 1.기준점 (x,y)와 경계의 길이 d1, d2를 정함