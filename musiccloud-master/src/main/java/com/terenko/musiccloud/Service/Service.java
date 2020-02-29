package com.terenko.musiccloud.Service;

import com.terenko.musiccloud.DTO.PlaylistDTO;
import com.terenko.musiccloud.DTO.SoundDTO;
import com.terenko.musiccloud.DTO.SoundInfo;
import com.terenko.musiccloud.Exeption.AlreadyExistExeption;
import com.terenko.musiccloud.Repository.PlaylistRepository;
import com.terenko.musiccloud.Repository.SoundRepository;
import com.terenko.musiccloud.Repository.UserRepository;
import com.terenko.musiccloud.model.CustomUser;
import com.terenko.musiccloud.model.Playlist;
import com.terenko.musiccloud.model.Sound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@org.springframework.stereotype.Service
public class Service implements Serviceinterface {
    private static Logger log = Logger.getLogger(Service.class.getName());
    @Autowired
    SoundRepository sr;
    @Autowired
    UserRepository ur;
    @Autowired
    PlaylistRepository pr;
    final String MAINPLUUID="00000001";
   static  Playlist mPL;
    @PostConstruct
   public void mainPL(){
        if(pr.findByUuid(MAINPLUUID)!=null){
            mPL=pr.findByUuid(MAINPLUUID);
        }else {
            mPL=new Playlist("Main",MAINPLUUID);
            pr.save(mPL);
        }
   }
    public List<SoundInfo> getAllSounds(Pageable page) {
        List<SoundInfo> list = new ArrayList<>();
        for (Sound s : sr.findAll(page)) {
            list.add(new SoundInfo(s));
        }

        return list;
    }
    @Override
    public List<SoundInfo> getAllSoundsByUUID(List<String> stringList,Pageable page) {
        List<SoundInfo> list = new ArrayList<>();
        for (Sound s : sr.findAllByUuid(stringList)) {
            list.add(new SoundInfo(s));
        }
        log.info(list.toString());
        return list;
    }
    @Override
    public List<PlaylistDTO> getPlaylists(CustomUser user) {
        List<PlaylistDTO> dto = new ArrayList<>();
        user.getPlayLists().forEach((x) -> dto.add(new PlaylistDTO(x)));
        return dto;
    }
    public  int getCountPL(String uuid){
        Playlist pl =pr.findByUuid(uuid);
        return pl.getSoundList().size();
    }
public int getCountALL(){
        return sr.findAll().size();
}
    @Transactional
    public void addPlaylist(CustomUser user, String plName) {

        user.addPlaylist(new Playlist(plName));
        ur.save(user);

    }

    @Override
    public Playlist getPlaylist(String uuid) {
        return pr.findByUuid(uuid);
    }

    @Override
    @Transactional
    public void deletePlaylist(CustomUser user, String uuid) {
        user.deletePlaylist(uuid);
        ur.save(user);
    }

    @Override
    public List<SoundDTO> getSoundsFromPlaylist(Playlist p) {
        List<SoundDTO> soundDTOList = new ArrayList<>();
        p.getSoundList().forEach((x) -> soundDTOList.add(SoundDTO.toDTO(getSoundByUUID(x))));
        return soundDTOList;
    }

    @Override
    public SoundDTO getSoundDTO(String name) {
        return SoundDTO.toDTO(sr.findByName(name));
    }

    @Override
    public Sound getSound(String name) {
        return sr.findByName(name);
    }

    @Override
    public Sound getSoundByUUID(String uuid) {
        return sr.findByUuid(uuid);
    }

    @Override
    @Transactional
    public void addSound(SoundDTO sound) {
        Sound s = SoundDTO.fromDTO(sound);
        sr.save(s);
        try {
            addSoundToPlaylist(s,pr.findByUuid(MAINPLUUID));
        } catch (AlreadyExistExeption alreadyExistExeption) {

        }
        log.info("sound "+s.getName()+" have added");
    }

    @Override
    @Transactional
    public void addSoundToPlaylist(Sound s, Playlist pl) throws AlreadyExistExeption {

        pl.addSound(s);
        pr.save(pl);
    }

    @Override
    @Transactional
    public void deleteSoundFromPlaylist(Playlist pl, String uuid) {
        pl.deleteSound(getSoundByUUID(uuid));
        pr.save(pl);
    }

    @Override
    public List<SoundInfo> getBySearch(String pattern, Pageable page) {
        List<SoundInfo> list = new ArrayList<>();
        for (Sound s : sr.findByNameContainingIgnoreCase(pattern,page)) {
            list.add(new SoundInfo(s));
        }
        log.info("search result: "+list.toString());
        return list;
    }
/*    @Transactional
    public void test(){
        try {
            InputStream fin=new FileInputStream("C:\\Users\\teren\\Desktop\\fall-out-boy-centuries.mp3");
            Sound s= new Sound("sound1",fin.readAllBytes());
            Sound s1= new Sound("sound2",fin.readAllBytes());
            sr.save(s);
            sr.save(s1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

}
