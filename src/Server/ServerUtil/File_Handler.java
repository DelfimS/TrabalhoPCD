package Server.ServerUtil;

import Server.DataTypes.News_File;

import java.io.File;
import java.util.ArrayList;

public class File_Handler {

    public static ArrayList<News_File> getFiles(String s) {
        ArrayList<News_File> list = new ArrayList<>();
        File[] f = new File(s).listFiles();
        if (f == null) {
            System.out.println("Path not Found");
            return null;
        }
        for (File file : f) {
            News_File nf = new News_File(file);
            list.add(nf);
        }
        return list;
    }
}

