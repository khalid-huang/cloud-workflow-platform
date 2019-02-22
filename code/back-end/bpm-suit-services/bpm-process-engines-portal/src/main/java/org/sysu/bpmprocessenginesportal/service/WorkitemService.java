package org.sysu.bpmprocessenginesportal.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/** bpm-process-engine-service的WorkitemController的门户接口 */
public interface WorkitemService {

    ResponseEntity<?> offerWorkitem(String processInstanceId, String workitemId, String username);

    ResponseEntity<?> allocateWorkitem(String processInstanceId, String workitemId, String username);

    ResponseEntity<?> reofferWorkitem(String processInstanceId, String workitemId, String username);

    ResponseEntity<?> acceptWorkitem(String processInstanceId, String workitemId, String username);

    ResponseEntity<?> acceptAndStartWorkitem(String processInstanceId, String workitemId, String username);

    ResponseEntity<?> completeWorkitem(String processInstanceId, String workitemId, Map<String, Object> data);

    ResponseEntity<?> suspendWorkitem(String processInstanceId, String workitemId);

    ResponseEntity<?> unsuspendWorkitem(String processInstanceId, String workitemId);

    ResponseEntity<?> deallocateWorkitem(String processInstanceId, String workitemId);

    ResponseEntity<?> reallocateWorkitem(String processInstanceId, String workitemId, String username);
}
