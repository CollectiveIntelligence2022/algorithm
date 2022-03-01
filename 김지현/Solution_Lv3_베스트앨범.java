import java.util.*;

class Song implements Comparable<Song> {
    int id;
    int play;
    String genre;
    
    public Song(int id, int play, String genre) {
        this.id = id;
        this.play = play;
        this.genre = genre;
    }
    
    @Override
    public int compareTo(Song that) {
        if(this.play == that.play)
            return Integer.compare(this.id, that.id);
        return -Integer.compare(this.play, that.play);
    }
}

class Solution {
    public ArrayList<Integer> solution(String[] genres, int[] plays) {
        ArrayList<Integer> answer = new ArrayList<>();
        
        // 데이터 정리
        Map<String, Integer> genreMap = new HashMap<>();
        ArrayList<Song> musicList = new ArrayList<>();
        
        for(int id = 0; id < genres.length; id++) {
            genreMap.put(genres[id], genreMap.getOrDefault(genres[id], 0) + plays[id]);
            musicList.add(new Song(id, plays[id], genres[id]));
        }
        
        // 해시맵의 value값을 기준으로 정렬하기 (내림차순) + 람다식
        List<Map.Entry<String, Integer>> entryList = new LinkedList<>(genreMap.entrySet());        
        entryList.sort(((o1, o2) -> genreMap.get(o2.getKey()) - genreMap.get(o1.getKey())));
        
        for(Map.Entry<String, Integer> entry : entryList){
             answer.addAll(check(entry.getKey(), musicList));
        }
        
        return answer;
    }
    
    public ArrayList<Integer> check(String genre, ArrayList<Song> playlist) {
        ArrayList<Integer> temp = new ArrayList<>();
        PriorityQueue<Song> sortList = new PriorityQueue<>();
        for(Song play : playlist) {
            if(genre.equals(play.genre)) {
                sortList.offer(play);
            }
        }
        int songCount = 0;
        
        while(!sortList.isEmpty() && songCount < 2) {
            temp.add(sortList.poll().id);
            songCount++;
        }
        
        return temp;
        
    }
}
