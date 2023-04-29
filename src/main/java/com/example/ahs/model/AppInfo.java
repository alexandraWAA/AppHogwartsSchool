package com.example.ahs.model;

import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    public class AppInfo {
        private String appEnvironment;

        public static final String APP_NAME =  "hogwarts-school";
        public static final String APP_VERSION = "0.0.1";
    }

