package common.utils;

import model.Process;
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
            process.setWt(Integer.parseInt(processList[i][1]));
            process.setPriority(Integer.parseInt(processList[i][2]));
            list.add(process);
        }
        //File file = new File(fileName);
        return list;
    }
}
