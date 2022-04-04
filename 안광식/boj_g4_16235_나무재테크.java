package problems;

import java.io.*;
import java.util.*;

public class boj_g4_16235_나무재테크 {
	static int N, M, K;
	static int[] di = { -1, -1, 0, 1, 1, 1, 0, -1 }; // 상 우상 우 우하 하 좌하 좌 좌상
	static int[] dj = { 0, 1, 1, 1, 0, -1, -1, -1 };
	static int[][] A;
	static int[][] energeMap;
	static ArrayDeque<Integer>[][] map;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		A = new int[N][N];
		energeMap = new int[N][N];
		map = new ArrayDeque[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
				energeMap[i][j] = 5;
				map[i][j] = new ArrayDeque<>();
			}
		}
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int r = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;
			int age = Integer.parseInt(st.nextToken());
			map[r][c].offer(age);
		}
		
		Solution();
		System.out.println(M);
		
		br.close();
	}
	
//	static ArrayDeque<int[]> dq;
	private static void Solution() {
		while (K-- > 0) {
			// 봄, 여름, 겨울
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					fourSeason(i, j);
				}
			}
			
			// 가을
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					fall(i, j);
				}
			}
		}
	}

	private static void fall(int r, int c) {
		int size = map[r][c].size();
		
		for (int i = 0; i < size; i++) {
			int curTree = map[r][c].poll();
			
			if (curTree % 5 == 0) {
				for (int d = 0; d < 8; d++) {
					int nr = r + di[d];
					int nc = c + dj[d];
					
					if (nr < 0 || nr >= N || nc < 0 || nc >= N)
						continue;
					
					map[nr][nc].offerFirst(1);
					M++;
				}
			}
			
			map[r][c].offer(curTree);
		}
	}

	private static void fourSeason(int r, int c) {
		int sum = 0;
		int size = map[r][c].size();
		
		for (int i = 0; i < size; i++) {
			int curTree = map[r][c].pollFirst();
			// 죽는 경우
			if (energeMap[r][c] < curTree) {
				sum += curTree / 2;
				M--;
				continue;
			}
			
			energeMap[r][c] -= curTree;
			map[r][c].offer(curTree + 1);
		}
		
		energeMap[r][c] += sum;
		
		// 겨울
		energeMap[r][c] += A[r][c];
	}
}

// N*N
// r, c -1씩 해주기
// 초기 양분값은 5
// 묘목 M개 구매
// 사계절
// 1.봄
//  - 나무가 자신의 나이만큼 양분을 먹고 나이 1 증가
//  - 한 칸에 나무 여러 개 있으면 나이가 어린 나무부터 양분을 먹음
//  - 땅에 양분이 부족해 자신의 나이만큼 먹을 수 없다면 죽음
// 2.여름
//  - 봄에 죽은 나무가 양분으로 변함
//  - 죽은 나무마다 나이를 2로 나눈 값이 양분으로 추가됨 (소수점 버림)
//  - 이건 봄에서 죽는거 처리할 때 바로 더해주진 말고 tmp에 저장했다가 바로 더해주면 될듯
// 3.가을
//  - 나무 번식, 번식 나무는 나이가 5의 배수여야함
//  - 인접한 8개 칸에 나이가 1인 나무가 생김
//  - 벽 밖으로는 나무 생기지 않음
// 4.겨울
//  - 로봇이 돌아다니며 양분 추가
//  - 각 칸에 추가될 양분은 배열로 주어짐
// K년 지난 후 남아있는 나무 수 구하기
// PQ로 N*N 만들기?
