package problems;

import java.io.*;
import java.util.*;

public class boj_g2_19236_청소년상어 {
	static class Fish {
		int r, c, dir;
		boolean isAlive;
		public Fish(int r, int c, int dir, boolean isAlive) {
			this.r = r;
			this.c = c;
			this.dir = dir;
			this.isAlive = isAlive;
		}
	}
	static int answer;
	static int[] di = { -1, -1, 0, 1, 1, 1, 0, -1 }; // 상 좌상 좌 좌하 하 우하 우 우상
	static int[] dj = { 0, -1, -1, -1, 0, 1, 1, 1 };
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int[][] map = new int[4][4];
		Fish[] fishInfo = new Fish[16 + 1];
		
		for (int i = 0; i < 4; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < 4; j++) {
				int num = Integer.parseInt(st.nextToken());
				int dir = Integer.parseInt(st.nextToken()) - 1;
				map[i][j] = num;
				fishInfo[num] = new Fish(i, j, dir, true);
			}
		}
		
		// 상어 입장
		int sharkR = 0;
		int sharkC = 0;
		int sharkDir = fishInfo[map[0][0]].dir;
		int eatNum = map[0][0];
		fishInfo[eatNum].isAlive = false;
		map[0][0] = -1;
		
		answer = 0;
		
		Solution(sharkR, sharkC, sharkDir, map, fishInfo, eatNum);
		
		System.out.println(answer);
		
		br.close();
	}
	
	private static void Solution(int sharkR, int sharkC, int sharkDir, int[][] map, Fish[] fishInfo, int sum) {
		fishMove(map, fishInfo);
		
		// 3.상어 이동
		//  - 현재 방향으로 이동 가능, 물고기 있는 칸으로만 이동 가능
		//  - 이동 후 먹은 물고기의 방향을 가지게 됨
		//  - 여러 칸 이동 가능하고 이동 경로에 있는 물고기에는 영향 안줌
		boolean isEnd = true;
		
		for (int i = 1; i <= 3; i++) {
			int nx = sharkR + di[sharkDir] * i;
			int ny = sharkC + dj[sharkDir] * i;
			
			if (nx < 0 || nx >= 4 || ny < 0 || ny >= 4 || map[nx][ny] == 0)
				continue;
			
			isEnd = false;
			
			int nd = fishInfo[map[nx][ny]].dir;
			int[][] copyMap = mapCopy(map);
			Fish[] copyFishInfo = fishInfoCopy(fishInfo);
			
			int eatNum = copyMap[nx][ny];
			copyFishInfo[eatNum].isAlive = false;
			
			copyMap[sharkR][sharkC] = 0;
			copyMap[nx][ny] = -1;
			
			Solution(nx, ny, nd, copyMap, copyFishInfo, sum + eatNum);
		}
		
		if (isEnd) {
			answer = Integer.max(answer, sum);
			return;
		}
	}

	private static int[][] mapCopy(int[][] map) {
		int[][] copyMap = new int[4][4];
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				copyMap[i][j] = map[i][j];
			}
		}
		
		return copyMap;
	}

	private static Fish[] fishInfoCopy(Fish[] fishInfo) {
		Fish[] copyFishInfo = new Fish[16 + 1];
		
		for (int i = 1; i <= 16; i++) {
			Fish toCopy = fishInfo[i];
			copyFishInfo[i] = new Fish(toCopy.r, toCopy.c, toCopy.dir, toCopy.isAlive);
		}
		
		return copyFishInfo;
	}

	private static void fishMove(int[][] map, Fish[] fishInfo) {
		// 2.물고기 번호순으로 이동
		//  - 빈 칸, 다른 물고기 있는 칸으로 이동 가능
		//  - 상어 있거나 맵 밖 나가면 이동 불가
		//  - 이동 칸 찾을 때 까지 45도씩 반시계 회전 후 이동, 만약 이동 불가능하면 기존 방향 유지하고 이동 안함
		for (int i = 1; i <= 16; i++) {
			Fish fish = fishInfo[i];
			
			if (!fish.isAlive)
				continue;
			
			for (int d = 0; d < 8; d++) {
				int nd = (fish.dir + d) % 8;
				int nx = fish.r + di[nd];
				int ny = fish.c + dj[nd];
				
				if (nx < 0 || nx >= 4 || ny < 0 || ny >= 4 || map[nx][ny] == -1)
					continue;
				
				// 이동
				// 빈 칸인 경우
				if (map[nx][ny] == 0) {
					map[fish.r][fish.c] = 0;
					map[nx][ny] = i;

					fish.dir = nd;
					fish.r = nx;
					fish.c = ny;
				}
				
				// 물고기인 경우
				else if (map[nx][ny] > 0) {
					Fish changeFish = fishInfo[map[nx][ny]]; // 바꿀 물고기
					
					// 맵 상 위치 변경
					map[fish.r][fish.c] = map[nx][ny];
					map[nx][ny] = i;
					
					// 물고기 정보 변경
					changeFish.r = fish.r;
					changeFish.c = fish.c;
					
					fish.dir = nd;
					fish.r = nx;
					fish.c = ny;
				}
				
				break;
			}
		}
	}
}

// 4*4
// 칸 마다 물고기 존재, 번호, 방향 가짐
// 번호는 1 ~ 16 중복 없음
// 방향은 8방

// 청소년 상어는 (0,0) 먹고 그 자리에 들어감

// 물고기는 번호가 작은 물고기부터 순서대로 이동
// 이동 가능 : 빈 칸, 다른 물고기가 있는 칸
// 이동 불가 : 상어가 있는 칸, 맵 밖
// 이동할 수 있는 칸을 향할 때 까지 45도 반시계 회전, 만약 이동 가능한 칸 없으면 이동 안함
// 그 외에는 해당 칸으로 이동
// 다른 물고기가 있는 경우, 서로 위치 바꾸는 방식으로 이동

// 물고기 이동 끝나면 상어 이동
// 상어는 방향에 있는 칸으로 이동 가능, 한 번에 여러 개 칸 이동 가능
// 물고기가 있는 칸으로 이동했다면, 그 칸의 물고기 먹고 그 물고기의 방향을  가짐 (이동 경로에 있는 물고기는 안먹음)
// 물고기 없는 칸으로는 이동 불가능
// 이동 후 위 과정 반복
// 상어 이동 불가능하면 종료

// 1.상어 입장 (입력 받을 때 바로 처리하면 될듯)
// 2.물고기 번호순으로 이동
//  - 빈 칸, 다른 물고기 있는 칸으로 이동 가능
//  - 상어 있거나 맵 밖 나가면 이동 불가
//  - 이동 칸 찾을 때 까지 45도씩 반시계 회전 후 이동, 만약 이동 불가능하면 기존 방향 유지하고 이동 안함
// 3.상어 이동
//  - 현재 방향으로 이동 가능, 물고기 있는 칸으로만 이동 가능
//  - 이동 후 먹은 물고기의 방향을 가지게 됨
//  - 여러 칸 이동 가능하고 이동 경로에 있는 물고기에는 영향 안줌

// 상어 이동은 재귀로 구현하자.. 가능한 여러 칸 모두 해봐야 하기 때문에..
// 물고기 정보는 16마리 픽스니까 배열로 관리, 인덱스가 물고기 번호, 값은 위치 정보, 방향, 생사여부
// 맵 정보
// 0 : 빈 칸
// 1 ~ 16 : 물고기
// -1 : 상어
















