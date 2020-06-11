package chess.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PromotionWindow extends JFrame {
    public static String promoteTo = "queen";
    public PromotionWindow() {
        this.setSize(616, 639);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setTitle("promote to: queen");

        JButton queenButton = new JButton();
        queenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promoteTo = "queen";
                setTitle("promote to: " + promoteTo);
            }
        });
        queenButton.setBounds(0, 0, 300, 300);
        queenButton.setText("queen");
        queenButton.setFont(new Font("Font", 0, 50));
        JButton rookButton = new JButton();
        rookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promoteTo = "rook";
                setTitle("promote to: " + promoteTo);
            }
        });
        rookButton.setBounds(300, 0, 300, 300);
        rookButton.setText("rook");
        rookButton.setFont(new Font("Font", 0, 50));
        JButton knightButton = new JButton();
        knightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promoteTo = "knight";
                setTitle("promote to: " + promoteTo);
            }
        });
        knightButton.setBounds(0, 300, 300, 300);
        knightButton.setText("knight");
        knightButton.setFont(new Font("Font", 0, 50));
        JButton bishopButton = new JButton();
        bishopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promoteTo = "bishop";
                setTitle("promote to: " + promoteTo);
            }
        });
        bishopButton.setBounds(300, 300, 300, 300);
        bishopButton.setText("bishop");
        bishopButton.setFont(new Font("Font", 0, 50));
        this.add(queenButton);
        this.add(rookButton);
        this.add(knightButton);
        this.add(bishopButton);
    }

}
