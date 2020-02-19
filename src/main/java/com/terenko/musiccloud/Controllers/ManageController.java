package com.terenko.musiccloud.Controllers;

import com.terenko.musiccloud.DTO.PlaylistDTO;
import com.terenko.musiccloud.DTO.RequestDTO;
import com.terenko.musiccloud.DTO.SoundDTO;
import com.terenko.musiccloud.Exeption.AlreadyExistExeption;
import com.terenko.musiccloud.Service.Service;
import com.terenko.musiccloud.Service.UserService;
import com.terenko.musiccloud.model.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class ManageController {
    @Autowired
    Service sr;
    @Autowired
    UserService ur;
    final int  PAGE=10;
    @PostMapping("addpl")
    public ResponseEntity createPlaylist(@RequestParam String plName) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser dbUser = ur.getUserByLogin(login);

        sr.addPlaylist(dbUser,plName);


        return new ResponseEntity<>( HttpStatus.OK);
    }
    @PostMapping("deletepl")
    public ResponseEntity deletePlaylist(@RequestParam String uuid){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser dbUser = ur.getUserByLogin(login);
        sr.deletePlaylist(dbUser,uuid);
        return new ResponseEntity<>( HttpStatus.OK);

    }
    @PostMapping("addstp")
    public ResponseEntity<RequestDTO> addSoundToPlaylist(@RequestParam String plUUID, String soundUUID){
        try {
            sr.addSoundToPlaylist(sr.getSoundByUUID(soundUUID),sr.getPlaylist(plUUID));
        } catch (AlreadyExistExeption alreadyExistExeption) {
            return new ResponseEntity<RequestDTO>(new RequestDTO(400,alreadyExistExeption.getMessage()),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<RequestDTO>(new RequestDTO(200,""),HttpStatus.OK);
    }
    @PostMapping("delsound")
    public  ResponseEntity deleteSoundfromPlaylist(@RequestParam String plUUID ,String soundUUID ){
        sr.deleteSoundFromPlaylist(sr.getPlaylist(plUUID),soundUUID);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("getplaylist")
    public List<PlaylistDTO> getPlaylists(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser dbUser = ur.getUserByLogin(login);
        return sr.getPlaylists(dbUser);
    }
    @GetMapping("getplaylistbyuuid")
    public PlaylistDTO getPlaylist(@RequestParam String uuid){
        return new PlaylistDTO(sr.getPlaylist(uuid),sr);
    }
}

