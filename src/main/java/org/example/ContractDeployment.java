package org.example;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import java.math.BigInteger;

public class ContractDeployment {
    private static final BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);
    private static final String PRIVATE_KEY = "0x46991fc6644090a94f3a6d6d146daf17463a5d16e37adab63dc4656579836c30";
    
    public static void main(String[] args) throws Exception{
        // Adresse du nœud Ethereum en mode RPC
        String nodeUrl = "HTTP://192.168.231.131:7545";

        // Chargement du portefeuille
        Credentials credentials = Credentials.create(PRIVATE_KEY);

        // Création d'une instance Web3j
        Web3j web3j = Web3j.build(new HttpService(nodeUrl));

        // Deployer le contract
        VotingContract contract = VotingContract.deploy(web3j, credentials, GAS_PRICE, GAS_LIMIT).send();
        String contractAddress = contract.getContractAddress();
        System.out.println("Contrat déployé à l'adresse : " + contractAddress);

    }
}