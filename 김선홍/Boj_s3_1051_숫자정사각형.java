import java.io.*;
import java.util.*;

public class Boj_s3_1051_숫자정사각형 {
	static int[][] arr;
	static int N, M;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		arr = new int[N][M];
		for (int i = 0; i < N; i++) {
			String str = br.readLine();
			for (int j = 0; j < M; j++) {
				arr[i][j] = str.charAt(j) - '0';
			}
		}

		System.out.println(solution());
		br.close();
	}

	private static int solution() {
		int res = 0;
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				res = Math.max(res, brute(i, j));
			}
		}

		return res;
	}

	private static int brute(int x, int y) {
		int len = 0, size = 0;

		while (x + len < N && y + len < M) {
			if (check(x, y, len)) {
				size = (len + 1) * (len + 1);
			}

			len++;
		}

		return size;
	}

	private static boolean check(int x, int y, int s) {
		return arr[x][y] == arr[x + s][y] && arr[x][y] == arr[x][y + s] && arr[x][y] == arr[x + s][y + s];
	}
}
