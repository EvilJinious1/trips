package com.teamgannon.trips.dialogs;

import com.teamgannon.trips.config.application.Localization;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.File;
import java.nio.file.Path;

public class AboutDialog extends Dialog<String> {

    private final Font font = Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 13);

    public AboutDialog(Localization localization) {

        VBox vBox = new VBox();
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        Label applicationLabel = new Label("Application");
        applicationLabel.setFont(font);
        gridPane.add(applicationLabel, 0, 1);
        gridPane.add(new Label(localization.getTitle()), 1, 1);

        Label develop = new Label("Developed by");
        develop.setFont(font);
        gridPane.add(develop, 0, 2);
        gridPane.add(new Label(localization.getContributors()), 1, 2);

        Label date = new Label("Date");
        date.setFont(font);
        gridPane.add(date, 0, 3);
        gridPane.add(new Label(localization.getReleaseDate()), 1, 3);

        Label versionLabel =new Label("Version");
        versionLabel.setFont(font);
        gridPane.add(versionLabel, 0, 4);
        gridPane.add(new Label(localization.getVersion()), 1, 4);

        Label projectPageLabel =new Label("ProjectPage");
        projectPageLabel.setFont(font);
        gridPane.add(projectPageLabel, 0, 5);
        gridPane.add(new Label(localization.getProjectPage()), 1, 5);

        Label fileDirLabel =new Label("Data files Location");
        File file = new File(localization.getFileDirectory());
        fileDirLabel.setFont(font);
        gridPane.add(fileDirLabel, 0, 6);
        gridPane.add(new Label(file.getAbsolutePath()), 1, 6);

        vBox.getChildren().add(gridPane);

        HBox hBox = new HBox();
        Button resetBtn = new Button("Dismiss");
        resetBtn.setOnAction(this::dismiss);
        hBox.getChildren().add(resetBtn);


        vBox.getChildren().add(hBox);


        this.getDialogPane().setContent(vBox);
    }

    private void dismiss(ActionEvent actionEvent) {
        setResult("  ");
    }

}
