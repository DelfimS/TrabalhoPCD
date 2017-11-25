package Utilities;


import javax.swing.*;
import java.awt.*;

public class ErrorWindow {

    public static void init(String msg){
        JDialog frame=new  JDialog();
        frame.setSize(100,100);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridLayout(1,1));
        frame.add(new JLabel(msg));
        frame.setLocation(400,500);
    }
    public static void main(String[] args) {
        init("Ola");
    }
}
