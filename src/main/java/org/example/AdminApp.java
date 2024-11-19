package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class AdminApp extends JFrame{
    private static final BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    
    private JFrame window;
    private JPanel mainPanel;
    private JButton resultButton;
    private JTextArea resultTextArea;

    public AdminApp(){
        window = new JFrame();
        window.setTitle("SDGLR Voting App");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(300, 300);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 240, 240));

        resultButton = new JButton("Show Result");
        resultButton.setBackground(new Color(50, 150, 50));
        resultButton.setForeground(Color.WHITE);
        resultButton.setFont(new Font("Arial", Font.BOLD, 14));
        resultButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        resultTextArea = new JTextArea(10, 20);
        resultTextArea.setEditable(false);
        resultTextArea.setBackground(new Color(155, 155, 200));
        
        resultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    resultActionPerformed();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        
        mainPanel.add(createButtonPanel(resultButton));
        mainPanel.add(createResultPanel(resultTextArea));

        window.add(mainPanel);
    }
    
    private JPanel createButtonPanel(JButton button) {
        JPanel panel = new JPanel();
        panel.add(button);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }
    
    private JPanel createResultPanel(JTextArea textArea) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel resultLabel = new JLabel("Result:");
        resultLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(resultLabel);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane);
        return panel;
    }

    private void resultActionPerformed(){
        String contractAddress = "0xa68a1bfdc718658afdd333c04ab1ed1165af12e5";
        String privateKey = "0x46991fc6644090a94f3a6d6d146daf17463a5d16e37adab63dc4656579836c30";
        
        String nodeUrl = "HTTP://192.168.231.131:7545";
        Web3j web3j = Web3j.build(new HttpService(nodeUrl));

        Credentials credentials = Credentials.create(privateKey);
        VotingContract votingContract = new VotingContract(contractAddress, web3j, credentials, GAS_PRICE, GAS_LIMIT);

        resultTextArea.setText("");
        String[] options = {"Abderrahmane","Zakaria","Amine","Yassine","Hamza","Oussama"};
        for (int i = 0; i < options.length; i++) {
            BigInteger score;
            try {
                score = votingContract.votesCount(stringToBytes32(options[i])).send();
                resultTextArea.append(options[i] + "------> " + score + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Bytes32 stringToBytes32(String string) {
        byte[] byteValue = string.getBytes();
        byte[] byteValueLen32 = new byte[32];
        System.arraycopy(byteValue, 0, byteValueLen32, 0, byteValue.length);
        return new Bytes32(byteValueLen32);
    }

    public void show() {
        window.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AdminApp app = new AdminApp();
                app.show();
            }
        });
    }
}
