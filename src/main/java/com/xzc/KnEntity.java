package com.xzc;


import com.alibaba.excel.annotation.ExcelProperty;

public class KnEntity {

    @ExcelProperty(index = 1)
    private String cId;
    @ExcelProperty(index = 4)
    private String oldCode;
    @ExcelProperty(index = 6)
    private String newCode;

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getOldCode() {
        return oldCode;
    }

    public void setOldCode(String oldCode) {
        this.oldCode = oldCode;
    }

    public String getNewCode() {
        return newCode;
    }

    public void setNewCode(String newCode) {
        this.newCode = newCode;
    }
}
