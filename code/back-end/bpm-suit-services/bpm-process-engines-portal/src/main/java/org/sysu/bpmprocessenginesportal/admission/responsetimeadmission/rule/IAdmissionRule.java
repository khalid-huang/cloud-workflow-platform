package org.sysu.bpmprocessenginesportal.admission.responsetimeadmission.rule;


import org.sysu.bpmprocessenginesportal.admission.responsetimeadmission.IRTLScheduler;
import org.sysu.bpmprocessenginesportal.requestcontext.IRequestContext;

//准入规则
public interface IAdmissionRule {

    public void setAdmissionor(IRTLScheduler admissionor);

    public IRTLScheduler getAdmissionor();

    public void admit(IRequestContext requestContext);
}
