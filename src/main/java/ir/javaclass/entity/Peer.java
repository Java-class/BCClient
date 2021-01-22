package ir.javaclass.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.web3j.tuples.generated.Tuple11;

import java.math.BigInteger;

@NoArgsConstructor
@ToString
@Setter
@Getter
public class Peer {
    private int id;
    private String owner;
    private String url;
    private long totalSpace;
    private long usedSpace;
    private long maxBandwidth;
    private int maxUser;
    private int uptimePercentage;
    private String availableTimeRange;
    private String date;
    private PeerStatus status;
    @Override
    public Object clone() {
        return new Peer(this);
    }

    public Peer(Peer peer){
        Peer copyPeer = new Peer();
        copyPeer.setId(peer.getId());
        copyPeer.setOwner(peer.getOwner());
        copyPeer.setUrl(peer.getUrl());
        copyPeer.setTotalSpace(peer.getTotalSpace());
        copyPeer.setUsedSpace(peer.getUsedSpace());
        copyPeer.setMaxBandwidth(peer.getMaxBandwidth());
        copyPeer.setMaxUser(peer.getMaxUser());
        copyPeer.setUptimePercentage(peer.getUptimePercentage());
        copyPeer.setAvailableTimeRange(peer.getAvailableTimeRange());
        copyPeer.setDate(peer.getDate());
        copyPeer.setStatus(peer.getStatus());
    }

    public int getTotalAvailableTimeInWeek(){
        try {
            String s = this.availableTimeRange;
            if (s.contains("*")) {
                String[] key = s.split("\\*");
                return Integer.parseInt(key[0]) * Integer.parseInt(key[1]);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return 0;
    }

    public long getFreeSpace(){
        return totalSpace - usedSpace;
    }


    public Peer(Tuple11<BigInteger, String, String, BigInteger, BigInteger, BigInteger, BigInteger, BigInteger, String, String, BigInteger> value) {
        this.id = value.component1().intValue();
        this.owner = value.component2();
        this.url = value.component3();
        this.totalSpace = value.component4().longValue();
        this.usedSpace = value.component5().longValue();
        this.maxBandwidth = value.component6().longValue();
        this.maxUser = value.component7().intValue();
        this.uptimePercentage = value.component8().intValue();
        this.availableTimeRange = value.component9();
        this.date = value.component10();
        this.status = PeerStatus.toEnum(value.component11().intValue());
    }

    public enum PeerStatus{
        ACTIVE,
        BLOCKED,
        SUSPENDED;

        public static PeerStatus toEnum(int id){
          if(id==1)
              return ACTIVE;
          else if(id==2)
              return BLOCKED;
          else
              return SUSPENDED;
        }
    }
}

