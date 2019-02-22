package org.sysu.bpmprocessenginesportal.service;

import org.springframework.http.ResponseEntity;

/** bpm-process-engine-service 的WorkQueueController的门户接口*/
public interface WorkQueueService {

    ResponseEntity<?> getWorkQueue(String username, String role);

}
