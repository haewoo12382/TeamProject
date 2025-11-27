package algorithm;

import common.CommonUtils;
import common.ReadText;
import model.Process;
import model.Result;

import java.util.ArrayList;
import java.util.List;

public class Fifo {
    //queue2 사이즈 설정
    private static final int InitSize = 10;

    public static Result run() {
        //파일 업로드 및 파일 선택한 경로를 아직 가져오지 않아 임시경로로 파일 가져오기
        List<Process> queue1 = ReadText.getProcessList("");

        //총 프로세스 사이즈 변수 설정
        int totalSize = queue1.size();

        //queue2 사이즈 설정 - 늘어날수있어서 밑에 코드로 제한적이게 설정
        List<Process> queue2 = new ArrayList<>(InitSize);

        //대기큐 -> 실행큐 초기 진입
        while (!queue1.isEmpty() && queue2.size() < InitSize) {
            Process process = queue1.remove(0);
            queue2.add(process);
        }

        int totalProcessTime = 0; //총 프로세스 시간
        int totalWaitTime = 0;    //총 프로세스가 기다리는 시간
        int index = 0;            //

        Process longProcess = new Process();
        Process shortProcess = new Process();

        // ★ 추가: 종료 조건을 위해 처리된 개수를 셉니다.
        int completedCount = 0;

        while (completedCount < totalSize) {
            System.out.println(queue2);

            Process process =  queue2.get(index%InitSize);
            if (process == null) {
                index++;
                continue;
            }

            //System.out.println(process.getId()+" - 실행");

            int activeCount = 0;
            for (Process p : queue2) {
                if (p != null) activeCount++;
            }
            totalProcessTime += process.getProcessTime();
            totalWaitTime += process.getProcessTime() * (activeCount-1);
            longProcess = CommonUtils.getLongProcess(process);
            shortProcess = CommonUtils.getShortProcess(process);

            //queue2.remove(index%InitSize);
            if(!queue1.isEmpty()) {
                Process newProcess = queue1.remove(0);
                queue2.set(index%InitSize, newProcess);

            }else{
                queue2.set(index%InitSize, null);
            }
            index++;
            completedCount++; // 처리 완료 카운트 증가

        }
        Result result = new Result();
        result.name = "FIFO";
        result.totalProcessTime = totalProcessTime;
        result.totalWaitTime = totalWaitTime;
        result.averageProcessTime = CommonUtils.getAverage(totalProcessTime, totalSize);
        result.averageWaitTime = CommonUtils.getAverage(totalWaitTime, totalSize);
        result.longProcess = longProcess;
        result.shortProcess = shortProcess;

        return result;
    }
}
