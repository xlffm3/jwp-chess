package chess.domain.user;

import chess.domain.piece.TeamType;

import java.util.List;

public class Users {
    private static final int MAXIMUM_USER_COUNTS_FOR_ONE_ROOM = 2;

    private final List<User> users;

    public Users(List<User> users) {
        validateUserCounts(users);
        this.users = users;
    }

    private void validateUserCounts(List<User> users) {
        if (users.size() > MAXIMUM_USER_COUNTS_FOR_ONE_ROOM) {
            throw new IllegalArgumentException("게임에 참여할 수 있는 유저는 2명입니다.");
        }
    }

    public TeamType generateTeamType() {
        if (users.isEmpty()) {
            return TeamType.WHITE;
        }
        User existingUser = users.get(0);
        TeamType teamType = TeamType.valueOf(existingUser.getTeamType());
        return teamType.findOppositeTeam();
    }

    public boolean hasMaximumCountsForGame() {
        return users.size() == MAXIMUM_USER_COUNTS_FOR_ONE_ROOM;
    }

    public boolean hasEqualUser(String password, TeamType teamType) {
        return users.stream()
                .anyMatch(user -> user.hasSameInformation(password, teamType));
    }
}
