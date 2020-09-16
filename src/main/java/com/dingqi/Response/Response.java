package com.dingqi.Response;

import lombok.Data;

@Data
public class Response {
    private int status;
    private String msg;
    private Object object;

    public Response(int status,String msg,Object object){
        this.msg=msg;
        this.status = status;
        this.object = object;
    }
}
