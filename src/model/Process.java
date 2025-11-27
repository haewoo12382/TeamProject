package model;

public class Process {

    public String id;  //프로세스 ID
    public int processTime; // 프로세스 실행 시간
    public int priority; // 프로세스 우선순위


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
