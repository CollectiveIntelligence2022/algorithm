package bj.bronze;

import java.io.*;

public class bj_1259_팰린드롬수 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input = "";
		
		while (true) {
			input = br.readLine();
			
			// 종료
			if (input.equals("0"))
				break;
			
			int len = input.length();
			
			boolean flag = false;
			// 양 끝에서부터 비교하면서 다른게 있으면 no
			for (int i = 0; i < len / 2; i++) {
				if (input.charAt(i) != input.charAt(len - 1 - i)) {
					System.out.println("no");
					flag = true;
					break;
				}
			}
			
			if (!flag)
				System.out.println("yes");
		}
		
		br.close();
	}
}
