package org.sysu.bpmprocessenginesportal.admission.responsetimeadmission.rule;


import org.sysu.bpmprocessenginesportal.admission.responsetimeadmission.IAdmissionor;

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
