package Utilities;


import javax.swing.*;
import java.awt.*;

public class ErrorWindow {
    public static void init(String msg,Component frameI){
        JOptionPane.showMessageDialog(frameI,"ERROR: "+msg);
    }
}
