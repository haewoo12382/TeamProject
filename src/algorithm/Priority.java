package algorithm;

import common.utils.CommonUtils;
import common.utils.ReadText;
import model.Process;
import model.Result;

import java.util.ArrayList;
import java.util.List;

public class Priority {
    public static Result run() {
        //변수 선언부
        int totalProcessTime = 0;   // 전체 실행시간
        int totalWaitTime = 0;      // 전체 대기시간
        int index = 0;              // 실행큐에서 실행될 PID의 위치 (우선순위에 따라 숫자 결정)

        Process longProcess = new Process();    // 프로세스 최장 시간
        Process shortProcess = new Process();   // 프로세스 최단 시간


        List<Process> wait_queue = ReadText.getProcessList(""); //파일에서 대기큐 가져오기

        int totalSize = wait_queue.size();  // 대기큐 크기

        List<Process> run_queue = new ArrayList<>(10); // 실행큐 선언

        System.out.println("초기 대기큐 -> 실행큐 진입");
        while (!wait_queue.isEmpty() && run_queue.size() < 10) {    //실행큐 10개 채울 때까지 반복
            Process process = wait_queue.remove(0);
            run_queue.add(process);
        }

        while (!run_queue.isEmpty()) {

            /*
            //<!--보여주기용
            for (int j = 0; j < run_queue.size(); j++) {
                if (j != 0) {
                    System.out.print(" - ");
                }
                System.out.print(run_queue.get(j).getId());
            }
            System.out.println();
            //-->보여주기용
            */

            index = bestProcess(run_queue);             // 우선순위 기준으로 인덱스 값 가져오기
            Process process = run_queue.remove(index);  // 우선순위 기준 인덱스위치의 process가져오기

            //System.out.println(process.getId() + " - 실행" + "priority : " + process.getPriority());

            totalProcessTime += process.getProcessTime();                   // 실행시간
            totalWaitTime += process.getProcessTime() * run_queue.size();   // 대기시간
            longProcess = CommonUtils.getLongProcess(process);              // 최장시간
            shortProcess = CommonUtils.getShortProcess(process);            // 최단시간

            if (!wait_queue.isEmpty()) {
                Process newProcess = wait_queue.remove(0);              // 대기큐의 제일 앞의 프로세스를
                if (index <= run_queue.size()) {
                    run_queue.add(index, newProcess);
                }
            }
        }
        Result result = new Result();
        result.name = "Priority";
        result.totalProcessTime = totalProcessTime;
        result.totalWaitTime = totalWaitTime;
        result.averageProcessTime = CommonUtils.getAverage(totalProcessTime, totalSize);
        result.averageWaitTime = CommonUtils.getAverage(totalWaitTime, totalSize);
        result.longProcess = longProcess;
        result.shortProcess = shortProcess;

        return result;
    }

    // pid 숫자 부분만 뽑는 함수 "p23" -> 23, 우선순위 같은 경우에 pid 숫자 작은 순으로 실행해야 해서 필요
    public static int getid(Process p) {
        return Integer.parseInt(p.id.substring(1)); // 'p' 제외한 부분을 정수로 변환
    }

    // run_queue 에서 최고 우선순위 프로세스 찾기 (찾아서 해당 Index값 반환)
    public static int bestProcess(List<Process> run_queue) {
        int bestindex = 0;
        Process best = run_queue.get(0);   // 첫번째 프로세스를 최고 우선순위라고 가정, 뒤의 프로세스와 비교

        for (int i = 1; i < run_queue.size(); i++) { // 반복하며 비교
            Process cur = run_queue.get(i); // 비교할 프로세스를 cur 변수에 담음

            // 1) 우선순위가 더 작으면 더 높은 우선순위
            if (cur.priority < best.priority) {
                best = cur;
                bestindex = i;
            }
            // 2) 우선순위가 같은 경우, id 숫자가 더 작으면 더 높은 우선순위
            else if (cur.priority == best.priority) {
                if (getid(cur) < getid(best)) {
                    best = cur;
                    bestindex = i;
                }
            }
        }
        return bestindex; // 최고 우선순위 프로세스 반환
    }
}
