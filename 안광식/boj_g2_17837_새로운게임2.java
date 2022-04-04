package problems;

import java.io.*;
import java.util.*;

public class boj_g2_17837_새로운게임2 {
	static class Mal {
		int n, r, c, d;
		public Mal(int n, int r, int c, int d) {
			this.n = n;
			this.r = r;
			this.c = c;
			this.d = d;
		}
	}
	static int N, K;
	static int[] di = { -1, 0, 1, 0 }; // 상 우 하 좌.. 방향 반대는 +=2 % 4
	static int[] dj = { 0, 1, 0, -1 };
	static int[][] colorMap;
	static ArrayList<Mal> malInfo;
	static ArrayDeque<Integer>[][] map;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		map = new ArrayDeque[N][N];
		colorMap = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = new ArrayDeque<>();
				colorMap[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		malInfo = new ArrayList<>();
		malInfo.add(new Mal(-1, -1, -1, -1));
		for (int i = 1; i <= K; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int r = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;
			int d = Integer.parseInt(st.nextToken());
			switch (d) {
			case 1: // 우
				d = 1;
				break;

			case 2: // 좌
				d = 3;
				break;

			case 3: // 상
				d = 0;
				break;

			case 4: // 하
				d = 2;
				break;

			default:
				break;
			}
			
			map[r][c].add(i);
			malInfo.add(new Mal(i, r, c, d));
		}
		
		System.out.println(Solution());
		
		br.close();
	}
	
	static boolean isDone;
	private static int Solution() {
		int turnCnt = 0;
		isDone = false;
		while (turnCnt++ < 1000) {
			for (int i = 1; i <= K; i++) {
				Mal cur = malInfo.get(i);
				
				moveMal(cur);
				
				if (isDone) 
					return turnCnt;
			}
		}
		
		return -1;
	}
	
	private static void moveMal(Mal cur) {
		int nr, nc;
		// 이동할 위치 선정,, 이동할 칸 먼저 정하기
		nr = cur.r + di[cur.d];
		nc = cur.c + dj[cur.d];
		
		// 벽이거나 파란 칸일 경우 방향 반전 후 이동할 칸 다시 정하기
		if (nr < 0 || nr >= N || nc < 0 || nc >= N || colorMap[nr][nc] == 2) {
			cur.d = (cur.d + 2) % 4;

			nr = cur.r + di[cur.d];
			nc = cur.c + dj[cur.d];
			
			// 반대 방향도 벽이거나 파란 칸일 경우 이동 안함 (단, 방향 변경한건 적용)
			if (nr < 0 || nr >= N || nc < 0 || nc >= N || colorMap[nr][nc] == 2)
				return;
		}
		
		// 함께 이동할 녀석들 뽑기
		ArrayDeque<Integer> moveList = new ArrayDeque<>();
		int size = map[cur.r][cur.c].size();
		boolean flag = false;
		
		for (int i = 0; i < size; i++) {
			int tmp = map[cur.r][cur.c].poll();
			
			if (flag || cur.n == tmp) {
				flag = true;
				moveList.offer(tmp);
				continue;
			}
			
			map[cur.r][cur.c].offer(tmp); 
		}
		
		// 흰 칸이면 순서대로 박기, 빨간 칸이면 역순으로 박기
		while (!moveList.isEmpty()) {
			int malNum;
			
			if (colorMap[nr][nc] == 0) 
				malNum = moveList.poll();
			else
				malNum = moveList.pollLast();
			
			map[nr][nc].offer(malNum);
			Mal mal = malInfo.get(malNum);
			mal.r = nr;
			mal.c = nc;
			
			if (map[nr][nc].size() >= 4)
				isDone = true;
		}
	}
}

// N*N
// K개 말 사용
// K개 말 놓고 시작, 1번~K번 번호 있고 이동 방향도 미리 정해져있음 (상하좌우 중 1)
// 1턴은 1번~K번 말까지 "순서대로" 이동시키는 것
// 한 말이 이동할 때 위에 올려져 있는 말도 함께 이동
// 말의 이동 방향에 있는 칸에 따라 말의 이동이 다름 (턴 진행 중 말이 4개 이상 쌓이는 순간 게임 종료)

// A번 말이 이동하려는 칸이
// 1.흰색인 경우 그 칸으로 이동, 이미 말이 있는 경우 가장 위에 A번 말을 올려둠
//  - A번 말 위에 다른 말이 있는 경우 A번 말과 그 위의 말들이 동시에 이동
// 2.빨간색인 경우 이동한 후에 A번 말과 그 위의 모든 말의 순서를 반대로 바꿈
//  - 이미 있는 애들은 냅두고 새로 올라가는 애들 (A 위에 있는 애들만) 순서 바꿈
// 3.파란색인 경우 A번 말의 이동 방향을 반대로 하고 한 칸 이동.. 만약 반대로 바꾼 칸도 파란색이면 이동하지 않고 가만히 있음 (파란 칸은 걍 못올라가는듯)
// 4.체스판 벗어나는 경우도 3번과 같이 취급

// 칸 색깔 저장해두는 맵
//  - 0흰 1빨 2파
// 칸 별로 말이 어떻게 올라와 있는지 저장할 맵.. 큐? 순서대로 저장해야함..
//  - 이건 그냥 Integer로 말 번호만 저장해도 될듯?
// K개 말들 정보 저장할 클래스.. 정보들을 저장할 어레리.. 위치랑 방향 저장