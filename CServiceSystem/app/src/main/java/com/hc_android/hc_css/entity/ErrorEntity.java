package com.hc_android.hc_css.entity;

public class ErrorEntity {


    /**
     * _err : 1
     * places : gobeyond
     * aihecong_version : 1.9.1
     * region : Shanghai
     */

    private int _err;
    private String places;
    private String aihecong_version;
    private String region;
    private String errorNo;


    public String getErrorNo() {
        return errorNo;
    }

    public void setErrorNo(String errorNo) {
        this.errorNo = errorNo;
    }

    public int get_err() {
        return _err;
    }

    public void set_err(int _err) {
        this._err = _err;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }

    public String getAihecong_version() {
        return aihecong_version;
    }

    public void setAihecong_version(String aihecong_version) {
        this.aihecong_version = aihecong_version;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
