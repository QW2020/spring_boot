package com.qw.modules.test.service.Impl;

import com.qw.modules.common.vo.Result;
import com.qw.modules.common.vo.SearchVo;
import com.qw.modules.test.pojo.Student;
import com.qw.modules.test.repository.StudentRepository;
import com.qw.modules.test.service.StudentService;
import net.bytebuddy.TypeCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

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

    //新增
    @Override
    public Result<Student> insertStudent(Student student) {
        student.setCreateDate(LocalDateTime.now());
        studentRepository.saveAndFlush(student);
        return new Result<Student>(Result.ResultStatus.SUCCESS.status,"insertStudent success.",student);
    }

    //查询所有  通过studentName倒序排序
    @Override
    public List<Student> getStudents() {
        Sort sort = Sort.by(DESC,"studentName");
        return studentRepository.findAll(sort);
    }

    //通过Id查询
    @Override
    public Student getStudentByStudentId(int studentId) {
        return studentRepository.findById(studentId).get();
    }

    //模糊查询  排序 分页
    @Override
    public Page<Student> getStudentBySearchVo(SearchVo searchVo) {
        String orderBy = StringUtils.isBlank(searchVo.getOrderBy()) ? "studentId" : searchVo.getOrderBy();
        Sort.Direction direction = StringUtils.isBlank(searchVo.getSort()) ||
        searchVo.getSort().equalsIgnoreCase("asc") ? ASC : DESC;
        //根据xx进行排序
        Sort sort = Sort.by(direction,orderBy);
        //当前页起始为 0（前端传来的）
        Pageable pageable = PageRequest.of(searchVo.getCurrentPage() - 1,searchVo.getPageSize(),sort);

        //build Example 对象
        Student student = new Student();
        //把传过来的keyWord设置为studentName 作为查询条件
        // 如果 keyWord 为 null，则设置的 studentName 不参与查询条件
        student.setStudentName(searchVo.getKeyWord());
        ExampleMatcher matcher = ExampleMatcher.matching()
        //全部模糊查询，即 %{studentName}%
//      .withMatcher("studentName",ExampleMatcher.GenericPropertyMatchers.contains())
        //新写法
        .withMatcher("studentName",match ->match.contains())
         //忽略字段，即不管id是什么值都不加入查询条件
        .withIgnorePaths("studentId");

        //给定student对象模板进行模糊查询
        Example<Student> example = Example.of(student,matcher);
        return studentRepository.findAll(example,pageable);
    }
}
