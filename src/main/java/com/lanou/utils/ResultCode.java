package com.lanou.utils;


/**
 * Created by lizhongren1 on 2017/5/5.
 */
public enum ResultCode {

    SUCCESS("0", "成功"),
    UnknownException("01", "未知异常"),
    SystemException("02", "系统异常"),
    MyException("03", "业务异常"),
    InfoException("04", "提示级异常"),
    DBException("020001", "数据库异常"),
    ParamException("040001", "参数验证异常");

    private String _code;
    private String _msg;

    public String getCode() {
        return _code;
    }

    public String getMsg() {
        return _msg;
    }

    private ResultCode(String code, String msg) {
        _code = code;
        _msg = msg;
    }

    public static ResultCode getByCode(String code){
        for (ResultCode resultCode : ResultCode.values()) {
            if (resultCode.getCode().equals(code)) {
                return resultCode;
            }
        }
        return null;
    }
}
