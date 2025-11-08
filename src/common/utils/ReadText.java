package common.utils;

import model.Process;
import java.util.ArrayList;
import java.util.List;

public class ReadText {

    static String [][] processList = {{"p1","4","2"},{"p2","4","2"},{"p3","4","2"}};

    public static List<Process> getList(String fileName) {
        List<Process> list = new ArrayList<Process>();

        for (int i = 0; i < processList.length; i++) {
            Process process = new Process();
            process.setId(processList[i][0]);
            process.setWt(Integer.parseInt(processList[i][1]));
            process.setPriority(Integer.parseInt(processList[i][2]));
            list.add(process);
        }
        //File file = new File(fileName);
        return list;
    }
}
