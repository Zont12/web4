package org.example.back1;

import jakarta.persistence.*;

import java.io.Serializable;
@Entity
@Table(name="point")
public class Point implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="x", nullable=false)
    private float x;

    @Column(name="y", nullable=false)
    private float y;

    @Column(name="r", nullable=false)
    private float r;

    @Column(name="is_hit", nullable=false)
    private boolean isHit;

    @Column(name="lead_time", nullable=false)
    private long leadTime;

    @Column(name="server_time", nullable=false)
    private String serverTime;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    public Point(){}

    public Point(float x, float y, float r) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.isHit = Checker.check(x, y, r);
        this.leadTime = System.currentTimeMillis();
        this.serverTime = java.time.Instant.now().toString();
        this.user = null;
    }

    public float getX() { return x; }
    public void setX(float x) { this.x = x; }

    public float getY() { return y; }
    public void setY(float y) { this.y = y; }

    public float getR() { return r; }
    public void setR(float r) { this.r = r; }

    public boolean getIsHit() { return isHit; }
    public void setIsHit(boolean hit) { isHit = hit; }

    public long getLeadTime() { return leadTime; }
    public void setLeadTime(long leadTime) { this.leadTime = leadTime; }

    public String getServerTime() { return serverTime; }
    public void setServerTime(String serverTime) { this.serverTime = serverTime; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
