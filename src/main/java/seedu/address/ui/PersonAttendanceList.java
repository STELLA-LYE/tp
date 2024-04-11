package seedu.address.ui;

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
import seedu.address.model.group.Group;
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
    public PersonAttendanceList(Person person, int displayedIndex, String groupName) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);

        populateGroupNameCol();
        createAttendanceCols();
        populateAttendanceCols(groupName);
        groups.getChildren().add(attendanceTable);
        setTableHt();
    }

    /**
     * Creates a new TableColumn for the group column and populates each row with a group.
     */
    public void populateGroupNameCol() {
        TableColumn<AttendanceRow, String> groupNameColumn = new TableColumn<>("Group");
        groupNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGroupName()));
        attendanceTable.getColumns().add(0, groupNameColumn);
        // Set cell value factory for the first column to show group names
        groupNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGroupName()));
    }

    /**
     * Create columns of attendance for the table, one for each week.
     */
    public void createAttendanceCols() {
        for (int i = 0; i < Group.MAX_NUM_OF_WEEKS; i++) {
            int weekNumber = i + 1;
            TableColumn<AttendanceRow, String> column = new TableColumn<>("Week " + weekNumber);
            column.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getAttendance(weekNumber)));
            attendanceTable.getColumns().add(column);
        }
    }

    /**
     * Populates columns with weekly attendance data.
     */
    public void populateAttendanceCols(String groupName) {
//        for (Group group : person.getGroups()) {
//            if (group.groupName.equals(groupName)) {
//                String[] attendanceArray = group.attendance.toArray(new String[0]);
//                AttendanceRow row = new AttendanceRow(group.groupName, attendanceArray);
//                attendanceTable.getItems().add(row);
//                break;
//            }
//        }
        person.getGroups().stream()
                .filter(group -> group.groupName.equals(groupName))
                .findFirst()
                .ifPresent(group -> {
                    String[] attendanceArray = group.attendance.toArray(new String[0]);
                    attendanceTable.getItems().add(new AttendanceRow(group.groupName, attendanceArray));
                });
//        List<AttendanceRow> attendanceRows = new ArrayList<>();
//        person.getGroups().forEach(group -> {
//            String[] attendanceArray = group.attendance.toArray(new String[0]);
//            attendanceRows.add(new AttendanceRow(group.groupName, attendanceArray));
//        });
//        attendanceTable.getItems().addAll(attendanceRows);
    }

    /**
     * Sets the height of the table based on the number of rows.
     */
    public void setTableHt() {
        attendanceTable.setFixedCellSize(45);
        DoubleBinding tableHeight = Bindings.createDoubleBinding(() ->
                        attendanceTable.getFixedCellSize() * (attendanceTable.getItems().size() + 1),
                attendanceTable.getItems());

        attendanceTable.prefHeightProperty().bind(tableHeight);
    }
}
