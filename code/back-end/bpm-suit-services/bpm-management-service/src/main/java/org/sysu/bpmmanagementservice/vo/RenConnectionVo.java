package org.sysu.bpmmanagementservice.vo;

import org.sysu.bpmmanagementservice.entity.RenCapabilityEntity;
import org.sysu.bpmmanagementservice.entity.RenPositionEntity;

import java.util.List;

public class RenConnectionVo {
    private List<RenCapabilityEntity> renCapabilityEntities;

    private List<RenPositionEntity> renPositionEntities;

    public List<RenCapabilityEntity> getRenCapabilityEntities() {
        return renCapabilityEntities;
    }

    public void setRenCapabilityEntities(List<RenCapabilityEntity> renCapabilityEntities) {
        this.renCapabilityEntities = renCapabilityEntities;
    }

    public List<RenPositionEntity> getRenPositionEntities() {
        return renPositionEntities;
    }

    public void setRenPositionEntities(List<RenPositionEntity> renPositionEntities) {
        this.renPositionEntities = renPositionEntities;
    }
}
