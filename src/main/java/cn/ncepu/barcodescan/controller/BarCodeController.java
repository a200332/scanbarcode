package cn.ncepu.barcodescan.controller;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.ncepu.barcodescan.dao.PersonDao;
import cn.ncepu.barcodescan.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@Lazy
@CrossOrigin
@DependsOn("personDao")
public class BarCodeController {
    private static final Logger log = LoggerFactory.getLogger(BarCodeController.class);
    @Autowired
    private PersonDao personDao;
    public float warn = 37.8f;
    public static int CURRENT_TOTAL = 0;
    public static int UNIT = 10;
    public static int table_num = 1;

    @PostConstruct
    public void postS() {
        CURRENT_TOTAL = personDao.selectCu();
        table_num = personDao.selectTa();
    }

    @GetMapping({"/", "index"})
    public String index(ModelMap map) {
        map.addAttribute("path", System.getProperty("user.home") + "\\ScanColl\\当天扫描日期" + "_全员统计表_自动导出全部.xls");
        return "index";
    }

    @PostMapping("/person")
    @ResponseBody
    public String save(@RequestBody Person person) {
        person.setGrou("" + ((CURRENT_TOTAL / UNIT) + 1));
        if (StringUtils.isEmpty(person.getId()) || StringUtils.isEmpty(person.getDepart()) || StringUtils.isEmpty(person.getTemperature()) || StringUtils.isEmpty(person.getName()) || StringUtils.isEmpty(person.getPhone())) {
            return "信息有误或不完整,未录入系统,该人员为" + person + "  \n已扫描" + CURRENT_TOTAL + " 人";
        }
        try {
            personDao.save(person);
        } catch (DuplicateKeyException e) {
            return "该人员已被录入，禁止重复扫描！" + person;
        }
        person.setTemperature(person.getTemperature().replaceAll("[\n\r]", ""));
        log.info(person + "保存至缓存");
        ArrayList<Person> people = new ArrayList<>();
        people.add(person);
        ExcelWriter writer = ExcelUtil.getWriter(System.getProperty("user.home") + "\\ScanColl\\" + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + "_全员统计表_自动导出全部(" + table_num + ").xls");
        writer.setCurrentRowToEnd();
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("id", "身份证");
        writer.addHeaderAlias("depart", "部门");
        writer.addHeaderAlias("phone", "联系方式");
        writer.addHeaderAlias("temperature", "温度");
        writer.addHeaderAlias("grou", "分组");
        writer.write(people);
        writer.autoSizeColumnAll();
        writer.close();
        log.info(person + "保存至Excel文件");
        table_num = (++CURRENT_TOTAL) % 500 == 0 ? (++table_num) : table_num;
        personDao.updateF(String.valueOf(CURRENT_TOTAL), String.valueOf(table_num));
        if (Float.parseFloat(person.getTemperature()) > warn) {
            return "该人员温度异常!!!!!!" + person + "  \n已扫描" + CURRENT_TOTAL + " 人";
        }
        return "扫描成功!  " + person + "  \n已扫描" + CURRENT_TOTAL + " 人";
    }

    //@GetMapping("/setarg")
    public String setWarn(Float w, Integer g) {
        try {
            if (w != null) {
                this.warn = w;
            }
            if (g != null) {
                UNIT = g;
            }
        } catch (Exception e) {
            return "设置失败";
        }
        return "设置成功";
    }

    @GetMapping("/export")
    @ResponseBody
    public String exportAll() {
        List<Person> people = personDao.selectAll();
        for (int i = 0; i < people.size(); i++) {
            people.get(i).setGrou("" + (i / UNIT + 1));
        }
        String c = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH时mm分ss秒"));
        ExcelWriter writer = ExcelUtil.getWriter(System.getProperty("user.home") + "\\ScanColl\\手动导出\\截止" + c + "_全员统计表_手动导出全部(不会自动更新).xls");
        writer.setCurrentRowToEnd();
        writer.addHeaderAlias("name", "姓名");
        writer.addHeaderAlias("id", "身份证");
        writer.addHeaderAlias("depart", "部门");
        writer.addHeaderAlias("phone", "联系方式");
        writer.addHeaderAlias("temperature", "温度");
        writer.addHeaderAlias("grou", "分组");
        writer.write(people);
        writer.autoSizeColumnAll();
        writer.close();
        return "导出成功,共导出" + personDao.countAll() + "  条记录。保存至" + System.getProperty("user.home") + "\\ScanColl\\手动导出\\截止" + c + "_全员统计表_手动导出全部(不会自动更新).xls" + "<br>此表记录自本日开始，截止至" + c + "。<br>手动导出的表不会自动维护，建议当天工作完毕后导出。";
    }
}

