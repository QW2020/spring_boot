package com.qw.modules.test.service.Impl;

import com.qw.modules.common.vo.Result;
import com.qw.modules.test.pojo.Student;
import com.qw.modules.test.repository.StudentRepository;
import com.qw.modules.test.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * ClassName: StudentServiceImpl <br/>
 * Description: <br/>
 * date: 2020/8/12 19:27<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Result<Student> insertStudent(Student student) {
        student.setCreateDate(LocalDateTime.now());
        studentRepository.saveAndFlush(student);
        return new Result<Student>(Result.ResultStatus.SUCCESS.status,"insertStudent success.",student);
    }
}
