package com.sahil.blog.repositories;



import org.springframework.data.jpa.repository.JpaRepository;

import com.sahil.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
