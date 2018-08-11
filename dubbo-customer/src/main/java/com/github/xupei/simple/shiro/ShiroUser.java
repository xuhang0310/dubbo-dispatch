package com.github.xupei.simple.shiro;

import java.io.Serializable;
import java.util.Set;

public class ShiroUser implements Serializable {
    private static final long serialVersionUID = -1373760761780840081L;
    
    private String id;
    private final String loginName;
    private String name;
    private Set<String> urlSet;
    private Set<String> roles;
    private String skintemplate;
    
    
    

    public ShiroUser(String loginName) {
        this.loginName = loginName;
    }

    public ShiroUser(String id, String loginName, String name, String skintemplate,Set<String> urlSet) {
        this.id = id;
        this.loginName = loginName;
        this.name = name;
        this.urlSet = urlSet;
        this.skintemplate=skintemplate;
    }
    
    

    public String getSkintemplate() {
		return skintemplate;
	}

	public void setSkintemplate(String skintemplate) {
		this.skintemplate = skintemplate;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getUrlSet() {
        return urlSet;
    }

    public void setUrlSet(Set<String> urlSet) {
        this.urlSet = urlSet;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getLoginName() {
        return loginName;
    }

    /**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    @Override
    public String toString() {
        return loginName;
    }
}
