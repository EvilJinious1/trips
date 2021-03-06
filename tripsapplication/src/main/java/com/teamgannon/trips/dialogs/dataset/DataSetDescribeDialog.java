package com.teamgannon.trips.dialogs.dataset;

import com.teamgannon.trips.jpa.model.DataSetDescriptor;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

@Slf4j
public class DataSetDescribeDialog extends Dialog<DataSetDescriptor> {

    private final static float LABEL_PREF_WIDTH = 200;
    private final @NotNull DataSetDescriptor descriptor;
    protected @NotNull GridPane planGrid = new GridPane();

    public DataSetDescribeDialog(@NotNull DataSetDescriptor descriptor) {
        this.descriptor = descriptor;
        this.setTitle("Dataset Description");
        this.setHeight(200);
        this.setWidth(450);

        planGrid.add(createLabel("name:  ", true), 0, 0);
        planGrid.add(createLabel(descriptor.getDataSetName(), false), 1, 0);

        planGrid.add(createLabel("Author:  ", true), 0, 1);
        planGrid.add(createLabel(descriptor.getFileCreator(), false), 1, 1);

        planGrid.add(createLabel("Date:  ", true), 0, 2);
        planGrid.add(createLabel(descriptor.getCreationDate(), false), 1, 2);

        planGrid.add(createLabel("# of Stars:  ", true), 0, 3);
        planGrid.add(createLabel(Long.toString(descriptor.getNumberStars()), false), 1, 3);

        planGrid.add(createLabel("Max Range (ly):  ", true), 0, 4);
        planGrid.add(createLabel(Double.toString(descriptor.getDistanceRange()), false), 1, 4);

        planGrid.add(createLabel("Notes:  ", true), 0, 5);
        planGrid.add(createLabel(descriptor.getNotes(), false), 1, 5);

        Separator mySep = new Separator();
        mySep.setMinHeight(10);
        planGrid.add(mySep, 0, 6, 2, 1);

        Button cancelDataSetButton = new Button("Cancel");
        cancelDataSetButton.setOnAction(this::close);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(cancelDataSetButton);
        planGrid.add(hBox, 0, 7, 2, 1);

        this.getDialogPane().setContent(planGrid);

        // set the dialog as a utility
        Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
        stage.setOnCloseRequest(this::close);

    }

    /**
     * close the dialog from the close button
     *
     * @param actionEvent the action event
     */
    private void close(ActionEvent actionEvent) {
        setResult(descriptor);
    }

    /**
     * close the dialog from stage x button
     *
     * @param we the windows event
     */
    private void close(WindowEvent we) {
        setResult(descriptor);
    }

    protected @NotNull Label createLabel(String textName, boolean boldFlag) {
        Label label = new Label(textName);
        label.setPrefWidth(LABEL_PREF_WIDTH);
        label.setFont(boldFlag ? Font.font("Arial", FontWeight.BOLD, 13) : Font.font("Arial", FontWeight.NORMAL, 13));
        return label;
    }

}
