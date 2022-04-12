import java.util.*;

class Solution {
    public int[] solution(int[] fees, String[] records) {
        HashMap<String, String> map = new HashMap<>();
        HashMap<String, Integer> totalTimeMap = new HashMap<>();
        
        for (String str : records) {
            String[] record = str.split(" ");
            
            // 들어온 경우
            if (record[2].equals("IN")) {
                map.put(record[1], record[0]);
                continue;
            }
            
            // 나간 경우            
            String inTime = map.get(record[1]);
            map.remove(record[1]);
            
            int parkTime = getParkTime(inTime, record[0]);
            if (totalTimeMap.containsKey(record[1])) 
                parkTime += totalTimeMap.get(record[1]);
            totalTimeMap.put(record[1], parkTime);
        }
        
        // 남은 애들 처리 (23:59 출차로 간주)
        for (String key : map.keySet()) {
            String inTime = map.get(key);
            // map.remove(key);
            
            int parkTime = getParkTime(inTime, "23:59");
            if (totalTimeMap.containsKey(key)) 
                parkTime += totalTimeMap.get(key);
            totalTimeMap.put(key, parkTime);
        }
        
        HashMap<Integer, Integer> feeMap = new HashMap<>();
        // 요금 계산해서 담기
        for (String key : totalTimeMap.keySet()) {
            int parkTime = totalTimeMap.get(key);
            double additionalFee = Math.ceil((parkTime - fees[0]) / (double) fees[2]) * fees[3];
            if (additionalFee < 0) 
                additionalFee = 0;
            int fee = fees[1] + (int) additionalFee;
            
            feeMap.put(Integer.parseInt(key), fee);
        }
        
        // 번호 작은 순으로 answer에 담기
        
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (Integer key : feeMap.keySet()) 
            pq.add(key);
        
        int[] answer = new int[pq.size()];
        
        int idx = 0;
        while (!pq.isEmpty()) {
            int key = pq.poll();
            answer[idx++] = feeMap.get(key);
        }
        
        return answer;
    }
    
    private int getParkTime(String inTime, String outTime) {        
        String[] in = inTime.split(":");
        String[] out = outTime.split(":");
        
        int inH = Integer.parseInt(in[0]);
        int inM = Integer.parseInt(in[1]);
        
        int outH = Integer.parseInt(out[0]);
        int outM = Integer.parseInt(out[1]);
        
        if (outH > inH && inM > outM) {
            outH--;
            outM += 60;
        }
        
        int time = ((outH - inH) * 60) + (outM - inM);
        
        return time;
    }
}