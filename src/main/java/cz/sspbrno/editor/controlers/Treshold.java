package cz.sspbrno.editor.controlers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

public class Treshold implements Initializable {
    @FXML
    private Slider slider;

    @FXML
    private Button apply;

    @FXML
    private Button cancel;

    @FXML
    private CheckBox auto;

    @FXML
    private ImageView picture;

    @FXML
    private Label text;

    private Image oImage;
    private Image mImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        auto.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                slider.setValue(getAutomaticTreshold());
            }
        });
    }

    public void setImage(Image oimage) {
        this.oImage = oimage;

        setTreshold();

        slider.valueProperty().addListener(e -> {
            setTreshold();
        });
    }

    private void setTreshold() {
        double sliderValue = slider.getValue();
        text.setText(String.valueOf(sliderValue));

        BufferedImage bi = SwingFXUtils.fromFXImage(oImage, null);
        for (int i = 0; i < bi.getHeight(); i++) {
            for (int j = 0; j < bi.getWidth(); j++) {
                Color c = new Color(bi.getRGB(j, i));
                if (c.getBlue() >= slider.getValue() || c.getGreen() >= slider.getValue()
                        || c.getRed() >= slider.getValue()){
                    bi.setRGB(j, i, Color.WHITE.getRGB());
                } else {
                    bi.setRGB(j, i, Color.BLACK.getRGB());
                }
            }
        }

        mImage = SwingFXUtils.toFXImage(bi, null);

        picture.setImage(SwingFXUtils.toFXImage(bi, null));

    }

    private int getAutomaticTreshold() {
        BufferedImage originalImage = SwingFXUtils.fromFXImage(oImage ,null);
        long outInt = 0;
        for (int x = 0; x < originalImage.getWidth(); x++) {
            for (int y = 0; y < originalImage.getHeight(); y++) {
                Color c = new Color(originalImage.getRGB(x, y));
                outInt = outInt + c.getRed() + c.getGreen() + c.getBlue();
            }
        }
        outInt = outInt / (originalImage.getHeight() * originalImage.getWidth() * 3);
        return (int) outInt;
    }

    public Image getImage(){
        return mImage;
    }

    public Button getApplyButton(){
        return apply;
    }

    public Button getCancelButton(){
        return cancel;
    }
}
