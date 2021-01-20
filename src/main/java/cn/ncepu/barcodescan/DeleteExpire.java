package cn.ncepu.barcodescan;

import cn.ncepu.barcodescan.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DeleteExpire implements ApplicationRunner {
    @Autowired
    private PersonDao personDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        personDao.delete();
        if (personDao.selectF()==null||personDao.selectF()==0) personDao.insertF();
    }
}
