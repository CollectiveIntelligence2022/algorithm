import java.io.*;

public class Boj_b1_1259_팰린드롬수 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		while (true) {
			String str = br.readLine();
			int size = str.length();

			if (str.equals("0")) {
				break;
			}

			boolean flag = true;
			for (int i = 0; i < size / 2; i++) {
				if (str.charAt(i) != str.charAt(size - 1 - i)) {
					flag = false;
					break;
				}
			}

			if (flag) {
				sb.append("yes\n");
			} else {
				sb.append("no\n");
			}
		}

		System.out.print(sb);
		br.close();
	}
}
