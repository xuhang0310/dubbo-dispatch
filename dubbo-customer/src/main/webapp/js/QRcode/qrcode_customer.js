 var qrcode = null;
function generateQRCode(divId,rendermethod, picwidth, picheight,url) {
		 qrcode = $("#"+divId+"").qrcode({ 
	         //   render: rendermethod, // 渲染方式有table方式（IE兼容）和canvas方式
	            width: picwidth, //宽度 
	            height:picheight, //高度 
	            text: utf16to8(url), //内容 utf16to8(url) 函数在 qrcode_customer.js中
	            typeNumber:-1,//计算模式
	            correctLevel:1,//二维码纠错级别
	            background:"#ffffff",//背景颜色
	            foreground:"#000000"  //二维码颜色
	
	        });
	       
    }

//QRcode  类库不支持中文，所以需要jquery-qrcode 类库采用 charCodeAt() 这个方式进行编码转换的
 function utf16to8(str) {
        var out, i, len, c;
        out = "";
        len = str.length;
        for (i = 0; i < len; i++) {
            c = str.charCodeAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) {
                out += str.charAt(i);
            } else if (c > 0x07FF) {
                out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
                out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            } else {
                out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F));
            }
        }
        return out;
    } 
    
    
//下载二维码图片到本地 相关代码
    
/**
 * 根据图片生成画布
 */
function convertImageToCanvas(image) {
    var canvas = document.createElement("canvas");
    canvas.width = image.width;
    canvas.height = image.height;
    canvas.getContext("2d").drawImage(image, 0, 0);
    return canvas;
}
  /**
   * 下载图片
  * imgId：代表jsp页面的中 img标签的id属性值；
  * aId:代表jsp页面中 a标签的id属性值
  * saveName 代表保存图片的名称
  */
function down(imgId,aId,saveName) {
	 //测试 导出二维码图片 start
    var canvas=qrcode.find('canvas').get(0);
    $('#'+imgId).attr('src',canvas.toDataURL('image/jpg'));
    var sampleImage = document.getElementById(imgId+""),
    canvas = convertImageToCanvas(sampleImage);
    url = canvas.toDataURL("image/png");//PNG格式
    //以下代码为下载此图片功能
    var triggerDownload = $("#"+aId).attr("href", url).attr("download", saveName+".png");
    triggerDownload[0].click();
}
    
    
    
    
    
    
    
    
    
    
    