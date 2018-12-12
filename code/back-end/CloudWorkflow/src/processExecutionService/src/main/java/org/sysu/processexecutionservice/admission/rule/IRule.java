package org.sysu.processexecutionservice.admission.rule;

import org.sysu.processexecutionservice.admission.IAdmissionor;
import org.sysu.processexecutionservice.admission.requestcontext.IRequestContext;

//准入规则
public interface IRule {

    public void setAdmissionor(IAdmissionor admissionor);

    public IAdmissionor getAdmissionor();

    public void admit(IRequestContext requestContext);
}
