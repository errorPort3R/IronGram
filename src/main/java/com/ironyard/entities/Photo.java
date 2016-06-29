package com.ironyard.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by jeffryporter on 6/28/16.
 */
@Entity
@Table(name = "photos")

public class Photo
{
    @Id
    @GeneratedValue
    int id;

    @ManyToOne
    User sender;

    @ManyToOne
    User recipient;

    @Column(nullable=false)
    String filename;

    @Column(nullable=false)
    int lifeInSeconds;

    @Column(nullable=false)
    boolean isPublic;

    LocalDateTime startTime;

    public Photo()
    {
    }

    public Photo(User sender, User recipient, String filename, int lifeInSeconds, boolean isPublic)
    {
        this.sender = sender;
        this.recipient = recipient;
        this.filename = filename;
        this.lifeInSeconds = lifeInSeconds;
        this.isPublic = isPublic;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public User getSender()
    {
        return sender;
    }

    public void setSender(User sender)
    {
        this.sender = sender;
    }

    public User getRecipient()
    {
        return recipient;
    }

    public void setRecipient(User recipient)
    {
        this.recipient = recipient;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public int getLifeInSeconds()
    {
        return lifeInSeconds;
    }

    public void setLifeInSeconds(int lifeInSeconds)
    {
        this.lifeInSeconds = lifeInSeconds;
    }

    public boolean isPublic()
    {
        return isPublic;
    }

    public void setPublic(boolean aPublic)
    {
        isPublic = aPublic;
    }

    public LocalDateTime getStartTime()
    {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime)
    {
        this.startTime = startTime;
    }
}
