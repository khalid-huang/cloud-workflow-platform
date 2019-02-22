package org.sysu.bpmprocessenginesportal.admission.rule;


import org.sysu.bpmprocessenginesportal.admission.IAdmissionor;

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
