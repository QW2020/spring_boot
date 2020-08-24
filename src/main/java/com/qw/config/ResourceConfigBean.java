package com.qw.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * ClassName: ResourceConfigBean <br/>
 * Description: <br/>
 * date: 2020/8/24 13:50<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 * 本地资源文件
 */
@Component
@PropertySource("classpath:config/application.properties")
public class ResourceConfigBean {

    //相对路径
    @Value("${spring.resource.path}")
    private String relativePath;
    @Value("${spring.resource.path.pattern}")
    private String relativePathPattern;
    //本地绝对路径
    @Value("${spring.resource.folder.windows}")
    private String locationPathForWindows;
    @Value("${spring.resource.folder.linux}")
    private String locationPathForLinux;

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getRelativePathPattern() {
        return relativePathPattern;
    }

    public void setRelativePathPattern(String relativePathPattern) {
        this.relativePathPattern = relativePathPattern;
    }

    public String getLocationPathForWindows() {
        return locationPathForWindows;
    }

    public void setLocationPathForWindows(String locationPathForWindows) {
        this.locationPathForWindows = locationPathForWindows;
    }

    public String getLocationPathForLinux() {
        return locationPathForLinux;
    }

    public void setLocationPathForLinux(String locationPathForLinux) {
        this.locationPathForLinux = locationPathForLinux;
    }
}
