import java.util.*;

class Solution_lv3_경주로건설 {
	int[] dx = { -1, 1, 0, 0 };
	int[] dy = { 0, 0, -1, 1 };
	int n;
	final int INF = Integer.MAX_VALUE;
	
	static class Node {
	    int x, y, cost, dir;
	    Node(int x, int y, int cost, int dir) {
	        this.x = x;
	        this.y = y;
	        this.cost = cost;
	        this.dir = dir;
	    }
	}

	public int solution(int[][] board) {
		n = board.length;
		return bfs(board);
	}

	/* 
     * 같은 좌표를 방문하더라도 방향에 따라 다르게 취급
     * -> 3차원 visit 배열 [방향][x][y]
     * 비용을 기록하고 더 최소비용으로 갈 수 있다면 갱신
     * -> price 배열
     */
	public int bfs(int[][] map) {
		int[][] price = new int[n][n];
		for (int i = 0; i < n; i++) {
			Arrays.fill(price[i], INF);
		}
		
		boolean[][][] visit = new boolean[4][n][n];
		ArrayDeque<Node> q = new ArrayDeque<>();
		for (int d = 0; d < 4; d++) {
			visit[d][0][0] = true;
		}
        
        price[0][0] = 0;
		q.offer(new Node(0, 0, 0, -1));
        
		int min = INF;
		while (!q.isEmpty()) {
			Node node = q.poll();

			if (node.x == n - 1 && node.y == n - 1) {
				if (min <= node.cost) {
					continue;
				}

				min = Math.min(min, node.cost);
			}

			for (int d = 0; d < 4; d++) {
				int nx = node.x + dx[d];
				int ny = node.y + dy[d];

				if (!isCheck(nx, ny) || map[nx][ny] == 1) {
					continue;
				}

				// 기본 직선 값
				int nCost = node.cost + 100;
				// 코너는 500 추가
				if (node.dir != -1 && node.dir != d) {
					nCost += 500;
				}

                // 해당 방향으로 방문 안했거나 더 싼 비용으로 갱신 가능
				if (!visit[d][nx][ny] || price[nx][ny] >= nCost) {
					visit[d][nx][ny] = true;
					price[nx][ny] = nCost;
					q.offer(new Node(nx, ny, nCost, d));
				}
			}
		}

		return min;
	}

	/* 배열 범위 체크 */
	public boolean isCheck(int x, int y) {
		return 0 <= x && x < n && 0 <= y && y < n;
	}
}