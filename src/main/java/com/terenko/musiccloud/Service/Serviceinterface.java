package com.terenko.musiccloud.Service;

import com.terenko.musiccloud.DTO.PlaylistDTO;
import com.terenko.musiccloud.DTO.SoundDTO;
import com.terenko.musiccloud.DTO.SoundInfo;
import com.terenko.musiccloud.Exeption.AlreadyExistExeption;
import com.terenko.musiccloud.model.CustomUser;
import com.terenko.musiccloud.model.Playlist;
import com.terenko.musiccloud.model.Sound;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface Serviceinterface {
    List<SoundInfo> getAllSounds(Pageable pageable);

    List<PlaylistDTO> getPlaylists(CustomUser user);

    void addPlaylist(CustomUser user, String plName);

    Playlist getPlaylist(String uuid);

    void deletePlaylist(CustomUser user, String uuid);

    List<SoundDTO> getSoundsFromPlaylist(Playlist p);
    public List<SoundInfo> getAllSoundsByUUID(List<String> stringList,Pageable page);


    SoundDTO getSoundDTO(String name);

    Sound getSound(String name);

    Sound getSoundByUUID(String uuid);

    void addSound(SoundDTO sound);

    void addSoundToPlaylist(Sound s, Playlist pl)throws AlreadyExistExeption;

    void deleteSoundFromPlaylist(Playlist pl, String uuid);

}
