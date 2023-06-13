import org.jfugue.pattern.Pattern;
import org.jfugue.player.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class DiceComposerFrame implements MouseListener {

    JFrame frameObj;
    JPanel[][] buttons = new JPanel[12][17];
    boolean checkColumn[] = new boolean[17];
    JRadioButton r = new JRadioButton("Randomize");
    JRadioButton r1 = new JRadioButton("Choose");
    int measureIds[] = new int[]{96,22,141,41,105,122,11,30,70,121,26,9,112,49,109,14,32,6,128,63,146,46,134,81,117,39,126,56,174,18,116,83,69,95,158,13,153,55,110,24,66,139,15,132,73,58,145,79,40,17,113,85,161,2,159,100,90,176,7,34,67,160,52,170,148,74,163,45,80,97,36,107,25,143,64,125,76,136,1,93,104,157,27,167,154,68,118,91,138,71,150,29,101,162,23,151,152,60,171,53,99,133,21,127,16,155,57,175,43,168,89,172,119,84,114,50,140,86,169,94,120,88,48,166,51,115,72,111,98,142,42,156,75,129,62,123,65,77,19,82,137,38,149,8,3,87,165,61,135,47,147,33,102,4,31,164,144,59,173,78,54,130,10,103,28,37,106,5,35,20,108,92,12,124,44,131};
    JPanel wrapper1;
    // constructor
    DiceComposerFrame()
    {
        frameObj = new JFrame();
        frameObj.add(generateGridPanel(), BorderLayout.CENTER);
        frameObj.add(generateButtonsPanel(), BorderLayout.SOUTH);
        frameObj.setTitle("Mozart Dice Composer");
        ImageIcon img = new ImageIcon("C:\\Users\\Francesca\\Desktop\\dice.png");
        frameObj.setIconImage(img.getImage());
        frameObj.setSize(800, 800);
        frameObj.setVisible(true);
    }

    private JPanel generateGridPanel(){
        JPanel grid = new JPanel(new GridLayout(12,17));
        generateGrid(grid);

        grid.setPreferredSize(new Dimension(450,450));
        JPanel wrapper = new JPanel();
        wrapper.add(grid);
        return wrapper;
    }

    private JPanel generateButtonsPanel(){
        ButtonGroup b = new ButtonGroup();
        b.add(r);
        b.add(r1);
        b.setSelected(r1.getModel(), true);
        r.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                randomize();
            }
        });

        r1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGrid();
            }
        });

        wrapper1 = new JPanel();
        JButton p = new JButton("Play");
        p.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                playMelody();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        wrapper1.add(r);
        wrapper1.add(r1);
        wrapper1.add(p);
        return wrapper1;
    }

    private void generateGrid(JPanel panel){
        int measureCount=0;
        for(int i=0; i<12; i++){
            for(int j=0; j<17; j++){
                buttons[i][j] = new JPanel();
                buttons[i][j].setBorder(new LineBorder(Color.BLACK));
                buttons[i][j].setBackground(Color.GRAY);
                JLabel num = new JLabel("");
                if(i==0 && j==0) {
                    num.setText("");
                    buttons[i][j].setBackground(Color.BLUE);
                }
                else if(i==0) {
                    num.setText(String.valueOf(j));
                    buttons[i][j].setBackground(Color.BLUE);
                }
                else if(j==0) {
                    num.setText(String.valueOf(i + 1));
                    buttons[i][j].setBackground(Color.BLUE);
                }
                else {
                    num.setText(String.valueOf(measureIds[measureCount]));
                    measureCount++;
                }
                num.setForeground(Color.WHITE);
                buttons[i][j].add(num);
                buttons[i][j].addMouseListener(this);
                buttons[i][j].setPreferredSize(new Dimension(40,40));
                panel.add(buttons[i][j]);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!r.isSelected()) {  //Disattiva l'opzione di interagire con la tabella se l'utente è in modalita "randomize"
            for (int i = 0; i < 12; i++) {
                for (int j = 0; j < 17; j++) {
                    if (e.getSource() == buttons[i][j] && buttons[i][j].getBackground().equals(Color.GRAY) && !checkColumn[j] && i != 0 && j != 0) {
                        buttons[i][j].setBackground(Color.RED);
                        checkColumn[j] = true;
                    } else if (e.getSource() == buttons[i][j] && buttons[i][j].getBackground().equals(Color.RED)) {
                        buttons[i][j].setBackground(Color.GRAY);
                        checkColumn[j] = false;
                    }
                }
            }
        }
    }

    private void randomize(){
        Random rand = new Random();
        int diceRolls[] = new int[16];

        for(int i=0; i<diceRolls.length; i++) {
            diceRolls[i] = rand.nextInt(11) + 2;

        }

        int index = 0;
        for(int j=1; j<17; j++){
            for(int i=1; i<12; i++){
                 if(diceRolls[index] == i+1){
                     buttons[i][j].setBackground(Color.RED);
                 }
                 else{
                     buttons[i][j].setBackground(Color.GRAY);
                 }
            }
        index++;
        }

        for(int i=0; i<checkColumn.length; i++)
            checkColumn[i]=true;
    }

    private void playMelody(){
        int[] num = new int[16];
        int k=0, count=0;
        Player p = new Player();
        Pattern melody = new Pattern();
        MeasureCollection mc = new MeasureCollection();

        for(int j=1; j<17; j++){
            for(int i=1; i<12; i++){
                if(buttons[i][j].getBackground().equals(Color.RED)){
                    JLabel lab = (JLabel) buttons[i][j].getComponent(0);
                    num[k] = Integer.parseInt(lab.getText());
                    k++;
                }
            }
        }

            if(k==16) {
                melody.add(mc.produceStaccato(num));
                melody.setTempo(110);
                p.play(melody);
            }


    }

    public void resetGrid(){
        for(int i=1; i<12; i++)
            for(int j=1; j<17; j++)
                buttons[i][j].setBackground(Color.GRAY);

        for(int i=0; i<checkColumn.length; i++)
            checkColumn[i]=false;
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
