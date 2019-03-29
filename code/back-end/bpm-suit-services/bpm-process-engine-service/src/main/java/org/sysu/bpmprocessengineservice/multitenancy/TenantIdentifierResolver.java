package org.sysu.bpmprocessengineservice.multitenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;
import org.sysu.bpmprocessengineservice.constant.GlobalContext;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {
    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId =TenantContext.getCurrentTenant();
        System.out.println("resolve - tenantId: " + tenantId);
        if(tenantId != null) {
            return tenantId;
        }
        return GlobalContext.DEFAULT_TENANT_ID;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
