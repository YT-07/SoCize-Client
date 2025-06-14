package com.socize.pages.fileserver.admin.serverhealthcheck.model;

import com.socize.api.serverhealthcheck.dto.ServerHealthcheckRequest;
import com.socize.pages.fileserver.admin.serverhealthcheck.dto.ServerHealthcheckResult;

public interface ServerHealthcheckModel {

    /**
     * Attempts to get the server's status
     * 
     * @param request the healthcheck request
     * @return the healthcheck result
     */
    ServerHealthcheckResult getServerStatus(ServerHealthcheckRequest request);
}