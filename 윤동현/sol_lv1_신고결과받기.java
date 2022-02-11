import java.util.*;

class sol_lv1_신고결과받기 {

  public ArrayList<Integer> solution(String[] id_list, String[] report, int k) {
    ArrayList<Integer> answer = new ArrayList<>();

    // key : 신고당한 사람, value : 신고한사람
    HashMap<String, HashSet<String>> report_id = new HashMap<>();
    HashMap<String, Integer> mail = new HashMap<>();

    // 초기화 과정
    for (String id : id_list) {
      report_id.put(id, new HashSet<>());
      mail.put(id, 0);
    }

    // report 배열 처리하기
    // from -> to 신고 처리
    for (String person : report) {
      String[] users = person.split(" ");
      String from = users[0]; // 신고하는 사람
      String to = users[1]; // 신고당한 사람 -> report_id 의 key값이다.

      // 신고당한 사람의 hashset 꺼낸다.
      HashSet<String> temp = report_id.get(to);
      // 누가 신고했는지 알기 위해서 hashset from 저장
      temp.add(from);
      // 신고당한사람[to]이 누가신고했는지[from](HashSet있으니깐) 알수있다.
      report_id.put(to, temp);
    }

    for (HashSet<String> temp : report_id.values()) {
      // k사이즈 보다 크면 메일을 보내줘야하므로
      if (temp.size() >= k) {
        for (String data : temp) {
          // 실제 메일 받을 카운트를 저장
          mail.put(data, mail.get(data) + 1);
        }
      }
    }

    for (String id : id_list) {
      answer.add(mail.get(id));
    }

    return answer;
  }
}