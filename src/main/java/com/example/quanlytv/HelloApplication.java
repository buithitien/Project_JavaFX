package com.example.quanlytv;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {
    VBox televitionsRoot = new VBox();
    TextField tfName = new TextField();

    TextField tfBrand = new TextField();

    TextField tfHigh_resolution = new TextField();
    TextField tfQuantity = new TextField();
    TextField tfImage = new TextField();

    TextField tfSize = new TextField();

    TextField tfPrice = new TextField();


    @Override

    public void start(Stage stage) throws IOException {
        DBConnection dbConnection = new DBConnection();
        VBox root = new VBox();
        HBox hInsertTelevition = new HBox();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root);


        Button btnAdd = new Button("Add");
        tfName.setPromptText("Name");
        tfBrand.setPromptText("Brand");
        tfQuantity.setPromptText("Quantity");
        tfHigh_resolution.setPromptText("High_resolution");
        tfImage.setPromptText("Image");
        tfSize.setPromptText("Size");
        tfPrice.setPromptText("Price");
        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dbConnection.insertTelevitions(new Televitions(tfName.getText(),tfBrand.getText(), Integer.parseInt(tfQuantity.getText()),tfHigh_resolution.getText(), tfImage.getText(), tfSize.getText(), Float.parseFloat(tfPrice.getText())));
                tfName.clear();
                tfBrand.clear();
                tfQuantity.clear();
                tfHigh_resolution.clear();
                tfImage.clear();
                tfSize.clear();
                tfPrice.clear();

                //VBox televitionsRoot = null;
                getThenDisplayTelevitions(televitionsRoot, dbConnection);
            }
        });
        Button btnEdit = new Button("Update");
        btnEdit.setOnAction(event -> {
            dbConnection.updateTelevitions(new Televitions(tfName.getText(),tfBrand.getText(), Integer.parseInt(tfQuantity.getText()),tfHigh_resolution.getText(), tfImage.getText(),tfSize.getText(), Float.parseFloat(tfPrice.getText())),Integer.parseInt(tfName.getId()));
            tfName.clear();
            tfBrand.clear();
            tfQuantity.clear();
            tfHigh_resolution.clear();
            tfImage.clear();
            tfSize.clear();
            tfPrice.clear();
            getThenDisplayTelevitions(televitionsRoot, dbConnection);
        });
        hInsertTelevition.getChildren().addAll(tfName,tfBrand, tfQuantity,tfHigh_resolution,tfImage,tfSize, tfPrice, btnAdd,btnEdit);
        root.getChildren().addAll(hInsertTelevition, televitionsRoot);
        getThenDisplayTelevitions(televitionsRoot, dbConnection);
        Scene scene = new Scene(scrollPane, 900, 700);
        stage.setTitle("Quản lý cửa hàng TV!");
        stage.setScene(scene);
        stage.show();
    }

    void displayTelevitions(DBConnection dbConnection, VBox root, List<Televitions> televitions) {
        root.getChildren().clear();
        for (int i = 0; i < televitions.size(); i++) {
            final int finialI = i;

            HBox televitonsBox = new HBox();
            Label lbId = new Label("" + televitions.get(i).id);
            Label lbName = new Label(televitions.get(i).name);
            Label lbBrand = new Label("" + televitions.get(i).brand);
            Label lbQuatity = new Label("" + televitions.get(i).quantity);
            Label lbHigh_resolution = new Label("" + televitions.get(i).high_resolution);


//
            Image image = new Image(televitions.get(i).image);
            ImageView imageView = new ImageView(image);
//            //Setting the position of the image
//            imageView.setX(50);
//            imageView.setY(25);
//            //setting the fit height and width of the image view
            imageView.setFitHeight(200);
            imageView.setFitWidth(200);
////            //Setting the preserve ratio of the image view
////            imageView.setPreserveRatio(true);

//            Label lbImage = new Label("" + televitions.get(i).image);

            Label lbSize = new Label("" + televitions.get(i).size);
            Label lbPrice = new Label("" + televitions.get(i).price);


            //Delete
            Button btnDelete = new Button("Delete");
            btnDelete.setOnAction(actionEvent -> {
                System.out.println("Click delete " + televitions.get(finialI).id);
                dbConnection.deleteTelevitions(televitions.get(finialI).id);
                getThenDisplayTelevitions(root, dbConnection);
            });

            // Update
            Button btnUpdate = new Button("Update");
            btnUpdate.setOnAction(actionEvent -> {
                tfName.setText(String.valueOf((televitions.get(finialI).name)));
                tfBrand.setText(String.valueOf((televitions.get(finialI).brand)));
                tfQuantity.setText(String.valueOf((televitions.get(finialI).quantity)));
                tfHigh_resolution.setText(String.valueOf((televitions.get(finialI).high_resolution)));
                tfImage.setText(String.valueOf((televitions.get(finialI).image)));
                tfSize.setText(String.valueOf((televitions.get(finialI).size)));
                tfPrice.setText(String.valueOf((televitions.get(finialI).price)));
                tfName.setId("" + televitions.get(finialI).id);



            });
            televitonsBox.setSpacing(50);
            televitonsBox.getChildren().addAll(lbId, lbName, lbBrand,lbQuatity,lbHigh_resolution,imageView,lbSize, lbPrice, btnDelete, btnUpdate);
            root.getChildren().add(televitonsBox);
        }
    }

    private void getThenDisplayTelevitions(VBox root, DBConnection dbConnection) {
        List<Televitions> televitions = dbConnection.getTelevitions();
        displayTelevitions(dbConnection, root, televitions);
    }

    public static void main(String[] args) {
        launch();
    }
}