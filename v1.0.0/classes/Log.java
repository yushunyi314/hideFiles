package classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Log {
    Date now;
    public File getNow(){
        now = new Date();
        String path = "file\\log\\"+(now.getYear()+1900)+"-"+now.getMonth()+".log";
        return new File(path);
    }
    public void WriteLog(String j,String s) {
        try {
            File f = getNow();
            f.createNewFile();
            FileWriter fw = new FileWriter(f,true);
            fw.write((now.getYear()+1900)+"/"+now.getMonth()+"/"+now.getDay()+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds()+" ["+j+"] "+s+"\n");
            fw.close();
        } catch (Exception e) {
            System.out.println("在输出日志时出现问题，请联系开发者");
        }
    }
    public void WriteLog(String s) {
        WriteLog("INFO",s);
    }
}
