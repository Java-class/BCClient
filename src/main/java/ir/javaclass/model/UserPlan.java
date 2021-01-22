package ir.javaclass.model;

public enum UserPlan {
    CHEAP(1),
    NORMAL(2),
    PREMIUM(3),
    VIP(5);

    private final int replicaCount;

    UserPlan(int replicaCount) {
        this.replicaCount = replicaCount;
    }

    public int getReplicaCount() {
        return replicaCount;
    }
}
