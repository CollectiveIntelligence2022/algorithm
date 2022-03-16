import java.io.*;
import java.util.*;

public class Boj_g3_19237_어른상어 {
	static class Shark {
		int x, y, num, dir;

		public Shark(int x, int y, int num, int dir) {
			super();
			this.x = x;
			this.y = y;
			this.num = num;
			this.dir = dir;
		}

		public Shark(int num, int dir) {
			this.num = num;
			this.dir = dir;
		}
	}

	static class Smell {
		int num, time;

		public Smell(int num, int time) {
			super();
			this.num = num;
			this.time = time;
		}
	}

	static ArrayDeque<Shark> shark = new ArrayDeque<>();
	static Smell[][] smell;
	static int[][] map;
	static int[][][] sharkPriDir;
	static int[] dx = { -1, 1, 0, 0 };
	static int[] dy = { 0, 0, -1, 1 };
	static int N, M, k;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());

		smell = new Smell[N][N];
		map = new int[N][N];
		sharkPriDir = new int[M + 1][4][4];
		int[] sharkDir = new int[M + 1];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				smell[i][j] = new Smell(0, 0);
			}
		}

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= M; i++) {
			sharkDir[i] = Integer.parseInt(st.nextToken()) - 1;
		}

		for (int i = 1; i <= M; i++) {
			for (int j = 0; j < 4; j++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int k = 0; k < 4; k++) {
					sharkPriDir[i][j][k] = Integer.parseInt(st.nextToken()) - 1;
				}
			}
		}

		// map 순회해서 상어가 있다면 큐에 해당 상어의 정보를 넣는다.
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] > 0) {
					shark.offer(new Shark(i, j, map[i][j], sharkDir[map[i][j]]));
				}
			}
		}

		System.out.println(solution());
		br.close();
	}

	// O(N*N*k) = 400000(단순 계산)
	// O((M + N*N + N*N + 8*M + N*N)*k) = 4800000(solution 부터)
	// 1. 모든 상어가 자신의 위치에 자신의 냄새를 뿌린다.
	// 2. 모든 상어가 동시에 상하좌우로 인접한 칸 중 하나로 이동
	private static int solution() {
		int res = 0;

		while (++res <= 1000) {
			spreadSmell();
			moveShark();

			if (shark.size() == 1) {
				break;
			}
		}

		return res > 1000 ? -1 : res;
	}

	/*
	 * 매초마다 해당 칸에 냄새를 남긴다.
	 * 냄새는 상어가 k번 이동하고 나면 사라진다.
	 */
	private static void spreadSmell() {
		int size = shark.size();
		for (int i = 0; i < size; i++) {
			Shark s = shark.poll();
			smell[s.x][s.y] = new Smell(s.num, k);
			shark.offer(s);
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (smell[i][j].time > 0) {
					smell[i][j].time--;
				} else if (smell[i][j].time == 0) {
					smell[i][j].num = 0;
				}
			}
		}
	}

	/*
	 * 모든 상어가 동시에 움직인다 방향 결정 우선순위
	 * 1. 인접한 칸 중 아무 냄새가 없는 칸
	 * 1-1. 여러개이면 우선순위에 따름
	 * 2. 그런칸이 없으면 자신의 냄새가 있는 칸의 방향으로
	 * 2-1. 여러개이면 우선순위에 따름
	 * 3. 1, 2 해당안되면 제자리
	 */
	private static void moveShark() {
		Shark[][] tShark = new Shark[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				tShark[i][j] = new Shark(0, 0);
			}
		}

		int size = shark.size();
		for (int k = 0; k < size; k++) {
			Shark s = shark.poll();
			boolean isEmpty = false, isMine = false;

			for (int d = 0; d < 4; d++) {
				// 현재 상어의 방향 우선순위에 따른 좌표
				int nx = s.x + dx[sharkPriDir[s.num][s.dir][d]];
				int ny = s.y + dy[sharkPriDir[s.num][s.dir][d]];

				if (!isCheck(nx, ny)) {
					continue;
				}

				// 빈 칸 발견
				if (smell[nx][ny].num == 0) {
					isEmpty = true;
					// 다른 상어가 없거나 다른 상어보다 번호가 작으면 해당 칸에 저장
					if (tShark[nx][ny].num == 0 || tShark[nx][ny].num > s.num) {
						tShark[nx][ny] = new Shark(s.num, sharkPriDir[s.num][s.dir][d]);
					}
					// 최초 빈칸을 찾았으면 더 이상 탐색 안함
					break;
				}
			}

			if (isEmpty) {
				continue;
			}
			
			// 빈칸 못찾음
			// -> 자기 자신의 냄새가 있는 칸을 찾자
			for (int d = 0; d < 4; d++) {
				int nx = s.x + dx[sharkPriDir[s.num][s.dir][d]];
				int ny = s.y + dy[sharkPriDir[s.num][s.dir][d]];

				if (!isCheck(nx, ny)) {
					continue;
				}

				// 자기 냄새 찾음
				if (smell[nx][ny].num == s.num) {
					isMine = true;
					if (tShark[nx][ny].num == 0 || tShark[nx][ny].num > s.num) {
						tShark[nx][ny] = new Shark(s.num, sharkPriDir[s.num][s.dir][d]);
					}
					break;
				}
			}
			
			if (isMine) {
				continue;
			}

			// 빈 칸도 못찾았고 현재 상어의 냄새가 있는 칸도 못 찾은 경우
			// -> 제자리
			if (tShark[s.x][s.y].num == 0 || tShark[s.x][s.y].num > s.num) {
				tShark[s.x][s.y].num = s.num;
				tShark[s.x][s.y] = new Shark(s.num, s.dir);
			}
		}

		// 살아남은 상어들을 다시 큐에 넣음
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (tShark[i][j].num > 0) {
					shark.offer(new Shark(i, j, tShark[i][j].num, tShark[i][j].dir));
				}
			}
		}
	}

	/* 배열 범위 체크 */
	private static boolean isCheck(int x, int y) {
		return 0 <= x && x < N && 0 <= y && y < N;
	}
}