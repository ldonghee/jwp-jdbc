package next.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import core.jdbc.support.template.JdbcTemplate;
import core.jdbc.support.template.RowMapper;
import next.model.User;

public class UserDao {
    public void insert(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";

        jdbcTemplate.insert(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public void update(User user) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE USERS SET password = ?, name = ?, email = ? WHERE userId = ?";

        jdbcTemplate.update(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }

    public List<User> findAll() throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        RowMapper rowMapper = rs -> new User(rs.getString("userId"), rs.getString("password"), rs.getString("name"), rs.getString("email"));
        String sql = "SELECT userId, password, name, email FROM USERS";

        List<Object> objects = jdbcTemplate.selectAll(sql, rowMapper);
        return objects.stream().map(o -> (User) o).collect(Collectors.toList());
    }

    public User findByUserId(String userId) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        RowMapper rowMapper = rs -> new User(rs.getString("userId"),
                                     rs.getString("password"),
                                     rs.getString("name"),
                                     rs.getString("email"));
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";

        return (User) jdbcTemplate.selectOne(sql, rowMapper, userId);
    }
}
