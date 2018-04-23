package com.sr.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
@MapperScan(basePackages = "com.sr.dao.useDb1DataSourceDao",sqlSessionTemplateRef = "baseSqlSessionTemplate")
public class BaseDataSourceCfg {

    private final String xmlResource = "classpath:mapper/*/*.xml";

    @Value("${spring.datasource.db1.url}")
    private String url;

    @Value("${spring.datasource.db1.username}")
    private String user;

    @Value("${spring.datasource.db1.password}")
    private String password;

    @Value("${spring.datasource.db1.driver-class-name}")
    private String driverClass;



    @Bean( name = "baseDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    @Primary
    public DataSource setDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "db1TransactionManager")
    @Primary
    public DataSourceTransactionManager setTransactionManager(){
        System.out.println("bbbbbbbbbbb");
        return new DataSourceTransactionManager(setDataSource());
    }

    @Bean(name = "baseSqlSessionFactory")
    @Primary
    public SqlSessionFactory setSqlSessionFactory(@Qualifier("baseDataSource") DataSource dataSource) throws Exception {
        System.out.println("ccccccccccc");
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(xmlResource));
        return bean.getObject();
    }

    @Bean(name = "baseSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate setSqlSessionTemplate(@Qualifier("baseSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        System.out.println("Ddddddddddddddddddd");
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
