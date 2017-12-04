package Server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class News_File implements Comparable<News_File>{
    private String content="";
    private String title="";
    private int encontrados=0;

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

    public void setTitle(String s){
        this.title=s;
    }

    public String toString(){
        if(encontrados!=0)return (encontrados+"-"+this.title);
        return "=>"+this.title;
        		
    }
    
    public String getSearchText() {
    	return this.title+" "+this.getContent();
    }

	public int getEncontrados() {
		return encontrados;
	}

	public void setEncontrados(int encontrados) {
		this.encontrados = encontrados;
	}

	@Override
	public int compareTo(News_File o) {
        return o.getEncontrados()-this.encontrados;
	}


}
