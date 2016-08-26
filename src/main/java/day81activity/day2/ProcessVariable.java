package day81activity.day2;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * 流程变量测试
 * @author L
 * @date 2016年8月26日
 */
public class ProcessVariable {
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	// 部署流程
	@Test
	public void deploy() {
		// 先得到仓库服务,用于管理流程定义
		RepositoryService repositoryService = processEngine.getRepositoryService();
		// 通过仓库服务对象创建一个部署构建器
		DeploymentBuilder dBuilder = repositoryService.createDeployment();
		dBuilder.addClasspathResource("diagrams/AppayBill.bpmn");// 从类路径中添加流程图资源,一次只能添加一个资源
		dBuilder.addClasspathResource("diagrams/AppayBill.png");
		dBuilder.name("支付流程");// 设置流程名称
		Deployment deploy = dBuilder.deploy();

		System.out.println("部署的流程id" + deploy.getId());
	}
	
	@Test
	public void startProcess() {
		String processDefinitionKey = "appayBill"; // 流程定义key
		//根据流程定义key，启动一个流程
		ProcessInstance pInstance = processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey);
		
		System.out.println("流程定义的id："+pInstance.getProcessDefinitionId());		//默认执行最新版本的流程
		System.out.println("流程实例id："+pInstance.getProcessInstanceId());	//ProcessInstance	流程实例对象
		System.out.println("流程执行对象id："+pInstance.getId());	//Execution	执行对象
	}
	
	
}
