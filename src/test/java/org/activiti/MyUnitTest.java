package org.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.test.Deployment;
import org.apache.commons.logging.impl.Log4JLogger;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;
/* 
 * Excercise 2: 
 * 4.3.1. Deploying the process 
 * 	https://www.activiti.org/userguide/index.html?&_ga=1.8101042.143973163.1479068727#_configuration
*/

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyUnitTest {
	private final Logger LOG = Logger.getLogger(this.getClass());
	private static ProcessEngine processEngine;
	private ProcessInstance processInstance;
	@Before
	public void setUp() {
		deployProcess();
		startProcess();
		fetchTasks();
	}
	public void deployProcess() {
		processEngine = ProcessEngines.getDefaultProcessEngine();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment().addClasspathResource("org/activiti/test/VacationRequest.bpmn20.xml")
				.deploy();
		LOG.info("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());
	}
	
	public void startProcess() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("employeeName", "Kermit");
		variables.put("numberOfDays", new Integer(4));
		variables.put("vacationMotivation", "I'm really tired!");

		RuntimeService runtimeService = processEngine.getRuntimeService();
		processInstance = runtimeService.startProcessInstanceByKey("vacationRequest", variables);

		// Verify that we started a new process instance
		LOG.info("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());
	}
	// Fetch all tasks for the management group
	public void fetchTasks() {

		TaskService taskService = processEngine.getTaskService();
		List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
		System.out.println("INSIDE FETCH TASKS  tasks size " + tasks.size());

		for (Task task : tasks) {
			  LOG.info("Task available: " + task.getName());
		}
	}
	
	@Test
	public void testTask() {
		System.out.println("Test running ");

	}

}
