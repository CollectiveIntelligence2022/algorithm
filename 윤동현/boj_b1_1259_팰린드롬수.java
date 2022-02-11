import java.io.*;

public class boj_b1_1259_팰린드롬수 {

  public static void main(String[] args) throws Exception {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();
    char[] words;
    int i, j;
    boolean isCheck;

    while (true) {
      words = br.readLine().toCharArray();
      if (words.length == 1 && words[0] == '0') {
        br.close();
        break;
      }
      isCheck = false;
      i = 0;
      j = words.length - 1;
      for (; i < words.length / 2; i++, j--) {
        if (i != j && words[i] != words[j]) {
          isCheck = true;
          break;
        }
      }
      sb.append((isCheck) ? "no" : "yes").append("\n");
    }

    System.out.println(sb.toString());
  }

}