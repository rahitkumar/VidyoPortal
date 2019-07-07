package com.vidyo.bo;

public class BrowseFilter {
    String nameext = "";
    String type = "All";

    String sort = "name";
    String dir = "ASC";

    int limit = 25;
    int start = 0;

    public String getNameext() {
        return nameext;
    }

    public void setNameext(String nameext) {
        this.nameext = nameext;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
}