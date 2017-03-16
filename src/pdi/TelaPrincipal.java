package pdi;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Andre
 */
public class TelaPrincipal extends JFrame{
    
    private JComboBox imagensJcomboBox;
    private JLabel label;
    private static final String[] names = {"","/imagens/bubbles.jpg","/imagens/circuit.jpg"};
    private Icon[] icons = {
        new ImageIcon(getClass().getResource(names[0])),
        new ImageIcon(getClass().getResource(names[1])),
        new ImageIcon(getClass().getResource(names[2]))
    };
    
    private Graph gp;

    public TelaPrincipal(){
        super("PDI");
        setLayout(new FlowLayout(5));
        
        imagensJcomboBox = new JComboBox(names); //configura o comboBox
        imagensJcomboBox.setMaximumRowCount(3);  //exibe três linhas
        
        
        imagensJcomboBox.addItemListener(
            new ItemListener() //classe interna anônima
            {
                //trata evento JComboBox
                public void itemStateChanged(ItemEvent e){
                    //determina se o item selecionado 
                    if(e.getStateChange() == ItemEvent.SELECTED)
                        label.setIcon(icons[
                                imagensJcomboBox.getSelectedIndex()]);
                }
            }
        );
                
        add(imagensJcomboBox); //adiciona combobox ao JFrame
        label = new JLabel(icons[0]); //exibe primeiro icone
        add(label); //adiciona rótulo ao JFrame
        
    }
    
}
