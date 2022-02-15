
class Solution_lv2_행렬테두리회전하기 {
	public static int[] solution(int rows, int columns, int[][] queries) {
		int[] answer = new int[queries.length];
		int[][] map = new int[rows][columns];

		int cnt = 0;
		// 배열 초기화
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				map[i][j] = ++cnt;
			}
		}

		for (int k = 0; k < queries.length; k++) {
			int sx = queries[k][0] - 1;
			int sy = queries[k][1] - 1;
			int ex = queries[k][2] - 1;
			int ey = queries[k][3] - 1;

			int min = rows * columns + 1;
			int tmp = map[sx][sy];

			// ↑
			for (int i = sx; i < ex; i++) {
				map[i][sy] = map[i + 1][sy];
				min = Math.min(min, map[i][sy]);
			}

			// ←
			for (int j = sy; j < ey; j++) {
				map[ex][j] = map[ex][j + 1];
				min = Math.min(min, map[ex][j]);
			}

			// ↓
			for (int i = ex; i > sx; i--) {
				map[i][ey] = map[i - 1][ey];
				min = Math.min(min, map[i][ey]);
			}

			// →
			for (int j = ey; j > sy; j--) {
				map[sx][j] = map[sx][j - 1];
				min = Math.min(min, map[sx][j]);
			}

			map[sx][sy + 1] = tmp;
			min = Math.min(min, map[sx][sy + 1]);

			answer[k] = min;
		}

		return answer;
	}
}