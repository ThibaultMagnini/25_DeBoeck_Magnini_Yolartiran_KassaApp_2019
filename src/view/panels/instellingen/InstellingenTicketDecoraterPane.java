package view.panels.instellingen;

import controller.KassaController;
import database.PropertiesLoadSave;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;

/**
 * @Author Jonas De Boeck
*/
public class InstellingenTicketDecoraterPane extends GridPane {

    private Label toevoeging;
    private KassaController controller;
    private CheckBox header, footer;
    private Button saveSettings;
    private ComboBox headerAddons, footerAddons;
    private ObservableList<String> headerAddonsList, footerAddonsList;
    private TextField headerMessage, footerMessage;

    public InstellingenTicketDecoraterPane(KassaController controller){
        setController(controller);
        toevoeging = new Label("Toevoegingen aan kassabon instellingen:");
        header = new CheckBox("Kies een header toevoeging");
        footer = new CheckBox("Kies een footer toevoeging");
        headerAddonsList = FXCollections.observableArrayList(
                "Algemene boodschap",
                        "Datum en tijd van afprinten"
        );
        footerAddonsList = FXCollections.observableArrayList(
                "Totale prijs zonder en met korting",
                        "Betaalde prijs ex. BTW en totale BTW",
                        "Algemene boodschap"
        );
        headerAddons = new ComboBox<>(headerAddonsList);
        footerAddons = new ComboBox<>(footerAddonsList);
        saveSettings = new Button("Slaag settings op");
        headerMessage = new TextField();
        footerMessage = new TextField();
        this.add(toevoeging, 1, 1);
        this.add(header, 1, 2);
        this.add(footer, 1, 3);
        this.add(headerAddons, 2, 2);
        this.add(headerMessage,3,2);
        this.add(footerAddons, 2, 3);
        this.add(footerMessage,3,3);
        this.add(saveSettings, 1, 4);

        this.setPadding(new Insets(10,10,10,10));
        this.setVgap(8);
        this.setHgap(10);

        //Alles wat in de Properties file aangeduid staat wordt aangeduid bij het opstartenb
        header.setSelected(controller.checkBestaat("HEADER"));
        footer.setSelected(controller.checkBestaat("FOOTER"));

        headerAddons.setVisible(header.isSelected());
        footerAddons.setVisible(footer.isSelected());
        headerMessage.setVisible(false);
        footerMessage.setVisible(false);

        if (controller.checkBestaat("HBOODSCHAP")) {
            headerMessage.setText(controller.loadProperty("HBOODSCHAP"));
        }

        if (controller.checkBestaat("FBOODSCHAP")) {
            footerMessage.setText(controller.loadProperty("FBOODSCHAP"));
        }

        if (PropertiesLoadSave.propertyBestaat("HEADER")) {
            headerAddons.getSelectionModel().select(controller.loadProperty("HEADER"));
            if (headerAddons.getSelectionModel().getSelectedItem().toString().equals("Algemene boodschap")) {
                headerMessage.setVisible(true);
            }
        }

        if (PropertiesLoadSave.propertyBestaat("FOOTER")) {
            footerAddons.getSelectionModel().select(controller.loadProperty("FOOTER"));
            if (footerAddons.getSelectionModel().getSelectedItem().toString().equals("Algemene boodschap")) {
                footerMessage.setVisible(true);
            }
        }

        header.setOnAction(event -> {
            headerAddons.setVisible(header.isSelected());
            footerAddons.setVisible(footer.isSelected());
        });

        footer.setOnAction(event -> {
            footerAddons.setVisible(footer.isSelected());
            headerAddons.setVisible(header.isSelected());
        });

        footerAddons.setOnAction(event -> {
            if (footerAddons.getSelectionModel().getSelectedItem().toString().equals("Algemene boodschap")) {
                footerMessage.setVisible(true);
            } else {
                footerMessage.setVisible(false);
            }
        });

        headerAddons.setOnAction(event -> {
            if (headerAddons.getSelectionModel().getSelectedItem().toString().equals("Algemene boodschap")) {
                headerMessage.setVisible(true);
            } else {
                headerMessage.setVisible(false);
            }
        });

        saveSettings.setOnAction(event -> {
            if (header.isSelected()) {
                if (headerAddons.getValue().equals("Algemene boodschap")) {
                    controller.saveProperty(headerMessage.getText(), "HBOODSCHAP");
                } else {
                    controller.saveProperty("", "HBOODSCHAP");
                }
                controller.saveProperty(headerAddons.getValue().toString(), "HEADER");
            } else {
                controller.saveProperty("", "HEADER");
            }
            if (footer.isSelected()) {
                if (footerAddons.getValue().equals("Algemene boodschap")) {
                    controller.saveProperty(footerMessage.getText(), "FBOODSCHAP");
                } else {
                    controller.saveProperty("", "FBOODSCHAP");
                }
                controller.saveProperty(footerAddons.getValue().toString(), "FOOTER");
            } else {
                controller.saveProperty("", "FOOTER");
            }
            Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
            confirmation.setTitle("INFO");
            confirmation.setHeaderText("Wijzigingen opgeslagen");
            confirmation.setContentText("Wijzigingen opgeslagen, herstart het programma voor de aanpassingen.");
            confirmation.showAndWait();
        });
    }

    public void setController(KassaController controller) {
        this.controller = controller;
    }
}
