package common.utils;

import model.Process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ReadText {

    public static String [][] processList =   {{"p1", "10", "9"},
                                        {"p2", "4", "7"},
                                        {"p3", "10", "1"},
                                        {"p4", "15", "5"},
                                        {"p5", "6", "10"},
                                        {"p6", "20", "4"},
                                        {"p7", "7", "3"},
                                        {"p8", "6", "3"},
                                        {"p9", "3", "8"},
                                        {"p10", "8", "1"}};

    public static List<Process> getList(String fileName) {
        List<Process> list = new ArrayList<Process>();

        for (int i = 0; i < processList.length; i++) {
            Process process = new Process();
            process.setId(processList[i][0]);
            process.setProcessTime(Integer.parseInt(processList[i][1]));
            process.setPriority(Integer.parseInt(processList[i][2]));
            list.add(process);
        }
        //File file = new File(fileName);
        return list;
    }

    public static List<Process> getProcessList(String fileName) {
        if(fileName == null || fileName.equals("")) {
            fileName = "resources/text/A.txt";
        }
        List<Process> list = new ArrayList<>();
        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("Error: 파일을 찾을 수 없습니다 -> " + file.getAbsolutePath());
            return list; // 빈 리스트 반환
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 빈 줄 건너뛰기
                if (line.trim().isEmpty()) continue;

                // 공백을 기준으로 분리
                String[] parts = line.trim().split("\\s+");

                if (parts.length >= 3) {
                    String id = parts[0];                 // 프로세스 ID (예: p1)
                    int time = Integer.parseInt(parts[1]); // 실행시간
                    int priority = Integer.parseInt(parts[2]);   // 우선수위

                    // Process 객체 생성 후 리스트에 추가
                    Process process = new Process();
                    process.setId(id);
                    process.setProcessTime(time);
                    process.setPriority(priority);
                    list.add(process);
                }
            }
        } catch (Exception e) {
          System.out.print(e);
        }

        return list;
    }

}
