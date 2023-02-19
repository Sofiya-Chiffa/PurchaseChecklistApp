package com.ephirium.purchasechecklistapplication;

public class SinglePurchase {
    private String name;
    private int count;
    private float price;
    private CategoryPurchase categoryPurchase;

    public SinglePurchase(){
        name = "Product";
        count = 1;
        price = 1;
        categoryPurchase = new CategoryPurchase();
    }

    public SinglePurchase(String name, int count, float price, CategoryPurchase categoryPurchase){
        this.name = name;
        this.price = price;
        this.count = count;
        this.categoryPurchase = categoryPurchase;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public float getPrice() {
        return price;
    }

    public CategoryPurchase getCategoryPurchase() {
        return categoryPurchase;
    }
}
