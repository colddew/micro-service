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

import edu.ustc.server.pojo.Person;
import edu.ustc.server.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@EnableAutoConfiguration
@RequestMapping("/person")
@Api("人员信息相关操作")
public class PersonController {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	
	@Autowired
	private PersonService personService;
	
	@RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
	@ApiOperation(value="批量查询人员信息")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
	public List<Person> list() {
    	
    	logger.info("invoke list method...");
    	
        return personService.list();
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value="查询单个人员信息")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
    public Person get(@ApiParam(value = "人员id") @PathVariable("id") Integer id) {
		
		logger.info("invoke get method...");
		
		return personService.get(id);
    }
    
    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
    @ApiOperation(value="新增人员信息")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
    public void add(@RequestBody Person person) {
		
    	logger.info("invoke add method...");
    	
    	personService.add(person);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation(value="修改人员信息")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
    public void update(@ApiParam(value = "人员id") @PathVariable("id") Integer id, @RequestBody Person personDto) {
    	
    	logger.info("invoke update method...");
    	
    	personService.update(id, personDto);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value="删除人员信息")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
    public void delete(@ApiParam(value = "人员id") @PathVariable("id") Integer id) {
    	
    	logger.info("invoke delete method...");
    	
    	personService.delete(id);
    }
}