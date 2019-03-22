package org.sysu.bpmprocessenginesportal.admission.responsetimeadmission.rule;


import org.sysu.bpmprocessenginesportal.admission.responsetimeadmission.IRTLScheduler;

public abstract class AbstractAdmissionAdmissionRule implements IAdmissionRule {
    private IRTLScheduler admissionor;

    @Override
    public void setAdmissionor(IRTLScheduler admissionor) {
        this.admissionor = admissionor;
    }

    @Override
    public IRTLScheduler getAdmissionor() {
        return this.admissionor;
    }
}
