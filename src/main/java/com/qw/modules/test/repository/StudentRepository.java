package com.qw.modules.test.repository;

import com.qw.modules.test.pojo.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    //属性查询
    //通过name查询
    List<Student> findByStudentName(String studentName);

    //模糊查询
    List<Student> findByStudentNameLike(String studentName);

    //模糊查询，取前两条
    List<Student> findTop2ByStudentNameLike(String studentName);

    @Query(nativeQuery = true, value = "select * from h_student where student_name = :studentName " + "and card_id = :cardId")
    List<Student> getStudentsByParams(@Param("studentName") String studentName, @Param("cardId") int cardId);
}
