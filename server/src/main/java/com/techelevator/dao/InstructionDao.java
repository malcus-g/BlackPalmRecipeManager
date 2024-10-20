package com.techelevator.dao;

import com.techelevator.model.Instruction;
import com.techelevator.model.Recipe;

import java.util.List;
import java.util.Map;

public interface InstructionDao {

    // Get a single instruction from a recipe
    Instruction getInstruction(int recipeId, int stepNumber);
    // Get all of the instructions for a recipe
    List<Instruction> getRecipeInstructions(int recipeId);

    // Create a new instruction
    void createInstruction(Instruction instruction);

    // Update existing instruction
    Instruction updateInstruction(Instruction instruction);

    // Update instructions for a specific recipe
    int updateRecipeInstructions(int recipeId, List<Instruction> instructions);

    // Delete an existing instruction by recipe and step number
    int deleteInstruction(int recipeId, int stepNumber);
}
