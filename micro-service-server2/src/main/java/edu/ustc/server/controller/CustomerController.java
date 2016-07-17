package edu.ustc.server.controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.ustc.server.cassandra.Customer;
import edu.ustc.server.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/v1/customers")
@Api("客户信息相关操作")
public class CustomerController {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
	@ApiOperation(value="批量查询客户信息")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
	public List<Customer> list() {
    	
    	logger.info("invoke list method...");
    	
        return customerService.list();
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(value="查询单个客户信息")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
    public Customer get(@ApiParam(value = "客户id") @PathVariable("id") String id) {
		
		logger.info("invoke get method...");
		
		return customerService.get(UUID.fromString(id));
    }
    
    @RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
    @ApiOperation(value="新增客户信息")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
    public void add(@RequestBody Customer customer) {
    	
    	logger.info("invoke add method...");
    	
    	customerService.save(customer);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ApiOperation(value="修改客户信息")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
    public void update(@ApiParam(value = "客户id") @PathVariable("id") String id, @RequestBody Customer customerDto) {
    	
    	logger.info("invoke update method...");
    	
    	customerService.save(customerDto);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value="删除客户信息")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
    public void delete(@ApiParam(value = "客户id") @PathVariable("id") String id) {
    	
    	logger.info("invoke delete method...");
    	
    	customerService.delete(UUID.fromString(id));
    }
}
