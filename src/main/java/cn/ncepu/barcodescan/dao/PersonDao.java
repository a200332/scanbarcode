package cn.ncepu.barcodescan.dao;

import cn.ncepu.barcodescan.entity.Person;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository("personDao")
public interface PersonDao {

    /**
     * @mbg.generated
     */
    int delete();

    /**
     * @mbg.generated
     */
    int save(Person record);


    /**
     * @mbg.generated
     */
    List<Person> selectAll();

    Integer countAll();

    Integer insertF();
    Integer selectF();

    Integer selectCu();
    Integer selectTa();
    Integer updateF(String c,String t);
}
