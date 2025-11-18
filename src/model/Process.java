package model;

public class Process {

    public String id;
    public int wt;
    public int pt;
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

    public int getWt() {
        return wt;
    }

    public void setWt(int wt) {
        this.wt = wt;
    }

    public int getPt() {
        return pt;
    }

    public void setPt(int pt) {
        this.pt = pt;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
