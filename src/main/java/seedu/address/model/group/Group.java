package seedu.address.model.group;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Group in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidGroupName(String)}
 */
public class Group {

    public static final String MESSAGE_CONSTRAINTS = "Groups names should be in correct format with 2 digit number. "
        + "EX) g/TUT04, g/LAB10, g/REC09>. Link should be a valid Telegram invite link.";
    public static final String WEEK_MESSAGE_CONSTRAINTS = "Week number should be 2 digit integer. ";
    public static final String ATTENDANCE_MESSAGE_CONSTRAINTS = "Attendance should be A or P. ";
    public static final String GROUP_NAME_VALIDATION_REGEX = "^(TUT|LAB|REC)\\d{2}$";
    public static final String LINK_VALIDATION_REGEX = "https://t\\.me/[A-Za-z0-9_]+";
    public static final String ATTENDANCE_VALIDATION_REGEX = "^[AP]$";
    public static final String WEEK_VALIDATION_REGEX = "^[1-9]\\d?$";

    public final String groupName;
    public final String telegramLink;
    public final List<String> attendance;

    /**
     * Constructs a {@code Group}.
     *
     * @param groupName A valid group name.
     */
    public Group(String groupName) {
        requireNonNull(groupName);
        checkArgument(isValidGroupName(groupName), MESSAGE_CONSTRAINTS);
        this.groupName = groupName;
        this.telegramLink = "";
        this.attendance = initializeAttendance();
    }

    /**
     * Constructs a {@code Group}.
     *
     * @param groupName A valid group name.
     */
    public Group(String groupName, String link) {
        requireNonNull(groupName);
        checkArgument(isValidGroupName(groupName), MESSAGE_CONSTRAINTS);
        this.groupName = groupName;
        this.telegramLink = link;
        this.attendance = initializeAttendance();
    }

    /**
     * Converts the boolean attendance array into a list of strings.
     *
     * @return A list of strings representing attendance where "1" represents present and "0" represents absent.
     */
    private List<String> initializeAttendance() {
        List<String> attendanceList = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            attendanceList.add("_");
        }
        return attendanceList;
    }

    public void markAttendance(Integer week, String update) {
        this.attendance.set(week - 1, update);
    }

    /**
     * Returns true if a given string is a valid group name.
     */
    public static boolean isValidGroupName(String test) {
        return test.matches(GROUP_NAME_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid link.
     */
    public static boolean isValidLink(String test) {
        return test.matches(LINK_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid week.
     */
    public static boolean isValidWeek(String test) {
        return test.matches(WEEK_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid attendance.
     */
    public static boolean isValidAttendance(String test) {
        return test.matches(ATTENDANCE_VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Group)) {
            return false;
        }

        Group otherGroup = (Group) other;
        return groupName.equals(otherGroup.groupName);
    }

    /**
     * Returns true if both groups have the same name.
     * This defines a weaker notion of equality between two groups.
     */
    public boolean isSameGroup(Group otherGroup) {
        if (otherGroup == this) {
            return true;
        }

        return otherGroup != null && otherGroup.groupName.equals(this.groupName);
    }

    @Override
    public int hashCode() {
        return groupName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + groupName + ']';
    }
}
