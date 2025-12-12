package common;

import algorithm.*;
import model.Process;
import model.Result;

import java.util.List;


public class OutText {

    public static void Print_console() {

        Result fifoResult = Fifo.run();
        Result priorityResult = Priority.run();
        /*
        // FIFO 실행
        Fifo.run(new StepListener() {
            @Override
            public void onStep(int index, List<Process> waitQ, List<Process> runQ, Process running) {
                // 콘솔 테스트에서는 step 출력하지 않음
            }

            @Override
            public void onFinish(Result result) {
                System.out.println("=== FIFO 결과 ===");
                printResult(result);
            }
        });

        // PRIORITY 실행
        Priority.run(new StepListener() {
            @Override
            public void onStep(int index, List<Process> waitQ, List<Process> runQ, Process running) {
            }

            @Override
            public void onFinish(Result result) {
                System.out.println("=== PRIORITY 결과 ===");
                printResult(result);
            }
        });*/
    }
}