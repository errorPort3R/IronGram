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
import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

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
        ArrayList<Photo> workingFiles = new ArrayList<>();
        workingFiles = (ArrayList<Photo>)photos.findByRecipient(user);
        for(Photo p : workingFiles)
        {
            LocalDateTime timestamp1 = LocalDateTime.now();
            if (p.getStartTime()==null)
            {
                photos.findOne(p.getId()).setStartTime(timestamp1);
            }
            else if (LocalDateTime.now().isAfter(p.getStartTime().plusSeconds(p.getLifeInSeconds())))
            {
                File f = new File("public/photos" + p.getFilename());
                f.delete();
                photos.delete(p.getId());
            }
        }
        return photos.findByRecipient(user);
    }

    @RequestMapping(path = "/photos-public", method = RequestMethod.GET)
    public Iterable<Photo> getPublicPhotos(String username) throws Exception
    {
        User user = users.findFirstByName(username);
        return photos.findBySenderAndIsPublic(user, true);
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public User getUser(HttpSession session)
    {
        String username = (String) session.getAttribute("username");
        if (username == null)
        {
            return null;
        }
        else
        {
            return users.findFirstByName(username);
        }
    }
}
