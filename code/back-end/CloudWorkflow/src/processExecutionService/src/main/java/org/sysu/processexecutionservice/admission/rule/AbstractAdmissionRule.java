package org.sysu.processexecutionservice.admission.rule;

import org.sysu.processexecutionservice.admission.IAdmissionor;

public abstract class AbstractAdmissionRule implements IRule {
    private IAdmissionor admissionor;

    @Override
    public void setAdmissionor(IAdmissionor admissionor) {
        this.admissionor = admissionor;
    }

    @Override
    public IAdmissionor getAdmissionor() {
        return this.admissionor;
    }
}
