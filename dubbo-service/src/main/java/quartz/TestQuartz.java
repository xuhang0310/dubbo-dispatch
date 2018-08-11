package quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Configuration
@PropertySource(value = "classpath:quartz.properties")
public class TestQuartz {
	  /**
     * 记录日志类
     */
    public Log logger = LogFactory.getLog(this.getClass());

    /**
     * 定时任务方法
     */
/*   @Scheduled(cron = "${jobs}")
    public void send(){
        logger.info("start.....22222");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
    }*/
  /* 
   * 要注意的是，要使用
   * 
   * @Bean public static PropertySourcesPlaceholderConfigurer
   * propertyConfigInDev() { return new PropertySourcesPlaceholderConfigurer(); }
   * 
   * 才能让spring正确解析出${} 中的值 http://blog.csdn.net/itchiang/article/details/51144218
   */
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
      return new PropertySourcesPlaceholderConfigurer();
  }
}
