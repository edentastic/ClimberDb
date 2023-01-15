package com.techelevator.DAO;

import com.techelevator.Model.Climber;

import java.util.List;

public interface ClimberDao {
//  CRUD
    public List<Climber> getAllClimber();
    public Climber insertClimber(Climber climber);
    public Climber getClimber(int climberId);
    public void updateClimber(Climber climber);
    public void deleteClimber(int climberId);
    public void setClimberAvailability(Climber climber);
}
