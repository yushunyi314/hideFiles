import classes.Files;
import classes.JiSuan;
import classes.Log;
import classes.Strs;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static Log log = new Log();
    private static String path = "file\\";
    public static Scanner sc = new Scanner(System.in, "UTF-8");
    public static void one(){
        List<String> l = Files.get();
        if(l.size() == 1 && l.getFirst().equals("114514")){
            System.out.println("获取文件列表失败");
            return;
        }
        System.out.println("请选择你需要操作的文件（可以一次选择多个，用空格隔开，也可以输入all表示全选）:\n0 取消");
        for(int i = 0;i < l.size()-1;i++){
            System.out.println((i+1)+" "+l.get(i));
        }
        List<Integer>l1 = new ArrayList<>();
        sc.nextLine();
        String s = sc.nextLine();
        StringBuilder s1 = new StringBuilder();
        if(s.equals("all")){
            for(int i = 0;i < s.length();i++){
                l1.add(i);
            }
        } else if(s.equals("0")){
            return;
        } else{
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == ' ') {
                    l1.add(Integer.parseInt(s1.toString())-1);
                    s1 = new StringBuilder();
                    continue;
                }
                s1.append(c);
            }
            l1.add(Integer.parseInt(s1.toString())-1);
        }
        System.out.println("请选择你要操作的内容:\n0 取消\n1 隐藏\n2 恢复\n3 删除");
        Integer n = sc.nextInt();
        if(n == 0){
            return;
        }
        for (Integer i : l1) {
            if (i < 0) {
                continue;
            }
            if (n == 1) {
                Files.YinCang(l.get(i));
            } else if(n == 2) {
                Files.HuiFu(l.get(i));
            } else {
                Files.remove(l.get(i));
            }
        }
        System.out.println("操作成功");
    }
    public static void two(String pa){
        try {
            File p = new File(path+"password.txt");
            p.createNewFile();
            FileWriter fw = new FileWriter(p,false);
            fw.write(Strs.encrypt(pa));
            fw.close();
            System.out.println("操作成功");
        } catch (Exception e) {
            System.out.println("出现问题，请查看日志");
            log.WriteLog("Error","在修改密码是出现问题");
        }
    }
    public static void main(String[] args) {
        try {
            File p = new File(path+"password.txt");
            if(p.createNewFile()){
                System.out.println("请设置密码（仅包含字母、数字和_）：");
                FileWriter fw = new FileWriter(p);
                String pa = sc.next();
                while(!Strs.check(pa)){
                    System.out.println("请输入合法的密码");
                    pa = sc.next();
                }
                fw.write(Strs.encrypt(pa));
                fw.close();
                System.out.println("设置成功");
            }
            FileReader fr = new FileReader(p);
            char c;
            StringBuilder pa = new StringBuilder();
            while((c = (char) fr.read()) != (char) -1) {
                pa.append(c);
            }
            pa = new StringBuilder(Strs.decrypt(pa.toString()));
            while(true) {
                while (true) {
                    log.WriteLog("进入计算器系统");
                    String s = sc.next();
                    if(s.equals(pa.toString())){
                        break;
                    }
                    if (!JiSuan.Check(s)) {
                        System.out.println("请输入正确的表达式");
                        continue;
                    }
                    s = JiSuan.zhongToHou(s);
                    BigDecimal ret = JiSuan.Suan(s);
                    if (ret.toString().equals(new BigDecimal("" + (Integer.MAX_VALUE - 1)).toString())) {
                        System.out.println("除数不能为0");
                    } else {
                        System.out.println(ret);
                    }
                }
                while(true){
                    log.WriteLog("进入文件系统");
                    System.out.println("请选择你要做的操作:\n0 退出\n1 文件管理\n2 修改密码\n3 添加文件");
                    Integer n = sc.nextInt();
                    if(n == 0){
                       break;
                    } else if(n == 1){
                        one();
                    } else if(n == 2){
                        System.out.println("请输入你要修改的密码（仅包含字母、数字和_）");
                        String s = sc.next();
                        if(Strs.check(s)){
                            two(s);
                        }
                        pa = new StringBuilder(s);
                    } else {
                        System.out.println("请输入你要添加的文件的路径");
                        sc.nextLine();
                        String s = sc.nextLine();
                        Files.add(s);
                        System.out.println("操作成功");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("出现问题，请查看日志");
            log.WriteLog("Error","出现问题");
        }
    }
}
