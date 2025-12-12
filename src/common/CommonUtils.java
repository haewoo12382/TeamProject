package common;

import model.Process;

public class CommonUtils {
    static Process longProcess = new Process();
    static Process shortProcess = new Process();

    // CPU 실행 큐(Ready Queue)의 최대 크기 (메모리 제한 10개)
    public static final int InitSize = 10;

    //데이터 초기화
    public static void reset() {
        longProcess = new Process();
        shortProcess = new Process();
    }
    
    /**
     * 평균 시간 계산
     * @param time
     * @param size
     * @return
     */
    public static double getAverage(int time, int size) {
        return (double) time / size;
    }

    /**
     * 가장 길었던 프로세스 가져오기
     * @param processParam
     * @return
     */
    public static Process getLongProcess(Process processParam) {
        if(longProcess.getProcessTime() < processParam.getProcessTime()) {
            longProcess = processParam;
        }
        return longProcess;
    }

    /**
     * 가장 짧았던 프로세스 가져오기
     * @param processParam
     * @return
     */
    public static Process getShortProcess(Process processParam) {
        if(shortProcess.getProcessTime() != 0) {
            if(shortProcess.getProcessTime() > processParam.getProcessTime()) {
                shortProcess = processParam;
            }
        }else{
            shortProcess = processParam;
        }
        return shortProcess;
    }


}
