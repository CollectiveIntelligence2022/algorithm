package problems;

import java.util.*;
import java.io.*;

public class boj_g5_14891_톱니바퀴 {
	static ArrayList<Integer>[] wheel;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		wheel = new ArrayList[4];
		for (int i = 0; i < 4; i++) {
			String info = br.readLine();

			wheel[i] = new ArrayList<>();
			for (int j = 0; j < 8; j++) 
				wheel[i].add(info.charAt(j) - '0');
		}
		
		int k = Integer.parseInt(br.readLine());
		for (int i = 0; i < k; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int n = Integer.parseInt(st.nextToken()) - 1;
			int d = Integer.parseInt(st.nextToken());
			rotate(n, 0, d == 1);
		}
		
		System.out.println(getScore());
		
		br.close();
	}

	private static void rotate(int n, int moveD, boolean rotateD) {
		// 왼쪽
		if (n > 0 && moveD <= 0) {
			if (wheel[n].get(6) != wheel[n - 1].get(2)) {
				rotate(n - 1, -1, !rotateD);
			}
		}
		
		// 오른쪽
		if (n < 3 && moveD >= 0) {
			if (wheel[n].get(2) != wheel[n + 1].get(6)) {
				rotate(n + 1, 1, !rotateD);
			}
		}
		
		// 시계 방향 회전
		if (rotateD) {
			int tmp = wheel[n].get(7);
			wheel[n].remove(7);
			wheel[n].add(0, tmp);
		}
		
		// 반시계 방향 회전
		else {
			int tmp = wheel[n].get(0);
			wheel[n].remove(0);
			wheel[n].add(tmp);
		}
	}
	
	private static Integer getScore() {
		int sum = 0;
		
		for (int i = 0; i < 4; i++) {
			if (wheel[i].get(0) == 1)
				sum += Math.pow(2, i);
		}
		
		return sum;
	}
}

// 톱니바퀴 4개
// k번 회전 (시계, 반시계)
// 맞닿은 톱니의 극이 다르면 반대 방향으로 회전
// 동시에 회전해야함.. 재귀 타고 쭉쭉 들어가서 나오면서 돌려주기
// 톱니바퀴는 12시 방향부터 시계방향 순서로 주어짐
// 오른쪽 칸 인덱스 2
// 왼쪽 칸 인덱스 6
// 시계 방향? d == true
// - 젤 뒷놈 빼서 젤 앞으로
// 반시계 방향? d == false
// - 젤 앞놈 빼서 젤 뒤로