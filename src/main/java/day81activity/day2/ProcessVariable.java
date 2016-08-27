package day81activity.day2;

import java.util.Date;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
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
	
	//办理任务
	@Test
	public void compileTask(){
		//得到任务服务
		TaskService taskService = processEngine.getTaskService();
		//根据任务id办理任务
		String taskID="32502";
		taskService.complete(taskID);
		System.out.println("任务执行完成");
	}
	
	//模拟流程变量设置
	@Test
	public void getAndProcessVariable(){
		//有两个服务可以设置流程变量
		TaskService taskService = processEngine.getTaskService();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		/**
		 * 1.通过runtimeService设置
		 * executionId	执行对象id
		 * variableName 变量名
		 * value 变量值
		 */
//		/**
//		 * 设置流程变量，作用域是整个流程实例，一个流程实例可以有多个执行对象
//		 */
//		runtimeService.setVariable(executionId, variableName, value);
//		/**
//		 * 设置本执行对象的变量，作用域只在当前execution执行对象
//		 */
//		runtimeService.setVariableLocal(executionId, variableName, value);
//		/**
//		 * 设置多个变量，变量存放在map<key,value>
//		 */
//		runtimeService.setVariables(executionId, variables);
		
		/**
		 * 2.通过taskService设置
		 * taskId 任务id
		 * variableName	变量名
		 * value 变量值
		 */
//		/**
//		 * 设置该任务id的变量
//		 */
//		taskService.setVariable(taskId, variableName, value);
//		/**
//		 * 设置多个变量，变量存放在map<key,value>
//		 */
//		taskService.setVariables(taskId, variables);
//		/**
//		 * 当任务开始的时候，设置变量参数
//		 * processDefinitionKey 流程定义的key
//		 * variables 多个任务map<key,values>
//		 */
//		runtimeService.startProcessInstanceByKey(processDefinitionKey, variables);
//		
//		/**
//		 * 当执行任务的时候，可以设置流程变量
//		 * taskId 任务id
//		 * variables 多个任务map
//		 */
//		taskService.complete(taskId, variables);
		
		/**
		 * 3.通过runtimeSerivce取的变量值
		 * executionId 执行对象id
		 */
//		runtimeService.getVariable(executionId, variableName);
//		runtimeService.getVariableLocal(executionId, variableName);
//		runtimeService.getVariables(executionId)
//		runtimeService.getVariablesLocal(executionId);
		
		/**
		 * 4.通过taskService取的变量值
		 * executionId 执行对象id
		 */
//		taskService.getVariable(taskId, variableName)
//		taskService.getVariableLocal(taskId, variableName);
//		taskService.getVariables(taskId);
//		taskService.getVariablesLocal(taskId);
	}
	
	//设置流程变量值
	@Test
	public void setVariable(){
		//有两个服务可以设置流程变量
		TaskService taskService = processEngine.getTaskService();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		
		//1设置流程变量	
//		String taskId="25004";//任务id
//		taskService.setVariable(taskId, "cost", 1000);	//设置申请1000	
//		taskService.setVariable(taskId, "申请时间", new Date());
//		taskService.setVariableLocal(taskId, "申请人", "小白");	//该变量只在本任务中有效
//		System.out.println("设置成功");
		
		//2在不同的任务当中设置变量	
//		String taskId="32502";//任务id
//		taskService.setVariable(taskId, "cost", 500);	//设置申请1000	
//		taskService.setVariable(taskId, "申请时间", new Date());
//		taskService.setVariableLocal(taskId, "申请人", "小黑");	//该变量只在本任务中有效

		
		 // 3.设置自定义类型变量
		
		//传递一个自定义的对象
		String taskId="40004";
		AppayBillBean aBean = new AppayBillBean();
		aBean.setId(1);
		aBean.setCost(300);
		aBean.setDate(new Date());
		aBean.setAppayPerson("小强");
		taskService.setVariable(taskId, "appayBillBean", aBean);
		System.out.println("设置成功");
	}
	
	//得到流程变量
	@Test
	public void getVariable(){
		TaskService taskService = processEngine.getTaskService();
//		String taskId="25004";//任务id
//		Integer cost=(Integer) taskService.getVariable(taskId, "cost");	//取变量
////		Date date=(Date) taskService.getVariable(taskId, "申请时间");	//取变量
//		Date date=(Date) taskService.getVariableLocal(taskId, "申请时间");	//取变量
//		String appayPerson=(String) taskService.getVariableLocal(taskId, "申请人");	//取本任务变量
//		System.out.println(appayPerson+"-"+date+"-"+cost);
		
		//读取自定义类型变量
		String taskId="40004";
		AppayBillBean appayBillBean = taskService.getVariable(taskId, "appayBillBean", AppayBillBean.class);
		System.out.println(appayBillBean);
	}
	
	//查询流程实例状态
	@Test
	public void getProcessInstanceState() {
		String processInstanceId = "5001";
		ProcessInstance pInstance = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		//singleResult()返回的数据要么是单行，要么是空，其他情况报错
		
		System.out.println("该流程实例"+pInstance+"正在执行，当前活动的任务："+pInstance.getActivityId());
	}
	
	
	
	
}
