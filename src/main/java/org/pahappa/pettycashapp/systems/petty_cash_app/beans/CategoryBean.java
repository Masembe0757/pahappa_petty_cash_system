package org.pahappa.pettycashapp.systems.petty_cash_app.beans;

import org.pahappa.pettycashapp.systems.petty_cash_app.dao.CategoryDao;
import org.pahappa.pettycashapp.systems.petty_cash_app.models.Category;
import org.pahappa.pettycashapp.systems.petty_cash_app.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.List;

@SessionScope
@Component
public class CategoryBean implements Serializable {
    private static final long serialVersionUID = 2L;
    private String name;
    private String description;
    private Category selectedCategory;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryDao categoryDao;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(Category selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public String addCategory() {
        String message = categoryService.saveCategory(getName(), getDescription());
        if (message.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "SUCCESS", "Category '" + getName() + "' added successfully")); // Include name
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "FAILED", message));
        }
        return null; // Return null if an error occurs
    }

    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    public void selectCategoryForUpdate(Category category) {
        this.selectedCategory = category;
    }

    public void updateCategory(Category tobeUpdated) {
        if (categoryService.categoryUpdated(tobeUpdated.getId(), tobeUpdated.getName(), tobeUpdated.getDescription())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success: ", "Category Updated!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error occurred", null));
        }
    }

    public void deleteCategory(Category category) {
        if (categoryDao.deleteCategory(category.getId())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Success: ", "Category Deleted!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, null));
        }
    }

}