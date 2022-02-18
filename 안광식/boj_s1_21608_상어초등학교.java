package problems;

import java.io.*;
import java.util.*;

public class boj_s1_21608_상어초등학교 {
	static int N, answer;
	static int[] di = { -1, 0, 1, 0 };
	static int[] dj = { 0, 1, 0, -1 };
	static int[][] map;
	static HashMap<Integer, ArrayList<Integer>> hm;
	static ArrayList<Integer> keys;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		hm = new HashMap<>();
		keys = new ArrayList<>();
		N = Integer.parseInt(br.readLine());
		map = new int[N][N];
		for (int i = 0; i < N*N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			
			int key = Integer.parseInt(st.nextToken());
			keys.add(key);
			ArrayList<Integer> list = new ArrayList<>();
			for (int j = 0; j < 4; j++) {
				list.add(Integer.parseInt(st.nextToken()));
			}
			
			hm.put(key, list);
		}
		
		answer = 0;
		Solution();
		
		System.out.println(answer);
		
		br.close();
	}
	
	private static void Solution() {
		setSeats();		
		getScore();
	}

	private static void setSeats() {
		for (int key : keys) {
			ArrayList<Integer> like = hm.get(key);

			// 1.빈 칸 중 좋아하는 학생이 인접한 칸이 가장 많은 칸으로 자리를 정함
			// 전부 다 돌면서 좋아하는 학생 인접 수 가장 큰놈들 저장 (저장하면서 지나가다가 최댓값 갱신시 초기화)
			ArrayDeque<int[]> seats = condition1(like);
			
			// 2.1을 만족하는 칸이 여러 개? 인접 칸 중 빈 칸이 가장 많은 칸으로 정함
			// 1에서 저장해둔거 길이가 2 이상이면 해당 칸들 빈 칸 수 가장 큰놈들 저장 (이것도 저장하면서 지나가다가 최댓값 갱신시 초기화)
			if (seats.size() == 1) {
				int[] seat = seats.poll();
				map[seat[0]][seat[1]] = key;
				continue;
			}
			
			ArrayDeque<int[]> seats2 = condition2(seats);
			
			// 3.2를 만족하는 칸도 여러 개? 제일 위 칸, 그것도 여러 개면 제일 왼쪽 칸
			// 어차피 1에서 탐색을 행, 열 순으로 진행하니까 굳이 정렬 필요 X 제일 앞놈 선택하면 됨 
			// 그냥 무조건 poll 해서 넣으면 될듯
			int[] seat = seats2.poll();
			map[seat[0]][seat[1]] = key;
		}
	}

	private static ArrayDeque<int[]> condition1(ArrayList<Integer> like) {
		ArrayDeque<int[]> result = new ArrayDeque<>();
		int max = Integer.MIN_VALUE;
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				// 빈 칸 아니면 패스
				if (map[i][j] != 0)
					continue;
				
				int cnt = 0;
				int emptyCnt = 0;
				
				// 인접 칸 확인
				for (int d = 0; d < 4; d++) {
					int nx = i + di[d];
					int ny = j + dj[d];
					
					if (nx < 0 || nx >= N || ny < 0 || ny >= N)
						continue;
					
					if (like.contains(map[nx][ny]))
						cnt++;
					else if (map[nx][ny] == 0)
						emptyCnt++;
				}
				
				// 최댓값 비교, 갱신
				if (max < cnt) {
					max = cnt;
					result.clear();
					result.add(new int[] { i, j, emptyCnt });
				}
				
				// 같으면 값만 추가
				if (max == cnt) {
					result.add(new int[] { i, j, emptyCnt });
				}
			}
		}
		
		return result;
	}

	private static ArrayDeque<int[]> condition2(ArrayDeque<int[]> seats) {
		ArrayDeque<int[]> result = new ArrayDeque<>();
		
		int max = Integer.MIN_VALUE;
		while (!seats.isEmpty()) {
			int[] cur = seats.poll();
			
			if (max < cur[2]) {
				max = cur[2];
				result.clear();
				result.add(new int[] { cur[0], cur[1]});
			}
			
			if (max == cur[2]) {
				result.add(new int[] { cur[0], cur[1]});
			}
		}
		
		return result;
	}

	private static void getScore() {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				ArrayList<Integer> like = hm.get(map[i][j]);
				int cnt = 0;
				
				for (int d = 0; d < 4; d++) {
					int nx = i + di[d];
					int ny = j + dj[d];
					
					if (nx < 0 || nx >= N || ny < 0 || ny >= N)
						continue;
					
					if (like.contains(map[nx][ny]))
						cnt++;
				}
				
				if (cnt == 0)
					continue;
				
				answer += Math.pow(10, cnt - 1);
			}
		}
	}
}

// N*N
// 학생수 N^2명
// 1번 ~ N^2 번호 학생 있음
// (r,c).. 1,1부터 시작.. -1 처리 해주고
// 상하좌우 인접

// 1.빈 칸 중 좋아하는 학생이 인접한 칸이 가장 많은 칸으로 자리를 정함
// 2.1을 만족하는 칸이 여러 개? 인접 칸 중 빈 칸이 가장 많은 칸으로 정함
// 3.2를 만족하는 칸도 여러 개? 제일 위 칸, 그것도 여러 개면 제일 왼쪽 칸

// 배치 끝난 후 만족도 계산
// 인접 칸에 앉은 좋아하는 학생 수(n)로 구함
// 0 -> 0
// 1 -> 1 -> 10^0
// 2 -> 10 -> 10^1
// 3 -> 100 -> 10^2
// 4 -> 1000 -> 10^3
// 10^(n-1)