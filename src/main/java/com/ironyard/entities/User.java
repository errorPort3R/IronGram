package com.ironyard.entities;

import javax.persistence.*;

/**
 * Created by jeffryporter on 6/28/16.
 */

@Entity
@Table(name = "users")

public class User
{
    @Id
    @GeneratedValue
    int id;

    @Column(nullable=false)
    String name;

    @Column(nullable=false)
    String password;

    public User()
    {
    }

    public User(String name, String password)
    {
        this.name = name;
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}

