<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>生产调度系统</title>
       
        <meta content='width=device-width, initial-scale=1, maximum-scale=1' name='viewport'>
        <script src="<%= request.getContextPath() %>/js/jquery-1.10.2.min.js"></script>
       <%@ include file="/jsp/header.jsp"%>
       <!-- ace styles -->
		<link href="<%= request.getContextPath() %>/css/fileManager.css" rel="stylesheet" type="text/css" />
		
       
    </head>
  
  <body style="margin:10px;overflow-x:hidden;" >
  <div class="row">
     <div class="col-sm-3">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div class="file-manager">
                        <h5>显示：</h5>
                        <a href="file_manager.html#" class="file-control active">所有</a>
                        <a href="file_manager.html#" class="file-control">文档</a>
                        <a href="file_manager.html#" class="file-control">视频</a>
                        <a href="file_manager.html#" class="file-control">图片</a>
                        <div class="hr-line-dashed"></div>
                        <button class="btn btn-primary btn-block" onclick="layer_show('上传文件','<%= request.getContextPath() %>/jsp/user/eidtUser.jsp','600')">上传文件</button>
                        <div class="hr-line-dashed"></div>
                        <h5>文件夹</h5>
                        <ul class="folder-list" style="padding: 0">
                            <li>
                                <a href="file_manager.html"><i class="fa fa-folder"></i>Word文档</a>
                            </li>
                            <li>
                                <a href="file_manager.html"><i class="fa fa-folder"></i>Excel文件</a>
                            </li>
                            <li>
                                <a href="file_manager.html"><i class="fa fa-folder"></i>压缩包</a>
                            </li>
                            <li>
                                <a href="file_manager.html"><i class="fa fa-folder"></i>图片</a>
                            </li>
                        </ul>
                        <h5 class="tag-title">标签</h5>
                        <ul class="tag-list" style="padding: 0">
                            <li>
                                <a href="file_manager.html">换热站</a>
                            </li>
                            <li>
                                <a href="file_manager.html">热源</a>
                            </li>
                            <li>
                                <a href="file_manager.html">个人</a>
                            </li>
                            
                           
                        </ul>
                        <div class="clearfix"></div>
                    </div>
                </div>
            </div>
        </div>
        
        
        <div class="col-sm-9 animated fadeInRight">
            <div class="row">
                <div class="col-sm-12">
                    <div class="file-box">
                        <div class="file">
                            <a href="file_manager.html#">
                                <span class="corner"></span>

                                <div class="icon">
                                    <i class="fa fa-file"></i>
                                </div>
                                <div class="file-name">
                                    Document_2014.doc <br>
                                    <small>添加时间：2014-10-13</small>
                                </div>
                            </a>
                        </div>

                    </div>
                    <div class="file-box">
                        <div class="file">
                            <a href="file_manager.html#">
                                <span class="corner"></span>

                                <div class="image">
                                    <img alt="image" class="img-responsive" src="http://ozwpnu2pa.bkt.clouddn.com/p1.jpg">
                                </div>
                                <div class="file-name">
                                    Italy street.jpg <br>
                                    <small>添加时间：2014-10-13</small>
                                </div>
                            </a>

                        </div>
                    </div>
                    <div class="file-box">
                        <div class="file">
                            <a href="file_manager.html#">
                                <span class="corner"></span>

                                <div class="image">
                                    <img alt="image" class="img-responsive" src="http://ozwpnu2pa.bkt.clouddn.com/p2.jpg">
                                </div>
                                <div class="file-name">
                                    My feel.png <br>
                                    <small>添加时间：2014-10-13</small>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="file-box">
                        <div class="file">
                            <a href="file_manager.html#">
                                <span class="corner"></span>

                                <div class="icon">
                                    <i class="fa fa-music"></i>
                                </div>
                                <div class="file-name">
                                    Michal Jackson.mp3 <br>
                                    <small>添加时间：2014-10-13</small>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="file-box">
                        <div class="file">
                            <a href="file_manager.html#">
                                <span class="corner"></span>

                                <div class="image">
                                    <img alt="image" class="img-responsive" src="http://ozwpnu2pa.bkt.clouddn.com/p3.jpg">
                                </div>
                                <div class="file-name">
                                    Document_2014.doc <br>
                                    <small>添加时间：2014-10-13</small>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="file-box">
                        <div class="file">
                            <a href="file_manager.html#">
                                <span class="corner"></span>

                                <div class="icon">
                                    <i class="img-responsive fa fa-film"></i>
                                </div>
                                <div class="file-name">
                                    Monica's birthday.mpg4 <br>
                                    <small>添加时间：2014-10-13</small>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="file-box">
                        <a href="file_manager.html#">
                            <div class="file">
                                <span class="corner"></span>

                                <div class="icon">
                                    <i class="fa fa-bar-chart-o"></i>
                                </div>
                                <div class="file-name">
                                    Annual report 2014.xls <br>
                                    <small>添加时间：2014-10-13</small>
                                </div>
                            </div>
                        </a>
                    </div>
                    <div class="file-box">
                        <div class="file">
                            <a href="file_manager.html#">
                                <span class="corner"></span>

                                <div class="icon">
                                    <i class="fa fa-file"></i>
                                </div>
                                <div class="file-name">
                                    Document_2014.doc <br>
                                    <small>添加时间：2014-10-13</small>
                                </div>
                            </a>
                        </div>

                    </div>
                    <div class="file-box">
                        <div class="file">
                            <a href="file_manager.html#">
                                <span class="corner"></span>

                                <div class="image">
                                    <img alt="image" class="img-responsive" src="http://ozwpnu2pa.bkt.clouddn.com/p1.jpg">
                                </div>
                                <div class="file-name">
                                    Italy street.jpg <br>
                                    <small>添加时间：2014-10-13</small>
                                </div>
                            </a>

                        </div>
                    </div>
                    <div class="file-box">
                        <div class="file">
                            <a href="file_manager.html#">
                                <span class="corner"></span>

                                <div class="image">
                                    <img alt="image" class="img-responsive" src="http://ozwpnu2pa.bkt.clouddn.com/p2.jpg">
                                </div>
                                <div class="file-name">
                                    My feel.png <br>
                                    <small>添加时间：2014-10-13</small>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="file-box">
                        <div class="file">
                            <a href="file_manager.html#">
                                <span class="corner"></span>

                                <div class="icon">
                                    <i class="fa fa-music"></i>
                                </div>
                                <div class="file-name">
                                    Michal Jackson.mp3 <br>
                                    <small>添加时间：2014-10-13</small>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="file-box">
                        <div class="file">
                            <a href="file_manager.html#">
                                <span class="corner"></span>

                                <div class="image">
                                    <img alt="image" class="img-responsive" src="http://ozwpnu2pa.bkt.clouddn.com/p3.jpg">
                                </div>
                                <div class="file-name">
                                    Document_2014.doc <br>
                                    <small>添加时间：2014-10-13</small>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="file-box">
                        <div class="file">
                            <a href="file_manager.html#">
                                <span class="corner"></span>

                                <div class="icon">
                                    <i class="img-responsive fa fa-film"></i>
                                </div>
                                <div class="file-name">
                                    Monica's birthday.mpg4 <br>
                                    <small>添加时间：2014-10-13</small>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="file-box">
                        <a href="file_manager.html#">
                            <div class="file">
                                <span class="corner"></span>

                                <div class="icon">
                                    <i class="fa fa-bar-chart-o"></i>
                                </div>
                                <div class="file-name">
                                    Annual report 2014.xls <br>
                                    <small>添加时间：2014-10-13</small>
                                </div>
                            </div>
                        </a>
                    </div>
                    <div class="file-box">
                        <div class="file">
                            <a href="file_manager.html#">
                                <span class="corner"></span>

                                <div class="icon">
                                    <i class="fa fa-file"></i>
                                </div>
                                <div class="file-name">
                                    Document_2014.doc <br>
                                    <small>添加时间：2014-10-13</small>
                                </div>
                            </a>
                        </div>

                    </div>
                    <div class="file-box">
                        <div class="file">
                            <a href="file_manager.html#">
                                <span class="corner"></span>

                                <div class="image">
                                    <img alt="image" class="img-responsive" src="http://ozwpnu2pa.bkt.clouddn.com/p1.jpg">
                                </div>
                                <div class="file-name">
                                    Italy street.jpg <br>
                                    <small>添加时间：2014-10-13</small>
                                </div>
                            </a>

                        </div>
                    </div>
                    <div class="file-box">
                        <div class="file">
                            <a href="file_manager.html#">
                                <span class="corner"></span>

                                <div class="image">
                                    <img alt="image" class="img-responsive" src="http://ozwpnu2pa.bkt.clouddn.com/p2.jpg">
                                </div>
                                <div class="file-name">
                                    My feel.png <br>
                                    <small>添加时间：2014-10-13</small>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="file-box">
                        <div class="file">
                            <a href="file_manager.html#">
                                <span class="corner"></span>

                                <div class="icon">
                                    <i class="fa fa-music"></i>
                                </div>
                                <div class="file-name">
                                    Michal Jackson.mp3 <br>
                                    <small>添加时间：2014-10-13</small>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="file-box">
                        <div class="file">
                            <a href="file_manager.html#">
                                <span class="corner"></span>

                                <div class="image">
                                    <img alt="image" class="img-responsive" src="http://ozwpnu2pa.bkt.clouddn.com/p3.jpg">
                                </div>
                                <div class="file-name">
                                    Document_2014.doc <br>
                                    <small>添加时间：2014-10-13</small>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="file-box">
                        <div class="file">
                            <a href="file_manager.html#">
                                <span class="corner"></span>

                                <div class="icon">
                                    <i class="img-responsive fa fa-film"></i>
                                </div>
                                <div class="file-name">
                                    Monica's birthday.mpg4 <br>
                                    <small>添加时间：2014-10-13</small>
                                </div>
                            </a>
                        </div>
                    </div>
                    <div class="file-box">
                        <a href="file_manager.html#">
                            <div class="file">
                                <span class="corner"></span>

                                <div class="icon">
                                    <i class="fa fa-bar-chart-o"></i>
                                </div>
                                <div class="file-name">
                                    Annual report 2014.xls <br>
                                    <small>添加时间：2014-10-13</small>
                                </div>
                            </div>
                        </a>
                    </div>

                </div>
            </div>
        </div>
        
        
   </div>
  </body>
</html>