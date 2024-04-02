package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonAttendanceList extends UiPart<Region> {

    private static final String FXML = "PersonAttendanceList.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private FlowPane groups;

    @FXML
    private TableView<AttendanceRow> attendanceTable;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonAttendanceList(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);

        // Create a TableColumn for the group name
        TableColumn<AttendanceRow, String> groupNameColumn = new TableColumn<>("Group");
        groupNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().groupName));
        attendanceTable.getColumns().add(0, groupNameColumn);
        groupNameColumn.getStyleClass().add("group-column");

        // Set cell value factory for the first column to show group names
        groupNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().groupName));

        // Create columns for the table
        for (int i = 1; i <= 13; i++) {
            TableColumn<AttendanceRow, String> column = new TableColumn<>("Week " + i);
            int weekNumber = i;
            column.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getAttendance(weekNumber)));
            attendanceTable.getColumns().add(column);
        }

        // Add attendance data to the table
        List<AttendanceRow> attendanceRows = new ArrayList<>();
        person.getGroups().forEach(group -> {
            String[] attendanceArray = group.attendance.toArray(new String[0]);
            attendanceRows.add(new AttendanceRow(group.groupName, attendanceArray));
        });
        attendanceTable.getItems().addAll(attendanceRows);

        // Add the table to the UI
        groups.getChildren().add(attendanceTable);
        attendanceTable.setFixedCellSize(45);

        DoubleBinding tableHeight = Bindings.createDoubleBinding(() ->
                        attendanceTable.getFixedCellSize() * (attendanceTable.getItems().size() + 1),
                attendanceTable.getItems());

        attendanceTable.prefHeightProperty().bind(tableHeight);

    }


    // Class to represent each row of attendance data
    public static class AttendanceRow {
        private final String groupName;
        private final String[] attendance;

        public AttendanceRow(String groupName, String[] attendance) {
            this.groupName = groupName;
            this.attendance = attendance;
        }

        public String getAttendance(int weekNumber) {
            if (weekNumber > 0 && weekNumber <= attendance.length) {
                return attendance[weekNumber - 1];
            } else {
                return "";
            }
        }
    }
}
