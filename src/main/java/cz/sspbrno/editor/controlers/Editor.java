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

    @FXML
    private RadioButton originalImage;

    @FXML
    private RadioButton modifiedImage;

    @FXML
    private MenuItem negativeButton;

    @FXML
    private MenuItem tresholdButton;

    @FXML
    private ImageView picture;

    @FXML
    private Image oImage;
    
    @FXML
    public Image mImage;

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
        enableButtons();
    }

    @FXML
    public void generateImage() {
        oImage = SwingFXUtils.toFXImage(makeColoredImage(), null);
        setOriginaImage();
        enableButtons();
    }

    public BufferedImage makeColoredImage() {
        BufferedImage bImage = new BufferedImage(600, 600, BufferedImage.TYPE_3BYTE_BGR);
        //RED
        for (int x = 0; x < bImage.getWidth(); x++){
            for (int y = 0; y < bImage.getHeight()/2; y++){
                bImage.setRGB(x, y, new Color(Math.abs(255 - y)%255, 0, 0).getRGB());
            }
        }
        int i = 0;
        for (int x = bImage.getWidth() - 1; x >= 0 ; x--){
            i ++;
            for (int y = bImage.getHeight() - 1; y >= bImage.getHeight()/2; y--){
                bImage.setRGB(y , x, new Color(0, Math.abs(255 - i)%255, 0).getRGB());
            }
        }
        return bImage;
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
        originalImage.setSelected(true);
        modifiedImage.setSelected(false);
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
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Treshold.fxml"));
        Parent treshooldSelector = loader.load();
        Treshold controler = loader.getController();

        //Initialize stage
        Stage s = new Stage();
        s.setResizable(false);
        s.initStyle(StageStyle.UNDECORATED);
        s.setScene(new Scene(treshooldSelector));
        s.show();

        //Set on action
        s.setOnHiding(windowEvent -> {
            picture.setImage(mImage);
            this.modifiedImage.setDisable(false);
            this.originalImage.setDisable(false);
        });

        controler.getCancelButton().setOnAction(e -> {
            s.close();
        });

        controler.getApplyButton().setOnAction(actionEvent -> {
            mImage = controler.getImage();
            setModifiedImage();
        });

        controler.setImage(oImage);
    }

    private void enableButtons(){
        tresholdButton.setDisable(false);
        negativeButton.setDisable(false);
    }

    @FXML
    public void restore() {
        originalImage.fire();
    }

    @FXML
    public void setOriginaImage() {
        picture.setImage(oImage);
        originalImage.setSelected(true);
        modifiedImage.setSelected(false);
    }

    @FXML
    public void setModifiedImage() {
        picture.setImage(mImage);
        originalImage.setSelected(false);
        modifiedImage.setSelected(true);
    }
}
