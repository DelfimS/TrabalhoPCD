package Utilities;

import Utilities.News_File;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class File_Handler {

    public static ArrayList<News_File> getFiles(String s){
        ArrayList<News_File> list=new ArrayList<>();
        File[] f=new File(s).listFiles();
        if(f==null) {
            System.out.println("Path not Found");
            return null;
        }
        for (File file:f) {
            News_File nf=new News_File(file);
            list.add(nf);
        }
        return list;
    }

//    void addFilesToList(DefaultListModel<News_File> list){
//        for (News_File nf:this.files) {
//        	nf.setEncontrados(0);
//            list.addElement(nf);
//        }
//    }
}
