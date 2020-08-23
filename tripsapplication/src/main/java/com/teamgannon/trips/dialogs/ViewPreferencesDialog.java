package com.teamgannon.trips.dialogs;

import com.teamgannon.trips.config.application.*;
import com.teamgannon.trips.dialogs.preferencespanes.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ViewPreferencesDialog extends Dialog<ApplicationPreferences> {

    private TabPane tabPane;

    private ButtonType buttonTypeOk;

    private PreferencesUpdater updater;
    private TripsContext tripsContext;
    private final ApplicationPreferences preferences;


    public ViewPreferencesDialog(PreferencesUpdater updater, TripsContext tripsContext, ApplicationPreferences preferences) {
        this.updater = updater;
        this.tripsContext = tripsContext;

        this.preferences = preferences;
        this.setTitle("View Preferences - currently disabled");

        VBox vBox = new VBox();
        createTabPanes(vBox);
        createButtons(vBox);

        this.setResultConverter(button -> okButtonAction(preferences, button));

        this.getDialogPane().setContent(vBox);
    }

    private void createTabPanes(VBox vBox) {
        tabPane = new TabPane();
        tabPane.setStyle("-fx-background-color: white");

        Tab displayTab = new Tab("Graph");
        String style1 = "-fx-background-color: lightgreen";
        displayTab.setStyle(style1);
        displayTab.setContent(new GraphPane(updater, tripsContext));
        tabPane.getTabs().add(displayTab);

        Tab linksTab = new Tab("Links");
        String style2 = "-fx-background-color: limegreen";
        linksTab.setStyle(style2);
        linksTab.setContent(new LinksPane(preferences.getLinkDisplayPreferences(), style2));
        tabPane.getTabs().add(linksTab);

        Tab starsTab = new Tab("Stars");
        String style3 = "-fx-background-color: aquamarine";
        starsTab.setStyle(style3);
        starsTab.setContent(new StarsPane(preferences.getStarDisplayPreferences(), style3));
        tabPane.getTabs().add(starsTab);

        Tab positionTab = new Tab("Position");
        String style4 = "-fx-background-color: lavender";
        positionTab.setStyle(style4);
        positionTab.setContent(new PositionPane(preferences.getPositionDisplayPreferences(), style4));
        tabPane.getTabs().add(positionTab);

        Tab routeTab = new Tab("Route");
        String style5 = "-fx-background-color: lightyellow";
        routeTab.setStyle(style5);
        routeTab.setContent(new RoutePane(preferences.getRouteDisplayPreferences(), style5));
        tabPane.getTabs().add(routeTab);

        Tab civilizationTab = new Tab("Civilization");
        String style6 = "-fx-background-color: limegreen";
        civilizationTab.setStyle(style6);
        civilizationTab.setContent(new CivilizationPane(preferences.getCivilizationDisplayPreferences(), style6));
        tabPane.getTabs().add(civilizationTab);

        vBox.getChildren().add(tabPane);
    }

    private void createButtons(VBox vBox) {
        HBox hBox = new HBox();

        ButtonType buttonTypeCancel = new ButtonType("Dismiss", ButtonBar.ButtonData.CANCEL_CLOSE);
        this.getDialogPane().getButtonTypes().add(buttonTypeCancel);

        vBox.getChildren().add(hBox);
    }


    private ApplicationPreferences okButtonAction(ApplicationPreferences preferences, ButtonType button) {
        if (button == buttonTypeOk) {
            // note: read from the panels
            return new ApplicationPreferences();
        }
        return preferences;
    }


}