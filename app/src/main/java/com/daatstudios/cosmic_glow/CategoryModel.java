package com.daatstudios.cosmic_glow;

public class CategoryModel
{
    private String CategoryIconlink;
    private String Category_name;

    public CategoryModel(String categoryIconlink, String category_name) {
        CategoryIconlink = categoryIconlink;
        Category_name = category_name;
    }

    public String getCategoryIconlink() {
        return CategoryIconlink;
    }

    public void setCategoryIconlink(String categoryIconlink) {
        CategoryIconlink = categoryIconlink;
    }

    public String getCategory_name() {
        return Category_name;
    }

    public void setCategory_name(String category_name) {
        Category_name = category_name;
    }
}
