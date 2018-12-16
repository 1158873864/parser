import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Scanner {
    private Map map=new HashMap();
    public void scanner(String fileName){
        setMap();
        ArrayList<String> source=new ArrayList<String>();
        ArrayList<String> result=new ArrayList<String>();
        Input input=new Input();
        source=input.readFileByLines(fileName);
        for(int lineIndex=0;lineIndex<source.size();lineIndex++){
            Stack stack=new Stack();
            stack.push('$');
            stack.push('E');
            int charIndex=0;
            while (charIndex<source.get(lineIndex).length()){
                char thisChar=source.get(lineIndex).charAt(charIndex);
                char top= stack.peek().toString().charAt(0);
                String com = String.valueOf(top) + "," + String.valueOf(thisChar);
                if(thisChar==top){
                    if(top=='$'){
                        result.add("字符串符合语法！");
                        break;
                    }
                    else {
                        result.add("匹配 " + thisChar);
                        stack.pop();
                        charIndex++;
                    }
                }
                else {
                    if(top=='#'){
                        stack.pop();
                    }
                    else {
                        String derivation = "";
                        if (map.containsKey(com)) {
                            derivation = map.get(com).toString();
                        } else {
                            result.add("字符串在" + thisChar + "处不符合语法！");
                            break;
                        }
                        String[] split = derivation.split("->");
                        String toAddChars = split[1];
                        stack.pop();
                        for (int toIndex = toAddChars.length() - 1; toIndex >= 0; toIndex--) {
                            stack.push(toAddChars.charAt(toIndex));
                        }
                        result.add("推导 " + derivation);
                    }
                }
            }
            result.add("\n");
        }
        Output output=new Output();
        output.output(fileName,result);
    }

    private void setMap(){
        map.put("E,(","E->TC");
        map.put("E,i","E->TC");
        map.put("C,+","C->AC");
        map.put("C,-","C->AC");
        map.put("C,)","C->#");
        map.put("C,$","C->#");
        map.put("A,+","A->+T");
        map.put("A,-","A->-T");
        map.put("T,(","T->FD");
        map.put("T,i","T->FD");
        map.put("D,+","D->#");
        map.put("D,-","D->#");
        map.put("D,*","D->BD");
        map.put("D,/","D->BD");
        map.put("D,)","D->#");
        map.put("D,$","D->#");
        map.put("B,*","B->*F");
        map.put("B,/","B->/F");
        map.put("F,(","F->(E)");
        map.put("F,i","F->i");
    }

}
