package com.qw.modules.test.controller;

import com.qw.modules.common.vo.Result;
import com.qw.modules.common.vo.SearchVo;
import com.qw.modules.test.pojo.Student;
import com.qw.modules.test.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: StudentController <br/>
 * Description: <br/>
 * date: 2020/8/12 19:29<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 127.0.0.1/api/student  ----  post
     * {"studentName":"qw","studentCard":{"cardId":"1"}}
     * 新增
     */
    @PostMapping(value = "/student",consumes = "application/json")
    public Result<Student> insertStudent(@RequestBody Student student){
        return studentService.insertStudent(student);
    }

    /**
     * 127.0.0.1/api/students  ----  get
     * 查询所有  通过name排序
     */
    @GetMapping("/students")
    public List<Student> getStudents(){
        return studentService.getStudents();
    }

    /**
     * 127.0.0.1/api/student/1  ----  get
     * 通过Id查询
     */
    @GetMapping("/student/{studentId}")
    public Student getStudentByStudentId(@PathVariable int studentId){
        return studentService.getStudentByStudentId(studentId);
    }

    /**
     * 127.0.0.1/api/students  ----  post
     * {"currentPage":"1","pageSize":"5","keyWord":"qw","orderBy":"studentName","sort":"desc"}
     * 模糊查询  排序 分页
     */
    @PostMapping(value = "/students",consumes = "application/json")
    public Page<Student> getStudentBySearchVo(@RequestBody SearchVo searchVo){
        return studentService.getStudentBySearchVo(searchVo);
    }

    /**
     * 127.0.0.1/api/studentName?studentName=qw1  ----  get
     * 通过name属性查询单个
     */
    @GetMapping("/studentName")
    public List<Student> getStudentByStudentName(@RequestParam String studentName){
        return studentService.getStudentByStudentName(studentName);
    }

    /**
     * 127.0.0.1/api/getStudentByStudentNameLike?studentName=qw  ----  get
     * 通过name属性模糊查询
     */
    @GetMapping("/getStudentByStudentNameLike")
    public List<Student> getStudentByStudentNameLike(@RequestParam String studentName){
        return studentService.getStudentByStudentNameLike(studentName);
    }

    /**
     * 127.0.0.1/api/getStudentsByParams?studentName=qw1&cardId=1  ----  get
     * @Query注解精准查询
     */
    @GetMapping("/getStudentsByParams")
    public List<Student> getStudentsByParams(@RequestParam String studentName,@RequestParam(required = false,defaultValue = "0") Integer cardId){
        return studentService.getStudentsByParams(studentName,cardId);
    }
}
