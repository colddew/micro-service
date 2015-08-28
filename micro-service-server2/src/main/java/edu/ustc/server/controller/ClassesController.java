package edu.ustc.server.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.ustc.server.pojo.Classes;
import edu.ustc.server.service.ClassesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@EnableAutoConfiguration
@RequestMapping("/classes")
@Api("班级信息相关操作")
public class ClassesController {
	
	private static final Logger logger = LoggerFactory.getLogger(ClassesController.class);
	
	@Autowired
	private ClassesService classesService;
	
	@RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
	@ApiOperation(value="批量查询班级信息")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
	public List<Classes> list() {
    	
    	logger.info("invoke list method...");
    	
        return classesService.list();
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value="查询单个班级信息")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
    public Classes get(@ApiParam(value = "班级id") @PathVariable("id") Integer id) {
		
		logger.info("invoke get method...");
		
		return classesService.get(id);
    }
    
    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
    @ApiOperation(value="新增班级信息")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
    public void add(@RequestBody Classes classes) {
		
    	logger.info("invoke add method...");
    	
    	classesService.add(classes);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation(value="修改班级信息")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
    public void update(@ApiParam(value = "班级id") @PathVariable("id") Integer id, @RequestBody Classes classesDto) {
    	
    	logger.info("invoke update method...");
    	
    	classesService.update(id, classesDto);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value="删除班级信息")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
    public void delete(@ApiParam(value = "班级id") @PathVariable("id") Integer id) {
    	
    	logger.info("invoke delete method...");
    	
    	classesService.delete(id);
    }
}
