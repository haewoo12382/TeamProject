package algorithm;
import common.utils.ReadText;
import model.Process;

import java.util.ArrayList;
import java.util.List;

public class Priority {


    // pid 숫자 부분만 뽑는 함수 "p23" -> 23, 우선순위 같은 경우에 pid 숫자 작은 순으로 실행해야 해서 필요
    public static int getid(Process p) {
        return Integer.parseInt(p.id.substring(1)); // 'p' 제외한 부분을 정수로 변환
    }

    // readyQueue 에서 최고 우선순위 프로세스 찾기
    public static Process bestProcess(List<Process> readyQueue) {
        Process best = readyQueue.get(0);   // 첫번째 프로세스를 최고 우선순위라고 가정, 뒤의 프로세스와 비교

        for (int i = 1; i < readyQueue.size(); i++) { // 반복하며 비교
            Process cur = readyQueue.get(i); // 비교할 프로세스를 cur 변수에 담음

            // 1) 우선순위가 더 작으면 더 높은 우선순위
            if (cur.priority < best.priority) {
                best = cur;
            }
            // 2) 우선순위가 같은 경우, id 숫자가 더 작으면 더 높은 우선순위
            else if (cur.priority == best.priority) {
                if (getid(cur) < getid(best)) {
                    best = cur;
                }
            }
        }
        return best; // 최고 우선순위 프로세스 반환
    }

    public static int runOneProcess( // 프로세스 1개만 실행하는 함수
                                     List<Process> readyQueue, // 지금 당장 실행 가능한 프로세스 목록
                                     List<Process> waitingQueue, // 대기열
                                     List<Process> finished, // 실행 완료된 프로세스 목록
                                     int currentTime // 지금까지 누적된 CPU 시간
    ) {
        if (readyQueue.isEmpty()) { // 실행할 프로세스가 없으면 시간 변화 없이 반환, readyQueue 비어있으면 실행할게 없음
            return currentTime;
        }

        // 1) 실행할(최고 우선순위) 프로세스 선택1
        Process best = bestProcess(readyQueue);

        // 2) 대기시간 = 지금까지 흐른 시간 (도착시간이 모두 0이라고 가정)
        best.waitingTime = currentTime;

        // 3) 현재 시간 증가 (CPU가 이 프로세스를 실행)
        currentTime += best.processTime;

        // 4) 실행 완료된 프로세스를 finished 로 이동
        finished.add(best);

        // 5) 실행이 끝났으므로 readyQueue에서 제거
        readyQueue.remove(best);

        // 6) (100개 전체 구현 시) waitingQueue에서 하나 가져와서 readyQueue 의 빈 자리에 넣기
        // if (!waitingQueue.isEmpty()) {
        //     Process next = waitingQueue.remove(0);
        //     readyQueue.add(next);
        // }

        return currentTime; // 최종적으로 currentTime 반환
    }

    public static void main(String[] args) {

        // A파일의 p1 ~ p10 데이터 - 임시
        List<Process> all = new ArrayList<>();

        // 실행 큐, 대기 큐, 완료 큐 준비
        List<Process> readyQueue = ReadText.getList("");    // 실행 큐 (10개까지)
        List<Process> waitingQueue = new ArrayList<>();  // 대기 큐 (지금은 비워둠)
        List<Process> finished = new ArrayList<>();      // 완료된 프로세스

        //System.out.println(readyQueue.get(0).getId());

        /*
        for (int i = 0; i < all.size(); i++) {
            readyQueue.add(all.get(i));
        }*/

        int currentTime = 0; // currentTime 초기화

        // 10개 중에서 일단 1개만 먼저 실행
        //currentTime = runOneProcess(readyQueue, waitingQueue, finished, currentTime);

        // 전부 실행하고 싶으면
        while (!readyQueue.isEmpty()) {
            currentTime = runOneProcess(readyQueue, waitingQueue, finished, currentTime);

        }

        // 코드 확인용 출력
        System.out.println("id\tPt\tPriority\tWait");
        for (Process p : finished) {
            System.out.println(p.id + "\t" + p.processTime + "\t" + p.priority + "\t\t\t" + p.waitingTime);
            System.out.println(currentTime);
        }
    }
}
