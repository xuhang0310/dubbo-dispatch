package com.github.xupei.dubbo.bean;

import java.io.File;

public class Picture {
	
	public String pic_name;
	public String pic_fname;
	public File pic_file;
	public String getPic_name() {
		return pic_name;
	}
	public File getPic_file() {
		return pic_file;
	}
	public void setPic_file(File pic_file) {
		this.pic_file = pic_file;
	}
	public void setPic_name(String pic_name) {
		this.pic_name = pic_name;
	}
	public String getPic_fname() {
		return pic_fname;
	}
	public void setPic_fname(String pic_fname) {
		this.pic_fname = pic_fname;
	}
	
	
	
}
