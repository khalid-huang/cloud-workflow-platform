package org.sysu.bpmprocessenginesportal.admission.responsetimeadmission.rule;

import org.sysu.bpmprocessenginesportal.admission.responsetimeadmission.IAdmissionor;
import org.sysu.bpmprocessenginesportal.admission.responsetimeadmission.ResponseTimeAdmissionScheduler;
import org.sysu.bpmprocessenginesportal.requestcontext.ExecuteRequestContext;
import org.sysu.bpmprocessenginesportal.requestcontext.IRequestContext;

//最基本的，直接放到执行队列中进行执行
public class BaseRule extends AbstractAdmissionRule {

    private ResponseTimeAdmissionScheduler responseTimeAdmissionScheduler;

    public BaseRule() {

    }

    public BaseRule(IAdmissionor admissionor) {
        this();
        this.responseTimeAdmissionScheduler = (ResponseTimeAdmissionScheduler) admissionor;
        setAdmissionor(admissionor);
    }

    @Override
    public void admit(IRequestContext requestContext) {
        ExecuteRequestContext executeRequestContext = (ExecuteRequestContext) requestContext;
        //直接放到执行队列中执行
        this.responseTimeAdmissionScheduler.getExecuteQueueContext().offer(requestContext);
    }

    public String getDelayQueueContextClassName() {
        return "null";
    }

    public String getExecuteQueueClassName() {
        return "LinkedBockingExecuteQueueContext";
    }
}
