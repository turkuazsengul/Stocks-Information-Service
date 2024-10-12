package com.stockinformationapp.core.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BaseResponse<T> {
    private int returnCode;
    private String returnMessage;
    private String detailMessage;
    private int totalDataCount;
    private List<T> returnData;

    public BaseResponse(){
        this.returnData = new ArrayList<>();
    }

    public void addList(T obj){
        returnData.add(obj);
    }
}
