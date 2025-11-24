package common.utils;

import algorithm.Fifo;
import algorithm.Priority;
import model.Result;


public class OutText {

    public static void Print_console() {
        Result fifoResult = Fifo.run();
        printResult(fifoResult);
        System.out.println();

        Result priorityResult = Priority.run();
        printResult(priorityResult);
        System.out.println();
    }


    public static void Print_txtfile() {
        //Result fifoResult = Fifo.run();
        //Result prioResult = Priority.run();
    }

    private static void printResult(Result r) {
        System.out.println("=======" +r.name +"결과=======");
        System.out.println("총 프로세스 시간: " + r.totalProcessTime);
        System.out.println("총 대기 시간: " + r.totalWaitTime);

        System.out.println("프로세스별 평균 프로세스 시간: " + r.averageProcessTime);
        System.out.println("프로세스별 평균 대기 시간: " + r.averageWaitTime);

        System.out.println("가장 길었던 프로세스 : " + r.longProcess.getId()
                + " - 프로세스 시간 : " + r.longProcess.getProcessTime());
        System.out.println("가장 짧았던 프로세스 : " + r.shortProcess.getId()
                + " - 프로세스 시간 : " + r.shortProcess.getProcessTime());
    }
}
