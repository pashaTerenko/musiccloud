package com.terenko.musiccloud.DTO;

import com.terenko.musiccloud.Service.FileService;
import com.terenko.musiccloud.model.Sound;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;


@Data
public class SoundDTO {

    String name;
    byte[] data;
  static   FileService fs=new FileService();
    public SoundDTO(String name, byte[] data) {
        this.name = name;
        this.data = data;
    }
    public SoundDTO(){}
    public static SoundDTO toDTO(Sound sound) {

        if(sound.getPath()!=null)
        return new SoundDTO(sound.getName(),fs.getFromCatalog( sound.getPath()));
     else  return new SoundDTO(sound.getName(),null);
    }

    public static Sound fromDTO(SoundDTO s) {

        return new Sound(s.getName(),fs.addToCatalog(s.getData(),s.getName()) );
    }


    public String getName() {
        return name;
    }

    public byte[] getData() {
        return data;
    }

}
