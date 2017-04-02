package com.pashkobohdan.umori_jokesandstories.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "posts")
public class PostModel {



    @DatabaseField(generatedId = true)
    private int Id;

    @DatabaseField(foreign = true)
    private SourceModel source;

    private boolean inDB;


    @DatabaseField()
    @SerializedName("site")
    @Expose
    private String site;

    @DatabaseField()
    @SerializedName("name")
    @Expose
    private String name;

    @DatabaseField()
    @SerializedName("desc")
    @Expose
    private String desc;

    @DatabaseField()
    @SerializedName("link")
    @Expose
    private String link;

    @DatabaseField()
    @SerializedName("elementPureHtml")
    @Expose
    private String elementPureHtml;

    /**
     * @return The site
     */
    public String getSite() {
        return site;
    }

    /**
     * @param site The site
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     * @return Site name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name Site name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Site description
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc Site description
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * @return The link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link The link
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return The elementPureHtml
     */
    public String getElementPureHtml() {
        return elementPureHtml;
    }

    /**
     * @param elementPureHtml The elementPureHtml
     */
    public void setElementPureHtml(String elementPureHtml) {
        this.elementPureHtml = elementPureHtml;
    }

    public SourceModel getSource() {
        return source;
    }

    public void setSource(SourceModel source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PostModel postModel = (PostModel) o;

        if (site != null ? !site.equals(postModel.site) : postModel.site != null) return false;
        if (name != null ? !name.equals(postModel.name) : postModel.name != null) return false;
        if (desc != null ? !desc.equals(postModel.desc) : postModel.desc != null) return false;
        if (link != null ? !link.equals(postModel.link) : postModel.link != null) return false;
        return elementPureHtml != null ? elementPureHtml.equals(postModel.elementPureHtml) : postModel.elementPureHtml == null;

    }

    @Override
    public int hashCode() {
        int result = site != null ? site.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (elementPureHtml != null ? elementPureHtml.hashCode() : 0);
        return result;
    }

    public boolean isInDB() {
        return inDB;
    }

    public void setInDB(boolean inDB) {
        this.inDB = inDB;
    }
}