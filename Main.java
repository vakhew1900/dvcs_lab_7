package model;

import view.GameFieldWidget;
import view.GameFinishMessageWidget;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(GamePanel::new);

    }

    public static class GamePanel extends JFrame {

        Game game;
        JPanel mainPanel;

        public GamePanel() throws HeadlessException {
            start();

        }

        private void start(){
            game = new Game();
            mainPanel = new JPanel();
            GameFieldWidget gameFieldWidget = new GameFieldWidget(game.gamefield());
            mainPanel.add("CENTER", gameFieldWidget);
            JPanel managePanel = createManagePanel();
            mainPanel.add("EAST", managePanel);
            add(mainPanel);
            defaultSetting();
        }

        public void restart(){
                remove(mainPanel);
                start();
        }

        private JPanel createManagePanel() {

            int WIDTH = 240;
            int HEIGHT = 160;

            JPanel managePanel = new JPanel();
            managePanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
            GameFinishMessageWidget gameFinishMessageWidget = new GameFinishMessageWidget();
            game.addGameFinishedActionListener(gameFinishMessageWidget);
            managePanel.add(gameFinishMessageWidget);

            JButton restartButton = createRestartButton();
            restartButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    restart();
                }
            });

            JButton flowWatterButton = new JButton("Открыть кран");
            flowWatterButton.addActionListener(new WaterStartAction());
            flowWatterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            ;
            managePanel.add("SOUTH", restartButton);
            managePanel.add("SOUTH", flowWatterButton);
            managePanel.setLayout(new BoxLayout(managePanel, BoxLayout.Y_AXIS));
            return managePanel;
        }

        private void defaultSetting() {
            setVisible(true);
            pack();
            setResizable(false);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

        }

        private JButton createRestartButton(){
            JButton btn = new JButton("Перезапустить"); // commit 2
            btn.setAlignmentX(Component.CENTER_ALIGNMENT); // commit 2

            return btn;  // commit 2
        }

       private class WaterStartAction implements ActionListener{

           @Override
           public void actionPerformed(ActionEvent e) {
               if(game.status() == game.RUNNING) {
                   game.flowWater();
                   ((JButton)e.getSource()).setEnabled(false);
               }
           }
       }
    }

}