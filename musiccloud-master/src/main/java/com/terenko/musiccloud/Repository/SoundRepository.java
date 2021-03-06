package com.terenko.musiccloud.Repository;


import com.terenko.musiccloud.model.CustomUser;
import com.terenko.musiccloud.model.Playlist;
import com.terenko.musiccloud.model.Sound;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface SoundRepository extends JpaRepository<Sound, Long> {

Sound findByName(String name);
Sound findByUuid(String uuid);
List<Sound> findAllByUuid(List<String> uuid);
List<Sound> findByNameContainingIgnoreCase(String name,Pageable page);
}
