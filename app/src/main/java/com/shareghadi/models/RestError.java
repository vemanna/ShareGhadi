package com.shareghadi.models;

/**
 * Created by BVN on 12/25/2015.
 */
public class RestError {
    public RestError(String localizedMessage) {
        this.message = localizedMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getExtendedMessage() {
        return extendedMessage;
    }

    public void setExtendedMessage(String extendedMessage) {
        this.extendedMessage = extendedMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(String moreInfo) {
        this.moreInfo = moreInfo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer errorCode;
    public String extendedMessage;
    public String message;
    public String moreInfo;
    public Integer status;

}
