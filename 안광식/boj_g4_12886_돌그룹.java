package problems;

import java.io.*;
import java.util.*;

public class boj_g4_12886_돌그룹 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine(), " ");
		ArrayList<Integer> list = new ArrayList<>();
		for (int i = 0; i < 3; i++) 
			list.add(Integer.parseInt(st.nextToken()));
		
		Collections.sort(list);
		
		System.out.println(bfs(list) ? 1 : 0);
		
		br.close();
	}

	private static boolean bfs(ArrayList<Integer> list) {
		int A = list.get(0);
		int B = list.get(1);
		int C = list.get(2);
		
		ArrayDeque<int[]> dq = new ArrayDeque<>();
		dq.offer(new int[] { A, B, C });
		
		boolean[][] visited = new boolean[1501][1501];
		visited[A][B] = true;
		visited[A][C] = true;
		visited[B][C] = true;
		
		while (!dq.isEmpty()) {
			int[] cur = dq.poll();
			int a = cur[0];
			int b = cur[1];
			int c = cur[2];
			
			if (a == b && b == c)
				return true;
			
			// a b (앞 수를 더 작은 수로 취급)
			if (a != b) {
				if (a > b) {
					int tmp = a;
					a = b;
					b = tmp;
				}
				
				int X = a + a;
				int Y = b - a;
				
				if (!visited[X][Y]) {
					visited[X][Y] = true;
					dq.offer(new int[] { X, Y, c });
				}
			}
			
			// a c
			if (a != c) {
				if (a > c) {
					int tmp = a;
					a = c;
					c = tmp;
				}
				
				int X = a + a;
				int Y = c - a;
				
				if (!visited[X][Y]) {
					visited[X][Y] = true;
					dq.offer(new int[] { X, Y, b });
				}
			}
			
			// b c
			if (b != c) {
				if (b > c) {
					int tmp = b;
					b = c;
					c = tmp;
				}
				
				int X = b + b;
				int Y = c - b;
				
				if (!visited[X][Y]) {
					visited[X][Y] = true;
					dq.offer(new int[] { X, Y, a });
				}
			}
		}
		
		return false;
	}
}


