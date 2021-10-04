package org.perfumepedia.DataBase.model;

import lombok.Data;

@Data
public class ResponseAjax {
    private String status;
    private Object data;

    public ResponseAjax(String status, Object data) {
        this.status = status;
        this.data = data;
    }
}
