package com.blackpalmrecipemanager.dao;


import org.apache.commons.dbcp2.BasicDataSource;

public abstract class JdbcDao {

    // Create the datasource used by all the DAOs
    protected final BasicDataSource dataSource = new BasicDataSource();

    public void setDataSource(){
        this.dataSource.setUrl("jdbc:postgresql://localhost:5432/RecipeManagerDB");
//        this.dataSource.setUsername( /*YOUR USERNAME*/ );
//        this.dataSource.setPassword(/*YOUR PASSWORD*/);
    }


}
