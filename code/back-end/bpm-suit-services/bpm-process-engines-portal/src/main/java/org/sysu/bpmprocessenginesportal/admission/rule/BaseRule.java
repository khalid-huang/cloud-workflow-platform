package org.sysu.bpmprocessenginesportal.admission.rule;

import org.sysu.bpmprocessenginesportal.admission.ExecuteAdmissionor;
import org.sysu.bpmprocessenginesportal.admission.IAdmissionor;
import org.sysu.bpmprocessenginesportal.requestcontext.ExecuteRequestContext;
import org.sysu.bpmprocessenginesportal.requestcontext.IRequestContext;

//最基本的，直接放到执行队列中进行执行
public class BaseRule extends AbstractAdmissionRule {

    private ExecuteAdmissionor executeAdmissionor;

    public BaseRule() {

    }

    public BaseRule(IAdmissionor admissionor) {
        this();
        this.executeAdmissionor = (ExecuteAdmissionor) admissionor;
        setAdmissionor(admissionor);
    }

    @Override
    public void admit(IRequestContext requestContext) {
        ExecuteRequestContext executeRequestContext = (ExecuteRequestContext) requestContext;
        //直接放到执行队列中执行
        this.executeAdmissionor.getExecuteQueueContext().offer(requestContext);
    }

    public String getDelayQueueContextClassName() {
        return "null";
    }

    public String getExecuteQueueClassName() {
        return "LinkedBockingExecuteQueueContext";
    }
}
