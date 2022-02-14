import java.io.*;
import java.util.*;

/**
 * @author	: KimSeonhong
 * @date	: 2022. 2. 10.
 * 
 * 분류: 브루트포스 알고리즘, 백트래킹
 * 난이도: 실버3
 * 소요 시간: 0h 9m
 * 혼자 품: O
 * 풀이: 
 * 1. 운동 키트 적용 순서에 따른 경우의 수를 구해야하므로 순열을 구해야 한다.
 * 2. 순열을 하나씩 만들어 가면서 중간에 sum값이 500을 넘지 못한다면 현재 탐색을 중단하고 다음 탐색을 시작한다.(가지치기)
 * 느낀 점: 순열을 구해야 한다는 것을 파악하면 어렵지 않은 문제이고 중간에 가지치기를 해주면 효율성이 보장되는 알고리즘이 된다.
 */
public class Main_s3_18429_근손실 {
	static int[] weight;
	static boolean[] isUsed;
	static int N, K, res;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		weight = new int[N];
		isUsed = new boolean[N];

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			weight[i] = Integer.parseInt(st.nextToken());
		}

		perm(0, 500);
		System.out.println(res);
		br.close();
	}

	private static void perm(int cnt, int sum) {
		if (sum < 500) {
			return;
		}

		if (cnt == N) {
			res++;
			return;
		}

		for (int i = 0; i < N; i++) {
			if (!isUsed[i]) {
				isUsed[i] = true;
				perm(cnt + 1, sum + weight[i] - K);
				isUsed[i] = false;
			}
		}
	}
}