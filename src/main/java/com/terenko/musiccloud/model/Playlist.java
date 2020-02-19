package com.terenko.musiccloud.model;

import com.terenko.musiccloud.DTO.SoundInfo;
import com.terenko.musiccloud.Exeption.AlreadyExistExeption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Playlist {
    @Id
    @GeneratedValue
    long playliistId;
    String name;
    String uuid;

    @ElementCollection
    List<String> soundList = new ArrayList<>();


    public Playlist(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID().toString();
    }

    public Playlist(){}
    public Playlist(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public long getId() {
        return playliistId;
    }

    public void setId(long id) {
        this.playliistId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSoundList() {
        return soundList;
    }

    public void addSound(Sound s)throws AlreadyExistExeption {
        if (!soundList.contains(s.getUuid()))
        soundList.add(s.getUuid());
        else throw new AlreadyExistExeption();
    }

    public void deleteSound(Sound s) {
        soundList.remove(s.getUuid());
    }
}
