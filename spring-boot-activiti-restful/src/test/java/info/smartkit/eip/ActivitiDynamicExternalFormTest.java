package info.smartkit.eip;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.impl.test.PluggableActivitiTestCase;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.Deployment;

public class ActivitiDynamicExternalFormTest extends PluggableActivitiTestCase {
	@Deployment(resources = { "test/FormKey.bpmn20.xml",
			"test/start.form", "test/first-step.form",
			"test/second-step.form" })
	public void testTaskFormsWithVacationRequestProcess() {

		// Get start form
		String procDefId = repositoryService.createProcessDefinitionQuery()
				.singleResult().getId();
		Object startForm = formService.getRenderedStartForm(procDefId);
		assertNotNull(startForm);

		assertEquals("<input id=\"start-name\" />", startForm);

		Map<String, String> formProperties = new HashMap<String, String>();
		formProperties.put("startName", "yangboz");
		ProcessInstance processInstance = formService.submitStartFormData(
				procDefId, formProperties);
		assertNotNull(processInstance);

		Task task = taskService.createTaskQuery().taskAssignee("user1")
				.singleResult();
		Object renderedTaskForm = formService.getRenderedTaskForm(task.getId());
		assertEquals(
				"<input id=\"start-name\" value=\"yangboz\" />\n<input id=\"first-name\" />",
				renderedTaskForm);

		formProperties = new HashMap<String, String>();
		formProperties.put("firstName", "yangboz");
		formService.submitTaskFormData(task.getId(), formProperties);

		task = taskService.createTaskQuery().taskAssignee("user2")
				.singleResult();
		assertNotNull(task);
		renderedTaskForm = formService.getRenderedTaskForm(task.getId());
		assertEquals("<input id=\"first-name\" value=\"yangboz\" />",
				renderedTaskForm);
	}

}
