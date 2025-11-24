package algorithm;

import common.utils.CommonUtils;
import common.utils.ReadText;
import model.Process;
import model.Result;

import java.util.ArrayList;
import java.util.List;

public class Fifo {
    public static Result run() {
        List<Process> queue1 = ReadText.getProcessList("");

        int totalSize = queue1.size();

        List<Process> queue2 = new ArrayList<>(10);

        System.out.println("레스고");

        System.out.println("초기 대기큐 -> 실행큐 진입");
        while (!queue1.isEmpty() && queue2.size() < 10) {
            Process process = queue1.remove(0);
            queue2.add(process);
        }

        System.out.println("스케줄러 스타트");

        int totalProcessTime = 0;
        int totalWaitTime = 0;
        int index = 0;

        Process longProcess = new Process();
        Process shortProcess = new Process();

        while (!queue2.isEmpty()) {

            /*
            //<!--보여주기용
            for (int j=0; j<queue2.size(); j++) {
                if (j != 0){
                    System.out.print(" - ");
                }
                System.out.print(queue2.get(j).getId());
            }
            System.out.println();
            //-->보여주기용
            */

            Process process = queue2.remove(index%10);

            //System.out.println(process.getId()+" - 실행");

            totalProcessTime += process.getProcessTime();
            totalWaitTime += process.getProcessTime() * queue2.size();
            longProcess = CommonUtils.getLongProcess(process);
            shortProcess = CommonUtils.getShortProcess(process);

            if(!queue1.isEmpty()) {
                Process newProcess = queue1.remove(0);
                queue2.add(index%10, newProcess);
                index++;
            }
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
