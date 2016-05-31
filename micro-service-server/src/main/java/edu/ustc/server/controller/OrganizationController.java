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

import edu.ustc.server.mongo.organization.Organization;
import edu.ustc.server.service.OrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/v1/organization")
@Api("组织信息相关操作")
public class OrganizationController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);
	
	@Autowired
	private OrganizationService organizationService;
	
	@RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
    @ApiOperation(value="新增组织信息")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
    public void add(@RequestBody Organization organizationDto) {
		
    	logger.info("invoke add method...");
    	
    	organizationService.add(organizationDto);
    }
	
	@RequestMapping(value = "/{oid}", method = RequestMethod.DELETE)
    @ApiOperation(value="删除组织信息")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
    public void delete(@ApiParam(value = "公司id") @PathVariable("oid") String oid) {
    	
    	logger.info("invoke delete method...");
    	
    	organizationService.delete(oid);
    }
	
	@RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
	@ApiOperation(value="批量查询组织信息")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
	public List<Organization> list() {
    	
    	logger.info("invoke list method...");
    	
        return organizationService.list();
    }
}
