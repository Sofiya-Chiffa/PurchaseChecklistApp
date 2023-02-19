package com.ephirium.purchasechecklistapplication;

public class CategoryPurchase {
    private String name;

    public CategoryPurchase(){
        name = "Default";
    }

    public CategoryPurchase(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
