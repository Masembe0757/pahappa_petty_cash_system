package org.pahappa.pettycashapp.systems.petty_cash_app.services;

import org.pahappa.pettycashapp.systems.petty_cash_app.dao.CategoryDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.List;
@SessionScope
@Service
public class CategoryService {

    @Autowired
    CategoryDao categoryDao;

    public String saveCategory(String name, String description) {
        String message = "";
        if (name == null || name.isEmpty() || description == null || description.isEmpty()) {
            message = "Fields Cannot Be Empty";
        } else if (!name.trim().matches("[a-zA-Z0-9\\s-_]+")) {
            message = "Category name can only contain alphanumeric characters, spaces, hyphens, and underscores.";
        } else if (!isDescriptionValid(description)) {
            message = "Description is too Long";
        } else {
            Category returnedCategory = categoryDao.returnCategory(name);
            if (returnedCategory!= null) {
                message ="Category already exists! Enter a new one";
            } else {
                Category category = new Category();
                category.setName(name);
                category.setDescription(description);
                categoryDao.saveCategory(category);
            }
        }
        return message;
    }

    private boolean isDescriptionValid(String description) {
        String[] words = description.trim().split("\\s+");
        return words.length <= 25;
    }

    public List<Category> getCategories(){
        return categoryDao.getCategories();
    }

    public boolean categoryUpdated(int categoryId, String name, String description) {
        return categoryDao.updateCategory(categoryId, name, description);
    }
}