class sol_lv2_피로도 {
    static int answer = -1;

    public int solution(int k, int[][] dungeons) {

        perm(0, 0, k, dungeons, new boolean[dungeons.length]);
        return answer;
    }

    private void perm(int cnt, int see, int k, int[][] dungeons, boolean[] visited) {
        answer = Math.max(answer, see);

        for(int i=0; i<dungeons.length; i++) {
            if(visited[i] || k < dungeons[i][0]) continue;

            visited[i] = true;
            perm(cnt+1, see+1, k - dungeons[i][1], dungeons, visited);
            visited[i] = false;
        }
    }
}
