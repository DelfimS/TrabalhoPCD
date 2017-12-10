package Client;


import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class Client_GUI {
    private JFrame frame;
    private JTextArea File_Text;
    private DefaultListModel<String> Title_List;
    private JList Title_Table;
    private ConnectionHandler client;
    private JTextField searchBar;
    private boolean searchLock=true;
    private boolean getTextLock=true;

    public JFrame getFrame() {
        return frame;
    }

    public void setSearchLock(boolean searchLock) {
        this.searchLock = searchLock;
    }

    public void setGetTextLock(boolean getTextLock) {
        this.getTextLock = getTextLock;
    }

    public DefaultListModel<String> getTitle_List() {
        return Title_List;
    }

    public JTextArea getFile_Text() {
        return File_Text;
    }


    public void init(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                client=new ConnectionHandler(Client_GUI.this).init();
                drawGUI();
            }
        });
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
                if (Client_GUI.this.Title_Table.getSelectedValue()!=null) {
                    if (getTextLock) {
                        setGetTextLock(false);
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                client.requestText(Client_GUI.this.Title_Table.getSelectedValue().toString());
                            }
                        });
                    }
                }
            }
        });
    	tf.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    if (searchLock) {
                        setSearchLock(false);
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                client.requestSearch(Client_GUI.this.searchBar.getText());
                            }
                        });
                    }
                }

            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (searchLock) {
                    setSearchLock(false);
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            client.requestSearch(Client_GUI.this.searchBar.getText());
                        }
                    });
                }
            }
        });
    }


}