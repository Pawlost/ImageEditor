package sample;

import javax.swing.*;

public class ImageEditor {
    public static void main(String[] args) {
        new ImageEditor();
    }

    public ImageEditor(){
        JFrame frame = new JFrame("My First GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        frame.setVisible(true);
    }
}
