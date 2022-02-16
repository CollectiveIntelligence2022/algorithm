import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_7576_토마토 {
	private static class Tomato {
		private int row;
		private int col;
		
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
	private static boolean[][] visited;

	private static Queue<Tomato> ripe = new LinkedList<>();
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
				if(map[i][j] == 1) {
					ripe.add(new Tomato(i, j));
				}
			}
		}
		
		BFS();
		
		result = ripeAll();
		System.out.println(result);
	}
	
	private static void BFS() {
		Tomato current;
		
		while(!ripe.isEmpty()) {
			current = ripe.poll();
			int day = map[current.row][current.col];
			
			for(int move = 0; move < 4; move++) {
				int nr = current.row + dr[move];
				int nc = current.col + dc[move];
				
				if(isLineOut(nr, nc))
					continue;
				else {
					if(map[nr][nc] == 0) {
						visited[nr][nc] = true;
						map[nr][nc] = day + 1;
						ripe.add(new Tomato(nr, nc));
					}
					
					
				}
			}
		}
	}
	
	private static boolean isLineOut(int r, int c) {
		if(r < 0 || c < 0 || r >= N || c >= M || visited[r][c] || map[r][c] == -1)
			return true;
		return false;
	}
	
	private static int ripeAll() {
		int temp = 0;
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(map[i][j] == 0)
					return -1;
				temp = Math.max(temp,  map[i][j]);
			}
		}
		return temp - 1;
	}
}

/**
 * 설계는 다음과 같다.
 * 
 * 토마토 박스에는 -1,0 그리고 익었다는 표시의 1이 있다.
 * 익었다는 표현을 며칠만에 익었냐는 표현으로 쓰고자 한다.
 * 
 * 보통의 완탐은 시작점이 1개이지만, 이 문제는 여러개다.
 * 고로, 입력할때 바로 익었다는 큐 ripe에 익은 토마토 위치 값을 저장해준다.
 * 
 * 그리고 완탐을 하면서 방문한적 있거나, 토마토가 없거나경계선벗어난 경우에는 skip
 * 체크해야하는 경우 중에서도, 0으로 익지 않은 토마토에 한해서만
 * visited처리, ripe에 익었다는 토마토로 큐에 삽입 그리고!값을 단순 1이 아닌날로 기입해준다.
 * 갓 익은 토마토의 값(날짜)에서 영향받아 익었기에 +1한것이다.
 * 
 * 그리고 최종적으로 맵을 돌면서, 익지않은 토마토가 하나라도 있다면 -1을, 그리고 다 익었다면 최대값 날짜가 출력될 것이다.
 * (여기서, 최종날짜 -1 한 것은 이미 시작할때부터 1로 시작하기에 시작부터 0일이 아닌 1일로 처리되었기에 이를 -1 처리)
**/