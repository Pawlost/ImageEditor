package cz.sspbrno.editor.controlers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

public class Treshold implements Initializable {
    public Slider slider;
    public Button apply;
    public Button cancel;
    public CheckBox auto;
    public static Image oimage;
    public static Stage s;
    public ImageView picture;
    public Label text;
    private long nanotime = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cancel.setOnAction(e -> {
            s.hide();
            s.close();
        });
        apply.setOnAction(actionEvent -> {
            Editor.mImage = picture.getImage();
            s.close();
        });
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        picture.setImage(oimage);

        slider.valueProperty().addListener(e -> {
            if (System.nanoTime() - nanotime < 10000000)
                return;
            text.setText(String.valueOf(slider.getValue()));
            BufferedImage bi = SwingFXUtils.fromFXImage(oimage, null);
            for (int i = 0; i < bi.getHeight(); i++) {
                for (int j = 0; j < bi.getWidth(); j++) {
                    Color c = new Color(bi.getRGB(j, i));
                    if (c.getBlue() >= slider.getValue() || c.getGreen() >= slider.getValue() || c.getRed() >= slider.getValue())
                        bi.setRGB(j, i, Color.WHITE.getRGB());
                    else bi.setRGB(j, i, Color.BLACK.getRGB());
                }
            }
            nanotime = System.nanoTime();
            picture.setImage(SwingFXUtils.toFXImage(bi, null));
            //Controller.mImage = picture.getImage();
        });

        auto.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                slider.setValue(getAutomaticTreshold());
            }
        });
    }

    private int getAutomaticTreshold() {
        BufferedImage originalImage = SwingFXUtils.fromFXImage(oimage,null);
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
}
