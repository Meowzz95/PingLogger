package ui;

import controller.PingController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by jjzzz on 10/2/2016.
 */
public class MainUi {
    private JPanel mainPanel;
    private JButton btnShowAllResults;
    private JButton button2;
    private JButton button3;
    private JLabel tvLessThan30;
    private JLabel tvMoreThan100;
    private JLabel tv30To100;
    private JLabel tvFailure;
    private JLabel tvInfo;
    private static PingController controller;

    public MainUi() {
        btnShowAllResults.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AllResultDialog allResultDialog=new AllResultDialog();
                allResultDialog.setResults(controller.getResults());
                allResultDialog.pack();
                allResultDialog.setVisible(true);
            }
        });
    }

    public void updateReadings(int less30, int between30to100, int greater100, int failure){
        tvLessThan30.setText(String.valueOf(less30));
        tv30To100.setText(String.valueOf(between30to100));
        tvMoreThan100.setText(String.valueOf(greater100));
        tvFailure.setText(String.valueOf(failure));

    }
    public void setInfo(String info){
        tvInfo.setText(info);
    }
    public static void main(String[] args) {
        final MainUi mainUi=new MainUi();
        final JFrame frame = new JFrame("MainUi");
        frame.setContentPane(mainUi.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(200,200,500,500);
        //frame.pack();
        frame.setVisible(true);
        System.out.println("Ui initialized starting controller");
        controller = new PingController("192.168.99.1").setListener(mainUi::updateReadings);
        try {
            controller.start();
            int i=0;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
