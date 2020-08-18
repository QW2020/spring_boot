package com.qw.modules.test.controller;

import com.qw.modules.test.pojo.City;
import com.qw.modules.test.pojo.Country;
import com.qw.modules.test.service.CityService;
import com.qw.modules.test.service.CountryService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName: TestController <br/>
 * Description: <br/>
 * date: 2020/8/10 13:58<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
@Controller
@RequestMapping("/test")
public class TestController {

    private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private CityService cityService;
    @Autowired
    private CountryService countryService;

    @Value("${server.port}")
    private int port;
    @Value("${com.name}")
    private String name;
    @Value("${com.age}")
    private int age;
    @Value("${com.desc}")
    private String desc;
    @Value("${com.random}")
    private String random;

    /**
     *
     * 127.0.0.1:8080/test/config ---get
     * 测试value属性
     */
    @GetMapping("/config")
    @ResponseBody
    public String configTest(){
        return new StringBuffer()
                .append(port).append("--------")
                .append(name).append("--------")
                .append(age).append("--------")
                .append(desc).append("--------")
                .append(random).append("--------")
                .toString();
    }


    /**
     * 127.0.0.1/test/testDesc?paramKey=fuck ---get
     * 测试过滤器
     */
    @GetMapping("/testDesc")
    @ResponseBody
    public String testDesc(HttpServletRequest request, @RequestParam(value = "paramKey") String paramValue){
        String paramValue2 = request.getParameter("paramKey");
        return "This is test module desc." + paramValue + "==" + paramValue2;
    }

    /**
     *
     * 127.0.0.1:8080/test/logTest ---get
     * 测试log
     */
    @GetMapping("/logTest")
    @ResponseBody
    public String logTest(){
        LOGGER.debug("-----------------logTest()---------------"+"this is DEBUG LOG");
        LOGGER.trace("-----------------logTest()---------------"+"this is trace LOG");
        LOGGER.info("-----------------logTest()---------------"+"this is info LOG");
        LOGGER.warn("-----------------logTest()---------------"+"this is warn LOG");
        LOGGER.error("-----------------logTest()---------------"+"this is error LOG");
        return "this is log test";
    }

    /**
     * 127.0.0.1/test/index    ---get
     */
    @GetMapping("/index")
    public String testIndexPage(ModelMap modelMap){
        int countryId = 522;
        List<City> cities = cityService.getCitiesByCountryId(countryId);
        cities = cities.stream().limit(10).collect(Collectors.toList());
        Country country = countryService.getCountryByCountryId(countryId);

        modelMap.addAttribute("thymeleafTitle", "scdscsadcsacd");
        modelMap.addAttribute("checked", true);
        modelMap.addAttribute("currentNumber", 99);
        modelMap.addAttribute("changeType", "checkbox");
        modelMap.addAttribute("baiduUrl", "/test/log");
        modelMap.addAttribute("city", cities.get(0));
        modelMap.addAttribute("shopLogo",
                "http://cdn.duitang.com/uploads/item/201308/13/20130813115619_EJCWm.thumb.700_0.jpeg");
        modelMap.addAttribute("shopLogo",
                "/upload/1111.png");
        modelMap.addAttribute("country", country);
        modelMap.addAttribute("cities", cities);
        modelMap.addAttribute("updateCityUri", "/api/city");
//        modelMap.addAttribute("template", "test/index");
        // 返回外层的碎片组装器
        return "index";
    }

    /**
     * 127.0.0.1/test/file  ---- post
     * 上传单个文件
     */
    @PostMapping(value = "/file",consumes = "multipart/form-data")
    public String uploadFile(@RequestParam MultipartFile file, RedirectAttributes redirectAttributes){

        //如果为空，重新选择
        if (file.isEmpty()){
            redirectAttributes.addFlashAttribute("message","Please select file.");
            return "redirect:/test/index";
        }

        try {
            //目标文件路径
            String destFilePath = "E:\\upload\\" + file.getOriginalFilename();
            //目标文件
            File destFile = new File(destFilePath);
            //上传到目标文件
            file.transferTo(destFile);
            redirectAttributes.addFlashAttribute("message","Upload file success.");
        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message","Upload file failed.");
        }
        return "redirect:/test/index";
    }

    /**
     * 127.0.0.1/test/files  ---- post
     * 上传多个文件
     */
    @PostMapping(value = "/files",consumes = "multipart/form-data")
    public String uploadFiles(@RequestParam MultipartFile[] files, RedirectAttributes redirectAttributes){
        boolean empty = true;
            try {
                for (MultipartFile file : files) {
                if (file.isEmpty()){
                    continue;
                }
                //目标文件路径
                String destFilePath = "E:\\upload\\" + file.getOriginalFilename();
                //目标文件
                File destFile = new File(destFilePath);
                //上传到目标文件
                file.transferTo(destFile);
                empty = false;
            }
            if (empty){
                redirectAttributes.addFlashAttribute("message","Please select file.");
            }else {
                redirectAttributes.addFlashAttribute("message","Upload file success.");
            }
            } catch (IOException e) {
                e.printStackTrace();
                redirectAttributes.addFlashAttribute("message","Upload file failed.");
            }

        return "redirect:/test/index";
    }

    /**
     * 127.0.0.1/test/file  ---- get
     * 下载文件  ResponseEntity方式
     */
    @GetMapping("/file")
    public ResponseEntity<Resource> downloadFile(@RequestParam String fileName){
        //资源提供
        Resource resource = null;
        try {
            resource = new UrlResource(
                    Paths.get("E:\\upload\\"+fileName).toUri());
            //判断资源是否存在、可读
            if (resource.exists()&&resource.isReadable()){
                return ResponseEntity.
                        ok()
                        //响应头
                        .header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
                        //下载描述   attachment-附件形式
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                String.format("attachment; filename=\"%s\"", resource.getFilename()))
                        //传输给页面的文件内容
                        .body(resource);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将文件以BufferedInputStream的方式读取到byte[]里面，然后用OutputStream.write输出文件
     */
    @RequestMapping("/download1")
    public void downloadFile1(HttpServletRequest request,
                              HttpServletResponse response, @RequestParam String fileName) {
        String filePath = "D:/upload" + File.separator + fileName;
        File downloadFile = new File(filePath);

        if (downloadFile.exists()) {
            response.setContentType("application/octet-stream");
            response.setContentLength((int)downloadFile.length());
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    String.format("attachment; filename=\"%s\"", fileName));

            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(downloadFile);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                LOGGER.debug(e.getMessage());
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    if (bis != null) {
                        bis.close();
                    }
                } catch (Exception e2) {
                    LOGGER.debug(e2.getMessage());
                    e2.printStackTrace();
                }
            }
        }
    }

    /**
     * 以包装类 IOUtils 输出文件
     */
    @RequestMapping("/download2")
    public void downloadFile2(HttpServletRequest request,
                              HttpServletResponse response, @RequestParam String fileName) {
        String filePath = "D:/upload" + File.separator + fileName;
        File downloadFile = new File(filePath);

        try {
            if (downloadFile.exists()) {
                response.setContentType("application/octet-stream");
                response.setContentLength((int)downloadFile.length());
                response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                        String.format("attachment; filename=\"%s\"", fileName));

                InputStream is = new FileInputStream(downloadFile);
                IOUtils.copy(is, response.getOutputStream());
                response.flushBuffer();
            }
        } catch (Exception e) {
            LOGGER.debug(e.getMessage());
            e.printStackTrace();
        }
    }
}
