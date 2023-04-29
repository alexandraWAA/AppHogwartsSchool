package com.example.ahs.dto;

import com.example.ahs.model.Avatar;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AvatarDTO {
    private Long id;
    private String filePath;
    private long fileSize;
    private String mediaType;
    private byte[] data;
    private Long studentId;

    public static AvatarDTO fromAvatar (Avatar avatar) {
        AvatarDTO avatarDTO = new AvatarDTO();
        avatarDTO.setId(avatar.getId());
        avatarDTO.setFilePath(avatar.getFilePath());
        avatarDTO.setFileSize(avatar.getFileSize());
        avatarDTO.setMediaType(avatar.getMediaType());
        avatarDTO.setData(avatar.getData());
        avatarDTO.setStudentId(avatar.getStudent().getId());
        return avatarDTO;
    }

    public Avatar toAvatar() {
        Avatar avatar = new Avatar();
        avatar.setId(this.getId());
        avatar.setFilePath(this.getFilePath());
        avatar.setFileSize(this.getFileSize());
        avatar.setMediaType(this.getMediaType());
        avatar.setData(this.getData());
        return avatar;
    }
}
