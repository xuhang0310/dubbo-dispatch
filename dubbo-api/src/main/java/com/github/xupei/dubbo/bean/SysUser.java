package com.github.xupei.dubbo.bean;

import java.io.Serializable;

public class SysUser implements Serializable {
	
    private String username;
	
	private String password;
	
	private String displayname; 
	
	private String orgid;
	
	private String roleid;
	
	private String userid;
	
	private String templatestyle;
	
	

	public String getTemplatestyle() {
		return templatestyle;
	}

	public void setTemplatestyle(String templatestyle) {
		this.templatestyle = templatestyle;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	

}
