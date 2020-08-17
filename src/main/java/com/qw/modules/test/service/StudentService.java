package com.qw.modules.test.service;

import com.qw.modules.common.vo.Result;
import com.qw.modules.common.vo.SearchVo;
import com.qw.modules.test.pojo.Student;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * ClassName: StudentService <br/>
 * Description: <br/>
 * date: 2020/8/12 19:27<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
public interface StudentService {

    Result<Student> insertStudent(Student student);

    List<Student> getStudents();

    Student getStudentByStudentId(int studentId);

    Page<Student> getStudentBySearchVo(SearchVo searchVo);

    List<Student> getStudentByStudentName(String studentName);

    List<Student> getStudentByStudentNameLike(String studentName);

    List<Student> getStudentsByParams(String studentName, Integer cardId);
}
