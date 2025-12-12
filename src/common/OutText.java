package common;

import algorithm.*;
import model.Result;


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
    public static String printResult(Result result) {

        if (result == null) {
            System.out.println("ERROR: Result is null");
            return null;
        }

        String content = "[알고리즘] " + result.name + "\n"
                + "총 실행시간 : " + result.totalProcessTime + "\n"
                + "총 대기시간 : " + result.totalWaitTime + "\n"
                + "평균 실행시간 : " + result.averageProcessTime + "\n"
                + "평균 대기시간 : " + result.averageWaitTime + "\n"
                + "최장 프로세스 : " + result.longProcess.getId() + " (" + result.longProcess.processTime + ")\n"
                + "최단 프로세스 : " + result.shortProcess.getId() + " (" + result.shortProcess.processTime + ")\n"
                + "총 프로세스 개수 : " + result.totalSize + "\n";

        return content;
    }
}