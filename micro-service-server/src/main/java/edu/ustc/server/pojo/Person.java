package edu.ustc.server.pojo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("人员信息")
public class Person implements Serializable {
	
	private static final long serialVersionUID = 4379146960917025627L;
	
	@ApiModelProperty(value = "人员id")
	private Integer id;
	
	@ApiModelProperty(value = "姓名")
	private String name;
	
	@ApiModelProperty(value = "年龄")
	private Integer age;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public void setAge(Integer age) {
		this.age = age;
	}
}
