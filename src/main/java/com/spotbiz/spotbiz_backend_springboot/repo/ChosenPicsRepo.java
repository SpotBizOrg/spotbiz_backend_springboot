package com.spotbiz.spotbiz_backend_springboot.repo;

import com.spotbiz.spotbiz_backend_springboot.entity.ChosenPics;
import com.spotbiz.spotbiz_backend_springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChosenPicsRepo extends JpaRepository<ChosenPics, Integer> {


    Optional<ChosenPics> findChosenPicsByUser(User user);
}
