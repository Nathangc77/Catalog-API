package com.moreira.catalog.services;

import com.moreira.catalog.dtos.CategoryDTO;
import com.moreira.catalog.entities.Category;
import com.moreira.catalog.repositories.CategoryRepository;
import com.moreira.catalog.services.exceptions.DatabaseException;
import com.moreira.catalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(Pageable pageable) {
        Page<Category> result = repository.findAll(pageable);
        return result.map(CategoryDTO::new);
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Category entity = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found"));
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO insert (CategoryDTO dto) {
        Category entity = new Category();
        entity.setName(dto.getName());

        entity = repository.save(entity);

        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        try {
            Category entity = repository.getReferenceById(id);
            entity.setName(dto.getName());

            entity = repository.save(entity);

            return new CategoryDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Entity not found");

        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Data Integrity Fail");
        }
    }
}
