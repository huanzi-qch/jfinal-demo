package cn.huanzi.qch.common.model;

import java.util.Objects;

/**
 * 统一返回对象
 */
public class Result<T> {
    /**
     * 通信数据
     */
    private T data;
    /**
     * 通信状态
     */

    private Integer code = 200;
    /**
     * 通信描述
     */
    private String msg = "操作成功";

    @Deprecated
    public Result() {
    }

    private Result(T data) {
        this.data = data;
    }

    private Result(T data, Integer code) {
        this.data = data;
        this.code = code;
    }

    private Result(T data, Integer code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    /**
     * 通过静态方法获取实例
     */
    public static <T> Result<T> of(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> of(T data, Integer code) {
        return new Result<>(data, code);
    }

    public static <T> Result<T> of(T data, Integer code, String msg) {
        return new Result<>(data, code, msg);
    }

    public static <T> Result<T> error(ErrorEnum errorEnum) {
        return new Result(false, errorEnum.getCode(), errorEnum.getMsg());
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Result)) return false;
        Result<?> result = (Result<?>) o;
        return Objects.equals(getData(), result.getData()) &&
                Objects.equals(getCode(), result.getCode()) &&
                Objects.equals(getMsg(), result.getMsg());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getData(), getCode(), getMsg());
    }

    @Override
    public String toString() {
        return "Result{" +
                "data=" + data +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
