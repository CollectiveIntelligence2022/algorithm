import java.io.*;
import java.util.*;

public class Boj_g3_2533_사회망서비스SNS {
	static List<Integer>[] list, tree;
	static int[][] dp;
	static boolean[] visit;
	static int N, res;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		list = new ArrayList[N + 1];
		tree = new ArrayList[N + 1];
		dp = new int[2][N + 1];
		visit = new boolean[N + 1];

		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
			tree[i] = new ArrayList<>();
		}

		for (int i = 0; i < N - 1; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			list[a].add(b);
			list[b].add(a);
		}

		System.out.println(solution());
		br.close();
	}

	private static int solution() {
		Arrays.fill(dp[0], -1);
		Arrays.fill(dp[1], -1);
		dfs(1);
		return findEarlyadopter(1, 1);
	}

	/* dfs로 트리를 만든다 -> 양방향에서 부모가 자식으로 향하는 방향인 단방향으로 만듬 */
	private static void dfs(int cur) {
		visit[cur] = true;

		for (int nxt : list[cur]) {
			if (!visit[nxt]) {
				tree[cur].add(nxt);
				dfs(nxt);
			}
		}
	}

	/*
	 * 얼리어답터 연결 구성
	 * -> 부모가 얼리어답터가 아니면(0) 이번에는 무조건 자식이 얼리어답터가 되야함
	 * -> 부모가 얼리어답터가 맞다면(1) 이번에는 내가 얼리어답터가 되거나 안되거나
	 */
	private static int findEarlyadopter(int cur, int isPre) {
		if (dp[isPre][cur] != -1) {
			return dp[isPre][cur];
		}
		
		int notAble = 1000005, able = 1;
		
		if (isPre == 0) {
			for (int child : tree[cur]) {
				able += findEarlyadopter(child, 1);
			}
		} else {
			notAble = 0;
			for (int child : tree[cur]) {
				notAble += findEarlyadopter(child, 0);
				able += findEarlyadopter(child, 1);
			}
		}
		
		return dp[isPre][cur] = Math.min(notAble, able);
	}

//	/*
//	 * 각 노드 마다 자식 노드의 개수를 비교
//	 * 리프 노드는 최소 자기자신 or 부모가 얼리어답터
//	 */
//	private static void dfs(int pre, int cur) {
//		visit[cur] = true;
//
//		int cnt = 0;
//		for (int nxt : list[cur]) {
//			if (!visit[nxt]) {
//				dfs(cur, nxt);
//			}
//		}
//		
//		if (cnt > 1) {
//			dp[cur] = true;
//		} else if (pre != -1) {
//			dp[pre] = true;
//			dp[cur] = false;
//		}
//
//		if (list[cur].isEmpty()) {
//			dp[pre] = true;
//		}
//	}
//
//	private static int getMinimum() {
//		int res = 0;
//		for (int i = 1; i <= N; i++) {
//			if (dp[i]) {
//				res++;
//			}
//		}
//		return res;
//	}
}