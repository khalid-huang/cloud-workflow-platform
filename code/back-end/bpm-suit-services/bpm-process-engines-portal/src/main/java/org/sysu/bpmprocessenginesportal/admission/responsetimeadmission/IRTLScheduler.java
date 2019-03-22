package org.sysu.bpmprocessenginesportal.admission.responsetimeadmission;


import org.sysu.bpmprocessenginesportal.requestcontext.IRequestContext;

public interface IRTLScheduler {
    public void admit(IRequestContext requestContext);

    public void dispatch(IRequestContext requestContext);
}
