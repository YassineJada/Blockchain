package org.example;

import javax.swing.*;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

public class VoteApp extends JFrame {

    private static final BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);

    private JFrame window;
    private JPanel mainPanel;
    private JTextField privateKeyField;
    private JTextField contractAddressField;
    private JComboBox<String> optionsComboBox;
    private JButton voteButton;

    public VoteApp() {
        window = new JFrame();
        window.setTitle("SDGLR Voting App");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(300, 400);
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        privateKeyField = new JTextField(20);
        contractAddressField = new JTextField(20);
        optionsComboBox = new JComboBox<>(new String[]{"Abderrahmane","Zakaria","Amine","Yassine","Hamza","Oussama"});

        voteButton = new JButton("Vote");
        voteButton.setBackground(new Color(50, 150, 50));
        voteButton.setForeground(Color.WHITE);
        voteButton.setFont(new Font("Arial", Font.BOLD, 14));
        voteButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(240, 240, 240));
        mainPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        addComponentsToPanel();

        voteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    voteActionPerformed();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        window.add(mainPanel);
    }

    private void addComponentsToPanel() {
        mainPanel.add(createPanelWithLabel("Your Private Key:", privateKeyField));
        mainPanel.add(createPanelWithLabel("Contract Address:", contractAddressField));
        mainPanel.add(createPanelWithLabel("Select Option:", optionsComboBox));
        mainPanel.add(createButtonPanel(voteButton));
    }

    private JPanel createPanelWithLabel(String labelText, JComponent component) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(labelText);
        panel.add(label);
        panel.add(component);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }

    private JPanel createButtonPanel(JButton button) {
        JPanel panel = new JPanel();
        panel.add(button);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }
    
    private void voteActionPerformed(){
        String privateKey = privateKeyField.getText();
        String contractAddress = contractAddressField.getText();
        String selectedOption = (String) optionsComboBox.getSelectedItem();

        if (privateKey.isEmpty() || contractAddress.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Private key and contract address are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nodeUrl = "HTTP://172.20.10.13:7545";
        Web3j web3j = Web3j.build(new HttpService(nodeUrl));

        try{
            Credentials credentials = Credentials.create(privateKey);
            VotingContract votingContract = new VotingContract(contractAddress, web3j, credentials, GAS_PRICE, GAS_LIMIT);
            votingContract.submitVote(stringToBytes32(selectedOption)).send();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Private key or contract address are invalid.\n Or you have alredy voted", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JLabel t = new JLabel("You voted for : " + selectedOption);
        mainPanel.add(t);
        mainPanel.revalidate();
        mainPanel.repaint();
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
                VoteApp app = new VoteApp();
                app.show();
            }
        });
    }
}