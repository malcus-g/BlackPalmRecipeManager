package com.techelevator.controller;

import com.techelevator.exception.DaoException;
import com.techelevator.exception.ServiceException;
import com.techelevator.model.Collection;
import com.techelevator.model.Recipe;
import com.techelevator.service.CollectionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping(path="/collections")
public class CollectionController {

    private CollectionService collectionService;

    public CollectionController(CollectionService collectionService){
        this.collectionService = collectionService;
    }

    @GetMapping
    public List<Collection> getCollections(){
        try{
            return collectionService.getCollections();
        }catch( ServiceException | DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(path="/{id}")
    public Collection getCollectionById(@PathVariable int id){
        try{
            return collectionService.getCollectionById(id);
        }catch( ServiceException | DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(path="/{id}/recipes")
    public List<Recipe> getCollectionRecipes(@PathVariable int id){
        try{
            return collectionService.getCollectionRecipes(id);
        }catch( ServiceException | DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Collection createCollection(@Valid @RequestBody Collection collection){
        try{
            return collectionService.createCollection(collection);
        }catch( ServiceException | DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteCollection(@PathVariable int id){
        try{
            collectionService.deleteCollection(id);
        }catch( ServiceException | DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
