class Solution_lv2_프렌즈4블록 {
    char[][] map;
    int R, C, answer;
    
    public int solution(int m, int n, String[] board) {
        R = m;
        C = n;
        map = copyBoard(board);
        // 블록을 더 이상 못 지울때까지 반복
        while (isFind()) {
            downBlock();
        }
        
        return answer;
    }
    
    /* 지울 블록을 찾아서 지운다 */
    public boolean isFind() {
        boolean[][] visit = new boolean[R][C];
        boolean isAble = false;
        
        for (int i = 0; i < R - 1; i++) {
            for (int j = 0; j < C - 1; j++) {
                // 빈 블록은 넘어감
                if (map[i][j] == ' ') {
                    continue;
                }

                // 2x2 블록이 같다면 지운다
                if (map[i][j] == map[i + 1][j] && map[i + 1][j] == map[i][j + 1] && map[i][j + 1] == map[i + 1][j + 1]) {
                    removeBlock(visit, i, j);
                    isAble = true;
                }
            }
        }
        
        if (!isAble) {
            return isAble;
        }
        
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (visit[i][j]) {
                    map[i][j] = ' ';
                }
            }
        }
        
        return isAble;
    }
    
    /* 지울 수 있는 2x2 블록을 지우고 카운팅 */
    public void removeBlock(boolean[][] visit, int x, int y) {
        for (int i = x; i < x + 2; i++) {
            for (int j = y; j < y + 2; j++) {
                if (!visit[i][j]) {
                    answer++;
                    visit[i][j] = true;
                }
            }
        }
    }
    
    /* 내릴 수 있는 블록을 모두 내린다 */
    public void downBlock() {
        for (int j = 0; j < C; j++) {
            int pre = R - 1;
            for (int i = R - 2; i >= 0; i--) {
                // 빈 칸이면 내릴 블록이 없다
                if (map[i][j] == ' ') {
                    continue;
                }
                
                // 내릴 수 있는 곳이 있다면 내린다
                if (map[pre][j] == ' ') {
					map[pre][j] = map[i][j];
					map[i][j] = ' ';
                    
                    // 없다면 마지막으로 가리키는 위치가 현재 행 + 1 보다 큰지 검사
                    // -> 후위 연산으로 체크하기 때문에 검사 후 pre를 한 칸 올린다
                    // -> 현재 행 + 1 보다 크다면 갱신된 pre행에 현재 블록을 이동시킨다
				} else {
					if (pre-- > i + 1) {
						map[pre][j] = map[i][j];
						map[i][j] = ' ';
					}
				}
            }
        }
    }
    
    /* String 배열 -> 2차원 char 배열로 변경 */
    public char[][] copyBoard(String[] board) {
        char[][] map = new char[R][C];
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                map[i][j] = board[i].charAt(j);
            }
        }
        return map;
    }
}