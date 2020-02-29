package com.terenko.musiccloud.Repository;

import com.terenko.musiccloud.model.CustomUser;
import com.terenko.musiccloud.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
Playlist findByUuid(String uuid);


}
