package com.qw.modules.test.repository;

import com.qw.modules.test.pojo.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ClassName: StudentRepository <br/>
 * Description: <br/>
 * date: 2020/8/12 19:25<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
//jpa  Dao层
@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
}
