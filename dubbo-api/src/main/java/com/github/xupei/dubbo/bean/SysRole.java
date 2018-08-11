package com.github.xupei.dubbo.bean;

import java.util.ArrayList;
import java.util.List;

public class SysRole {
	private String roleid; //角色ID
	private Integer orderid; //排序id
	private String rolename; //角色名称
	private String permissions; //功能权限
	private Integer del = 1; //删除标识符
	private String note; //备注信息
	private String orgid; //机构id
	private String roletype; //0:能耗角色 1:生产角色
	private String paramRoleIds; // 角色IDs
	private String paramOrgName; // 机构名称
	private String permissionTreeData;
	public String getRoleid() {
		return roleid;
	}
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getPermissions() {
		return permissions;
	}
	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
	public Integer getDel() {
		return del;
	}
	public void setDel(Integer del) {
		this.del = del;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getOrgid() {
		return orgid;
	}
	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}
	public String getRoletype() {
		return roletype;
	}
	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}
	public String getParamRoleIds() {
		return paramRoleIds;
	}
	public void setParamRoleIds(String paramRoleIds) {
		this.paramRoleIds = paramRoleIds;
	}
	public String getParamOrgName() {
		return paramOrgName;
	}
	public void setParamOrgName(String paramOrgName) {
		this.paramOrgName = paramOrgName;
	}
	public String getPermissionTreeData() {
		return permissionTreeData;
	}
	public void setPermissionTreeData(String permissionTreeData) {
		this.permissionTreeData = permissionTreeData;
	}
	
	
	
	
}
