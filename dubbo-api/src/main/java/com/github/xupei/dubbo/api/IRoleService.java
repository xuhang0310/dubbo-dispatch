package com.github.xupei.dubbo.api;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IRoleService {
	
	public Map<String, Set<String>> selectRoleResoureByUserId(String userid) throws Exception;

}
