package algorithm;

public class RunState {
    public volatile boolean paused = false;     // paused  : 일시정지 여부
    public volatile boolean aborted = false;    // aborted : 정지(중단) 여부
    public volatile boolean finished = false;   // finished: 정상 종료 여부
}