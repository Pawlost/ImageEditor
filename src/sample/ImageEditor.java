package sample;

import javax.swing.*;
import java.awt.*;

public class ImageEditor {
    public static void main(String[] args) {
        new ImageEditor();
    }

    public ImageEditor(){
        JFrame frame = new JFrame("Image editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300,600);
        frame.setJMenuBar(initMenu());
        JPanel leftPanel = initPanel();
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.add(leftPanel);
        frame.add(panel);
        frame.setVisible(true);
    }

    public JMenuBar initMenu(){
        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        bar.add(file);
        JMenu filters = new JMenu("Filters");
        bar.add(filters);
        JMenu about = new JMenu("About");
        bar.add(about);
        JMenu exit = new JMenu("Exit");
        bar.add(exit);

        return bar;
    }

    public JPanel initPanel(){
        JPanel panel = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();

        panel.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;

        panel.add(new Label(), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;

        JButton select = new JButton("Select Image File");
        select.setSize(300, 20);
        panel.add(select, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;

        JButton matrix = new JButton("Edit Matrix");
        matrix.setSize(300, 20);
        panel.add(matrix, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;

        JButton filter = new JButton("Apply Matrix Filter");
        filter.setSize(300, 20);
        panel.add(filter, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;

        panel.add(new JLabel(), gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;

        JButton generate = new JButton("Generate Image");
        generate.setSize(300, 20);
        panel.add(generate, gbc);

        gbc.gridx = 1;
        gbc.gridy = 6;

        JButton restore = new JButton("Restore Original Image");
        restore.setSize(300, 20);
        panel.add(restore, gbc);

        return panel;
    }
}
