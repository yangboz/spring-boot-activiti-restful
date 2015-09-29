package info.smartkit.eip.test.activiti.delegate;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

//
public class ServiceClassDelegateSample implements JavaDelegate {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.activiti.engine.delegate.JavaDelegate#execute(org.activiti.engine
	 * .delegate.DelegateExecution)
	 */
	public void execute(DelegateExecution delegateExecution) throws Exception {

		System.out.println("Executed process with key "
				+ delegateExecution.getProcessBusinessKey()
				+ " with process definition Id "
				+ delegateExecution.getProcessDefinitionId()
				+ " with process instance Id "
				+ delegateExecution.getProcessInstanceId()
				+ " and current task name is "
				+ delegateExecution.getCurrentActivityName());

	}

}