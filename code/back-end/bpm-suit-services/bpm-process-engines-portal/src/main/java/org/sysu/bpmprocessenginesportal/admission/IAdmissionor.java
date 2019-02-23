package org.sysu.bpmprocessenginesportal.admission;


import org.sysu.bpmprocessenginesportal.requestcontext.IRequestContext;

public interface IAdmissionor {
    public void admit(IRequestContext requestContext);

    public void dispatch(IRequestContext requestContext);
}
