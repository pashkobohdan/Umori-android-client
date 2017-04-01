package com.pashkobohdan.umori_jokesandstories.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.pashkobohdan.umori_jokesandstories.data.ormLite.HelperFactory;

import java.sql.SQLException;
import java.util.List;


/**
 * Created by bohdan on 25.03.17.
 */
@DatabaseTable(tableName = "sources")
public class SourceModel {

    @DatabaseField(generatedId = true)
    private int Id;



    @DatabaseField()
    @SerializedName("site")
    @Expose
    private String site;

    @DatabaseField()
    @SerializedName("name")
    @Expose
    private String name;

    @DatabaseField()
    @SerializedName("url")
    @Expose
    private String url;

    @DatabaseField()
    @SerializedName("parsel")
    @Expose
    private String parsel;

    @DatabaseField()
    @SerializedName("encoding")
    @Expose
    private String encoding;

    @DatabaseField()
    @SerializedName("linkpar")
    @Expose
    private String linkpar;

    @DatabaseField()
    @SerializedName("desc")
    @Expose
    private String desc;



    @ForeignCollectionField(eager = true)
    private List<PostModel> postList;

    public void addRole(PostModel value) throws SQLException{
        value.setSource(this);
        HelperFactory.getHelper().getPostDAO().create(value);
        postList.add(value);
    }

    public void removeRole(PostModel value) throws SQLException{
        postList.remove(value);
        HelperFactory.getHelper().getPostDAO().delete(value);
    }



    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParsel() {
        return parsel;
    }

    public void setParsel(String parsel) {
        this.parsel = parsel;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getLinkpar() {
        return linkpar;
    }

    public void setLinkpar(String linkpar) {
        this.linkpar = linkpar;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}