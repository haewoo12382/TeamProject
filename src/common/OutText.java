package common;

import algorithm.*;
import model.Process;
import model.Result;

import java.util.List;


public class OutText {

    public static void Print_console() {
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
        });
    }

    private static void printResult(Result result) {

        if (result == null) {
            System.out.println("ERROR: Result is null");
            return;
        }

        System.out.println("[알고리즘] " + result.name);
        System.out.println("총 실행시간 : " + result.totalProcessTime);
        System.out.println("총 대기시간 : " + result.totalWaitTime);
        System.out.println("평균 실행시간 : " + result.averageProcessTime);
        System.out.println("평균 대기시간 : " + result.averageWaitTime);

        if (result.longProcess != null)
            System.out.println("최장 프로세스 : "
                    + result.longProcess.id + " (" + result.longProcess.processTime + ")");

        if (result.shortProcess != null)
            System.out.println("최단 프로세스 : "
                    + result.shortProcess.id + " (" + result.shortProcess.processTime + ")");

        System.out.println("총 프로세스 개수 : " + result.totalSize);
        System.out.println();
    }
}