package com.lesie.framework.request;


import java.util.Map;

public class SharingRequest {

    private String dataControllerKey;
    private String dataSubjectKey;
    private String thirdPartyKey;
    private String contextKey;
    private Map<String, String> data;

    public SharingRequest(String dataControllerKey, String dataSubjectKey, String thirdPartyKey,
                          String contextKey, Map<String, String> data){
        this.dataControllerKey = dataControllerKey;
        this.dataSubjectKey = dataSubjectKey;
        this.thirdPartyKey = thirdPartyKey;
        this.contextKey = contextKey;
        this.data = data;
    }

    public String getDataControllerKey() {
        return dataControllerKey;
    }

    public void setDataControllerKey(String dataControllerKey) {
        this.dataControllerKey = dataControllerKey;
    }

    public String getDataSubjectKey() {
        return dataSubjectKey;
    }

    public void setDataSubjectKey(String dataSubjectKey) {
        this.dataSubjectKey = dataSubjectKey;
    }

    public String getThirdPartyKey() {
        return thirdPartyKey;
    }

    public void setThirdPartyKey(String thirdPartyKey) {
        this.thirdPartyKey = thirdPartyKey;
    }

    public String getContextKey() {
        return contextKey;
    }

    public void setContextKey(String contextKey) {
        this.contextKey = contextKey;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
