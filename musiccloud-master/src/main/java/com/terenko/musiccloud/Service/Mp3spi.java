package com.terenko.musiccloud.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tritonus.share.sampled.file.TAudioFileFormat;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Map;
import java.util.Objects;

@Component
public class Mp3spi {

    public static Double getDurationWithMp3Spi(String path) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
     File file =new File(path);
        AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
        if (fileFormat instanceof TAudioFileFormat) {
            Map<?, ?> properties = ((TAudioFileFormat) fileFormat).properties();
            String key = "duration";
            Long microseconds = (Long) properties.get(key);
            int mili = (int) (microseconds / 1000);
            int sec = (mili / 1000) % 60;
            int min = (mili / 1000) / 60;
            return (double)microseconds;
        } else {
            throw new UnsupportedAudioFileException();
        }
    }

}
