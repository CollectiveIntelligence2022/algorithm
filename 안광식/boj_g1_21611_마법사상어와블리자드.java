package problems;

import java.util.*;
import java.io.*;

public class boj_g1_21611_마법사상어와블리자드 {
	static int N, M;
	static int[] di = { -1, 1, 0, 0 }; // 상하좌우
	static int[] dj = { 0, 0, -1, 1 };
	static int[] destroyedBall;
	static int[][] map;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		destroyedBall = new int[3 + 1];
		
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int d = Integer.parseInt(st.nextToken()) - 1;
			int s = Integer.parseInt(st.nextToken());
			
			Solution(d, s);
		}
		
		int answer = 0;
		for (int i = 1; i < 4; i++) 
			answer += destroyedBall[i] * i;
		
		System.out.println(answer);
		
		br.close();
	}
	
	static ArrayDeque<Integer> dq;
	private static void Solution(int d, int s) {
		// 1.d방향 s칸 만큼 구슬 파괴
		blizzard(d, s);
		
		// 2.앞으로 쭉 땡기기 (큐에 저장?)
		getBalls();
		
		// 3.구슬 폭파
		boolean flag = true;
		while (flag) {
			flag = destroy();
		}
		
		// 4.구슬 변화
		changeBalls();
	}

	private static void blizzard(int d, int s) {
		int nx = N / 2;
		int ny = N / 2;
		
		for (int i = 0; i < s; i++) {
			nx += di[d];
			ny += dj[d];
			
			if (nx < 0 || nx >= N || ny < 0 || ny >= N)
				continue;
			
			map[nx][ny] = 0;
		}
	}

	private static void getBalls() {
		dq = new ArrayDeque<>();
		
		int x = N / 2;
		int y = N / 2;
		int dist = 1;
		
		while (true) {
			// 좌
			for (int i = 0; i < dist; i++) {
				y--;
				
				if (y < 0) break;
				
				if (map[x][y] > 0) {
					dq.offer(map[x][y]);
					map[x][y] = 0;
				}
			}
			
			if (y < 0) break;
			
			// 하
			for (int i = 0; i < dist; i++) {
				x++;
				if (map[x][y] > 0) {
					dq.offer(map[x][y]);
					map[x][y] = 0;
				}
			}
			
			dist++;
			
			// 우
			for (int i = 0; i < dist; i++) {
				y++;
				if (map[x][y] > 0) {
					dq.offer(map[x][y]);
					map[x][y] = 0;
				}
			}
			
			// 상
			for (int i = 0; i < dist; i++) {
				x--;
				if (map[x][y] > 0) {
					dq.offer(map[x][y]);
					map[x][y] = 0;
				}
			}
			
			dist++;
		}
	}

	private static boolean destroy() {
		// 3.구슬 폭파
		//  - 연속하는 구슬이 4개 이상일 때 파괴 (파괴되는 구슬의 번호, 개수 저장하기)
		//  - 폭파 후 다시 2번 수행
		//  - 폭발할 수 있느 구슬이 없을 때 까지 반복
		// 큐 poll 하면서 구슬 번호 체크, cnt 기록
		// 가장 최근에 뽑은 구슬과 peek의 번호가 다르다면 cnt 체크
		// cnt가 < 4 라면 새로운 큐에 그냥 넣고
		// 아니라면 폭파 (큐에 넣지 않음, 번호랑 터진 개수 저장, 터졌다고 flag 표시하기)
		ArrayDeque<Integer> newDq = new ArrayDeque<>();
		int size = dq.size();
		
		int prev = 0;
		int cnt = 0;
		while (!dq.isEmpty()) {
			int cur = dq.poll();
			
			// 이전에 뽑은놈, 지금 뽑은놈 같은 경우 cnt++하고 넘어가기
			if (cur == prev) {
				cnt++;
				
				continue;
			}
			
			// 다른 경우 1.연속하는 구슬이 4개 미만일 때.. newDq에 개수만큼 넣고 prev, cnt 업데이트
			if (cnt < 4) {
				for (int i = 0; i < cnt; i++)
					newDq.offer(prev);
				
				prev = cur;
				cnt = 1;
				
				continue;
			}
			
			// 다른 경우 2.연속하는 구슬이 4개 이상일 때.. 파괴, 개수 저장
			destroyedBall[prev] += cnt;
			prev = cur;
			cnt = 1;
		}
		
		// 마지막에 남은놈들 처리
		if (cnt < 4) {
			for (int i = 0; i < cnt; i++)
				newDq.offer(prev);
		}
		
		else 
			destroyedBall[prev] += cnt;
		
		dq = newDq;
		
		return size != dq.size();
	}

	private static void changeBalls() {
		// 4.구슬 변화
		//  - 그룹화.. 연속하는 구슬을 하나의 그룹이라고 함
		//  - 하나의 그룹은 두 개의 구슬 A, B로 변화
		//  - A : 그룹에 들어있는 구슬의 개수
		//  - B : 그룹을 이루는 구슬의 번호
		//  - 전부 변화하고 구슬 개수가 칸 수 보다 많으면 걔넨 사라짐
		ArrayDeque<Integer> newDq = new ArrayDeque<>();
		
		int prev = 0;
		int cnt = 0;
		while (!dq.isEmpty()) {
			int cur = dq.poll();
			
			// 이전에 뽑은놈, 지금 뽑은놈 같은 경우 cnt++하고 넘어가기
			if (cur == prev) {
				cnt++;
				
				continue;
			}
			
			// 다른 경우
			if (cnt > 0) {
				newDq.offer(cnt);
				newDq.offer(prev);
			}
			
			prev = cur;
			cnt = 1;
		}
		
		// 마지막에 남은놈들 처리
		newDq.offer(cnt);
		newDq.offer(prev);
		
		// 맵에 다시 넣어주기
		dq = newDq;
		setMap();
	}

	private static void setMap() {
		int x = N / 2;
		int y = N / 2;
		int dist = 1;
		
		while (true) {
			// 좌
			for (int i = 0; i < dist; i++) {
				y--;
				
				if (y < 0) return;
				
				if (dq.isEmpty()) return;
				map[x][y] = dq.poll();
			}
			
			// 하
			for (int i = 0; i < dist; i++) {
				x++;

				if (dq.isEmpty()) return;
				map[x][y] = dq.poll();
			}
			
			dist++;
			
			// 우
			for (int i = 0; i < dist; i++) {
				y++;

				if (dq.isEmpty()) return;
				map[x][y] = dq.poll();
			}
			
			// 상
			for (int i = 0; i < dist; i++) {
				x--;

				if (dq.isEmpty()) return;
				map[x][y] = dq.poll();
			}
			
			dist++;
		}
	}
}

// N*N
// 상어 위치 : (N/2, N/2) (중앙)
// 구슬 종류는 1, 2, 3 세 가지

// 1.d방향 s칸 만큼 구슬 파괴
// 2.앞으로 쭉 땡기기
// 3.구슬 폭파
//  - 연속하는 구슬이 4개 이상일 때 파괴 (파괴되는 구슬의 번호, 개수 저장하기)
//  - 폭파 후 다시 2번 수행
//  - 폭발할 수 있느 구슬이 없을 때 까지 반복
// 4.구슬 변화
//  - 그룹화.. 연속하는 구슬을 하나의 그룹이라고 함
//  - 하나의 그룹은 두 개의 구슬 A, B로 변화
//  - A : 그룹에 들어있는 구슬의 개수
//  - B : 그룹을 이루는 구슬의 번호
//  - 전부 변화하고 구슬 개수가 칸 수 보다 많으면 걔넨 사라짐

// 폭발한 1번, 2번, 3번 구슬의 개수 카운트 하기 (마법써서 없어진건 취급안함)