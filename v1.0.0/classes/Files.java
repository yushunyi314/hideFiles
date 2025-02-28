package classes;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Files {
    public static Log l = new Log();
    private static String path = "file\\";
    private Files(){}
    public static List<String> get(){
        List<String>ret = new ArrayList<>();
        try {
            String c_path = path+"file_path.log";
            File f = new File(c_path);
            f.createNewFile();
            FileReader fr = new FileReader(f);
            char c;
            StringBuilder s = new StringBuilder();
            while((c = (char) fr.read()) != (char) -1){
                if(c == '\n'){
                    ret.add(Strs.decrypt(s.toString()));
                    s = new StringBuilder();
                    continue;
                }
                s.append(c);
            }
            ret.add(Strs.decrypt(s.toString()));
            fr.close();
        } catch (Exception e) {
            l.WriteLog("Error","在获取路径备份时出现问题");
            System.out.println("出现问题，请查看日志");
            ret.add("114514");
        }
        return ret;
    }
    public static void add(String s){
        try {
            List<String> l = get();
            for(String str:l){
                if(str.equals(s)){
                    return;
                }
            }
            String c_path = path+"file_path.log";
            File f = new File(c_path);
            f.createNewFile();
            FileWriter fw = new FileWriter(f,true);
            fw.write(Strs.encrypt(s)+"\n");
            fw.close();
        } catch (Exception e) {
            l.WriteLog("Error","在添加隐藏文件路径时出现问题");
            System.out.println("出现问题，请查看日志");
        }
    }
    public static void remove(String s){
        try {
            FileWriter fw = new FileWriter(path+"file_path.log");
            HuiFu(s);
            List<String> l = get();
            for(String str:l){
                if(str.equals(s)){
                    continue;
                }
                fw.write(str+'\n');
            }
        } catch (Exception e) {
            l.WriteLog("Error","在删除隐藏目录时出现问题");
            System.out.println("出现问题，请查看日志");
        }
    }
    private static void copyFile(String s1, String s2) {
        try {
            byte[] b = new byte[1024];
            int n;
            File f1 = new File(s1);
            f1.createNewFile();
            FileInputStream fis = new FileInputStream(f1);
            File f2 = new File(s2);
            f2.createNewFile();
            FileOutputStream fos = new FileOutputStream(f2);
            while ((n = fis.read(b)) != -1) {
                fos.write(b, 0, n);
            }
            fis.close();
            fos.close();
        } catch (Exception e) {
            System.out.println("出现问题，请查看日志");
            l.WriteLog("Error","在复制文件时出现问题");
        }
    }
    public static void YinCang(String path_file){
        try {
            File file = new File(path_file);
            add(path_file);
            copyFile(path_file,path+path_file.hashCode()+".tmp");
            file.delete();
        } catch (Exception e) {
            l.WriteLog("Error","在隐藏文件时出现问题");
            System.out.println("出现问题，请查看日志");
        }
    }
    public static void HuiFu(String path_file){
        try {
            copyFile(path+path_file.hashCode()+".tmp",path_file);
        } catch (Exception e) {
            l.WriteLog("Error","在恢复文件时出现问题");
            System.out.println("出现问题，请查看日志");
        }
    }
}
