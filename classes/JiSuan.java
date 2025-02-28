package classes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class JiSuan {
    private static Map<Character, Integer> mp = new HashMap<>();

    static {
        mp.put('+', 1);
        mp.put('-', 1);
        mp.put('*', 2);
        mp.put('/', 2);
        mp.put('^', 3);
    }

    private JiSuan() {
    }

    public static boolean youXianJi(char a, char b) {
        return mp.get(a) >= mp.get(b);
    }

    public static String zhongToHou(String s) {
        StringBuilder ret = new StringBuilder();
        Stack<Character> st = new Stack<>();
        for (int i = 0; i < s.length();i++) {
            char c = s.charAt(i);
            if ((c >= '0' && c <= '9') || c == '.') {
                ret.append(c);
                continue;
            } else{
                ret.append(' ');
            }
            if (c == '(' || st.empty() || st.peek() == '(') {
                st.push(c);
            } else if(c == ')'){
                while (st.peek() != '(') {
                    ret.append(' ');
                    ret.append(st.pop());
                }
                st.pop();
            } else{
                if (youXianJi(c, st.peek())) {
                    st.push(c);
                } else {
                    ret.append(' ');
                    ret.append(st.pop());
                    st.push(c);
                }
            }
        }
        while (!st.empty()) {
            ret.append(' ');
            ret.append(st.pop());
        }
        return ret.toString();
    }

    public static BigDecimal Suan(String s){
        List<BigDecimal>l = new ArrayList<>();
        StringBuilder s1 = new StringBuilder();
        for(int i = 0;i < s.length();i++){
            char c = s.charAt(i);
            int len = l.size();
            if((c >= '0' && c <= '9') || c == '.'){
                s1.append(c);
            } else{
                try {
                    l.add(new BigDecimal(s1.toString()));
                    s1 = new StringBuilder();
                } catch (NumberFormatException ignored) {

                }
            }
            if(c == '+'){
                l.set(len-2,l.get(len-2).add(l.get(len-1)));
                l.remove(len-1);
            }
            if (c == '-') {
                l.set(len-2,l.get(len-2).subtract(l.get(len-1)));
                l.remove(len-1);
            }
            if(c == '*'){
                l.set(len-2,l.get(len-2).multiply(l.get(len-1)));
                l.remove(len-1);
            }
            if(c == '/'){
                try {
                    l.set(len-2,l.get(len-2).divide(l.get(len-1),5,RoundingMode.HALF_UP));
                    l.remove(len-1);
                } catch (ArithmeticException ignored) {
                    return new BigDecimal(""+(Integer.MAX_VALUE-1));
                }
            }
            if(c == '^'){
                l.set(len-2,l.get(len-2).pow((int) Double.parseDouble(l.get(len-1).toString())));
                l.remove(len-1);
            }
        }
        return l.getFirst();
    }

    public static boolean Check(String s){
        int count = 0;
        StringBuilder s1 = new StringBuilder();
        for(int i = 0;i < s.length();i++){
            char c = s.charAt(i);
            if((c >= '0' && c <= '9') || c == '.'){
                s1.append(c);
                count++;
                continue;
            } else{
                int cnt = 0;
                for(int j = 0;j < s1.length();j++){
                    if(s1.charAt(j) == '.'){
                        cnt++;
                    }
                }
                if(cnt > 1){
                    return false;
                }
            }
            String s2 = " +-*/^()";
            if(!Strs.find(s2,c)){
                return false;
            }
        }
        int cnt = 0;
        for(int i = 0;i < s.length();i++){
            char c = s.charAt(i);
            if(c == '('){
                cnt++;
            }
            if(c == ')'){
                cnt--;
            }
            if(cnt < 0){
                return false;
            }
        }
        return count>0&&cnt==0;
    }
}
