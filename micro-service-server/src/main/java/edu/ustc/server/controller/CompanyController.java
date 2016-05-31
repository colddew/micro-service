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

import edu.ustc.server.mongo.company.Company;
import edu.ustc.server.service.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@EnableAutoConfiguration
@RequestMapping("/api/v1/company")
@Api("公司信息相关操作")
public class CompanyController {
	
	private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);
	
	@Autowired
	private CompanyService companyService;
	
	@RequestMapping(value = {"/", ""}, method = RequestMethod.POST)
    @ApiOperation(value="新增公司信息")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
    public void add(@RequestBody Company companyDto) {
		
    	logger.info("invoke add method...");
    	
    	companyService.add(companyDto);
    }
	
	@RequestMapping(value = "/{cid}", method = RequestMethod.DELETE)
    @ApiOperation(value="删除公司信息")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
    public void delete(@ApiParam(value = "公司id") @PathVariable("cid") String cid) {
    	
    	logger.info("invoke delete method...");
    	
    	companyService.delete(cid);
    }
	
	@RequestMapping(value = "/{cid}", method = RequestMethod.PUT)
    @ApiOperation(value="修改公司信息")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
    public void update(@ApiParam(value = "公司id") @PathVariable("cid") String cid, @RequestBody Company companyDto) {
    	
    	logger.info("invoke update method...");
    	
    	companyService.update(cid, companyDto);
    }
	
	@RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
	@ApiOperation(value="批量查询公司信息")
	@ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
	public List<Company> list() {
    	
    	logger.info("invoke list method...");
    	
        return companyService.list();
    }
    
    @RequestMapping(value = "/{cid}", method = RequestMethod.GET)
    @ApiOperation(value="查询单个公司信息")
    @ApiResponses(value = { @ApiResponse(code = 401, message = "请求未通过认证") })
    public Company get(@ApiParam(value = "公司id") @PathVariable("cid") String cid) {
		
		logger.info("invoke get method...");
		
		return companyService.findById(cid);
    }
}
