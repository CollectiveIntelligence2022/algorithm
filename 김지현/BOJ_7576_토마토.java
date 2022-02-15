import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_7576_토마토 {
	static class Tomato {
		private int row;
		private int col;
		public Tomato() {};
		public Tomato(int row, int col) {
			super();
			this.row = row;
			this.col = col;
		}

		public int getRow() {
			return row;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public int getCol() {
			return col;
		}

		public void setCol(int col) {
			this.col = col;
		}
		
		
	}
	
	private static int N, M;
	
	private static int[] dr = {-1, 1, 0, 0};
	private static int[] dc = {0, 0, -1, 1};
	
	private static int[][] map;
	private static Queue<Tomato> ripe = new LinkedList<>();
	private static boolean[][] visited;
	
	private static int result;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		
		map = new int[N][M];
		visited = new boolean[N][M];
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for(int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 1)
					ripe.add(new Tomato(i, j));
			}
		}
		
		result = BFS(0);
		System.out.println(result);
	}
	
	private static int BFS(int dayCount) {
		Tomato current;
		while(!ripe.isEmpty()) {
			current = ripe.poll();
			for(int move = 0; move < 4; move++) {
				int nr = current.row + dr[move];
				int nc = current.col + dc[move];
				
				if(isLineOut(nr, nc))
					continue;
				else {
					if(map[nr][nc] == 0) {
						ripe.offer(new Tomato(nr, nc));
						visited[nr][nc] = true;
						map[nr][nc] = 1;
						dayCount++;
					}
				}
			}
		}
		
		if(ripeAll())
			return dayCount;
		else
			return -1;
	}
	
	private static boolean isLineOut(int row, int col) {
		if(row < 0 || col < 0 || row >= N || col >= M || visited[row][col] || map[row][col] == -1)
			return true;
		return false;
	}
	
	private static boolean ripeAll() {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(map[i][j] == 0)
					return false;
			}
		}
		return true;
	}
}

