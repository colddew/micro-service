package edu.ustc.server.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DruidStatController {

    @GetMapping("/druid/stat")
    public Object druidStat(){
        // fetch all datasource monitor data
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }
}