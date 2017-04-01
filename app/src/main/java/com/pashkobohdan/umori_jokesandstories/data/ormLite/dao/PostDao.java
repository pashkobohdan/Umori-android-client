package com.pashkobohdan.umori_jokesandstories.data.ormLite.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.pashkobohdan.umori_jokesandstories.data.model.PostModel;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by bohdan on 01.04.17.
 */

public class PostDao extends BaseDaoImpl<PostModel, Integer> {

    public PostDao(ConnectionSource connectionSource, Class<PostModel> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public List<PostModel> getAllPosts() throws SQLException{
        return this.queryForAll();
    }

}