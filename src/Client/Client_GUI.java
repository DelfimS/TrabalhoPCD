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
    private JTextArea File_Text;
    private DefaultListModel<String> Title_List;
    private JList Title_Table;

    public void init(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                drawGUI();
            }
        });
    }

    private void drawGUI(){
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setSize(1000, 500);
        addElements(window);
        window.setVisible(true);
    }

    private void addElements(JFrame w) {
        w.setLayout(new BorderLayout());
        JButton button = new JButton("Search");
        JTextField search_bar = new JTextField();
        JPanel Search = new JPanel();
        Search.setLayout(new GridLayout(1, 2));
        Search.add(button);
        Search.add(search_bar);
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
        //fh.addFilesToList(listModel);
        addListeners(Title_Table,search_bar,button);
    }
    
    private void addListeners(JList<String> list,JTextField tf,JButton b) {
    	list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
            }
        });
    	tf.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void setFile_Text(String selected) {
//            File_Text.setText(selected);
//            File_Text.append("\n\n");
//            File_Text.append(selected.getContent());
//            File_Text.setCaretPosition(0);
    }

    
    private void setTitle_List(ArrayList<String> list) {
            for (String nf:list) {
    			Title_List.addElement(nf);
    		}
    }

}