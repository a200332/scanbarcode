package cn.ncepu.barcodescan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
@MapperScan("cn.ncepu.barcodescan.dao")
public class BarcodescanApplication {
    public static void main(String[] args) throws IOException {
        System.out.println("等待程序启动......");
        System.out.println("程序运行期间请勿关闭此窗口，长时间无反应可按几下回车...");
        new File(System.getProperty("user.home")+"/.h2/table49823.lock.db").delete();
        new File(System.getProperty("user.home")+"/.h2/table49823.mv.db").delete();
        new File(System.getProperty("user.home")+"/.h2/table49823.trace.db").delete();
        SpringApplication.run(BarcodescanApplication.class, args);
        System.out.println("启动完成...\n");
        System.out.println("使用说明:");
        System.out.println("\t1、本程序将每天的扫描记录自动整理到  " + System.getProperty("user.home") + "\\ScanColl\\当天扫描日期" + "_全员统计表_自动导出全部.xls");
        System.out.println("\t2、当程序意外崩溃，已保存的扫描记录不会丢失，您可以打开程序继续扫描;");
        System.out.println("\t3、同一个二维码只能扫描一次，重复扫描会提示扫描失败;");
        System.out.println("\t4、本程序运行期间，不能打开Excel表;");
        System.out.println("\t5、每张表保存500条记录，超过500条会自动分表;");
        System.out.println("\t6、系统时间与真实时间要保持一致。");
        System.out.println("打开网页  http://localhost:35007/  操作。");
        try {
            Runtime.getRuntime().exec(
                    "cmd   /c   start   http://localhost:35007/");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("请打开网页  http://localhost:35007/");
        }
    }

}
