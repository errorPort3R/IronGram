package com.ironyard.controllers;

import com.ironyard.entities.Photo;
import com.ironyard.entities.User;
import com.ironyard.services.PhotoRepository;
import com.ironyard.services.UserRepository;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;

/**
 * Created by jeffryporter on 6/28/16.
 */
@Controller
public class IronGramController
{
    @Autowired
    UserRepository users;

    @Autowired
    PhotoRepository photos;

    @PostConstruct
    public void init() throws SQLException
    {
        Server.createWebServer().start();
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String upload(MultipartFile file, String receiver, HttpSession session, String is_public, String secs) throws Exception
    {
        if (file.getContentType().toString().contains("image"))
        {
            Boolean isPublic;
            int intSecs;
            String username = (String) session.getAttribute("username");
            User sender = users.findFirstByName(username);
            User rec = users.findFirstByName(receiver);

            if (sender == null || rec == null)
            {
                throw new Exception("Can't find sender or receiver!");
            }

            File dir = new File("public/photos");
            dir.mkdirs();

            File photoFile = File.createTempFile("photo", file.getOriginalFilename(), dir);
            FileOutputStream fos = new FileOutputStream(photoFile);

            System.out.println(file.getContentType().toString());
            fos.write(file.getBytes());
            if (secs == "[0-9}+")
            {
                intSecs = Integer.valueOf(secs);
            } else
            {
                intSecs = 10;
            }
            if (is_public != null && is_public.equals("on"))
            {
                isPublic = true;
            } else
            {
                isPublic = false;
            }
            Photo photo = new Photo(sender, rec, photoFile.getName(), intSecs, isPublic);
            photos.save(photo);
        }
        else
        {
            System.err.printf("%s is not an known image format!", file.getName());
        }
        return "redirect:/";
    }


}
