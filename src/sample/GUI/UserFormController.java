package sample.GUI;



import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.models.*;

import java.net.URL;
import java.util.ResourceBundle;

public class UserFormController implements Initializable {

    public ChoiceBox cmbUserType;
    public TextField txtTitle;
    public TextField txtFio;
    public ChoiceBox cmbGroup;
    public TextField txtMark;
    public TextField txtYear;


    public VBox teacherPane;
    public CheckBox chkEssay;

    public VBox adminPane;
    public TextField txtTag;
    public TextArea txtTagMore;

    final String ADMIN = "Администратор";
    final String TEACHER = "Преподаватель";
    final String USER = "Пользователь";

    public UsersModel userModel;
    private Integer id = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbUserType.setItems(FXCollections.observableArrayList(
                ADMIN,
                TEACHER
        ));

        cmbUserType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updatePanes((String) newValue);
        });
        //выпадающий список выбора
        cmbGroup.getItems().setAll(
                User.Group.ist,
                User.Group.asu,
                User.Group.evm,
                User.Group.ibb

        );

        cmbGroup.setConverter(new StringConverter<User.Group>() {
            @Override
            public String toString(User.Group object) {
                switch (object) {
                    case ist:
                        return "ИСТ";
                    case asu:
                        return "АСУ";
                    case evm:
                        return "ЭВМ";
                    case ibb:
                        return "ИББ";
                }
                return null;
            }

            @Override
            public User.Group fromString(String string) {
                return null;
            }
        });

        updatePanes("");
        txtYear.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) {
                if (!txtYear.getText().matches("[0-9]+")) {
                    txtYear.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");

                }
                else {txtYear.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");}
            }
        });
        txtMark.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) {
                if (!txtMark.getText().matches("[0-9]+")) {
                    txtMark.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");

                }
                else {txtMark.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");}
            }
        });
    }


    public void updatePanes(String value) {


        this.teacherPane.setVisible(value.equals(TEACHER));
        this.teacherPane.setManaged(value.equals(TEACHER));

        this.adminPane.setVisible(value.equals(ADMIN));
        this.adminPane.setManaged(value.equals(ADMIN));
    }

    public void onSaveClick(ActionEvent actionEvent) {
        if (this.id != null) {
            User user = getUser();
            user.id = this.id;

            this.userModel.edit(user);
        } else {
            this.userModel.add(getUser());
        }

        ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).close();
    }


    public void onCancelClick(ActionEvent actionEvent) {
        ((Stage)((Node)actionEvent.getSource()).getScene().getWindow()).close();
    }


    //метод добавления записи
    public User getUser (){
        User result = null;

        int mark = Integer.parseInt(this.txtMark.getText());
        int year = Integer.parseInt(this.txtYear.getText());
        String title = this.txtTitle.getText();



        String value = (String) this.cmbUserType.getValue();
        if (ADMIN.equals(value)) {
            result = new Admin(
                     this.txtFio.getText(),
                    title,
                    year,
                    mark,
                    (User.Group) this.cmbGroup.getValue(),
                     this.txtTag.getText(),
                     this.txtTagMore.getText());
        } else if (TEACHER.equals(value)) {
            result = new Teacher(
                    title,
                    year,
                    mark,
                    (User.Group) this.cmbGroup.getValue(),
                    this.chkEssay.isSelected()
                    );
        } else if (TEACHER.equals(value)) {
            result = new MainUser( this.txtFio.getText(),title,year, mark,(User.Group)cmbGroup.getValue(), this.chkEssay.isSelected(), this.txtTag.getText());

        }
        return result;
    }
    public void setUser(User user) {

        this.cmbUserType.setDisable(user != null);
        this.id = user != null ? user.id : null;
        if (user != null) {
            this.txtYear.setText(String.valueOf(user.getYear()));
            this.txtMark.setText(String.valueOf(user.getMark()));
            this.txtTitle.setText(user.getTitle());

            if (user instanceof Admin) {
                this.cmbUserType.setValue(ADMIN);
                this.txtFio.setText(((Admin) user).getFio());
                this.txtTag.setText(((Admin) user).getTag());
                this.txtTagMore.setText(((Admin) user).getTagDescription());

            } else if (user instanceof Teacher) {
                this.cmbUserType.setValue(TEACHER);
                this.chkEssay.setSelected(((Teacher) user).isEssay);

            } else if (user instanceof MainUser) {
                this.cmbUserType.setValue(USER);
                this.chkEssay.setSelected(((MainUser) user).isEssay());
                this.txtTag.setText(((MainUser) user).getTag());
            }
        }
    }


}

