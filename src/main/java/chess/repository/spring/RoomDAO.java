package chess.repository.spring;

import chess.domain.room.Room;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoomDAO {
    private static final RowMapper<Room> ROW_MAPPER = (resultSet, rowNumber) -> {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        return new Room(id, name);
    };

    private final JdbcTemplate jdbcTemplate;

    public RoomDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Room> findAllRooms() {
        String query = "SELECT * FROM ROOM";
        return jdbcTemplate.query(query, ROW_MAPPER);
    }
}
