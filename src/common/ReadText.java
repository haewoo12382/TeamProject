package common;

import model.Process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ReadText {
    public static String selectedFile = "";   // Read.txt 파일명

    public static List<Process> getProcessList(String fileName) {
        if (selectedFile != null && !selectedFile.equals("")) {
            fileName = "resources/text/" + selectedFile;
        } else {
            // 선택한 파일이 없으면 A.txt 기본 사용
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
