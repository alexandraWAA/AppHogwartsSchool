package com.example.ahs.dto;

import com.example.ahs.model.AppInfo;
import lombok.Data;

@Data
public class AppInfoDTO {
    private String appName;
    private String appVersion;
    private String appEnvironment;


    public static AppInfoDTO fromAppInfo(AppInfo appInfo) {
        AppInfoDTO dto = new AppInfoDTO();
        dto.setAppName(AppInfo.APP_NAME);
        dto.setAppVersion(AppInfo.APP_VERSION);
        dto.setAppEnvironment(appInfo.getAppEnvironment());
        return dto;
    }
}
