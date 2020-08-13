package com.qw.modules.test.service;

import com.qw.modules.common.vo.Result;
import com.qw.modules.test.pojo.Student;

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
}
