package model;

public class Process {

    public String id;
    public int waitingTime;
    public int processTime;
    public int priority;

    /*public Process(String id,int pt, int priority) {
        this.id = id;           // 프로세스 이름 저장
        this.pt = pt;             // 프로세스 처리 시간 저장
        this.priority = priority; // 우선순위 저장
    }*/



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getProcessTime() {
        return processTime;
    }

    public void setProcessTime(int processTime) {
        this.processTime = processTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
