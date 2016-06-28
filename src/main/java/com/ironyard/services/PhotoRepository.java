package com.ironyard.services;

import com.ironyard.entities.Photo;
import com.ironyard.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jeffryporter on 6/28/16.
 */
public interface PhotoRepository extends CrudRepository
{
    public Iterable<Photo> findByRecipient(User recipient);
}
