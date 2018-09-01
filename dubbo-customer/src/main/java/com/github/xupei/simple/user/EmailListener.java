package com.github.xupei.simple.user;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.github.xupei.simple.shiro.ShiroUser;
@Service
public class EmailListener implements ApplicationListener<UserLoginEvent>{

	@Override
	public void onApplicationEvent(UserLoginEvent event) {
		// TODO Auto-generated method stub
		ShiroUser user = 	(ShiroUser) event.getSource();
		System.out.println(user.getLoginName()+"------------邮件");
	}

}
