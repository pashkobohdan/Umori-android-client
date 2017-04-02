package com.pashkobohdan.umori_jokesandstories.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.pashkobohdan.umori_jokesandstories.data.ormLite.HelperFactory;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
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
    private Collection<PostModel> postList;

    public void addPost(PostModel value) throws SQLException{
        value.setSource(this);
        //HelperFactory.getHelper().getPostDAO().create(value);
        postList.add(value);
    }

    public void removePost(PostModel value) throws SQLException{
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SourceModel that = (SourceModel) o;

        if (site != null ? !site.equals(that.site) : that.site != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (parsel != null ? !parsel.equals(that.parsel) : that.parsel != null) return false;
        if (encoding != null ? !encoding.equals(that.encoding) : that.encoding != null)
            return false;
        if (linkpar != null ? !linkpar.equals(that.linkpar) : that.linkpar != null) return false;
        return desc != null ? desc.equals(that.desc) : that.desc == null;

    }

    @Override
    public int hashCode() {
        int result = site != null ? site.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (parsel != null ? parsel.hashCode() : 0);
        result = 31 * result + (encoding != null ? encoding.hashCode() : 0);
        result = 31 * result + (linkpar != null ? linkpar.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        return result;
    }

    public Collection<PostModel> getPostList() {
        return postList;
    }

    public void setPostList(Collection<PostModel> postList) {
        this.postList = postList;
    }
}