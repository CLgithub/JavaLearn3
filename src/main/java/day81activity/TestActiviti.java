package day81activity;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;

/**
 * 模拟activity工作流框架执行
 * 
 * @author L
 * @date 2016年8月25日
 */
public class TestActiviti {

	public static void main(String[] args) {
		// 1.得到流程引擎对象
		ProcessEngine processEngine = createProcessEngine3();
		// 部署流程引擎
		deploy(processEngine);
	}

	/**
	 * 取得流程引擎，且自动创建activiti涉及的数据库和表 方法一
	 * 
	 * @author L
	 * @date 2016年8月25日
	 */
	public static ProcessEngine createProcessEngine() {
		// 1.取得ProcessEngineConfiguration对象
		ProcessEngineConfiguration engineConfiguration = ProcessEngineConfiguration
				.createStandaloneProcessEngineConfiguration();
		// 2.设置数据库的连接属性
		engineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
		// 如果没有数据库，会自动创建
		engineConfiguration.setJdbcUrl("jdbc:mysql://localhost:3306/activitiDB?createDatabaseIfNotExist=true"
				+ "&useUnicode=true&characterEncoding=utf8");
		engineConfiguration.setJdbcUsername("root");
		engineConfiguration.setJdbcPassword("123456");
		// 3.设置创建表的策略，当没有表时自动创建
		// public static final String DB_SCHEMA_UPDATE_FALSE = "false";
		// //不会自动创建表，没有表，抛出异常
		// public static final String
		// DB_SCHEMA_UPDATE_CREATE_DROP="create-drop";//先删除表，后后创建
		// public static final String DB_SCHEMA_UPDATE_TRUE =
		// "true";//如果没有表，则自动创建
		engineConfiguration.setDatabaseSchemaUpdate("true");

		// 4. 通过ProcessEngineConfiguration对象创建ProcessEngine对象
		ProcessEngine processEngine = engineConfiguration.buildProcessEngine();
		System.out.println("数据库创建完成");
		return processEngine;
	}

	/**
	 * 方法二 通过加载activiti.cfg.xml获取流程引擎和自动创建数据库及表
	 */
	public static ProcessEngine createProcessEngine2() {
		ProcessEngineConfiguration engineConfiguration = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti.cfg.xml");// 从类的加载路径中加载资源，
		ProcessEngine processEngine = engineConfiguration.buildProcessEngine();
		System.out.println("数据库创建完成");
		return processEngine;
	}

	/**
	 * 方法三 通过processengines来获取默认的流程引擎
	 */
	public static ProcessEngine createProcessEngine3() {
		return ProcessEngines.getDefaultProcessEngine();// 默认会加载类路径下的activiti.cfg.xml文件
	}

	// 部署流程引擎
	public static void deploy(ProcessEngine processEngine) {
		// 先得到仓库服务,用于管理流程定义
		RepositoryService repositoryService = processEngine.getRepositoryService();
		// 通过仓库服务对象创建一个部署构建器
		DeploymentBuilder dBuilder = repositoryService.createDeployment();
		dBuilder.addClasspathResource("diagrams/MyProcess.bpmn");// 从类路径中添加流程图资源,一次只能添加一个资源
		dBuilder.addClasspathResource("diagrams/MyProcess.png");
		dBuilder.name("请假单流程");// 设置流程名称
		dBuilder.category("办公类别");// 设置流程类别
		Deployment deploy = dBuilder.deploy();

		System.out.println("部署的流程id" + deploy.getId());

	}
}
