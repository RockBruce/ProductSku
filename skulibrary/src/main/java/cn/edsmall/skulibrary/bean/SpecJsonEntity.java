package cn.edsmall.skulibrary.bean;

import java.util.List;

public class SpecJsonEntity {
    private String name;
    private String id;
    private List<SpecParamsEntity> specParams;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SpecParamsEntity> getSpecParams() {
        return specParams;
    }

    public void setSpecParams(List<SpecParamsEntity> specParams) {
        this.specParams = specParams;
    }
}
