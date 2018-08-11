package testchart;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.github.xupei.simple.base.BaseController;
import com.github.xupei.simple.util.echart.EchartVo;

/**
 * 功能描述：  .  <BR>
 * 历史版本: <Br>
 * 开发者: Guangxing   <BR>
 * 时间：2018-5-28 上午9:22:37  <BR>
 * 变更原因：    <BR>
 * 变化内容 ：<BR>
 * 首次开发时间：2018-5-28 上午9:22:37 <BR>
 * 描述：   <BR>
 * 版本：V1.0
 */
@Controller
@RequestMapping(value="testchart")
public class TestChartController extends BaseController {
	
	
	public void getTestChart(HttpServletRequest request, HttpServletResponse response,
			@RequestParam Map<String, String> paramsMap){
		//line 折线图数据格式
		//vo.chartData='[{"TIME":"17","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"18","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"19","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"20","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"21","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"22","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"23","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"0","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"1","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"2","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"3","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"4","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"5","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"6","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"7","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"8","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"9","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"10","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"11","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"12","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"13","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"14","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"15","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"16","FLOW":"8200.0","TEMP":"66.0"},{"TIME":"17","FLOW":"8200.0","TEMP":"66.0"}]';
		//vo.xaisData='["17","18","19","20","21","22","23","0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17"]';
		//pie 饼图数据格式
		//vo.chartData='[{"value":23988,"name":"对外经贸合作"},{"value":23017,"name":"科技"},{"value":21013,"name":"文化体育"},{"value":18650,"name":"金融"},{"value":17820,"name":"教育"},{"value":17356,"name":"基础设施"},{"value":17355,"name":"物流"},{"value":17343,"name":"能源"},{"value":14928,"name":"铁路"},{"value":14752,"name":"国内贸易"},{"value":12484,"name":"旅游"},{"value":9184,"name":"农林牧渔"},{"value":8999,"name":"重大项目"},{"value":8108,"name":"环境保护"},{"value":7985,"name":"公路"},{"value":7720,"name":"电力"},{"value":7684,"name":"民航"},{"value":7487,"name":"医药卫生"},{"value":7318,"name":"信息产业"},{"value":7141,"name":"民族宗教"}]';
		EchartVo vo = new EchartVo();
		JSONObject jsonNode = null;
		if(vo.getXaisData().size()>0){
		    jsonNode=JSONObject.fromObject(vo);
		}
		PrintWriter out =null;
		response.setContentType("text/html;charset=utf-8");
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.write(jsonNode.toString());
	}

}
