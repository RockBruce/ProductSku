package cn.edsmall.skulibrary.bean;

public class SkuAttribute {
    /**
     * groupName : 颜色
     * groupId : 1335892298138910720
     * name : 哑白+砂黑
     * id : 2D1B93024B4C81F7438D4A16DAACD762
     */
    private String groupName;
    private String groupId;
    private String name;
    private String id;
    public SkuAttribute() {
    }
    public SkuAttribute(String key, String value) {
        this.groupName = key;
        this.name = value;
    }
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

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
}
