package Server.DataTypes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class News_File implements Comparable<News_File>{
    private String content="";
    private String title="";

    public News_File(File f){
        try {
            Scanner sc=new Scanner(new FileInputStream(f),"UTF-8");
            if(sc.hasNextLine())
            this.title=sc.nextLine();
            if(sc.hasNextLine())
            this.content=sc.nextLine();
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public String getTitle() {
		return title;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private String getContent() {
        return content;
    }

    public String getShowText(){
        return title+"\n\n"+content;
    }

    public void setTitle(String s){
        this.title=s;
    }

    public String toString(){

        return this.title+" "+this.content;

    }
    
    public String getSearchText() {
    	return this.title+" "+this.getContent();
    }

	@Override
    public int compareTo(News_File o) {return 1;
            //o.getEncontrados()-this.encontrados;
         }


}
