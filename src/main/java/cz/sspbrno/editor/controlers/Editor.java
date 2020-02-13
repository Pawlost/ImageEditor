package cz.sspbrno.editor.controlers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Editor implements Initializable {
    public RadioButton originalImage;
    public RadioButton modifiedImage;
    public MenuItem negativeButton;
    public MenuItem tresholdButton;
    @FXML
    private ImageView picture;


    private Image oImage;

    public static Image mImage;


    @FXML
    public void saveImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        FileChooser.ExtensionFilter jpegFilter = new FileChooser.ExtensionFilter("JPEG", "jpg");
        FileChooser.ExtensionFilter pngFilter = new FileChooser.ExtensionFilter("PNG", "png");
        fileChooser.getExtensionFilters().add(jpegFilter);
        fileChooser.getExtensionFilters().add(pngFilter);
        Image image = picture.getImage();
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @FXML
    public void exit() {
        System.exit(0);
    }

    @FXML
    public void loadImage() throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All Images", "*.jpeg", "*.jpg", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(null);
        Image image = new Image(new FileInputStream(file));
        picture.setImage(image);
        oImage = picture.getImage();
        negativeButton.setDisable(false);
        tresholdButton.setDisable(false);

    }

    @FXML
    public void generateImage() {
        picture.setImage(SwingFXUtils.toFXImage(makeColoredImage(), null));
        oImage = picture.getImage();
        negativeButton.setDisable(false);
    }

    public BufferedImage makeColoredImage() {
        BufferedImage bufferedImage = new BufferedImage(600, 600, BufferedImage.TYPE_3BYTE_BGR);
        for (int i = 0; i < bufferedImage.getWidth(); i++) {
            for (int j = 0; j < bufferedImage.getHeight(); j++) {
                bufferedImage.setRGB(i, j, new Color((i + j) % 256, Math.abs(i - j) % 256, (i % (j + 1)) % 256).getRGB());
            }
        }
        return bufferedImage;
    }

    @FXML
    public void negative() {
        BufferedImage img = SwingFXUtils.fromFXImage(picture.getImage(), null);

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                Color clr = new Color(img.getRGB(i, j));
                Color newClr = new Color(255 - clr.getRed(), 255 - clr.getGreen(), 255 - clr.getBlue());
                img.setRGB(i, j, newClr.getRGB());
            }
        }
        mImage = SwingFXUtils.toFXImage(img, null);
        picture.setImage(mImage);
        originalImage.setSelected(false);
        modifiedImage.setSelected(true);
        originalImage.setDisable(false);
        modifiedImage.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final ToggleGroup group = new ToggleGroup();
        originalImage.setToggleGroup(group);
        modifiedImage.setToggleGroup(group);
    }
    
    @FXML
    public void about(ActionEvent event) {
        try {
            Parent root = new FXMLLoader(getClass().getClassLoader().getResource("About.fxml")).load();
            Stage stage = new Stage();
            stage.setTitle("Prase");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
            // Hide this current window (if this is what you want)
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void threshold() throws IOException {
        Treshold.oimage = this.picture.getImage();
        Parent treshooldSelector = FXMLLoader.load(getClass().getClassLoader().getResource("Treshold.fxml"));
        Stage s = new Stage();
        Treshold.s = s;
        s.setResizable(false);
        s.initStyle(StageStyle.UNDECORATED);
        s.setScene(new Scene(treshooldSelector));
        s.show();
        s.setOnHiding(windowEvent -> {
            picture.setImage(mImage);
            this.modifiedImage.setDisable(false);
            this.originalImage.setDisable(false);
        });
    }

    @FXML
    public void restore() {
        originalImage.fire();
    }

    @FXML
    public void setOriginaImage() {
        picture.setImage(oImage);
    }

    @FXML
    public void setModifiedImage() {
        picture.setImage(mImage);
    }

    public void setOriginaImageB(ActionEvent actionEvent) {
        setOriginaImage();
        originalImage.setSelected(true);
        modifiedImage.setSelected(false);
    }
}
