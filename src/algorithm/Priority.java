package algorithm;

import common.CommonUtils;
import common.ReadText;
import model.Process;
import model.Result;

import java.util.ArrayList;
import java.util.List;

public class Priority {
    //queue2 사이즈 설정
    static int initSize = CommonUtils.InitSize;

    public static void run(StepListener listener, RunState state) {
        //변수 선언부
        int totalProcessTime = 0;                   // 전체 실행시간
        int totalWaitTime = 0;                      // 전체 대기시간
        int totalSize;                              // 전체 큐 크기
        int completedCount = 0;                     // 큐 완료 개수

        Process longProcess = new Process();        // 프로세스 최장 시간
        Process shortProcess = new Process();       // 프로세스 최단 시간

        // 데이터 로드 - 추후 경로 받기 필요
        List<Process> wait_queue = ReadText.getProcessList("");

        totalSize = wait_queue.size();                                      // 전체 큐 개수 저장

        List<Process> run_queue = new ArrayList<>(initSize);                // 실행큐 선언

        while (!wait_queue.isEmpty() && run_queue.size() < initSize) {      // 실행큐 10개 채울 때까지 반복
            Process process = wait_queue.remove(0);
            run_queue.add(process);
        }

        //처리된 개수가 전체 개수와 같아질 때까지 반복
        while (completedCount < totalSize) {

            while (state.paused && !state.aborted) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ignored) {}
            }
            if (state.aborted) {
                return;
            }

            int index = bestProcess(run_queue);                                 // 우선순위 기준으로 인덱스 값 가져오기
            Process process = run_queue.get(index);                         // 우선순위 기준 인덱스위치의 process가져오기

            if (process == null) {
                continue;
            }

            int activeCount = 0;
            for (Process p : run_queue) {
                if (p != null) activeCount++;
            }

            if (listener != null) {
                listener.onStep(index, wait_queue, run_queue, process);
            }

            totalProcessTime += process.getProcessTime();                   // 실행시간
            totalWaitTime += process.getProcessTime() * (activeCount-1);      // 대기시간
            longProcess = CommonUtils.getLongProcess(process);              // 최장시간
            shortProcess = CommonUtils.getShortProcess(process);            // 최단시간

            if (!wait_queue.isEmpty()) {
                Process newProcess = wait_queue.remove(0);            // 대기큐의 제일 앞의 프로세스를 가져옴
                if (index <= run_queue.size()) {
                    run_queue.set(index, newProcess);
                }
            }
            // 대기큐가 비었으면 현재 자리를 null로 만들어서 "빈 자리" 표시
            else{
                run_queue.set(index, null);
            }

            completedCount++;
        }

        // OutText에 보낼 Data Setting
        Result result = new Result();
        /*result.name = "Priority";
        result.totalProcessTime = totalProcessTime;
        result.totalWaitTime = totalWaitTime;
        result.averageProcessTime = CommonUtils.getAverage(totalProcessTime, totalSize);
        result.averageWaitTime = CommonUtils.getAverage(totalWaitTime, totalSize);
        result.longProcess = longProcess;
        result.shortProcess = shortProcess;*/

        result.setName("Priority");
        result.setTotalProcessTime(totalProcessTime);
        result.setTotalWaitTime(totalWaitTime);
        result.setAverageProcessTime(CommonUtils.getAverage(totalProcessTime, totalSize));
        result.setAverageWaitTime(CommonUtils.getAverage(totalWaitTime, totalSize));
        result.setLongProcess(longProcess);
        result.setShortProcess(shortProcess);
        result.setTotalSize(totalSize);

        // GUI에 결과 전달
        state.finished = true;
        if (!state.aborted && listener != null) {
            listener.onFinish(result);
        }
    }

    // Result만 필요할 때 사용하는 편의 함수 (콘솔 프린트용)
    public static Result run() {
        final Result[] holder = new Result[1];
        RunState state = new RunState();

        run(new StepListener() {
            @Override
            public void onStep(int runIndex, List<Process> waitQueue, List<Process> runQueue, Process executing) {}

            @Override
            public void onFinish(Result result) {
                holder[0] = result;
            }
        }, state);

        return holder[0];
    }

    // pid 숫자 부분만 뽑는 함수 "p23" -> 23, 우선순위 같은 경우에 pid 숫자 작은 순으로 실행해야 해서 필요
    public static int getid(Process p) {
        //return Integer.parseInt(p.id.substring(1)); // 'p' 제외한 부분을 정수로 변환
        return Integer.parseInt(p.getId().substring(1));
    }

    // run_queue 에서 최고 우선순위 프로세스 찾기 (찾아서 해당 Index값 반환)
    public static int bestProcess(List<Process> run_queue) {
        int bestindex = 0;
        Process best = null;
        
        // 초기 비교 값 설정
        for (int i = 0; i < run_queue.size(); i++) {
            best = run_queue.get(i);
            bestindex = i;
            
            if(best != null) //best 값이 null일 경우 for문 반복 아니면 break
                break;
        }
        
        for (int i = bestindex + 1; i < run_queue.size(); i++) {    // 반복하며 비교
            Process cur = run_queue.get(i);             // 비교할 프로세스를 cur 변수에 담음

            if (cur == null)                            // null값이면 무시하고 다음 공간 비교
                continue;
            else if(best == null)                       // best가 null일경우 break (그럴 일 없지만 예외처리)
                break;

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
