import java.util.*;
import java.util.Map.Entry;

class Music implements Comparable<Music> {
	int idx, cnt;

	Music(int idx, int cnt) {
		this.idx = idx;
		this.cnt = cnt;
	}

	@Override
	public int compareTo(Music m) {
		// **장르 내에서 재생 횟수가 같은 노래 중에서는 고유 번호가 낮은 노래를 먼저 수록**
		if (this.cnt == m.cnt) {
			return m.idx - this.idx;
		}
		return this.cnt - m.cnt;
	}
}

class Solution_lv3_베스트앨범 {
	public ArrayList<Integer> solution(String[] genres, int[] plays) {
		// key: 장르, value: 총 재생 수
		Map<String, Integer> song = new HashMap<>();
		// key: 장르, value: Music 객체를 가지는 우선순위 큐
		Map<String, PriorityQueue<Music>> top2 = new HashMap<>();
		// top2에 넣기 위한 우선순위 큐
		PriorityQueue<Music> pq = new PriorityQueue<>();

		for (int i = 0; i < genres.length; i++) {
			// 등록된 장르
			if (song.containsKey(genres[i])) {
				song.put(genres[i], song.get(genres[i]) + plays[i]);
				pq = top2.get(genres[i]);

				// top2가 이미 있다 -> 비교해서 갱신
				if (pq.size() == 2) {
					Music music = pq.poll();

					// 등록하는 노래의 재생수가 두 번째 노래의 재생수보다 많다면
					// 등록하려는 노래를 넣는다
					if (music.cnt < plays[i]) {
						pq.offer(new Music(i, plays[i]));
					} else {// 아니라면 원래 뽑았던 노래를 넣는다
						pq.offer(music);
					}
				} else {// 아직 top2가 아니다 -> Music 객체 추가
					pq.offer(new Music(i, plays[i]));
				}

				// 위 과정으로 연산된 우선순위 큐 top2에 추가
				top2.put(genres[i], pq);

			} else {
				song.put(genres[i], plays[i]);
				pq = new PriorityQueue<>();
				pq.offer(new Music(i, plays[i]));
				top2.put(genres[i], pq);
			}
		}

		// 맵을 엔트리 타입 리스트로 변환시켜 정렬 정의 -> 재생 수 내림차순
		List<Entry<String, Integer>> list = new ArrayList<>(song.entrySet());
		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> e1, Entry<String, Integer> e2) {
				return e2.getValue().compareTo(e1.getValue());
			}
		});

		ArrayList<Integer> answer = new ArrayList<>();
		for (Entry<String, Integer> entry : list) {
			pq = top2.get(entry.getKey());
			Music m1 = pq.poll();

			// 어떤 장르는 곡이 하나만 있을 수가 있다
			// 그게 아니라면 큐에 남아있는 곡은 해당 장르 top1 곡
			// top1 곡을 먼저 추가
			if (!pq.isEmpty()) {
				Music m2 = pq.poll();
				answer.add(m2.idx);
			}

			// 남은 곡 추가
			answer.add(m1.idx);
		}

		return answer;
	}
}