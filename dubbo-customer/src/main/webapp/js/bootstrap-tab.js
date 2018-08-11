var arr=new Array();
var addTabs = function (options) {
 //var rand = Math.random().toString();
 //var id = rand.substring(rand.indexOf('.') + 1);
 var url = window.location.protocol + '//' + window.location.host;

 options.url = url + options.url;

 
 id = "tab_" + options.id;
 $(".active").removeClass("active");
 //如果TAB不存在，创建一个新的TAB
 if (!$("#" + id)[0]) {
  //固定TAB中IFRAME高度
	// debugger;
  //mainHeight = document.body.scrollHeight - 90;
  mainHeight = window.innerHeight-80;
  //alert(mainHeight);
  //创建新TAB的title
  title = '<li role="presentation" id="tab_' + id + '"><a href="#' + id + '" aria-controls="' + id + '" role="tab" data-toggle="tab">' + options.title;
  //是否允许关闭
  if (options.close) {
   title += ' <i class="icon-remove" tabclose="' + id + '"></i>';
  }
  title += '</a></li>';
  //是否指定TAB内容
  if (options.content) {
   content = '<div role="tabpanel" class="tab-pane" id="' + id + '">' + options.content + '</div>';
  } else {//没有内容，使用IFRAME打开链接
   content = '<div role="tabpanel" class="tab-pane" id="' + id + '"><iframe id="iframe'+id+'" src="' + options.url + '" width="100%" height="' + mainHeight +
     '" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe></div>';
  }
  //加入TABS
  var total=document.body.clientWidth-220;
  var realWidth=getLiWidth() ;
 
  if(total-realWidth<0){
	 $("#tabs").animate({right: realWidth-total+100}, 200);
  }
  $(".nav-tabs").append(title);
  $(".tab-content").append(content);
 }
// alert("开启");
 var idd="tab_"+id;
 var flag=isInArray(arr,idd);
 if(flag==false){
 arr.push(idd);
 }
 //激活TAB
 $("#tab_" + id).addClass('active');
 $("#" + id).addClass("active");
};
var closeTab = function (id) {
	 //alert("关闭：")
	 var idd2="tab_"+id;
	  for(i=0;i<arr.length;i++){
	 	 if(idd2===arr[i]){
	 		 arr.splice(i, 1);
	 	 } 
	  }
 //如果关闭的是当前激活的TAB，激活他的前一个TAB
	
 if ($("li.active").attr('id') == "tab_" + id) {
  $("#tab_" + id).prev().addClass('active');
  $("#" + id).prev().addClass('active');
 }
 //关闭TAB
//加入TABS
 var total=document.body.clientWidth-220;
 var realWidth=getLiWidth() ;
  
 if(realWidth-total>50){
	 $("#tabs").animate({right: realWidth-total-getLastLiWidth()}, 200);
 }
 

 
 $("#tab_" + id).remove();
 $("#" + id).remove();

  
};
$(function () {
 mainHeight = $(document.body).height() - 45;
 $('.main-left,.main-right').height(mainHeight);
 $("[addtabs]").click(function () {
  addTabs({ id: $(this).attr("id"), title: $(this).attr('title'), close: true });
 });

 $(".nav-tabs").on("click", "[tabclose]", function (e) {
  id = $(this).attr("tabclose");
  closeTab(id);
 });
 
 var menu = new BootstrapMenu('.nav-tabs li', {
		fetchElementData: function(item) {
			return item;
		},
		actionsGroups: [
			['close', 'refresh'],
			['closeOther', 'closeAll'],
			['closeRight', 'closeLeft']
		],
		actions: {
			close: {
				name: '关闭',
				iconClass: 'icon-remove',
				onClick: function(item) {
					debugger;
					var id=$(item)[0].id;
					closeTab(id.substring(4,id.length));
				}
			},
			closeOther: {
				name: '关闭其他',
				iconClass: 'icon-refresh',
				onClick: function(item) {
					var id=$(item)[0].id;
				//	alert(arr.length);
				//	alert(id);
					var len=arr.length;
					var arr2=new Array();
					//arr2.push(arr);
					for(i=0;i<len;i++){
						arr2.push(arr[i]);
					}
					
					for(j=0;j<len;j++){

						if(arr2[j]!==id){
							closeTab(arr2[j].toString().substring(4,18));	
	
						}
						
					}
					
					
				}
			},
			closeAll: {
				name: '关闭全部',
				iconClass: 'icon-remove',
				onClick: function(item) {
				/*	$('.nav-tabs li').each(function() {
						Tab.closeTab($(this));
					});*/

					var id=$(item)[0].id;
					var len2=arr.length;
					var arr3=new Array();
					for(i=0;i<len2;i++){
						arr3.push(arr[i]);
					}					
					for(j=0;j<len2;j++){						
							closeTab(arr3[j].toString().substring(4,18));	
					}
					
					
					
					
					
				}
			},
			closeRight: {
				name: '关闭右侧所有',
				iconClass: 'icon-indent-right',
				onClick: function(item) {
					var id=$(item)[0].id;
					var len=arr.length;
					var arr2=new Array();
					for(i=0;i<len;i++){
						arr2.push(arr[i]);
					}
					var index=arr2.indexOf(id);
					//alert(index);
					for(j=index+1;j<len;j++){				
							closeTab(arr2[j].toString().substring(4,18));								
					}
			
				}
			},
			closeLeft: {
				name: '关闭左侧所有',
				iconClass: 'icon-indent-left',
				onClick: function(item) {

					var id=$(item)[0].id;
					var len=arr.length;
					var arr2=new Array();
					for(i=0;i<len;i++){
						arr2.push(arr[i]);
					}
					var index=arr2.indexOf(id);
					//alert(index);
					for(j=0;j<index;j++){				
							closeTab(arr2[j].toString().substring(4,18));								
					}
			
				
				}
			},
			refresh: {
				name: '刷新',
				iconClass: 'icon-refresh',
				onClick: function(item) {
					
					var id=$(item)[0].id;
					$('#iframe'+id.substring(4,id.length)).attr('src', $('#iframe'+id.substring(4,id.length)).attr('src'));
					
				}
			}
		}
	});
 
 
 
});

function getLiWidth(){
	
	var lis = $("#tabs li");
	
	var w1 = 0;
	lis.each(function(){
		w1 += $(this).width();
	});
	return w1;
}
function getLastLiWidth(){
	
	var lis = $("#tabs li");
	
	var w1 = 0;
	lis.each(function(){
		w1 = $(this).width();
	});
	return w1;
}

function isInArray(arr,value){
    for(var i = 0; i < arr.length; i++){
        if(value === arr[i]){
            return true;
        }
    }
    return false;
}











