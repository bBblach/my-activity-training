package org.activiti;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyUnitTest {
	private ProcessInstance processInstance;
	private String processName;
	
	@Before
	
	public void setUp() {
		
		
	}
	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();
	
	@Test
	@Deployment(resources = {"org/activiti/test/my-process.bpmn20.xml"})
	public void test() {
		
		
		this.processName = "my-test-process";
		this.processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey(processName);
		
		assertNotNull(processInstance);
		Task task = activitiRule.getTaskService().createTaskQuery().singleResult();		
	
		assertEquals("Activiti is awesome!", task.getName());
		
	}
	

}
