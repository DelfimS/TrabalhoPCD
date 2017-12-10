package Server.DataTypes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class News_File{
    private String content;
    private String title;

    public News_File(File f){
            String title = null;
            String content = null;
        try {
            Scanner sc=new Scanner(new FileInputStream(f),"UTF-8");
            if(sc.hasNextLine())
            title=sc.nextLine();
            if(sc.hasNextLine())
            content=sc.nextLine();
            sc.close();
            this.title=title;
            this.content=content;
        } catch (FileNotFoundException e) {
            System.out.println("Ficheiro nao encontrado: "+f.toString());
        }

    }

    public String getTitle() {
		return title;
	}

    public String getShowText(){
        return title+"\n\n"+content;
    }

    public String toString(){
        return this.title+" "+this.content;
    }


}
