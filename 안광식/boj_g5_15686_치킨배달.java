package problems;

import java.io.*;
import java.util.*;

public class boj_g5_15686_치킨배달 {
	static class Pos {
		int x, y;
		public Pos(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	static int N, M, answer;
	static int[] numbers;
	static ArrayList<Pos> Customers;
	static ArrayList<Pos> Stores;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		Customers = new ArrayList<>();
		Stores = new ArrayList<>();
		
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int j = 0; j < N; j++) {
				int input = Integer.parseInt(st.nextToken());
				if (input == 1) {
					Customers.add(new Pos(i, j));
				}
				else if (input == 2) {
					Stores.add(new Pos(i, j));
				}
			}
		}
		
		numbers = new int[M];
		answer = Integer.MAX_VALUE;
		comb(0, 0);
		
		System.out.println(answer);
		
		br.close();
	}
	
	private static void comb(int cnt, int start) {
		if (cnt == M) {
//			System.out.println(Arrays.toString(numbers));
			updateMinValue();
			
			return;
		}
		
		for (int i = start; i < Stores.size(); i++) {
			numbers[cnt] = i;
			comb(cnt + 1, i + 1);
		}
	}

	private static void updateMinValue() {
		int cityDist = 0;
		
		for (Pos cur : Customers) {
			cityDist += getChickenDist(cur);
		}
		
		answer = Integer.min(answer, cityDist);
	}

	private static int getChickenDist(Pos customer) {
		int minDist = Integer.MAX_VALUE;
		
		for (int idx : numbers) {
			Pos store = Stores.get(idx);
			minDist = Integer.min(minDist, getDist(customer, store));
		}
		
		return minDist;
	}

	private static int getDist(Pos customer, Pos store) {
		return Math.abs(customer.x - store.x) + Math.abs(customer.y - store.y);
	}
}

// 치킨 거리 : 맨해튼 거리
// 조합.. 치킨집 수는 M보다 크거나 같고 13보다 작거나 같음
// 맵 정보는 굳이?
// 집 정보 어레리
// 치킨집 정보 어레리
// 치킨집 3개 뽑기 (인덱스로)
// 집마다 모든 치킨집 정보 돌면서 최소 거리 sum에 더해주기
// 다 돌고나면 sum, min 비교 갱신