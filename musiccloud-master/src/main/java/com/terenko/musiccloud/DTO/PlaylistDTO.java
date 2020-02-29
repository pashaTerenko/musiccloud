package com.terenko.musiccloud.DTO;


import com.terenko.musiccloud.Service.Service;
import com.terenko.musiccloud.model.Playlist;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDTO {

    public   String name;
    public String uuid;
    public List<SoundInfo> soundList=new ArrayList<>();
    public PlaylistDTO(){}
    public PlaylistDTO(Playlist p,Service sr) {
        this.name = p.getName();
        this.uuid=p.getUuid();
        p.getSoundList().forEach((x)->soundList.add(new SoundInfo(sr.getSoundByUUID(x))));
    }
    public PlaylistDTO(Playlist p) {
        this.name = p.getName();
        this.uuid=p.getUuid();

    }
    public static Playlist fromDTO(PlaylistDTO pl){
        if(pl.uuid!="")
            return new Playlist(pl.name,pl.uuid);
        else return new Playlist(pl.name);
    }
}
