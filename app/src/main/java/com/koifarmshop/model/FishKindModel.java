package com.koifarmshop.model;

import java.util.List;

public class FishKindModel {
    boolean success;
    String message;
    List<FishKind> result;

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

    public List<FishKind> getResult() {
        return result;
    }
    public void setResult(List<FishKind> result) {
        this.result = result;
    }
}
