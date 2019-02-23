package org.sysu.bpmprocessenginesportal.admission.rule;


import org.sysu.bpmprocessenginesportal.admission.IAdmissionor;
import org.sysu.bpmprocessenginesportal.requestcontext.IRequestContext;

//准入规则
public interface IRule {

    public void setAdmissionor(IAdmissionor admissionor);

    public IAdmissionor getAdmissionor();

    public void admit(IRequestContext requestContext);
}
