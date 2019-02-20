package com.hacknife.onlite;


public class TableDb {
    String table;
    Integer version;

    public TableDb() {
    }

    public TableDb(String table) {
        this.table = table;
    }

    public TableDb(String table, Integer version) {
        this.table = table;
        this.version = version;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
