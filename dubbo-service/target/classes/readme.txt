spring-servlet.xml中dubbo注册进行以下拆分，请各位务必按照以下说明进行功能归类

dubbo-system  
     存储系统设置、开发管理相关功能配置文件
dubbo-weather
     存储天气预报相关功能配置文件
dubbo-scada
     存储实时监控相关功能配置文件
dubbo-energy
     存储能耗分析相关功能配置文件
dubbo-duty
     存储调度值班相关功能配置文件
dubbo-base
     存储基础信息相关功能配置文件
dubbo-dispatch
     存储调度指挥相关功能配置文件
dubbo-defect
     存储缺陷管理相关功能配置文件
     
     
     
以后package命名规则参考一下规则

com.github.xupei.simple.bas.service.impl  基础信息
com.github.xupei.simple.duty.service.impl 调度值班
com.github.xupei.simple.energy.service.impl 能耗信息
com.github.xupei.simple.scada.service.impl  实时工况
com.github.xupei.simple.weather.service.impl 天气预报
com.github.xupei.simple.system.service.impl  系统设置、开发管理