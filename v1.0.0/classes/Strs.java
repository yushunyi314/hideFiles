package classes;

import java.util.Random;

public class Strs {
    public static Random r = new Random();
    private Strs(){}
    public static String encrypt(String s){
        StringBuilder s1 = new StringBuilder();
        StringBuilder s2 = new StringBuilder();
        for(int i = 0;i < s.length();i++){
            int c1 = s.charAt(i);
            int x = 130;
            while(c1 + x > 127 && c1 <= 127){
                x = r.nextInt(10);
            }
            if(!(c1 <= 127)){
                x = 0;
            }
            s1.append((char)(c1+x));
            s2.append(x);
        }
        s1.append(s2);
        return s1.toString();
    }
    public static String decrypt(String s){
        int len = s.length();
        String s2 = s.substring(len/2);
        StringBuilder ret = new StringBuilder();
        for(int i = 0;i < len/2;i++){
            char c = (char)(s.substring(0, len / 2).charAt(i) - Integer.parseInt("" + s2.charAt(i)));
            ret.append(c);
        }
        return ret.toString();
    }
    public static boolean find(String s,char c){
        for(int i = 0;i < s.length();i++){
            if(s.charAt(i) == c){
                return true;
            }
        }
        return false;
    }
    public static boolean check(String s){
        for(int i = 0;i < s.length();i++){
            char c = s.charAt(i);
            if((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9') || c == '_'){
                continue;
            }
            return false;
        }
        return true;
    }
}
