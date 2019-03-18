package org.sysu.bpmprocessenginesportal.admission.responsetimeadmission.rule;

import org.sysu.bpmprocessenginesportal.admission.responsetimeadmission.IRTLScheduler;
import org.sysu.bpmprocessenginesportal.admission.responsetimeadmission.RTLScheduler;
import org.sysu.bpmprocessenginesportal.requestcontext.ExecuteRequestContext;
import org.sysu.bpmprocessenginesportal.requestcontext.IRequestContext;

//最基本的，直接放到执行队列中进行执行
public class BaseAdmissionRule extends AbstractAdmissionAdmissionRule {

    private RTLScheduler RTLScheduler;

    public BaseAdmissionRule() {

    }

    public BaseAdmissionRule(IRTLScheduler admissionor) {
        this();
        this.RTLScheduler = (RTLScheduler) admissionor;
        setAdmissionor(admissionor);
    }

    @Override
    public void admit(IRequestContext requestContext) {
        ExecuteRequestContext executeRequestContext = (ExecuteRequestContext) requestContext;
        //直接放到执行队列中执行
        this.RTLScheduler.getExecuteQueueContext().offer(requestContext);
    }

    public String getDelayQueueContextClassName() {
        return "null";
    }

    public String getExecuteQueueClassName() {
        return "LinkedBockingExecuteQueueContext";
    }
}
