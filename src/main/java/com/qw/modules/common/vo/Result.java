package com.qw.modules.common.vo;

/**
 * ClassName: Result <br/>
 * Description: <br/>
 * date: 2020/8/12 9:57<br/>
 *
 * @author Acer-pc<br/>
 * @since JDK 1.8
 */
public class Result<T> {

    private int status;
    private String message;
    private T object;

    public Result() {
    }

    public Result(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public Result(int status, String message, T object) {
        this.status = status;
        this.message = message;
        this.object = object;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public enum ResultStatus{
        SUCCESS(200),FAILD(500);
        public int status;

        ResultStatus(int status) {
            this.status = status;
        }
    }

    //普通定义常量
    private final static int BIG_IMAGE_WIDTH = 1000;
    private final static int BIG_IMAGE_HEIGHT = 1000;
    private final static int MIDDLE_IMAGE_WIDTH = 500;
    private final static int MIDDLE_IMAGE_HEIGHT = 500;
    private final static int SMALL_IMAGE_WIDTH = 100;
    private final static int SMALL_IMAGE_HEIGHT = 100;

    //枚举更好用些
    public enum IMAGE{
        BIG_IMAGE(1000, 1000, 100),
        MIDDEL_IMAGE(500, 500, 50),
        SMALL_IMAGE(100, 100, 20);
        public int width;
        public int height;
        public int size;

        IMAGE(int width, int height, int size) {
            this.width = width;
            this.height = height;
            this.size = size;
        }
    }
}
