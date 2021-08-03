package cn.huanzi.qch.common.model;

/**
 * 自定义业务异常
 */
public class ServiceException extends RuntimeException {

    /**
     * 自定义异常枚举类
     */
    private ErrorEnum errorEnum;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String errorMsg;


    public ServiceException() {
        super();
    }

    public ServiceException(ErrorEnum errorEnum) {
        super("{code:" + errorEnum.getCode() + ",errorMsg:" + errorEnum.getMsg() + "}");
        this.errorEnum = errorEnum;
        this.code = errorEnum.getCode();
        this.errorMsg = errorEnum.getMsg();
    }

    public ServiceException(Integer code,String errorMsg) {
        super("{code:" + code + ",errorMsg:" + errorMsg + "}");
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public ErrorEnum getErrorEnum() {
        return errorEnum;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
