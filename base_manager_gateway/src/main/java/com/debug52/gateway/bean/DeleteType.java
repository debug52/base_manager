package com.debug52.gateway.bean;

public enum DeleteType {
    DELETE (1),//删除
    NOT_DELETE(0);//未删除
    private Integer type;
    private DeleteType(int type){
        this.type=type;
    }
    public int getIndex() {
        return type;
    }
    public void setIndex(int index) {
        this.type = index;
    }
}
