package org.pahappa.pettycashapp.systems.petty_cash_app.services;

import org.pahappa.pettycashapp.systems.petty_cash_app.dao.CategoryDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            Category category = new Category();
            category.setName(name);
            category.setDescription(description);
            categoryDao.saveCategory(category);
        }
        return message;
    }

    private boolean isDescriptionValid(String description) {
        String[] words = description.trim().split("\\s+");
        return words.length <= 25;
    }
}