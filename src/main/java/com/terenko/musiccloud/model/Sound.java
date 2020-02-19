package com.terenko.musiccloud.model;

import com.terenko.musiccloud.Service.FileService;
import com.terenko.musiccloud.Service.Mp3spi;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.*;

@Entity

@Table(name="Sounds")

public class Sound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "sound_id") long id;
    String uuid;
    String name;

    String path;
    Double time;

    public Sound(String name,String path) {
        this.name = name;
        this.path = path;
        this.uuid=UUID.randomUUID().toString();
        try {
            this.time=Mp3spi.getDurationWithMp3Spi(path);
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Sound(){}

    public Sound(String name, String path,String uuid) {
        this.name = name;
        this.path = path;
        this.uuid=uuid;
        try {
            this.time=Mp3spi.getDurationWithMp3Spi(path);
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getUuid() {
        return uuid;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }
}
