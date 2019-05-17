package edu.ustc.server.pojo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("人员信息")
public class Person implements Serializable {
	
	private static final long serialVersionUID = 4379146960917025627L;
	
	@ApiModelProperty(value = "主键id")
	private Integer id;
	
	@ApiModelProperty(value = "人员id")
	@NotNull(message = "人员id不能为空")
	private Integer pid;
	
	@ApiModelProperty(value = "姓名")
	@NotBlank(message = "姓名不能为空")
	private String name;
	
	@ApiModelProperty(value = "年龄")
	@NotNull(message = "年龄不能为空")
	private Integer age;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getPid() {
		return pid;
	}
	
	public void setPid(Integer pid) {
		this.pid = pid;
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
