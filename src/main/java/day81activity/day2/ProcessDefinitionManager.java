package day81activity.day2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.cmd.DeleteIdentityLinkForProcessDefinitionCmd;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.junit.Test;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

/**
 * 流程定义管理
 * @author L
 * @date 2016年8月26日
 */
public class ProcessDefinitionManager {
	private ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
	
	//部署流程定义
	@Test
	public void deployProcessDefi(){
		//得到仓库服务
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//通过仓库服务得到部署构建器
		DeploymentBuilder dBuilder = repositoryService.createDeployment();
		//设置部署属性
		dBuilder.addClasspathResource("diagrams/BuyBill_bpmn.bpmn");
		dBuilder.addClasspathResource("diagrams/BuyBill_bpmn.png");
		dBuilder.name("采购流程");
		//部署流程
		Deployment deployment = dBuilder.deploy();
		
		System.out.println("部署ID："+deployment.getId());
		System.out.println("部署名称："+deployment.getName());
	}
	
	//删除流程定义
	@Test
	public void DeleteProcessDefi(){
		RepositoryService rService = processEngine.getRepositoryService();
		//通过删除部署来删除
		String deploymentId="1";
		rService.deleteDeployment(deploymentId);
	}
	
	//查看流程定义
	@Test
	public void queryProcessDefination(){
		RepositoryService repositoryService = processEngine.getRepositoryService();
		ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
		
//		processDefinitionQuery.processDefinitionId("buyBill:1:15004");//通过流程定义的id来查询
//		processDefinitionQuery.processDefinitionKey("buyBill");//通过流程定义的key来查询
//		processDefinitionQuery.processDefinitionName("");//通过流程定义的name来查询
//		processDefinitionQuery.processDefinitionVersion(1);//通过版本来查询
		processDefinitionQuery.latestVersion();//查询最新版本
		//按版本降序排序
//		processDefinitionQuery.orderByProcessDefinitionVersion().desc();
		
		List<ProcessDefinition> list = processDefinitionQuery.list();
		if(list!=null){
			for(ProcessDefinition pDefinition:list){
				System.out.print("流程定义id："+pDefinition.getId());
				System.out.print("流程定义名称："+pDefinition.getName());
				System.out.println("流程定义版本："+pDefinition.getVersion());
			}
		}
	}
	
	//查看bpmn图片
	@Test
	public void viewImg() throws IOException{
		RepositoryService repositoryService = processEngine.getRepositoryService();
		String deploymentId="15001";
		String imageName="";
		//获取部署资源名称
		List<String> rescourceNames = repositoryService.getDeploymentResourceNames(deploymentId);
		if(rescourceNames!=null){
			for(String rescourceName:rescourceNames){
				if(rescourceName.contains(".png")){
					imageName=rescourceName;
				}
			}
		}
		//读取资源
		InputStream inputStream = repositoryService.getResourceAsStream(deploymentId, imageName);
		System.out.println(inputStream.toString());
		FileOutputStream fOutputStream=new FileOutputStream("D:/aaa.png");
		
		int i=0;
		byte[] buf=new byte[1024];
		while((i=inputStream.read(buf))!=-1){
			fOutputStream.write(buf, 0, i);
		}
		fOutputStream.close();
		inputStream.close();
	}
}
