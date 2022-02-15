class Solution {
    static int[][] map;
    public int[] solution(int rows, int columns, int[][] queries) {
        int[] answer = new int[queries.length];
        map = new int[rows][columns];
        
        
        int num = 1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                map[i][j] = num++;
            }
        }
        
        for (int i = 0; i < queries.length; i++) {
            answer[i] = rotate(queries[i]);
        }
        
        return answer;
    }
    
    private int rotate(int[] query) {
        int x1 = query[0] - 1;
        int y1 = query[1] - 1;
        int x2 = query[2] - 1;
        int y2 = query[3] - 1;
        
        int tmp = map[x1][y1];
        int min = map[x1][y1];
        
        for (int i = x1; i < x2; i++) {
            map[i][y1] = map[i + 1][y1];
            if (min > map[i][y1])
                min = map[i][y1];
        }
        
        for (int i = y1; i < y2; i++) {
            map[x2][i] = map[x2][i + 1];
            if (min > map[x2][i])
                min = map[x2][i];
        }
        
        for (int i = x2; i > x1; i--) {
            map[i][y2] = map[i - 1][y2];
            if (min > map[i][y2])
                min = map[i][y2];
        }
        
        for (int i = y2; i > y1; i--) {
            map[x1][i] = map[x1][i - 1];
            if (min > map[x1][i])
                min = map[x1][i];
        }
        map[x1][y1 + 1] = tmp;
        return min;
    }
}