package com.ironyard.controllers;

import com.ironyard.entities.Photo;
import com.ironyard.entities.User;
import com.ironyard.services.PhotoRepository;
import com.ironyard.services.UserRepository;
import com.ironyard.utilities.PasswordStorage;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

/**
 * Created by jeffryporter on 6/28/16.
 */
@RestController
public class IronGramRestController
{

    @Autowired
    UserRepository users;

    @Autowired
    PhotoRepository photos;


    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public User login(@RequestBody User user, HttpSession session) throws Exception
    {
        User userFromDb = users.findFirstByName(user.getName());
        if(userFromDb == null)
        {
            user.setPassword(PasswordStorage.createHash(user.getPassword()));
            users.save(user);
        }
        else if (!PasswordStorage.verifyPassword(user.getPassword(), userFromDb.getPassword()))
        {
            throw new Exception("Wrong Password!");
        }

        session.setAttribute("username", user.getName());
        return user;
    }

    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public void logout(HttpSession session) throws Exception
    {
        session.invalidate();
    }


    @RequestMapping(path = "/photos", method = RequestMethod.GET)
    public Iterable<Photo> getPhotos(HttpSession session) throws Exception
    {
        String username = (String) session.getAttribute("username");
        User user = users.findFirstByName(username);
        return photos.findByRecipient(user);
    }

}
