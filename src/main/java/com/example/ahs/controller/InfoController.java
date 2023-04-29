package com.example.ahs.controller;

import com.example.ahs.dto.AppInfoDTO;
import com.example.ahs.service.AppInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/info")
@RestController
public class InfoController {
    private final AppInfoService appInfoService;

    public InfoController(AppInfoService appInfoService) {
        this.appInfoService = appInfoService;
    }

    @GetMapping("/appInfo")
    public ResponseEntity<AppInfoDTO>  appInfo() {
        return ResponseEntity.ok(appInfoService.getAppInfo());
    }
}
