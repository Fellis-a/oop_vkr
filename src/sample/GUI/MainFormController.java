package sample.GUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.models.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {

    public TableView<User> mainTable;
    public ComboBox cmbUserType;

    ObservableList<Class<? extends User>> UserTypes = FXCollections.observableArrayList(
            User.class,
            Admin.class,
            Teacher.class,
            MainUser.class
    );

    UsersModel userModel = new UsersModel();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userModel.addDataChangedListener(gadgets -> {
            mainTable.setItems(FXCollections.observableArrayList(gadgets));
        });


        TableColumn<User, String> titleColumn = new TableColumn<>("Название");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));


        TableColumn<User, String> yearColumn = new TableColumn<>("Год защиты");
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));


        TableColumn<User, String> markColumn = new TableColumn<>("Оценка");
        markColumn.setCellValueFactory(new PropertyValueFactory<>("mark"));


        TableColumn<User, String> groupColumn = new TableColumn<>("Уч. группа");
        groupColumn.setCellValueFactory(new PropertyValueFactory<>("group"));


        TableColumn<User, String> descriptionColumn = new TableColumn<>("Описание");

        descriptionColumn.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getDescription());
        });


        mainTable.getColumns().addAll(titleColumn, groupColumn,yearColumn, markColumn, descriptionColumn );

        cmbUserType.setItems(UserTypes);

        cmbUserType.getSelectionModel().select(0);

        cmbUserType.setConverter(new StringConverter<Class>() {
            @Override
            public String toString(Class object) {
                if (User.class.equals(object)) {
                    return "Все";
                } else if (Admin.class.equals(object)) {
                    return "Администратор";
                } else if (Teacher.class.equals(object)) {
                    return "Преподаватель";
                } else if (MainUser.class.equals(object)) {
                    return "Пользователь";
                }
                return null;
            }

            @Override
            public Class fromString(String string) {
                return null;
            }
        });


        cmbUserType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.userModel.setUserFilter((Class<? extends User>) newValue);
        });

        //mainTable.getItems().stream().filter(item -> item.getId()==searchId).findAny()


    }

    public void onAddClick(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UserForm.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.mainTable.getScene().getWindow());

        UserFormController controller = loader.getController();
        controller.userModel = userModel;

        stage.showAndWait();
    }
    public void onEditClick(ActionEvent actionEvent) throws IOException {
        if (this.mainTable.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("UserForm.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.mainTable.getScene().getWindow());

        UserFormController controller = loader.getController();
        controller.setUser((User) this.mainTable.getSelectionModel().getSelectedItem());
        controller.userModel = userModel;
        stage.showAndWait();


    }
    public void onDeleteClick(ActionEvent actionEvent) throws IOException{
         User gadget = (User) this.mainTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText(String.format("Точно удалить %s?", gadget.getTitle()));

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            userModel.delete(gadget.id);
        }
    }
    public void onSaveToFileClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить данные");
        fileChooser.setInitialDirectory(new File("."));

        File file = fileChooser.showSaveDialog(mainTable.getScene().getWindow());

        if (file != null) {
            userModel.saveToFile(file.getPath());
        }
    }

    public void onLoadToFileClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузить данные");
        fileChooser.setInitialDirectory(new File("."));

        File file = fileChooser.showOpenDialog(mainTable.getScene().getWindow());

        if (file != null) {
            userModel.loadFromFile(file.getPath());
        }
    }
}
