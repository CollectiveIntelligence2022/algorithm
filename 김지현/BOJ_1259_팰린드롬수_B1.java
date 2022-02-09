import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_1259_팰린드롬수_B1 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String testCase = null;
		
		while(true) {
			testCase = br.readLine().trim();
			
			if(testCase.equals("0"))
				break;
			
			boolean flag = true;
			int lineSize = testCase.length();
			int rearIdx = lineSize - 1;
			
			for(int frontIdx = 0; frontIdx < lineSize / 2; frontIdx++, rearIdx--) {
				if(testCase.charAt(frontIdx) != testCase.charAt(rearIdx)) {
					flag = false;
					break;
				} else 
					flag = true;
			}
			
			if(flag)
				System.out.println("yes");
			else
				System.out.println("no");
		}
	}
}
