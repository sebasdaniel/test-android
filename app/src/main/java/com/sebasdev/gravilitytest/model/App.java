package com.sebasdev.gravilitytest.model;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sebasdev.gravilitytest.R;

/**
 * Created by m4605 on 16/03/16.
 * App model object
 */
public class App {

    private String name;
    private String author;
    private String price;
    private String description;
    private String image;
    private Bitmap imageBitmap;
    private Category category;

    public App() {
        imageBitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.img_not_found);
    }

    public App(String name, String author, String price, String description, String image, Bitmap imageBitmap, Category category) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.description = description;
        this.image = image;
        this.imageBitmap = imageBitmap;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
