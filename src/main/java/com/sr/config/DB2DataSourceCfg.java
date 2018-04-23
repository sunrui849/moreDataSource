package com.sr.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by Administrator on 2018/4/20.
 */
@Configuration
@MapperScan(basePackages = "com.sr.dao.userDb2DataSourceDao",sqlSessionTemplateRef = "db2SqlSessionTemplate")
public class DB2DataSourceCfg {

    private final String xmlResource = "classpath:mapper/useDb2DataSource/*.xml";


    @Value("${spring.datasource.db2.url}")
    private String url;

    @Value("${spring.datasource.db2.username}")
    private String user;

    @Value("${spring.datasource.db2.password}")
    private String password;

    @Value("${spring.datasource.db2.driver-class-name}")
    private String driverClass;


    @Bean(name = "db2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.db2")
    public DataSource setDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "db2TransactionManager")
    public DataSourceTransactionManager setTransactionManager(){
        return new DataSourceTransactionManager(setDataSource());
    }

    @Bean(name = "db2SqlSessionFactory")
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("db2DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(xmlResource));
        return bean.getObject();
    }

    @Bean(name = "db2SqlSessionTemplate")
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("db2SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
