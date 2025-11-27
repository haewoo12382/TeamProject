package common;

import algorithm.Fifo;
import algorithm.Priority;
import model.Result;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;


public class OutText {

    public static void Print_console() {
        Result fifoResult = Fifo.run();
        Result priorityResult = Priority.run();

        List<Result> totalResult = new ArrayList<>();
        totalResult.add(fifoResult);
        totalResult.add(priorityResult);
        printResult(totalResult);
    }

    private static void printResult(List<Result> results) {
        StringBuilder sb = new StringBuilder();
        for (Result result : results) {
            sb.append("=======" + result.name + " 결과 =======\n");
            sb.append("총 프로세스 시간: " + result.totalProcessTime + "\n");
            sb.append("총 대기 시간: " + result.totalWaitTime + "\n\n");

            sb.append("프로세스별 평균 프로세스 시간: " + result.averageProcessTime + "\n");
            sb.append("프로세스별 평균 대기 시간: " + result.averageWaitTime + "\n\n");

            sb.append("가장 길었던 프로세스 : " + result.longProcess.getId() + " - 프로세스 시간 : " + result.longProcess.getProcessTime() + "\n");
            sb.append("가장 짧았던 프로세스 : " + result.shortProcess.getId()+" - 프로세스 시간 : " + result.shortProcess.getProcessTime() + "\n\n");

        }
        // 콘솔 출력
        System.out.println(sb.toString());

        // ===== B.txt 파일에 쓰기 =====
        try {
            FileWriter resultTxt = null;
            String filePath = "resources/text/B.txt";
            resultTxt = new FileWriter(filePath);
            resultTxt.write(sb.toString());
            resultTxt.close();
            System.out.println("→ 결과가 B.txt에 저장되었습니다.");
        } catch (Exception e) {
            System.out.println("입출력 오류");
        }

    }
}
