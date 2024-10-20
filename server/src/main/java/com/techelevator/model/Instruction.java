package com.techelevator.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class Instruction {
    // Properties
    @NotNull
    private int stepNumber;
    @NotBlank
    private String stepText;
    @NotNull
    private int recipeId;

    // Constructors
    public Instruction(){

    }
    public Instruction(int stepNumber, String stepText, int recipeId){
        this.stepNumber = stepNumber;
        this.stepText = stepText;
        this.recipeId = recipeId;
    }

    // Getters & Setters
    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getStepText() {
        return stepText;
    }

    public void setStepText(String stepText) {
        this.stepText = stepText;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    // Methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instruction that = (Instruction) o;
        return getStepNumber() == that.getStepNumber() && getRecipeId() == that.getRecipeId() && Objects.equals(getStepText(), that.getStepText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStepNumber(), getStepText(), getRecipeId());
    }
}
