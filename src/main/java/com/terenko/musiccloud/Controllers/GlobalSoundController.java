package com.terenko.musiccloud.Controllers;

import com.terenko.musiccloud.DTO.RequestDTO;
import com.terenko.musiccloud.DTO.SoundDTO;
import com.terenko.musiccloud.DTO.SoundInfo;
import com.terenko.musiccloud.Service.FileService;
import com.terenko.musiccloud.Service.Service;
import com.terenko.musiccloud.model.CustomUser;
import com.terenko.musiccloud.model.Sound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class GlobalSoundController {
    @Autowired
    FileService fs;
    private static final int PAGE_SIZE = 5;
    final int  PAGE=10;
    @Autowired
    Service sr;
    @GetMapping("all")
    public List<SoundInfo> getAllSound(@RequestParam(required = false, defaultValue = "0")int page){

        return sr.getAllSounds( PageRequest.of(page, PAGE, Sort.Direction.DESC, "id"));

    }
    @GetMapping("getbyuuid")
    public List<SoundInfo> getSoundbyUUID(@RequestParam(required = false, defaultValue = "0")int page,@RequestBody List<String> uuid){

        return sr.getAllSoundsByUUID(uuid, PageRequest.of(page, PAGE_SIZE, Sort.Direction.DESC, "id"));

    }
    @PostMapping("add")
    public ResponseEntity<RequestDTO> addSound(@RequestBody SoundDTO d){
        sr.addSound(d);
        return new ResponseEntity<RequestDTO>(new RequestDTO(HttpStatus.OK.value(),""),HttpStatus.OK);
    }

    @GetMapping("get")
    public  ResponseEntity<byte[]> getSound(@RequestParam String uuid, HttpServletResponse response){
        Sound s =sr.getSoundByUUID(uuid);
        try {
            byte[]data=fs.getFromCatalog(s.getPath());
            response.setContentType("audio/mp3");
            response.setHeader("Content-Disposition", "attachment; filename=\"" +s.getName()+ "\"");
            response.setContentLength((int)data.length);
            response.getOutputStream().write(data);
            response.flushBuffer();

        } catch (Exception ignored) {
        }
        return null;
    }

    @GetMapping("getInfo")
    public SoundInfo getSoundInfo(@RequestParam String uuid) {
        Sound s =sr.getSoundByUUID(uuid);
        return new SoundInfo(s);
    }

    private long getPageCount(List<?> list) throws  NullPointerException{
        long totalCount = list.size();
        return (totalCount / PAGE) + ((totalCount % PAGE > 0) ? 1 : 0);
    }
    @GetMapping("count")
    public CustomUser.PageCountDTO getCount(@RequestParam(defaultValue = "0") String uuid, @RequestParam(defaultValue = "0")int l){
        try {
            switch (l){
                case 0:return  CustomUser.PageCountDTO.of(sr.getCountPL(uuid),PAGE);

                case 1:return  CustomUser.PageCountDTO.of(sr.getCountALL(),PAGE);



                default:return null;
            }
        } catch (NullPointerException e) {
            return null;
        }
    }

}
