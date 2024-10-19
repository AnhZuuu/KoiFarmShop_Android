package com.koifarmshop.model;

import java.util.List;

public class NewKoiModel {
    boolean success;
    String message;
    List<NewKoi> result;

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public List<NewKoi> getResult() {
        return result;
    }
    public void setResult(List<NewKoi> result) {
        this.result = result;
    }
}
