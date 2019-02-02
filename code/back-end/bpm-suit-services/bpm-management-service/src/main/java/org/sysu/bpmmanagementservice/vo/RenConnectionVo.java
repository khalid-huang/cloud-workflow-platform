package org.sysu.bpmmanagementservice.vo;

import org.sysu.bpmmanagementservice.entity.RenCapabilityEntity;
import org.sysu.bpmmanagementservice.entity.RenPositionEntity;

import java.util.ArrayList;
import java.util.List;

public class RenConnectionVo {
    private List<RenCapabilityEntity> renCapabilityEntities = new ArrayList<>();

    private List<RenPositionEntity> renPositionEntities = new ArrayList<>();

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
