import java.util.*;

class Solution_lv2_주차요금계산 {
    public int[] solution(int[] fees, String[] records) {
        // key: 차 번호, value: 주차 시간
        // 현재 주차된 차를 저장하는 맵
		Map<String, Integer> carMap = new HashMap<>();
        // 누적 주차 시간을 저장하는 맵
		Map<String, Integer> timeMap = new TreeMap<>();

		for (int i = 0; i < records.length; i++) {
			String[] input = records[i].split(" ");
			String[] time = input[0].split(":");
			int h = Integer.parseInt(time[0]);
			int m = Integer.parseInt(time[1]);
			int t = 60 * h + m;

            // 주차하는 차라면 차 번호와 현재 시간을 저장
			if (input[2].equals("IN")) {
				carMap.put(input[1], t);
                // 나가는 차라면 나가는 시간 - 주차 시간을 계산해서 timeMap에 누적
                // carMap에서 현재 주차 차량의 번호를 지운다
			} else {
				t -= carMap.get(input[1]);
				carMap.remove(input[1]);
				timeMap.put(input[1], timeMap.getOrDefault(input[1], 0) + t);
			}
		}

        // 아직 안나간 차들의 주차시간을 누적
		int t = 23 * 60 + 59;
		for (String key : carMap.keySet()) {
			int time = t - carMap.get(key);
			timeMap.put(key, timeMap.getOrDefault(key, 0) + time);
		}

		int[] answer = new int[timeMap.size()];
		int i = 0;
        
        // 요금 정산
        // timeMap은 TreeMap이라서 key 순으로 오름차순 정렬 되어 있음
		for (String key : timeMap.keySet()) {
			t = timeMap.get(key);
			int money = t > fees[0] ? fees[1] + (int) Math.ceil(1.0 * (t - fees[0]) / fees[2]) * fees[3] : fees[1];
			answer[i++] = money;
		}

		return answer;
	}
}