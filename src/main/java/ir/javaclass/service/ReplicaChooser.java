package ir.javaclass.service;

import ir.javaclass.entity.Peer;
import ir.javaclass.model.UserPlan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplicaChooser {

    private final PeerService peerService;

    public List<String> choseStoragePeer(long itemLength, UserPlan userPlan){
        List<String> publicUrl = new ArrayList<>();
        List<Peer> peerInfoList = peerService.getPeerInfoList();
        peerInfoList = peerInfoList.stream().filter(p -> p.getStatus().equals(Peer.PeerStatus.ACTIVE)).collect(Collectors.toList());;
        switch (userPlan){
            case CHEAP:{
                sortForCheapPlan(peerInfoList);
                if(peerInfoList.size() > 0)
                    publicUrl.add(peerInfoList.get(0).getUrl());
                break;
            }
            case NORMAL:{
                sortForNormalPlan(peerInfoList);
                if(peerInfoList.size() > 1) {
                    publicUrl.add(peerInfoList.get(0).getUrl());
                    publicUrl.add(peerInfoList.get(1).getUrl());
                }
                break;
            }

            case PREMIUM:{
                sortForPremiumPlan(peerInfoList);
                if(peerInfoList.size() > 2) {
                    publicUrl.add(peerInfoList.get(0).getUrl());
                    publicUrl.add(peerInfoList.get(1).getUrl());
                    publicUrl.add(peerInfoList.get(2).getUrl());
                }
                break;
            }

            case VIP:{
                sortForVipPlan(peerInfoList);
                if(peerInfoList.size() > 4) {
                    publicUrl.add(peerInfoList.get(0).getUrl());
                    publicUrl.add(peerInfoList.get(1).getUrl());
                    publicUrl.add(peerInfoList.get(2).getUrl());
                    publicUrl.add(peerInfoList.get(3).getUrl());
                    publicUrl.add(peerInfoList.get(4).getUrl());
                }
                break;
            }
        }
        return Collections.unmodifiableList(publicUrl);
    }

    private void sortForCheapPlan(List<Peer> peerList){
        peerList.sort(Comparator.comparing(Peer::getFreeSpace)
                .thenComparing(Peer::getUptimePercentage)
                .thenComparing(Peer::getTotalAvailableTimeInWeek)
                .thenComparing(Peer::getMaxUser));
    }

    private void sortForNormalPlan(List<Peer> peerList){
        peerList.sort(Comparator.comparing(Peer::getFreeSpace)
                .thenComparing(Peer::getUptimePercentage)
                .thenComparing(Peer::getTotalAvailableTimeInWeek)
                .thenComparing(Peer::getMaxUser));
    }

    private void sortForPremiumPlan(List<Peer> peerList){
        peerList.sort(Comparator.comparing(Peer::getTotalAvailableTimeInWeek)
                .thenComparing(Peer::getUptimePercentage)
                .thenComparing(Peer::getFreeSpace)
                .thenComparing(Peer::getMaxUser));
    }

    private void sortForVipPlan(List<Peer> peerList){
        peerList.sort(Comparator.comparing(Peer::getUptimePercentage)
                .thenComparing(Peer::getTotalAvailableTimeInWeek)
                .thenComparing(Peer::getMaxBandwidth)
                .thenComparing(Peer::getFreeSpace)
                .thenComparing(Peer::getMaxUser));
    }
}
