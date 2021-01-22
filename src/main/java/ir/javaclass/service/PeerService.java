package ir.javaclass.service;

import ir.javaclass.config.Commons;
import ir.javaclass.contract.PeerList;
import ir.javaclass.entity.Peer;
import ir.javaclass.util.ConnectionUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.tuples.generated.Tuple11;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Getter
public class PeerService {

    private  List<Peer> peerInfoList = null;

    public static List<Peer> list(String pk) throws Exception {
        List<Peer> peers = new ArrayList<>();
        Credentials credentials = Credentials.create(pk);
        PeerList peerList = PeerList.load(Commons.PEER_CONTRACT_ADDRESS, ConnectionUtil.getWeb3jConnection(), credentials, new DefaultGasProvider());
        List<String> list = peerList.getPublicAddress().send();
        for(String add : list) {
            Tuple11<BigInteger, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, String, String, BigInteger> value = peerList.peers(add).send();
            Peer peer = new Peer(value);
            peers.add(peer);
        }
        return peers;
    }

    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void loadPeerInfoJob() {
        log.info("load Peer Info Job Started...");
        try {
            List<Peer> peerList = list(Commons.USER_PRIVATE_KEY);
            if(peerList.size()>0)
                this.peerInfoList = peerList;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        log.info("load Peer Info Job Finished...");
    }

}
