package com.pashkobohdan.umori_jokesandstories.data.ormLite.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.pashkobohdan.umori_jokesandstories.data.model.SourceModel;

import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by bohdan on 01.04.17.
 */

public class SourceDao extends BaseDaoImpl<SourceModel, Integer> {

    public SourceDao(ConnectionSource connectionSource, Class<SourceModel> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public Collection<SourceModel> getAllSources() throws SQLException {
        return this.queryForAll();
    }

}