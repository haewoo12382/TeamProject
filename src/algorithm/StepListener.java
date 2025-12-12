package algorithm;

import model.Process;
import model.Result;
import java.util.List;

// GUI와 스케줄링 알고리즘을 연동하기 위한 알고리즘
public interface StepListener {

    /*
       알고리즘이 한 스텝 실행할 때마다 호출됨

       @param runIndex  실행큐(queue2/runQueue) 중 이번에 실행하는 인덱스
       @param waitQueue 현재 대기큐 상태 (queue1)
       @param runQueue  현재 실행큐 상태 (queue2)
       @param executing 현재 실행 중인 프로세스
     */
    void onStep(int runIndex, List<Process> waitQueue, List<Process> runQueue, Process executing, int totalSize);

    /*
       알고리즘이 모든 프로세스를 마치면 호출됨

       @param result 최종 계산된 Result 객체
     */
    void onFinish(Result result);
}
