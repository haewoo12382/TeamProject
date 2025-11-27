package algorithm;

import common.CommonUtils;
import common.ReadText;
import model.Process;
import model.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * FIFO (First-In First-Out) 스케줄링 알고리즘 구현 클래스
 */
public class Fifo {

    //queue2 사이즈 설정
    static int initSize = CommonUtils.InitSize;

    public static Result run() {
        // 1. 데이터 로드
        // 파일 업로드 및 선택 기능이 완성되면 실제 경로를 파라미터로 받아야 함 (현재는 빈 문자열로 테스트)
        List<Process> queue1 = ReadText.getProcessList("");

        // 전체 프로세스 총 개수 저장
        int totalSize = queue1.size();

        // 2. 실행 큐 초기화
        //ArrayList 생성 시 크기를 지정해도 데이터를 넣으면 계속 늘어남
        List<Process> queue2 = new ArrayList<>(initSize);

        //3. 초기 로드
        //대기큐(queue1)에 있는 데이터를 실행큐(queue2)가 꽉 찰 때까지(10개) 옮겨 담음
        while (!queue1.isEmpty() && queue2.size() < initSize) {
            Process process = queue1.remove(0);
            queue2.add(process);
        }


        int totalProcessTime = 0; //전체 실행 시간 합계
        int totalWaitTime = 0;    //전체 대기 시간 합계
        int index = 0;            //원형 큐처럼 순회하기 위한 인덱스 변수

        // 최대/최소 프로세스 저장용 (초기화)
        Process longProcess = new Process();
        Process shortProcess = new Process();

        //처리된 프로세스 개수 카운트
        int completedCount = 0;


        //4. 메인 스케줄링 루프
        //처리된 개수가 전체 개수와 같아질 때까지 반복
        while (completedCount < totalSize) {

            // 4-1 현재 실행할 프로세스 가져오기
            // index % InitSize 를 사용하여 0~9 인덱스를 계속 뱅글뱅글 돎
            Process process =  queue2.get(index%initSize);

            // 가져온 자리가 null이면 (이미 실행 완료되고 채워질 데이터가 없어서 비어있는 상태)
            // 인덱스만 증가시키고 다음 칸 확인하러 감
            if (process == null) {
                index++;
                continue;
            }

            //System.out.println(process.getId()+" - 실행");

            // 4.2 유효한 대기 프로세스 수 계산
            // 마지막 10개 처리 구간에서는 queue2에 null이 섞여 있음.
            // 대기 시간 계산을 위해 null이 아닌 진짜 프로세스가 몇 개인지 셈
            int activeCount = 0;
            for (Process p : queue2) {
                if (p != null) activeCount++;
            }

            // 4.3 결과값 계산
            totalProcessTime += process.getProcessTime();
            totalWaitTime += process.getProcessTime() * (activeCount-1);
            longProcess = CommonUtils.getLongProcess(process);
            shortProcess = CommonUtils.getShortProcess(process);

            // 4.4 큐 갱신
            if(!queue1.isEmpty()) {
                Process newProcess = queue1.remove(0);
                queue2.set(index%initSize, newProcess);
            }else{
                // 대기큐가 비었으면 현재 자리를 null로 만들어서 "빈 자리" 표시
                queue2.set(index%initSize, null);
            }
            // 다음 칸으로 이동 및 처리 카운트 증가
            index++;
            completedCount++;

        }
        // 5. 결과 객체 생성 및 반환
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
