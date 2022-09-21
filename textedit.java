package projectss.texteditor;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.*;
import java.awt.event.*;
// import javax.swing.JFrame;
// import javax.swing.JLabel;
// import javax.swing.JScrollPane;
// import javax.swing.JSpinner;
// import javax.swing.JTextArea;
// import javax.swing.ScrollPaneConstants;
// import javax.swing.event.ChangeEvent;
// import javax.swing.event.ChangeListener;
// import nejava.guifiles.labels;
// import java.awt.Dimension;
// import java.awt.FlowLayout;
// import nejava.guifiles.flowlayout;
// import java.awt.Font;
// //import java.awt.LayoutManager;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.util.Scanner;

public class textedit extends JFrame implements ActionListener {
    JTextArea textarea;
    JLabel fontlabel ;
    JScrollPane scroll;
    JSpinner fontspin;
    JButton colorbButton;
    JComboBox fontbox;
    JMenuBar menubar;
    JMenu files;
    JMenuItem open;
    JMenuItem save;
    JMenuItem exit;

    textedit(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("textpad");
        this.setSize(500,500);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);
        
        textarea  = new JTextArea();
        //textarea.setPreferredSize(new Dimension(480,480));
        textarea.setLineWrap(true);
        textarea.setWrapStyleWord(true);
        textarea.setFont(new Font("Arial",Font.PLAIN,20));


        scroll = new JScrollPane(textarea);
        scroll.setPreferredSize(new Dimension(480,480));
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        fontlabel = new JLabel();
        fontlabel.setText("fontsize:");

        fontspin = new JSpinner();
        fontspin.setPreferredSize(new Dimension(50,25));
        fontspin.setValue(20);
        fontspin.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                textarea.setFont(new Font(textarea.getFont().getFamily(),Font.PLAIN,  (int) fontspin.getValue())); 
            }     
        });
        colorbButton = new JButton("color");
        colorbButton.addActionListener(this);

        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontbox = new JComboBox<>(fonts);
        fontbox.addActionListener(this);
        fontbox.setSelectedItem("Arial");
        
    // menu bar area
        menubar = new JMenuBar();
        files = new JMenu("Files");
        open = new JMenuItem("open");
        save = new JMenuItem("save");
        exit = new JMenuItem("exit");

        open.addActionListener(this);
        save.addActionListener(this);
        exit.addActionListener(this);

        files.add(open);
        files.add(save);
        files.add(exit);
        menubar.add(files);


    

        // menu bar 
        this.setJMenuBar(menubar);
        this.add(fontlabel);
        this.add(fontspin); 
        this.add(colorbButton);
        this.add(fontbox);
        this.add(scroll);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==colorbButton){
            JColorChooser colorchooser = new JColorChooser();

            Color color = colorchooser.showDialog(null, "choose colot", Color.BLACK);
            textarea.setForeground(color);
        }
        
        if(e.getSource()==fontbox){
            textarea.setFont(new Font((String)fontbox.getSelectedItem(),Font.PLAIN,textarea.getFont().getSize()));
        }

        if(e.getSource()==open){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            // to search certain file extension

            FileNameExtensionFilter filter = new FileNameExtensionFilter("text files", "txt");
            fileChooser.setFileFilter(filter);


            int result = fileChooser.showOpenDialog(null);
            if(result == JFileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner filein = null;
                try {
                    filein = new Scanner(file);
                    if(file.isFile()){
                        while(filein.hasNextLine()){ // reading file line by line
                            String line = filein.nextLine()+"\n";
                            textarea.append(line);
                        }
                    }
                } catch (FileNotFoundException e1) {
                    
                    e1.printStackTrace();
                }
                finally{
                    filein.close();
                }
                
            }

        }
        if(e.getSource()==save){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));//. for default paths otherwise a real file path
            int result = fileChooser.showSaveDialog(null);

            if(result == JFileChooser.APPROVE_OPTION){
                File file;
                PrintWriter fileout =null;

                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    fileout = new PrintWriter(file);
                    fileout.println(textarea.getText());
                } catch (FileNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                finally{
                    fileout.close();
                }
            }
        }
        if(e.getSource()==exit){
            System.exit(0);
        }
    }
    
}
