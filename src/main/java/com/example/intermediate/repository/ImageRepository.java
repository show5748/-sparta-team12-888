package com.example.intermediate.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;
import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,Long> {

    Optional<Image> findByImgURL(String imgUrl);
}
