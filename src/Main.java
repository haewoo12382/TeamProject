import common.utils.OutText;
import common.utils.ReadText;
import model.Process;

import java.util.ArrayList;
import java.util.List;

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
public class Main {
    public static void main(String[] args) {
        //TIP 캐럿을 강조 표시된 텍스트에 놓고 <shortcut actionId="ShowIntentionActions"/>을(를) 누르면
        // IntelliJ IDEA이(가) 수정을 제안하는 것을 확인할 수 있습니다.
        System.out.println("Hello and welcome!");

        List<Process> list = ReadText.getList("temp");

        for(Process p : list){
            System.out.println(p.getId());
        }
        System.out.println("민호");
        System.out.println("해우");
        System.out.println("미지ㅎㅇ");
        System.out.println("도연gd");

        OutText.Print_console();   // 여기서 한 번에 FIFO + PRIORITY 결과 출력

    }
}