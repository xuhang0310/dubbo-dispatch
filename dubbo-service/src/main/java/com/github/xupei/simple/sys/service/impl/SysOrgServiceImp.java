package com.github.xupei.simple.sys.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.xupei.dubbo.api.ISysOrgService;
import com.github.xupei.simple.dao.BaseDao;

@Service
public class SysOrgServiceImp implements ISysOrgService {
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	@Override
	public Map getOrgList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String sql="select t.*,(select so.orgname from sys_org so where so.orgid=t.parentid ) pname ,t.orgid note1 from sys_org t " +
				"  where 1=1  [ and   (orgid={orgid} or t.parentid={orgid} )  ]  [ and  orgname like '%{orgname}%'  ]order by orgid ";
		return baseDao.queryGridList(sql, paramMap);
	}

	@Override
	public List getOrgListForTree(Map<String, String> paramMap)
			throws Exception {
		// TODO Auto-generated method stub
		String sql="select orgid \"id\",parentid \"pid\" ,orgname \"title\"  from sys_org ";
		return baseDao.findAll(sql);
	}


	public List getAllOrgList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String sql="select * from sys_org ";
		return baseDao.findAll(sql,paramMap);
	}
	
	

}
