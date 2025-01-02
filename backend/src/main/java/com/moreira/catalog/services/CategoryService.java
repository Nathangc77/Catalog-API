package com.moreira.catalog.services;

import com.moreira.catalog.entities.Category;
import com.moreira.catalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<Category> findAll() {
        List<Category> result = repository.findAll();
        return result;
    }
}
