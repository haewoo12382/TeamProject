package model;

public class Result {
    public String name;          // 알고리즘 이름 (FIFO, PRIORITY)
    public int totalProcessTime; // 전체 프로세스 시간
    public int totalWaitTime;    // 전체 대기 시간
    public double averageProcessTime;        // 프로세스 개수
    public double averageWaitTime;        // 프로세스 개수
    public Process longProcess;  // 최장 시간 프로세스
    public Process shortProcess; // 최단 시간 프로세스
    public int totalSize;

    public void setName(String name) {
        this.name = name;
    }

    public void setTotalProcessTime(int totalProcessTime) {
        this.totalProcessTime = totalProcessTime;
    }


    public void setTotalWaitTime(int totalWaitTime) {
        this.totalWaitTime = totalWaitTime;
    }


    public void setAverageProcessTime(double averageProcessTime) {
        this.averageProcessTime = averageProcessTime;
    }


    public void setAverageWaitTime(double averageWaitTime) {
        this.averageWaitTime = averageWaitTime;
    }


    public void setLongProcess(Process longProcess) {
        this.longProcess = longProcess;
    }

    public void setShortProcess(Process shortProcess) {
        this.shortProcess = shortProcess;
    }


    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
}
