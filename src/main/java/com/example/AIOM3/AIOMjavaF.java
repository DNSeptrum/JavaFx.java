package com.example.AIOM3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
public class AIOMjavaF extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
ArrayList<Teacher> teachers = new ArrayList<>();
ArrayList<ClassTeacher> classTeachers= new ArrayList<>();

        Obslugasceny obsluga = new Obslugasceny(teachers,classTeachers);
        obsluga.stw();


        primaryStage.setTitle("Menu");

        Button showTeachersButton = new Button("Pokaż Nauczycieli");
        showTeachersButton.setOnAction(e -> obsluga.ShowTeacherTable());
        Button showClassesButton = new Button("Pokaż Klasy Nauczycieli");
        showClassesButton.setOnAction(e -> obsluga.ShowClassTable());

        TextField filterTextField = new TextField();
        filterTextField.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                obsluga.applyTextFilter(filterTextField.getText());
            }
        });


        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(showTeachersButton, showClassesButton);
        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(hbox,filterTextField);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}