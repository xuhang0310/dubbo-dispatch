package com.github.xupei.simple.sys.service.impl;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;



import com.github.xupei.dubbo.api.IPictureService;
import com.github.xupei.dubbo.api.IUserService;
import com.github.xupei.dubbo.bean.SysUser;
import com.github.xupei.simple.dao.BaseDao;






@Service
public class PictureServiceImpl implements IPictureService{
	
	
	@Resource(name="baseDao")
	private BaseDao baseDao;

	
	public Map getPictureList(Map<String, String> paramMap) throws Exception {
		// TODO Auto-generated method stub
		String sql;
		if(paramMap.get("picturename").isEmpty()){
		 sql="select t.*,t.id view1,t.id note1  from picture_set t order by id";
		 }else{
			 sql="select t.*,t.id view1,t.id note1  from picture_set t  where t.pic_name like '%"+paramMap.get("picturename")+"%' order by id";
		 }
		
		paramMap=new HashMap<String, String>();
		paramMap.put("page","1");
		paramMap.put("rows","20");
		return baseDao.queryGridList(sql,paramMap);
	}



	@Override
	public List getPictureRightList(String userid) throws Exception {
		// TODO Auto-generated method stub
		String sql="select sr.permissions from sys_user su ,sys_role sr where sr.roleid=su.roleid and su.userid='"+userid+"' ";
		Map<String, String> map=new HashMap();
		List list=baseDao.findAll(sql,  map);
		String permissions=((HashMap)list.get(0)).get("PERMISSIONS").toString();
		List menuList=getParentList(permissions);
		
		List rightList=new ArrayList();
		for(int i=0;i<menuList.size();i++){
			
			HashMap mapMenu=(HashMap)menuList.get(i);
			if(Integer.parseInt(mapMenu.get("NUMS").toString())>=1){
				mapMenu.put("hasSub", true);
				mapMenu.put("menus", getSubRightList(mapMenu.get("id").toString(),permissions));
			}else{
				mapMenu.put("hasSub", true);
			}
			rightList.add(mapMenu);
			
		}
		return rightList;
	}
	
	public List getParentList(String permissions ) throws Exception{
		String sql="select t.* ,(select count(1) from sys_menu sm where sm.parentid=t.menuid) nums from sys_menu t where del = '1' and parentid='0' and t.menuid in ('"+permissions.replace(",", "','")+"') order by to_number(orderid) ";
		
		List list= baseDao.findAll(sql,  new HashMap());
		ArrayList menuList=new ArrayList();
		for(int i=0;i<list.size();i++){
			HashMap map=(HashMap)list.get(i);
			HashMap mapMenu=new HashMap();
			String name=map.get("MENUNAME")+"";
			String id=map.get("MENUID")+"";
			String icon=map.get("ICONCLASS")==null?"icon-leaf":map.get("ICONCLASS")+"";
			mapMenu.put("text", name);
			mapMenu.put("id", id);
			mapMenu.put("icon", icon);
			mapMenu.put("url", "''");
			mapMenu.put("NUMS", map.get("NUMS"));
			menuList.add(mapMenu);
		}
		
		return menuList;
	}
	
	public List getSubRightList(String menuid,String permissions) throws Exception{
		String sql="select t.*  "+
				"  from sys_menu t "+
				"  where del = '1' and  t.menuid in ('"+permissions.replace(",", "','")+"') and t.parentid='"+menuid+"' order by orderid ";
		List list= baseDao.findAll(sql,  new HashMap());
		ArrayList menuList=new ArrayList();
		for(int i=0;i<list.size();i++){
			HashMap map=(HashMap)list.get(i);
			HashMap mapMenu=new HashMap();
			String name=map.get("MENUNAME")+"";
			String id=map.get("MENUID")+"";
			String icon=map.get("ICONCLASS")==null?"icon-leaf":map.get("ICONCLASS")+"";
			String url=map.get("MENUURL")+"";
			mapMenu.put("text", name);
			mapMenu.put("id", id);
			mapMenu.put("icon", icon);
			mapMenu.put("url", url);
			
			menuList.add(mapMenu);
		}
		
		return menuList;
	}

	@Override
	public void savePicture(String pic_name,String filename ) throws Exception {
		// TODO Auto-generated method stub
		
	//	String sql="insert into sys_user(username,password,displayname,roleid,orgid) values('"+paramMap.get("username")+"','123456','"+paramMap.get("displayname")+"','1','0') ";
	String sql="insert into picture_set(pic_name,pic_fname) values('"+pic_name+"','"+filename+"')";
   baseDao.addObject(sql);
	}
	@Override
	public void savePictureById(String pic_name,String filename,String id ) throws Exception {
	
	String sql="update picture_set set pic_name='"+pic_name+"',pic_fname='"+filename+"' where id="+id;
   baseDao.execute(sql);
	}
	
	
	public void deletePicture(String id) throws Exception {
		// TODO Auto-generated method stub
		
		String sql="delete from picture_set where id='"+id+"'";
	    baseDao.deleteObject(sql);
	}
	
	public static void main(String arg []){
		for(int i=0;i<100;i++){
			SimpleDateFormat simple=new SimpleDateFormat("yyyyMMddHHmmssSSSS");
			System.out.println(simple.format(new Date()));
		}
		
	}


	@Override
	public void thumbnail(CommonsMultipartFile file, String uploadPath,
			String path) throws Exception{
		// TODO Auto-generated method stub
		String des=path+file.getOriginalFilename();
		try {
			Thumbnails.of(file.getInputStream()).size(300, 300).toFile(des);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



	@Override
	public void updatePicture(String id) throws Exception {
		
	
	}



	@Override
	public List getPicture(String id) throws Exception {
		
		// TODO Auto-generated method stub
		String sql="select t.id,t.pic_name,t.pic_fname  from picture_set t ";
		if(null!=id&&!"".equals(id)){
			sql+="where t.id= "+id;
		}
				
		
		return baseDao.findAll(sql);
		
	
	}

	

}

