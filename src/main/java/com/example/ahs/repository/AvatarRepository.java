package com.example.ahs.repository;


import com.example.ahs.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findAvatarByStudentId(Long studentId);
}
