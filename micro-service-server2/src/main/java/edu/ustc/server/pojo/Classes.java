package edu.ustc.server.pojo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("班级信息")
public class Classes implements Serializable {
	
	private static final long serialVersionUID = -2170266853318647332L;
	
	@ApiModelProperty(value = "班级id")
	private Integer id;
	
	@ApiModelProperty(value = "年级")
	private String grade;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getGrade() {
		return grade;
	}
	
	public void setGrade(String grade) {
		this.grade = grade;
	}
}