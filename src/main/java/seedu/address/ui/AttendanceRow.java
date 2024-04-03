package seedu.address.ui;

/**
 * Represents each row of attendance data.
 */
public class AttendanceRow {
    private final String groupName;
    private final String[] attendance;

    /**
     * Constructs an attendance row.
     * @param groupName A group name.
     * @param attendance A String[] of the semester's attendance.
     */
    public AttendanceRow(String groupName, String[] attendance) {
        this.groupName = groupName;
        this.attendance = attendance;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getAttendance(int weekNumber) {
        if (weekNumber > 0 && weekNumber <= attendance.length) {
            return attendance[weekNumber - 1];
        } else {
            return "";
        }
    }
}
