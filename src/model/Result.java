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
}
