package day81activity.day2;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.junit.Test;

import com.sun.swing.internal.plaf.synth.resources.synth_sv;

/**
 * 流程实例和流程任务
 * 
 * @author L
 * @date 2016年8月26日
 */
public class ProcessInstanceAndTask {
	private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	// 部署流程定义，采用buyBull
	// 执行流程，开始跑流程
	@Test
	public void startProcess() {
		String processDefinitionKey = "buyBill"; // 流程定义key
		//根据流程定义key，启动一个流程
		ProcessInstance pInstance = processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey);
		
		System.out.println("流程定义的id："+pInstance.getProcessDefinitionId());		//默认执行最新版本的流程
		System.out.println("流程实例id："+pInstance.getProcessInstanceId());	//ProcessInstance	流程实例对象
		System.out.println("流程执行对象id："+pInstance.getId());	//Execution	执行对象
	}
	
	//查询任务
	@Test
	public void queryTask(){
		//先得到任务查询服务
		TaskService taskService = processEngine.getTaskService();
		//通过查询服务取得任务查询对象
		TaskQuery taskQuery = taskService.createTaskQuery();
		List<Task> list = taskQuery.list();
		for(Task task:list){
			System.out.println("任务办理人："+task.getAssignee());
			System.out.println("任务ID："+task.getId());
			System.out.println("任务名字："+task.getName());
		}
	}
	
	//办理任务
	@Test
	public void compileTask(){
		//得到任务服务
		TaskService taskService = processEngine.getTaskService();
		//根据任务id办理任务
		String taskID="22502";
		taskService.complete(taskID);
		System.out.println("任务执行完成");
	}
	
	//查询流程实例状态
	@Test
	public void getProcessInstanceState() {
		String processInstanceId = "17501";
		ProcessInstance pInstance = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId)
				.singleResult();
		//singleResult()返回的数据要么是单行，要么是空，其他情况报错
		
		System.out.println("该流程实例"+pInstance+"正在执行，当前活动的任务："+pInstance.getActivityId());
	}
	
	//查看历史执行流程实例信息
	@Test
	public void queryHisProcInst(){
		List<HistoricProcessInstance> list = processEngine.getHistoryService().createHistoricProcessInstanceQuery().list();
		if(list!=null){
			for(HistoricProcessInstance hProcessInstance:list){
				System.out.println("历史流程实例id："+hProcessInstance.getId());
				System.out.println("历史流程定义id："+hProcessInstance.getProcessDefinitionId());
				System.out.println("历史流程开始时间--结束时间"+hProcessInstance.getStartTime()+"---"+hProcessInstance.getEndTime());
			}
		}
	}
	
	//查看历史执行流程任务信息
	@Test
	public void queryHistASK() {
		String processInstanceId="17501";
		List<HistoricTaskInstance> list = processEngine.getHistoryService().createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list();
		if (list != null) {
			for (HistoricTaskInstance hTaskInstance : list) {
				System.out.println("历史流程定义id：" + hTaskInstance.getProcessDefinitionId());
				System.out.println("历史流程实例id：" + hTaskInstance.getProcessInstanceId());
				System.out.println("历史流程实例任务id：" + hTaskInstance.getId());
				System.out.println("历史流程实例任务名称：" + hTaskInstance.getName());
				System.out.println("历史流程实例任务处理人：" + hTaskInstance.getAssignee());
//				System.out.println("历史流程实例任务处理时间：" + hTaskInstance.getTime());
			}
		}
	}
}
