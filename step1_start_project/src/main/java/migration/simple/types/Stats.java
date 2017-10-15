package migration.simple.types;

public class Stats {
    private Integer userCount;
    private User oldestUser;
    private User youngestUser;

    public Stats(Integer userCount, User oldestUser, User youngestUser) {
        this.userCount = userCount;
        this.oldestUser = oldestUser;
        this.youngestUser = youngestUser;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public User getOldestUser() {
        return oldestUser;
    }

    public User getYoungestUser() {
        return youngestUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Stats stats = (Stats) o;

        if (userCount != null ? !userCount.equals(stats.userCount) : stats.userCount != null) return false;
        if (oldestUser != null ? !oldestUser.equals(stats.oldestUser) : stats.oldestUser != null) return false;
        return youngestUser != null ? youngestUser.equals(stats.youngestUser) : stats.youngestUser == null;
    }

    @Override
    public int hashCode() {
        int result = userCount != null ? userCount.hashCode() : 0;
        result = 31 * result + (oldestUser != null ? oldestUser.hashCode() : 0);
        result = 31 * result + (youngestUser != null ? youngestUser.hashCode() : 0);
        return result;
    }
}
