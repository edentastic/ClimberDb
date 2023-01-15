package com.techelevator.DAO;

import com.techelevator.Model.Climber;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcClimberDao implements ClimberDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcClimberDao(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Climber> getAllClimber() {
        List<Climber> climbers = new ArrayList<>();
        String sql = "SELECT climber_id, climber_name, is_injured " +
                "FROM climber " +
                "ORDER BY climber_id";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()){
            climbers.add(mapRowToClimber(results));
        }
        return climbers;
    }

    @Override
    public Climber insertClimber(Climber climber) {
        String sql = "INSERT INTO climber (climber_name, is_injured) " +
                "VALUES (?,?) RETURNING climber_id";
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, climber.getClimberName(),climber.isInjured());
        return getClimber(newId);
    }

    @Override
    public Climber getClimber(int climberId) {
        Climber climber = null;
        String sql = "SELECT climber_id, climber_name, is_injured " +
                "FROM climber " +
                "WHERE climber_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, climberId);
        if(results.next()){
            climber = mapRowToClimber(results);
        }
        return climber;
    }

    @Override
    public void updateClimber(Climber climber) {
        String sql = "UPDATE climber SET climber_name=?, is_injured=? " +
                "WHERE climber_id=?";
        jdbcTemplate.update(sql, climber.getClimberName(), climber.isInjured(), climber.getClimberId());
    }

    @Override
    public void deleteClimber(int climberId) {
        String sql = "DELETE FROM climber WHERE climber_id = ?";
        jdbcTemplate.update(sql, climberId);
    }

    public void setClimberAvailability(Climber climber){
        String sql = "INSERT INTO climber_availability (day, climber_id) " +
                "VALUES (?,?)";
        for(String day : climber.getDaysAvailable()){
            jdbcTemplate.update(sql, day, climber.getClimberId());
        }
    }

    public Climber mapRowToClimber(SqlRowSet results){
        int id = results.getInt("climber_id");
        String name = results.getString("climber_name");
        boolean isInjured = results.getBoolean("is_injured");
        return new Climber(id,name, isInjured);
    }
}
