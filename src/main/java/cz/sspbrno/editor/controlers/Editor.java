package cz.sspbrno.editor.controlers;

import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Editor 
{
    
    @FXML
    private AnchorPane mainPane;

    @FXML
    private ImageView picture;
    
    @FXML
    private Menu menuFilters;
    
    @FXML
    private ImageView miniPicture;
    
    final FileChooser fileChooser = new FileChooser();
    
    @FXML
    public void exit(ActionEvent event) {
        System.exit(0);
    }
    
    @FXML
    public void loadImage() throws Exception{
        fileChooser.setTitle("Otevri soubor");
        File file = fileChooser.showOpenDialog(new Stage());
        Image image = new Image(new FileInputStream(file));
        picture.setImage(image);
        miniPicture.setImage(image);
    }
    
    @FXML
    public void saveImage() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("All Images", ".jpeg", ".jpg", "*.png");
        fileChooser.setSelectedExtensionFilter(extFilter);
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
}
