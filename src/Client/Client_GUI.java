package Client;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;



class Client_GUI {
    private JFrame frame;
    private JTextArea File_Text;
    private DefaultListModel<String> Title_List;
    private JList Title_Table;
    private Client client;
    private JTextField searchBar;

    public JFrame getFrame() {
        return frame;
    }

    public DefaultListModel<String> getTitle_List() {
        return Title_List;
    }

    public JTextArea getFile_Text() {
        return File_Text;
    }

    {
    }

    public void init(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                addClientWorker();
                drawGUI();
            }
        });
    }
    private void addClientWorker(){
        client = new Client(this).init();
        client.execute();
    }
    private void drawGUI(){
        JFrame window = new JFrame();
        this.frame=window;
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(1000, 500);
        addElements(window);
        window.setVisible(true);
    }

    private void addElements(JFrame w) {
        w.setLayout(new BorderLayout());
        JButton button = new JButton("Search");
        searchBar = new JTextField();
        JPanel Search = new JPanel();
        Search.setLayout(new GridLayout(1, 2));
        Search.add(button);
        Search.add(searchBar);
        w.add(Search, BorderLayout.NORTH);
        DefaultListModel<String> listModel = new DefaultListModel<>();
        this.Title_Table = new JList<>(listModel);
        this.Title_List = listModel;
        this.File_Text = new JTextArea();
        File_Text.setWrapStyleWord(true);
        File_Text.setLineWrap(true);
        File_Text.setEditable(false);
        JScrollPane scrollPaneFile_Text = new JScrollPane(File_Text);
        JScrollPane scrollPaneTitle_table = new JScrollPane(Title_Table);
        JPanel Text = new JPanel();
        Text.setLayout(new GridLayout(1, 2));
        Text.add(scrollPaneTitle_table);
        Text.add(scrollPaneFile_Text);
        w.add(Text);
        addListeners(Title_Table,searchBar,button);
    }
    
    private void addListeners(JList<String> list,JTextField tf,JButton b) {
    	list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        client.requestText(e.toString());

                    }
                });
            }
        });
    	tf.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER)
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        System.out.println("Search Requested");client.requestSearch(searchBar.getText());
                    }
                });
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        System.out.println("Search Requested");client.requestSearch(searchBar.getText());
                    }
                });
            }
        });
    }

    private void setFile_Text(String selected) {
            File_Text.setText(selected);
            File_Text.append("\n\n");
            File_Text.append(selected);
            File_Text.setCaretPosition(0);
    }

    
    private void setTitle_List(ArrayList<String> list) {
            for (String nf:list) {
    			Title_List.addElement(nf);
    		}
    }

}