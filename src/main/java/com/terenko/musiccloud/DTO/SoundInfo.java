package com.terenko.musiccloud.DTO;

import com.terenko.musiccloud.model.Sound;

public class SoundInfo {
    String name;
    String uuid;
    String aLong;

    public SoundInfo(Sound s) {
        this.name = s.getName();
        this.uuid = s.getUuid();
        try {
            int mili = (int) (s.getTime() / 1000);
            int sec = (mili / 1000) % 60;
            int min = (mili / 1000) / 60;

            this.aLong = (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);
        } catch (NullPointerException e) {
            this.aLong="0";
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getaLong() {
        return aLong;
    }

    public void setaLong(String aLong) {
        this.aLong = aLong;
    }
}
