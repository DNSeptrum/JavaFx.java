package com.example.AIOM3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.util.ArrayList;
import java.util.Comparator;

public class Obslugasceny {
ArrayList<Teacher> teachers;
ArrayList<ClassTeacher> classTeachers;
FilteredList<Teacher> filteredTeacherList;
    ObservableList<Teacher> observableTeacherList;
    FilteredList<ClassTeacher> filteredClassTeacherList;
    ObservableList<ClassTeacher> observableClassTeacherList;
    public Obslugasceny(ArrayList<Teacher> teachers, ArrayList<ClassTeacher> classTeachers) {
        this.teachers = teachers;
        this.classTeachers = classTeachers;
    }
// wypełnienie danymi
    public void stw(){
        ArrayList<ClassTeacher> classlist = new ArrayList<>();
        ArrayList<Teacher> list1 = new ArrayList<>();
        ArrayList<Teacher> list2 = new ArrayList<>();
        ArrayList<Teacher> list3 = new ArrayList<>();
        Teacher teacher1 = (new Teacher("Dawid", "Kowalski", TeacherCondition.Obecny, 1980, 5000.0));
        Teacher teacher2 =(new Teacher("Anna", "Nowak", TeacherCondition.Nieobecny, 1985, 3000.0));
        Teacher teacher3 =(new Teacher("Wiktor", "Nowak", TeacherCondition.Nieobecny, 1985, 4000.0));
        teachers.add(teacher1);
        teachers.add(teacher2);
        teachers.add(teacher3);
        ClassTeacher class1 = new ClassTeacher("class1",10,list1);
        class1.addteacher(teacher1);
        class1.addteacher(teacher2);

ClassTeacher class2 = new ClassTeacher("class2",10,list2);
        ClassTeacher class3 = new ClassTeacher("class3",10,list3);
class3.addteacher(teacher3);

        classlist.add(class1);
        classlist.add(class2);
        classlist.add(class3);
        classTeachers = classlist;

}
// główne tabele
    void ShowTeacherTable(){


        // Konwersja ArrayListy na ObservableList (wymagane do TableView)
        observableTeacherList = FXCollections.observableArrayList(teachers);
        filteredTeacherList = new FilteredList<>(observableTeacherList);

        // Tworzenie kolumn w TableView
        TableColumn<Teacher, String> imieColumn = new TableColumn<>("Imię");
        imieColumn.setCellValueFactory(cellData -> cellData.getValue().imieProperty());

        TableColumn<Teacher, String> nazwiskoColumn = new TableColumn<>("Nazwisko");
        nazwiskoColumn.setCellValueFactory(cellData -> cellData.getValue().nazwiskoProperty());

        TableColumn<Teacher, TeacherCondition> teacherConditionColumn = new TableColumn<>("Stan Nauczyciela");
        teacherConditionColumn.setCellValueFactory(cellData -> cellData.getValue().teacherConditionProperty());

        TableColumn<Teacher, Integer> rokUrodzeniaColumn = new TableColumn<>("Rok Urodzenia");
        rokUrodzeniaColumn.setCellValueFactory(cellData -> cellData.getValue().rok_urodzeniaProperty().asObject());

        TableColumn<Teacher, Double> wynagrodzenieColumn = new TableColumn<>("Wynagrodzenie");
        wynagrodzenieColumn.setCellValueFactory(cellData -> cellData.getValue().wynagrodzenieProperty().asObject());

        // Tworzenie TableView i przypisanie kolumn
        TableView<Teacher> teacherTableView = new TableView<>();
        teacherTableView.setItems(observableTeacherList);
        teacherTableView.getColumns().addAll(imieColumn, nazwiskoColumn, teacherConditionColumn, rokUrodzeniaColumn, wynagrodzenieColumn);

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteSelectedTeacher(teacherTableView));
        Button modifyButton = new Button("modify");
        modifyButton.setOnAction(e -> showModifyTeacherDialog(teacherTableView));
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> showAddTeacherDialog());
        Button sortButton = new Button("Sort");
        sortButton.setOnAction(e -> sortTeacherTable());


        VBox newVBox = new VBox(10);
        HBox newHBox = new HBox(10);

      //  newVBox.getChildren().addAll(teacherTableView,deleteButton,addButton,modifyButton,sortButton);
        newHBox.getChildren().addAll(deleteButton,addButton,modifyButton,sortButton);
        newVBox.getChildren().addAll(teacherTableView,newHBox);
        newVBox.setPadding(new Insets(10));
        // Ustawianie nowej sceny i wyświetlanie nowego okna
        Stage newStage = new Stage();
        Scene newScene = new Scene(newVBox, 455, 300);
        newStage.setScene(newScene);
        newStage.setTitle("Teacher Table");
        newStage.show();
    }
    public void ShowClassTable() {


        // Konwersja ArrayListy na ObservableList (wymagane do TableView)
        observableClassTeacherList = FXCollections.observableArrayList(classTeachers);
        filteredClassTeacherList = new FilteredList<>(observableClassTeacherList);
        // Tworzenie kolumn w TableView
        TableColumn<ClassTeacher, String> nazwaGrupyColumn = new TableColumn<>("Nazwa Grupy");
        nazwaGrupyColumn.setCellValueFactory(cellData -> cellData.getValue().nazwaProperty());

        TableColumn<ClassTeacher, Double> liczbaNauczycieliColumn = new TableColumn<>("Liczba Nauczycieli");
        liczbaNauczycieliColumn.setCellValueFactory(cellData -> cellData.getValue().liczba_nauczycieliProperty().asObject());

        TableColumn<ClassTeacher, Double> Zapelnienie = new TableColumn<>("Zapelnienie(%)");
        Zapelnienie .setCellValueFactory(cellData -> {
            ClassTeacher classTeacher = cellData.getValue();
            int liczbaNauczycieli = classTeacher.getNauczyciele().size();
            double zapelnienie = (double)liczbaNauczycieli / classTeacher.getLiczba_nauczycieli()*100;

            return new javafx.beans.property.SimpleObjectProperty<>(zapelnienie);
        });

        // Tworzenie TableView i przypisanie kolumn
        TableView<ClassTeacher> classTeacherTableView = new TableView<>();
        classTeacherTableView.setItems(observableClassTeacherList);
        classTeacherTableView.getColumns().addAll(nazwaGrupyColumn,liczbaNauczycieliColumn,Zapelnienie );

        // Obsługa zdarzeń po naciśnięciu wiersza
        classTeacherTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                ClassTeacher selectedClassTeacher = classTeacherTableView.getSelectionModel().getSelectedItem();
                if (selectedClassTeacher != null) {
                    showTeachersOfClass(selectedClassTeacher);
                }
            }
        });

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteSelectedClass(classTeacherTableView));

        Button modifyButton = new Button("modify");
        modifyButton.setOnAction(e -> showModifyClassTeacherDialog(classTeacherTableView));


        Button addButton = new Button("Add");
        addButton.setOnAction(e -> showAddClassTeacherDialog());

        Button sortButton = new Button("Sort");
        sortButton.setOnAction(e -> sortClassTable());

        Button addTeacherButton = new Button("Add Teacher");
        addTeacherButton.setOnAction(e -> showAddTeacherToClassDialog(classTeacherTableView));


        // Układowanie elementów w nowym oknie
        VBox newVBox = new VBox(10);
        HBox newHbox = new HBox(10);
        newHbox.getChildren().addAll(deleteButton,addButton,modifyButton,sortButton,addTeacherButton);
        newVBox.getChildren().addAll(classTeacherTableView,newHbox);
        newVBox.setPadding(new Insets(10));

        Stage newStage = new Stage();
        Scene newScene = new Scene(newVBox, 400, 300);
        newStage.setScene(newScene);
        newStage.setTitle("Class Teacher Table");
        newStage.show();
    }
//wyświetlanie
    private void showTeachersOfClass(ClassTeacher classTeacher) {

        ArrayList<Teacher> teachers = classTeacher.getNauczyciele();


        Stage teacherStage = new Stage();
        TableView<Teacher> teacherTableView = new TableView<>();
        TableColumn<Teacher, String> imieColumn = new TableColumn<>("Imię");
        imieColumn.setCellValueFactory(cellData -> cellData.getValue().imieProperty());

        TableColumn<Teacher, String> nazwiskoColumn = new TableColumn<>("Nazwisko");
        nazwiskoColumn.setCellValueFactory(cellData -> cellData.getValue().nazwiskoProperty());

        teacherTableView.setItems(FXCollections.observableArrayList(teachers));
        teacherTableView.getColumns().addAll(imieColumn, nazwiskoColumn);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(teacherTableView);

        Scene teacherScene = new Scene(vbox, 300, 200);
        teacherStage.setScene(teacherScene);
        teacherStage.setTitle("Nauczyciele Klasy: " + classTeacher.getNazwa_grupy());
        teacherStage.show();
    }
    // usuwanie
    private void deleteSelectedClass(TableView<ClassTeacher> classTeacherTableView) {
        ClassTeacher selectedClassTeacher = classTeacherTableView.getSelectionModel().getSelectedItem();
        if (selectedClassTeacher != null) {
            classTeachers.remove(selectedClassTeacher);
            observableClassTeacherList.remove(selectedClassTeacher);
        }
    }
    private void deleteSelectedTeacher(TableView<Teacher> teacherTableView) {
        Teacher selectedTeacher = teacherTableView.getSelectionModel().getSelectedItem();
        if (selectedTeacher != null) {
            teachers.remove(selectedTeacher);
            observableTeacherList.remove(selectedTeacher);
        }
        for (ClassTeacher classTeacher : classTeachers) {
            if (selectedTeacher != null)
                classTeacher.nauczyciele.remove(selectedTeacher);
        }
    }
    //dodawnie
    private void showAddTeacherDialog() {

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Dodaj Nauczyciela");

        // Tworzenie pól do wprowadzania danych
        TextField imieField = new TextField();
        TextField nazwiskoField = new TextField();
        ComboBox<TeacherCondition> conditionComboBox = new ComboBox<>();
        conditionComboBox.getItems().addAll(TeacherCondition.Obecny, TeacherCondition.Nieobecny,TeacherCondition.Delegacja,TeacherCondition.Chory);
        TextField rokUrodzeniaField = new TextField();
        TextField wynagrodzenieField = new TextField();

        // Tworzenie przycisku do potwierdzenia dodania nauczyciela
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {
            // Pobieranie danych z pól
            String imie = imieField.getText();
            String nazwisko = nazwiskoField.getText();
            TeacherCondition condition = conditionComboBox.getValue();
            int rokUrodzenia = Integer.parseInt(rokUrodzeniaField.getText());
            double wynagrodzenie = Double.parseDouble(wynagrodzenieField.getText());

            // Tworzenie nowego obiektu Teacher i dodanie do listy
            Teacher newTeacher = new Teacher(imie, nazwisko, condition, rokUrodzenia, wynagrodzenie);
            teachers.add(newTeacher);
            observableTeacherList.add(newTeacher);


            dialogStage.close();
        });

        //przycisk "Anuluj"
        Button cancelButton = new Button("Anuluj");
        cancelButton.setOnAction(e -> dialogStage.close());

        // Układowanie pól i przycisków w oknie dialogowym
        VBox dialogVBox = new VBox(10);
        //  VBox newVBox = new VBox(10);
        dialogVBox.getChildren().addAll(imieField, nazwiskoField, conditionComboBox, rokUrodzeniaField, wynagrodzenieField, okButton, cancelButton);

        Scene newScene = new Scene(dialogVBox, 400, 300);
        dialogStage.setScene(newScene);
        dialogStage.setTitle("Dodawanie nauczyciela");
        dialogStage.show();
    }

    private void showAddClassTeacherDialog() {

        Stage newStage = new Stage();
        newStage.setTitle("Dodaj Clase");

        // Tworzenie pól do wprowadzania danych
        TextField nazwa_grupyField = new TextField();
        TextField liczba_nauczcieliField = new TextField();


        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {

            String nazwa_grupy = nazwa_grupyField.getText();

            int liczba_nauczcieli = Integer.parseInt(liczba_nauczcieliField.getText());
            ArrayList<Teacher> teacheradd = new ArrayList<>();

            // Tworzenie nowego obiektu Teacher i dodanie do listy
            ClassTeacher newClassTeacher = new ClassTeacher(nazwa_grupy,liczba_nauczcieli,teacheradd);

classTeachers.add(newClassTeacher);
            observableClassTeacherList.add(newClassTeacher);
            newStage.close();
        });


        Button cancelButton = new Button("Anuluj");
        cancelButton.setOnAction(e -> newStage.close());


        VBox dialogVBox = new VBox(10);
        dialogVBox.getChildren().addAll(nazwa_grupyField,liczba_nauczcieliField, okButton, cancelButton);
        Scene newScene = new Scene(dialogVBox, 400, 300);
        newStage.setScene(newScene);
        newStage.setTitle("Dodawanie clasy");
        newStage.show();
    }
// modyfikowanie
    private void showModifyTeacherDialog(TableView<Teacher> teacherTableView) {
        Teacher selectedTeacher = teacherTableView.getSelectionModel().getSelectedItem();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Modyfikuj Nauczyciela");

        // Tworzenie pól do wprowadzania danych
        TextField imieField = new TextField(selectedTeacher.getImie());
        TextField nazwiskoField = new TextField(selectedTeacher.getNazwisko());
        ComboBox<TeacherCondition> conditionComboBox = new ComboBox<>();
        conditionComboBox.getItems().addAll(TeacherCondition.Obecny, TeacherCondition.Nieobecny,TeacherCondition.Delegacja,TeacherCondition.Chory);
        conditionComboBox.setValue(selectedTeacher.getTeacherCondition());
        TextField rokUrodzeniaField = new TextField(Integer.toString(selectedTeacher.getRok_urodzenia()));
        TextField wynagrodzenieField = new TextField(Double.toString(selectedTeacher.getWynagrodzenie()));

        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {
            // Pobieranie danych z pól
            selectedTeacher.setImie(imieField.getText());
            selectedTeacher.setNazwisko(nazwiskoField.getText());
            selectedTeacher.setTeacherCondition(conditionComboBox.getValue());
            selectedTeacher.setRok_urodzenia(Integer.parseInt(rokUrodzeniaField.getText()));
            selectedTeacher.setWynagrodzenie(Double.parseDouble(wynagrodzenieField.getText()));
            teacherTableView.refresh();

            dialogStage.close();
        });

        Button cancelButton = new Button("Anuluj");
        cancelButton.setOnAction(e -> dialogStage.close());

        VBox dialogVBox = new VBox(10);
        dialogVBox.getChildren().addAll(
                new Label("Imię:"),
                imieField,
                new Label("Nazwisko:"),
                nazwiskoField,
                new Label("Stan Nauczyciela:"),
                conditionComboBox,
                new Label("Rok Urodzenia:"),
                rokUrodzeniaField,
                new Label("Wynagrodzenie:"),
                wynagrodzenieField,
                new HBox(10, okButton, cancelButton)
        );

        dialogVBox.setPadding(new Insets(10));
        Scene dialogScene = new Scene(dialogVBox, 400, 400);
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }

    private void showModifyClassTeacherDialog(TableView<ClassTeacher> classTeacherTableView) {
        ClassTeacher selectedClass = classTeacherTableView.getSelectionModel().getSelectedItem();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Modyfikuj Nauczyciela");

        // Tworzenie pól do wprowadzania danych
        TextField Nazwa_grupyField = new TextField(selectedClass.getNazwa_grupy());
        TextField Liczba_nauczycieliField = new TextField(Double.toString(selectedClass.getLiczba_nauczycieli()));

        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {
            // Pobieranie danych z pól
            selectedClass.setNazwa_grupy(Nazwa_grupyField.getText());
            selectedClass.setLiczba_nauczycieli(Double.parseDouble(Liczba_nauczycieliField.getText()));

            classTeacherTableView.refresh();
            dialogStage.close();
        });

        Button cancelButton = new Button("Anuluj");
        cancelButton.setOnAction(e -> dialogStage.close());

        // Układowanie pól i przycisków w oknie dialogowym
        VBox dialogVBox = new VBox(10);
        dialogVBox.getChildren().addAll(
                new Label("Nazwa grupy:"),
                Nazwa_grupyField,
                new Label("liczba nauczycieli:"),
                Liczba_nauczycieliField,
                new HBox(10, okButton, cancelButton)
        );

        dialogVBox.setPadding(new Insets(10));
        Scene dialogScene = new Scene(dialogVBox, 400, 300);
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }

    private void sortTeacherTable() {
        teachers.sort(Comparator.comparing(Teacher::getWynagrodzenie));
        observableTeacherList.sort(Comparator.comparing(Teacher::getWynagrodzenie));
    }

    private void sortClassTable() {
        classTeachers.sort(Comparator.comparingDouble(this::calculateZapelnienie));
        observableClassTeacherList.sort(Comparator.comparingDouble(this::calculateZapelnienie));
    }

    private double calculateZapelnienie(ClassTeacher classTeacher) {
        return (double) classTeacher.getNauczyciele().size() / classTeacher.getLiczba_nauczycieli();
    }
// filtrowanie po nazwisku
    private void createAndShowFilteredTeacherTable(ObservableList<Teacher> filteredList) {

        TableColumn<Teacher, String> imieColumn = new TableColumn<>("Imię");
        imieColumn.setCellValueFactory(cellData -> cellData.getValue().imieProperty());

        TableColumn<Teacher, String> nazwiskoColumn = new TableColumn<>("Nazwisko");
        nazwiskoColumn.setCellValueFactory(cellData -> cellData.getValue().nazwiskoProperty());

        TableColumn<Teacher, TeacherCondition> teacherConditionColumn = new TableColumn<>("Stan Nauczyciela");
        teacherConditionColumn.setCellValueFactory(cellData -> cellData.getValue().teacherConditionProperty());

        TableColumn<Teacher, Integer> rokUrodzeniaColumn = new TableColumn<>("Rok Urodzenia");
        rokUrodzeniaColumn.setCellValueFactory(cellData -> cellData.getValue().rok_urodzeniaProperty().asObject());

        TableColumn<Teacher, Double> wynagrodzenieColumn = new TableColumn<>("Wynagrodzenie");
        wynagrodzenieColumn.setCellValueFactory(cellData -> cellData.getValue().wynagrodzenieProperty().asObject());

        //TableView
        TableView<Teacher> teacherTableView = new TableView<>();
        observableTeacherList = filteredList;
        filteredTeacherList = new FilteredList<>(filteredList);
        teacherTableView.setItems(filteredList);
        teacherTableView.getColumns().addAll(imieColumn, nazwiskoColumn, teacherConditionColumn, rokUrodzeniaColumn, wynagrodzenieColumn);

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteSelectedTeacher(teacherTableView));
        Button modifyButton = new Button("modify");
        modifyButton.setOnAction(e -> showModifyTeacherDialog(teacherTableView));
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> showAddTeacherDialog());
        Button sortButton = new Button("Sort");
        sortButton.setOnAction(e -> sortTeacherTable());

        VBox newVBox = new VBox();
        HBox newHBox = new HBox();
        newHBox.getChildren().addAll(deleteButton,addButton,modifyButton,sortButton);
        newVBox.getChildren().addAll(teacherTableView,newHBox);
        newVBox.setPadding(new Insets(10));
        Stage newStage = new Stage();
        Scene newScene = new Scene(newVBox, 400, 300);
        newStage.setScene(newScene);
        newStage.setTitle("Teacher Table");
        newStage.show();
    }

    public void applyTextFilter(String filterText) {
        if (filterText.isEmpty()) {
        } else {
            ObservableList<Teacher> filtlist = FXCollections.observableArrayList(teachers);
            ObservableList<Teacher> filteredList = filtlist.filtered(
                    teacher -> teacher.getNazwisko().equalsIgnoreCase(filterText)
            );
            createAndShowFilteredTeacherTable(filteredList);
        }
    }

    private void showAddTeacherToClassDialog(TableView<ClassTeacher> classTeacherTableView) {

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Dodaj Nauczyciela do Klasy");
        ObservableList<Teacher> filtlist = FXCollections.observableArrayList(teachers);
        // Tworzenie ComboBox z dostępnymi nauczycielami
        ComboBox<Teacher> teacherComboBox = new ComboBox<>(filtlist);

        teacherComboBox.setPromptText("Wybierz Nauczyciela");
        teacherComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Teacher teacher, boolean empty) {
                super.updateItem(teacher, empty);
                if (empty || teacher == null) {
                    setText(null);
                } else {
                    setText(teacher.getImie());
                }
            }
        });

        // Dostosowanie obiektu Teacher do tekstu w ComboBox
        teacherComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Teacher teacher) {
                return (teacher != null) ? teacher.getImie() : null;
            }

            @Override
            public Teacher fromString(String string) {
                return null;
            }
        });

        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {
            Teacher selectedTeacher = teacherComboBox.getValue();
            if (selectedTeacher != null) {
                // Pobieranienklasy z tabeli
                ClassTeacher selectedClassTeacher = classTeacherTableView.getSelectionModel().getSelectedItem();
                if (selectedClassTeacher != null) {
                    selectedClassTeacher.addteacher(selectedTeacher);
                }
            }
            classTeacherTableView.refresh();

            dialogStage.close();
        });

        Button cancelButton = new Button("Anuluj");
        cancelButton.setOnAction(e -> dialogStage.close());

        VBox dialogVBox = new VBox(10);
        dialogVBox.getChildren().addAll(
                new Label("Wybierz Nauczyciela:"),
                teacherComboBox,
                new HBox(10, okButton, cancelButton)
        );

        dialogVBox.setPadding(new Insets(10));
        Scene dialogScene = new Scene(dialogVBox, 300, 200);
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }
    
}