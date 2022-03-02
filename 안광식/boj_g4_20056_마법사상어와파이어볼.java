package problems;

import java.io.*;
import java.util.*;

public class boj_g4_20056_마법사상어와파이어볼 {
	static class Fireball {
		int r, c, m, d, s;
		public Fireball(int r, int c, int m, int s, int d) {
			this.r = r;
			this.c = c;
			this.m = m;
			this.s = s;
			this.d = d;
		}
	}
	
	static int N, M, K;
	static ArrayDeque<Fireball>[][] map;
	static ArrayDeque<Fireball> fireballs;
	static int[] di = { -1, -1, 0, 1, 1, 1, 0, -1 }; // 상 우상 우 우하 하 좌하 좌 좌상
	static int[] dj = { 0, 1, 1, 1, 0, -1, -1, -1 };
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		fireballs = new ArrayDeque<>();
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int r = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;
			int m = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			
			fireballs.offer(new Fireball(r, c, m, s, d));
		}
		
		Solution();
		System.out.println(getAnswer());
		
		br.close();
	}
	
	private static void Solution() {
		map = new ArrayDeque[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				map[i][j] = new ArrayDeque<>();
			}
		}
		
		while (K-- > 0) {
			move();
			
			divide();
		}
	}

	private static void move() {
		// 1.모든 파볼이 자신 방향 d로 s칸 이동 (이동중에는 충돌 허용)
		while (!fireballs.isEmpty()) {
			Fireball cur = fireballs.poll();
			
			int nx = (cur.r + (di[cur.d] + N) * cur.s) % N;
			int ny = (cur.c + (dj[cur.d] + N) * cur.s) % N;
			
			map[nx][ny].offer(new Fireball(nx, ny, cur.m, cur.s, cur.d));
		}
	}

	private static void divide() {
		// 2.이동 후 한 칸에 2개 이상 파볼 있는 경우 다음이 일어남
		//  1)같은 칸 파볼은 하나로 합쳐짐
		//  2)파볼은 4개로 나누어짐
		//  3)나눠진 파볼 질량, 속력, 방향은 다음과 같음
		//	    질량 = (질량 합) / 5
		//	    속력 = (속력 합) / (파볼 개수)
		//	    합쳐진 파볼 방향이 모두 홀or짝이면 분리된 4개 파볼 방향은 0,2,4,6 그렇지 않다면 1,3,5,7
		//  4)질량이 0인 파볼은 소멸됨 (2-3에서 질량 0이면 추가 안하면 될듯)
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j].isEmpty())
					continue;
				
				// 하나 뿐이라면 그대로
				if (map[i][j].size() == 1) {
					fireballs.offer(map[i][j].poll());
					continue;
				}
				
				int mSum = 0;
				int sSum = 0;
				int cnt = map[i][j].size();
				boolean hol = false;
				boolean zac = false;
				while (!map[i][j].isEmpty()) {
					Fireball cur = map[i][j].poll();
					
					mSum += cur.m;
					sSum += cur.s;
					
					if (cur.d % 2 == 0)
						zac = true;
					
					else
						hol = true;
				}
				
				int mAvg = mSum / 5;
				int sAvg = sSum / cnt;
				
				// 질량 0이면 소멸
				if (mAvg == 0)
					continue;
				
				int tmp = 0;
				if (hol && zac)
					tmp = 1;
				
				for (int k = 0; k < 8; k += 2) {
					fireballs.offer(new Fireball(i, j, mAvg, sAvg, k + tmp));
				}
			}
		}
	}

	private static int getAnswer() {
		int sum = 0;
		
		while (!fireballs.isEmpty())
			sum += fireballs.poll().m;
		
		return sum;
	}
}

// N*N
// 파이어볼 M개
// 좌표 (r,c), 질량 m, 방향 d, 속력 s => 클래스 만들자
// 좌우, 상하 벽 뚫려있음
// 8방 (상 우상 우 우하 하 좌하 좌 좌상)

// 이동 명령
// 1.모든 파볼이 자신 방향 d로 s칸 이동 (이동중에는 충돌 허용)
// 2.이동 후 한 칸에 2개 이상 파볼 있는 경우 다음이 일어남
//  1)같은 칸 파볼은 하나로 합쳐짐
//  2)파볼은 4개로 나누어짐
//  3)나눠진 파볼 질량, 속력, 방향은 다음과 같음
//    질량 = (질량 합) / 5
//    속력 = (속력 합) / (파볼 개수)
//    합쳐진 파볼 방향이 모두 홀or짝이면 분리된 4개 파볼 방향은 0,2,4,6 그렇지 않다면 1,3,5,7
//  4)질량이 0인 파볼은 소멸됨 (2-3에서 질량 0이면 추가 안하면 될듯)
// K번 이동 후 남은 파볼 질량 합 구하기