import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_18429_근손실_S3 {

	private static int N, K;
	private static int[] kit;
	private static int result=0;
	private static boolean[] visited;
	private static int[] temp;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		kit = new int[N];
		visited = new boolean[N];
		temp = new int[N];
		
		st = new StringTokenizer(br.readLine(), " ");
		for(int i = 0; i < N; i++) {
			kit[i] = Integer.parseInt(st.nextToken()) - K;
		}
		
		combination(0);
		System.out.println(result);
		
	}
	
	private static void combination(int count) {
		if(count == N) {
			process(temp, 500);
			return;
		}
		for(int i = 0; i < N; i++) {
			if(visited[i])
				continue;
			
			temp[count] = kit[i];
			visited[i] = true;
			combination(count+1);
			visited[i] = false;
		}
	}
	
	private static void process(int[] kitOrder, int weight) {
		for(int i = 0; i < kitOrder.length; i++) {
			weight = weight + kitOrder[i];
			
			if(weight < 500 && i != kitOrder.length-1)
				break;
			if(weight >= 500) {
				if(i == kitOrder.length - 1) {
					result++;
					return;
				}
				else
					continue;
			}
		}
		return;
	}
	
	
}

/**
1. 입력처리 (미리 무게 빼놓기)
2. 키트 사용하는 순서 모두 고려하기.
3. 그 순서들의 리스트를 


원래는 
**/