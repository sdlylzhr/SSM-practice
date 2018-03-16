package com.lanou.utils;

/**
 * Created by lizhongren1 on 2017/5/5.
 */
public class AjaxResult {

    private String ErrorCode = ResultCode.SUCCESS.getCode();
    private String Message = ActionConstants.DEFAULT_SUCCESS_RETURNMSG;

    private Object Data = null;

    public AjaxResult(){}

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }

    public static AjaxResult getOK(String message, Object obj){
        AjaxResult result = new AjaxResult();
        result.setMessage(message);
        result.setData(obj);
        return result;
    }

    public static AjaxResult getOK(Object obj) {
        AjaxResult result = new AjaxResult();
        result.setMessage(ActionConstants.DEFAULT_SUCCESS_RETURNMSG);
        result.setData(obj);
        return result;
    }

    public static AjaxResult getOK(String message, Object obj, ResultCode code){
        AjaxResult result = new AjaxResult();
        result.setMessage(message);
        result.setData(obj);
        result.setErrorCode(code.getCode());
        return result;
    }

}
