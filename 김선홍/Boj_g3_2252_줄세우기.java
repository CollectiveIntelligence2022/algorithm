import java.io.*;
import java.util.*;

public class Boj_g3_2252_줄세우기 {
	static List<Integer>[] list;
	static int[] cnt;
	static int N, M;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		list = new ArrayList[N + 1];
		cnt = new int[N + 1];

		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			// 반대로
			list[a].add(b);
			cnt[b]++;
		}

		solution();
		br.close();
	}

	private static void solution() {
		ArrayDeque<Integer> q = new ArrayDeque<>();
		StringBuilder sb = new StringBuilder();
		
		if (N == 1) {
			System.out.println(1);
			return;
		}

		for (int i = 1; i <= N; i++) {
			if (cnt[i] == 0) {
				q.offer(i);
			}
		}

		while (!q.isEmpty()) {
			int cur = q.poll();
			sb.append(cur).append(" ");

			for (int nxt : list[cur]) {
				if (--cnt[nxt] == 0) {
					q.offer(nxt);
				}
			}
		}

		System.out.println(sb);
	}
}