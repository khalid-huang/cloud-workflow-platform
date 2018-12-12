package org.sysu.processexecutionservice.admission.rule;

import org.sysu.processexecutionservice.admission.ActivitiExecuteAdmissionor;
import org.sysu.processexecutionservice.admission.IAdmissionor;
import org.sysu.processexecutionservice.admission.requestcontext.ActivitiExecuteRequestContext;
import org.sysu.processexecutionservice.admission.requestcontext.IRequestContext;

//最基本的，直接放到执行队列中进行执行
public class BaseRule extends AbstractAdmissionRule {

    private ActivitiExecuteAdmissionor activitiExecuteAdmissionor;

    public BaseRule() {

    }

    public BaseRule(IAdmissionor admissionor) {
        this();
        this.activitiExecuteAdmissionor = (ActivitiExecuteAdmissionor) admissionor;
        setAdmissionor(admissionor);
    }

    @Override
    public void admit(IRequestContext requestContext) {
        ActivitiExecuteRequestContext activitiExecuteRequestContext = (ActivitiExecuteRequestContext) requestContext;
        //直接放到执行队列中执行
        this.activitiExecuteAdmissionor.getExecuteQueueContext().offer(requestContext);
    }

    public String getDelayQueueContextClassName() {
        return "null";
    }

    public String getExecuteQueueClassName() {
        return "LinkedBockingExecuteQueueContext";
    }
}
