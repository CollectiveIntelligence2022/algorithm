package problems;

import java.util.*;
import java.io.*;

public class boj_g3_19237_어른상어 {
	static class Smell {
		int sharkNum, hp;

		public Smell(int sharkNum, int hp) {
			this.sharkNum = sharkNum;
			this.hp = hp;
		}
	}
	
	static int N, M, K, time, sharkCnt;
	static int[] di = { -1, 1, 0, 0 }; // 상 하 좌 우
	static int[] dj = { 0, 0, -1, 1 };
	static int[] curDir;
	static int[][][] dirPriority;
	static int[][] m
	static Smell[][] sMap;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		sharkCnt = M;
		map = new int[N][N];
		sMap = new Smell[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		curDir = new int[M + 1];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= M; i++) {
			curDir[i] = Integer.parseInt(st.nextToken()) - 1;
		}
		
		dirPriority = new int[M + 1][4][4];
		for (int i = 1; i <= M; i++) {
			for (int j = 0; j < 4; j++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int k = 0; k < 4; k++) {
					dirPriority[i][j][k] = Integer.parseInt(st.nextToken()) - 1;
				}
			}
		}
		
		Solution();
		
		System.out.println(time);
		
		br.close();
	}
	
	private static void Solution() {
		while (true) {
			// 1.모든 상어가 자신 위치에 냄새 뿌림 (냄새 수명은 k초)
			setSmell();
			
			// 1000초 넘어가면 종료, -1 출력
			if (++time > 1000) {
				time = -1;
				return;
			}
			
			// 2.모든 상어 동시에 이동, time++
			//  - 이동은 아무 냄새가 없는 칸 우선
			//  - 냄새가 없는 칸이 없다면 자신의 냄새가 있는 칸으로
			//  - 이동 가능한 칸이 여러 개라면 개체별, 방향별 우선순위에 따라 이동
			//  - 자신 냄새가 있는 칸도 없다면...? 아 여태 이동해왔으니 이런 경우는 없음
			// 3.같은 칸에 여러 마리가 함께 있다면 숫자 높은놈 빼고 다 사라짐.. 이건 그냥 이동할 때 겹치면 숫자 높은놈만 살아남도록 하면 될듯
			sharkMove();
			
			// 냄새들 수명 조정
			smellHpSet();
			
			// 1번 상어만 남았다면 스톱
			if (sharkCnt == 1) {
				return;
			}
		}
	}

	private static void setSmell() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] > 0) {
					sMap[i][j] = new Smell(map[i][j], K);
				}
			}
		}
	}

	private static void sharkMove() {
		// 2.모든 상어 동시에 이동, time++
		//  - 이동은 아무 냄새가 없는 칸 우선
		//  - 냄새가 없는 칸이 없다면 자신의 냄새가 있는 칸으로
		//  - 이동 가능한 칸이 여러 개라면 개체별, 방향별 우선순위에 따라 이동
		//  - 자신 냄새가 있는 칸도 없다면...? 아 여태 이동해왔으니 이런 경우는 없음
		// 3.같은 칸에 여러 마리가 함께 있다면 숫자 높은놈 빼고 다 사라짐.. 이건 그냥 이동할 때 겹치면 숫자 높은놈만 살아남도록 하면 될듯
		ArrayDeque<int[]> dq = new ArrayDeque<>();
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] == 0)
					continue;
				
				int sharkNum = map[i][j];
				int nonSmellCnt = 0;
				int tx = 0;
				int ty = 0;
				int td = -1;
				
				// 4방향 냄새 없는 곳 cnt 구하기
				for (int d = 0; d < 4; d++) {
					int nx = i + di[d];
					int ny = j + dj[d];
					
					if (nx < 0 || nx >= N || ny < 0 || ny >= N)
						continue;
					
					if (sMap[nx][ny] == null) {
						nonSmellCnt++;
						tx = nx;
						ty = ny;
						td = d;
					}
				}
				
				// 냄새 없는 곳 1군데면? 그냥 이동
				if (nonSmellCnt == 1) {
					map[i][j] = 0;
					dq.offer(new int[] { tx, ty, td, sharkNum }); // 좌표, 방향, 상어번호 저장
					continue;
				}
				
				// 여러 군데면?
				// 상하좌우 우선순위
				if (nonSmellCnt > 1) {
					int curD = curDir[sharkNum];
					
					for (int d = 0; d < 4; d++) {
						int nd = dirPriority[sharkNum][curD][d];
						
						int nx = i + di[nd];
						int ny = j + dj[nd];
						
						if (nx < 0 || nx >= N || ny < 0 || ny >= N || sMap[nx][ny] != null)
							continue;

						map[i][j] = 0;
						dq.offer(new int[] { nx, ny, nd, sharkNum });
						break;
					}
					
					continue;
				}
				
				// 아예 없으면?
//				if (nonSmellCnt == 0)

				int curD = curDir[sharkNum];
				
				for (int d = 0; d < 4; d++) {
					int nd = dirPriority[sharkNum][curD][d];
					
					int nx = i + di[nd];
					int ny = j + dj[nd];
					
					if (nx < 0 || nx >= N || ny < 0 || ny >= N || sMap[nx][ny].sharkNum != sharkNum)
						continue;

					map[i][j] = 0;
					dq.offer(new int[] { nx, ny, nd, sharkNum });
					break;
				}
			}
		}
		
		// 한 번에 이동
		while (!dq.isEmpty()) {
			int[] cur = dq.poll();
			
			int x = cur[0];
			int y = cur[1];
			int d = cur[2];
			int n = cur[3];
			
			if (map[x][y] > 0) {
				sharkCnt--;
				if (map[x][y] < n) {
					continue;
				}
			}
			
			map[x][y] = n;
			curDir[n] = d;
		}
	}

	private static void smellHpSet() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (sMap[i][j] == null)
					continue;
				if (--sMap[i][j].hp == 0)
					sMap[i][j] = null;
			}
		}
	}
}

// 1 ~ M 번호 상어 (모두 다름)
// 다른 상어를 쫓아냄, 1번이 제일 쌔서 나머지 다 쫓아낼 수 있음
// N*N 격자, M개 칸에 상어 한 마리씩 있음
// 처음에 모든 상어가 자신의 위치에 본인 냄새 뿌림
// 그 후 1초마다 모든 상어가 "동시에" 상하좌우 인접 칸 중 하나로 이동, 이동한 자리에 냄새 뿌림 (냄새는 k번 이동 후 사라짐)
// 상어 이동 방향은 인접 칸 중 아무 냄새 없는 칸으로 감, 그런 칸 없으면 자기 냄새 있는 칸으로 감
// 가능한 칸이 여러 개면 특정 우선 순위를 따름 (입력으로 주어짐)
// 우선 순위는 상어마다 다를 수 있고, 같은 상어라도 현재 상어가 보는 방향에 따라 또 다를 수 있음
// 처음 바라보는 방향은 주어지고, 이후에는 이동한 방향이 보는 방향이 됨
// 이동 후 여러 마리 겹치면 숫자 제일 높은놈 빼고 다 쫓겨남
// 

// 1.모든 상어가 자신 위치에 냄새 뿌림 (냄새 수명은 k초)
// 2.모든 상어 동시에 이동, time++
//  - 이동은 아무 냄새가 없는 칸 우선
//  - 냄새가 없는 칸이 없다면 자신의 냄새가 있는 칸으로
//  - 이동 가능한 칸이 여러 개라면 개체별, 방향별 우선순위에 따라 이동
//  - 자신 냄새가 있는 칸도 없다면...? 아 여태 이동해왔으니 이런 경우는 없음
// 3.같은 칸에 여러 마리가 함께 있다면 숫자 높은놈 빼고 다 사라짐















