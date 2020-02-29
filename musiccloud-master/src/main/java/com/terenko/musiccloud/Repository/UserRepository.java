package com.terenko.musiccloud.Repository;


import com.terenko.musiccloud.model.CustomUser;
import com.terenko.musiccloud.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<CustomUser, Long> {
    CustomUser findByLogin(@Param("login") String login);
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM CustomUser u WHERE u.login = :login")
    boolean existsByLogin(@Param("login") String login);
}
