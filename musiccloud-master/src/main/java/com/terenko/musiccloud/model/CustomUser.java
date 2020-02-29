package com.terenko.musiccloud.model;

import com.terenko.musiccloud.DTO.AccountDTO;

import lombok.Data;

import javax.persistence.*;

import java.util.ArrayList;

import java.util.List;


@Entity
@Data
public class CustomUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long id;

    private String login;
    private String password;
    private String picUrl;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<Playlist> PlayLists = new ArrayList<>();

    public long getId() {
        return id;
    }

    public CustomUser(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public CustomUser(){}

    public static CustomUser fromDTO(AccountDTO accountDTO) {
        return new CustomUser(accountDTO.getName(), accountDTO.getPictureUrl());
    }

    public List<Playlist> getPlayLists() {
        return PlayLists;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addPlaylist(Playlist pl) {
        PlayLists.add(pl);
    }

    public void deletePlaylist(String uuid) {
        Playlist pl=null;
        for (Playlist p : PlayLists) {
            if (p.getUuid().equals(uuid)) {
                pl=p;
            }
        }
        PlayLists.remove(pl);
    }

    public static class PageCountDTO {
        private final long count;
        private final int pageSize;

        PageCountDTO(long count, int pageSize) {
            this.count = count;
            this.pageSize = pageSize;
        }

        public static PageCountDTO of(long count, int pageSize) {
            return new PageCountDTO(count, pageSize);
        }

        public long getCount() {
            return count;
        }

        public int getPageSize() {
            return pageSize;
        }
    }
}
