package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Instruction;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;


@Repository
public class JdbcInstructionDao implements InstructionDao{

    private final JdbcTemplate jdbcTemplate;

    public JdbcInstructionDao(DataSource datasource){
        this.jdbcTemplate = new JdbcTemplate(datasource);
    }

    @Override
    public Instruction getInstruction(int recipeId, int stepNumber){
        Instruction instruction = null;
        String sql = "SELECT recipe_id, step_number, step_text " +
                        "FROM instruction WHERE recipe_id = ? AND step_number = ?;";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, recipeId, stepNumber);
            if (results.next()) {
                instruction = mapRowToInstruction(results);
            }
            return instruction;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database");
        }
    }

    @Override
    public List<Instruction> getRecipeInstructions(int recipeId) {
       List<Instruction> instructions = new ArrayList<>();
       String sql = "SELECT recipe_id, step_number, step_text FROM instruction WHERE recipe_id = ?;";
       try{
           SqlRowSet results = jdbcTemplate.queryForRowSet(sql, recipeId);
           while(results.next()){
               instructions.add(mapRowToInstruction(results));
           }
           return instructions;
       }catch(CannotGetJdbcConnectionException e){
           throw new DaoException("Cannot connect to the database", e);
       }
    }

    @Override
    public void createInstruction(Instruction instruction) {
        Instruction newInstruction = null;
        String sql = "INSERT INTO instruction (recipe_id, step_number, step_text) VALUES (?, ?, ?);";
        try{
            jdbcTemplate.update(sql, instruction.getRecipeId(), instruction.getStepNumber(), instruction.getStepText());
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }catch(DataIntegrityViolationException e){
            throw new DaoException("Error creating instruction - Data integrity violation", e);
        }
    }

    @Override
    public Instruction updateInstruction(Instruction instruction) {
        return null;
    }

    @Override
    public int updateRecipeInstructions(int recipeId, List<Instruction> updatedInstructions) {
        try{
            String sql = "UPDATE instruction SET step_text = ? WHERE recipe_id = ? AND step_number = ?;";
            int rowsAffected = 0;
            for(Instruction instruction : updatedInstructions){
                jdbcTemplate.update(sql, instruction.getStepText(), recipeId, instruction.getStepNumber());
                rowsAffected++;
            }
            return rowsAffected;
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }catch(DataIntegrityViolationException e){
            throw new DaoException("Error updating recipe instructions - Data integrity violation", e);
        }
    }

    @Override
    public int deleteInstruction(int recipeId, int stepNumber) {
        String sql = "DELETE FROM instruction WHERE recipe_id = ? AND step_number = ?;";
        try{
            return jdbcTemplate.update(sql, recipeId, stepNumber);
        }catch(CannotGetJdbcConnectionException e){
            throw new DaoException("Cannot connect to the database", e);
        }catch(DataIntegrityViolationException e){
            throw new DaoException("Error deleting instruction - Data integrity violation");
        }
    }

    public Instruction mapRowToInstruction(SqlRowSet rowSet){
        Instruction instruction = new Instruction();
        instruction.setRecipeId(rowSet.getInt("recipe_id"));
        instruction.setStepNumber(rowSet.getInt("step_number"));
        instruction.setStepText(rowSet.getString("step_text"));
        return instruction;
    }
}
