package ru.wishList.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Friends implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId, friendId;
    private String friendName;

    public Friends() {
    }

    public Friends(Long userId, Long friendId, String friendName) {
        this.userId = userId;
        this.friendId = friendId;
        this.friendName = friendName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFriendId() {
        return friendId;
    }

    public void setFriendId(Long friendId) {
        this.friendId = friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }
}
