package com.bartoszswiech.home_inventory_api.beans;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class NutritionalInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "upca")
    @JsonBackReference(value = "product_nutritional")
    private Product product;

    private int calories = 0;
    private double totalFat = 0.0;
    private double saturatedFat = 0.0;
    private double transFat = 0.0;
    private int cholesterol = 0;
    private int sodium = 0;
    private int totalCarbohydrate = 0;
    private int dietaryFiber = 0;
    private int sugars = 0;
    private double protein = 0.0;

    // Vitamins and minerals with default values
    private int vitaminD = 0;
    private int calcium = 0;
    private int iron = 0;
    private int potassium = 0;

    // Default constructor
    public NutritionalInformation() {}

    // Getters and Setters
    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public double getTotalFat() {
        return totalFat;
    }

    public void setTotalFat(double totalFat) {
        this.totalFat = totalFat;
    }

    public double getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(double saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public double getTransFat() {
        return transFat;
    }

    public void setTransFat(double transFat) {
        this.transFat = transFat;
    }

    public int getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(int cholesterol) {
        this.cholesterol = cholesterol;
    }

    public int getSodium() {
        return sodium;
    }

    public void setSodium(int sodium) {
        this.sodium = sodium;
    }

    public int getTotalCarbohydrate() {
        return totalCarbohydrate;
    }

    public void setTotalCarbohydrate(int totalCarbohydrate) {
        this.totalCarbohydrate = totalCarbohydrate;
    }

    public int getDietaryFiber() {
        return dietaryFiber;
    }

    public void setDietaryFiber(int dietaryFiber) {
        this.dietaryFiber = dietaryFiber;
    }

    public int getSugars() {
        return sugars;
    }

    public void setSugars(int sugars) {
        this.sugars = sugars;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public int getVitaminD() {
        return vitaminD;
    }

    public void setVitaminD(int vitaminD) {
        this.vitaminD = vitaminD;
    }

    public int getCalcium() {
        return calcium;
    }

    public void setCalcium(int calcium) {
        this.calcium = calcium;
    }

    public int getIron() {
        return iron;
    }

    public void setIron(int iron) {
        this.iron = iron;
    }

    public int getPotassium() {
        return potassium;
    }

    public void setPotassium(int potassium) {
        this.potassium = potassium;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getId() {
        return id;
    }

    @Override
        public String toString() {
            return "NutritionalInformation(" +
                    "calories=" + calories +
                    ", totalFat=" + totalFat +
                    ", saturatedFat=" + saturatedFat +
                    ", transFat=" + transFat +
                    ", cholesterol=" + cholesterol +
                    ", sodium=" + sodium +
                    ", totalCarbohydrate=" + totalCarbohydrate +
                    ", dietaryFiber=" + dietaryFiber +
                    ", sugars=" + sugars +
                    ", protein=" + protein +
                    ", vitaminD=" + vitaminD +
                    ", calcium=" + calcium +
                    ", iron=" + iron +
                    ", potassium=" + potassium +
                    ')';
        }
    }
