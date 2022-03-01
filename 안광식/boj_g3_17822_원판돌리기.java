package problems;

import java.io.*;
import java.util.*;

// 인접 체크하는 부분에서 bfs 돈게 메모리, 시간 엄청 잡아먹은듯
// 그냥 간단하게 배열만 돌면서 체크해도 충분히 할 수 있음

public class boj_g3_17822_원판돌리기 {
	static int N, M, T;
	static int[] di = { -1, 0, 1, 0 };
	static int[] dj = { 0, 1, 0, -1 };
	static int[][] map;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		T = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		for (int t = 0; t < T; t++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			int k = Integer.parseInt(st.nextToken());
			solution(x, d, k);
		}
		
		System.out.println(getSum());
		
		br.close();
	}
	
	private static int getSum() {
		int sum = 0;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == -1)
					continue;
				sum += map[i][j];
			}
		}
		return sum;
	}

	private static void solution(int x, int d, int k) {
		// x의 배수 번호들 회전
		for (int i = x; i <= N; i += x) {
			rotate(i - 1, d, k);
		}
		
		// 인접 수 처리
		double sum = 0;
		double cnt = 0;
		boolean flag = false;
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == -1)
					continue;
				
				sum += map[i][j];
				cnt++;
				
				if (bfs(i, j))
					flag = true;
			}
		}
		
		if (flag)
			return;
		
		double avg = sum / cnt;
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == -1)
					continue;
				
				if (map[i][j] < avg)
					map[i][j]++;
				
				else if (map[i][j] > avg)
					map[i][j]--;
			}
		}
	}

	private static boolean bfs(int x, int y) {
		ArrayDeque<int[]> dq = new ArrayDeque<>();
		ArrayList<int[]> list = new ArrayList<>();
		boolean[][] visited = new boolean[N][M];
		
		dq.offer(new int[] { x, y });
		list.add(new int[] { x, y });
		visited[x][y] = true;
		
		while (!dq.isEmpty()) {
			int[] cur = dq.poll();
			
			for (int d = 0; d < 4; d++) {
				int nx = cur[0] + di[d];
				int ny = (cur[1] + dj[d] + M) % M; // 좌우는 뚫려있음
				
				if (nx < 0 || nx >= N || ny < 0 || ny >= M || visited[nx][ny])
					continue;
				
				if (map[cur[0]][cur[1]] != map[nx][ny])
					continue;
				
				dq.offer(new int[] { nx, ny });
				list.add(new int[] { nx, ny });
				visited[nx][ny] = true;
			}
		}
		
		if (list.size() == 1) 
			return false;
		
		for (int[] cur : list) {
			map[cur[0]][cur[1]] = -1;
		}
		
		return true;
	}

	private static void rotate(int x, int d, int k) {
		if (d == 0) {
			rotateRight(x, k);
		}
		
		else if (d == 1) {
			rotateLeft(x, k);
		}
	}

	private static void rotateRight(int x, int k) {
		int[] copy = deepCopy(map[x]);
		
		for (int i = 0; i < M; i++) {
			int idx = (i - k + M) % M;
			map[x][i] = copy[idx];
		}
	}

	private static void rotateLeft(int x, int k) {
		int[] copy = deepCopy(map[x]);
		
		for (int i = 0; i < M; i++) {
			int idx = (i + k) % M;
			map[x][i] = copy[idx];
		}
	}

	private static int[] deepCopy(int[] arr) {
		int[] copy = new int[M];
		for (int i = 0; i < M; i++) {
			copy[i] = arr[i];
		}
		return copy;
	}
}

// N : 원판의 수
// M : 원판 위 정수 개수

// 인접 관계
// (i,1) => (i,2), (i,M)
// (i,M) => (i,M-1), (i,1)
// (i,j) => (i,j-1), (i,j+1) *(2 <= j <= M-1)
// (1,j) => (2,j)
// (N,j) => (N-1,j)
// (i,j) => (i-1,j), (i+1,j) *(2 <= i <= N-1)

// 이거 그냥... 2차원 배열 좌우만 뚫려있는 모양인데?

// T번 회전
// x, d, k
// 1.번호가 "x의 배수"인 원판을 d 방향으로 k 칸 회전, d가 0이면 시계, 1이면 반시계
// 2.원판에 수가 남아있으면, 인접하면서 수가 같은 것을 모두 찾음
//  2-1)그러한 수가 있으면 인접하면서 같은 수를 모두 지움
//  2-2)없는 경우 원판에 적힌 수의 평균을 구하고, 평균보다 큰 수에서 1을 빼고, 작은 수에는 1을 더함















