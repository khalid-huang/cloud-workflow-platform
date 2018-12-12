package org.sysu.processexecutionservice.admission;

import org.sysu.processexecutionservice.admission.requestcontext.IRequestContext;

public interface IAdmissionor {
    public void admit(IRequestContext requestContext);

    public void dispatch(IRequestContext requestContext);
}
