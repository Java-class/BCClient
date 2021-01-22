package ir.javaclass.util;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

public class ConnectionUtil {
    private static Web3j web3jConnection = null;
    private static Web3j web3jPeerInfoConnection = null;

    private ConnectionUtil() {
        super();
    }


    public static Web3j getWeb3jConnection() {
        if (web3jConnection == null) {
            synchronized (Web3j.class) {
                if (web3jConnection == null) {
                    try {
                        System.out.println("Connecting to Ethereum ...");
                        web3jConnection = Web3j.build(new HttpService("http://127.0.0.1:7545"));
                        System.out.println("Successfully connected to Ethereum Network.");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return web3jConnection;
    }


    public static Web3j getWeb3jPeerInfoConnection() {
        if (web3jPeerInfoConnection == null) {
            synchronized (Web3j.class) {
                if (web3jPeerInfoConnection == null) {
                    try {
                        System.out.println("Connecting to Ethereum Peer Info ...");
                        web3jPeerInfoConnection = Web3j.build(new HttpService("http://127.0.0.1:7545"));
                        System.out.println("Successfully connected to Ethereum Peer Info Network.");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return web3jPeerInfoConnection;
    }

    public static void shutdown() {
        getWeb3jConnection().shutdown();
        getWeb3jPeerInfoConnection().shutdown();
    }
}
