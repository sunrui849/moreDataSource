package com.sr.controller;

import com.sr.dao.useDb1DataSourceDao.MysqlDb1Mapper;
import com.sr.dao.userDb2DataSourceDao.MysqlDb2Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/4/20.
 */
@RestController
public class Controller {
    @Autowired
    MysqlDb1Mapper m;
    @Autowired
    MysqlDb2Mapper m2;

    @RequestMapping("/test")
    public void test(){
        System.out.println(m.getCount());
        System.out.println(m2.getCount());

        ;
    }
}
