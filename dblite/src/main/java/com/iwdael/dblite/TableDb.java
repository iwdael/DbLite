package com.iwdael.dblite;
/**
 * author  : iwdael
 * e-mail  : iwdael@outlook.com
 * github  : http://github.com/iwdael
 * project : DbLite
 */
public class TableDb {
    String tableName;
    Integer tableVersion;

    public TableDb(String tableName, Integer tableVersion) {
        this.tableName = tableName;
        this.tableVersion = tableVersion;
    }

    public TableDb(String tableName) {
        this.tableName = tableName;
    }

    public TableDb() {
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getTableVersion() {
        return tableVersion;
    }

    public void setTableVersion(Integer tableVersion) {
        this.tableVersion = tableVersion;
    }
}
