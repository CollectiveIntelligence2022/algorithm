package bj.silver;

import java.io.*;
import java.util.*;

public class bj_18429_근손실 {
	static int N, K, answer;
	static int[] kit;
	static boolean[] isSelected;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		kit = new int[N];
		isSelected = new boolean[N];
		
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			kit[i] = Integer.parseInt(st.nextToken());
		}
		
		answer = 0;
		perm(0, 500);
		
		System.out.println(answer);
		
		br.close();
	}
	
	private static void perm(int cnt, int sum) {
		if (cnt == N) {
			answer++;
			return;
		}
		
		if (sum < 500) {
			return;
		}
		
		for (int i = 0; i < N; i++) {
			if (isSelected[i])
				continue;
			isSelected[i] = true;
			perm(cnt + 1, sum - K + kit[i]);
			isSelected[i] = false;
		}
	}
}

// 하루에 가능 중량 K씩 감소
// 순열