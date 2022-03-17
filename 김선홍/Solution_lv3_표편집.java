import java.util.*;

class Node {
    // 이전 노드, 현재 노드, 다음 노드
    int pre, cur, nxt;

    Node(int pre, int cur, int nxt) {
        this.pre = pre;
        this.cur = cur;
        this.nxt = nxt;
    }
}

class Solution {
	public String solution(int n, int k, String[] cmd) {
		Stack<Node> s = new Stack<>();
        // 이중 연결 리스트
        // 이전 노드, 다음 노드 정보를 저장
		int[] pre = new int[n];
		int[] nxt = new int[n];
        // 삭제된 노드
		boolean[] isRemoved = new boolean[n];

		for (int i = 0; i < n; i++) {
			pre[i] = i - 1;
			nxt[i] = i + 1;
		}
		nxt[n - 1] = -1;

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cmd.length; i++) {
			String[] in = cmd[i].split(" ");
			int X = 0;
			switch (in[0]) {
			case "U":// 포인터 X번 위로 이동
				X = Integer.parseInt(in[1]);
				while (--X >= 0) {
					k = pre[k];
				}
				break;
			case "D":// 포인터 X번 밑으로 이동
				X = Integer.parseInt(in[1]);
				while (--X >= 0) {
					k = nxt[k];
				}
				break;
			case "C":// 현재 위치에 있는 노드를 삭제
				s.push(new Node(pre[k], k, nxt[k]));
				if (pre[k] != -1) {
					nxt[pre[k]] = nxt[k];
				}
				if (nxt[k] != -1) {
					pre[nxt[k]] = pre[k];
				}
				isRemoved[k] = true;
                
                // 현재 포인터 갱신
                if (nxt[k] != -1) {
                    k = nxt[k];
                } else {
                    k = pre[k];
                }
				break;
			case "Z":// 최근에 삭제한 노드를 복구 -> 연결도 복구
				Node node = s.pop();
				if (node.pre != -1) {
					nxt[node.pre] = node.cur;
				}
				if (node.nxt != -1) {
					pre[node.nxt] = node.cur;
				}
				isRemoved[node.cur] = false;
				break;
			}
		}

        // 삭제된 노드 - X, 삭제안된 노드 - O
		for (int i = 0; i < n; i++) {
			if (isRemoved[i]) {
				sb.append("X");
			} else {
				sb.append("O");
			}
		}
		return sb.toString();
	}
}
