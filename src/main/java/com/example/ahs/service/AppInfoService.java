package com.example.ahs.service;

import com.example.ahs.dto.AppInfoDTO;
import com.example.ahs.model.AppInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AppInfoService {
    @Value("${app.env}")
    private String env;

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    public AppInfoDTO getAppInfo() {
        logger.info("Was invoked method for get info");
        AppInfo appInfo = new AppInfo();
        appInfo.setAppEnvironment(env);

        return AppInfoDTO.fromAppInfo(appInfo);
    }
}
