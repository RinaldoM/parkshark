package com.switchfully.sharkitects.members;

import javax.persistence.*;

@Entity
@Table(name = "MEMBERSHIP_LEVEL")
public class MembershipLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "membership_level_seq")
    @SequenceGenerator(name = "membership_level_seq", sequenceName = "membership_level_seq", allocationSize = 1)
    private Long id;
    @Column(name = "NAME")
    private MembershipLevelName membershipLevelName;
    @Column(name = "MONTHLY_COST")
    private double monthlyCost;
    @Column(name = "ALLOCATION_REDUCTION")
    private double allocationReduction;
    @Column(name = "MAX_ALLOCATION_TIME")
    private double maxAllocationTime;


    public MembershipLevel() {
    }

    public MembershipLevel(Long id, MembershipLevelName membershipLevelName, double monthlyCost, double allocationReduction, double maxAllocationTime) {
        this.id = id;
        this.membershipLevelName = membershipLevelName;
        this.monthlyCost = monthlyCost;
        this.allocationReduction = allocationReduction;
        this.maxAllocationTime = maxAllocationTime;
    }

    public Long getId() {
        return id;
    }

    public MembershipLevelName getMembershipLevelName() {
        return membershipLevelName;
    }

    public double getMonthlyCost() {
        return monthlyCost;
    }

    public double getAllocationReduction() {
        return allocationReduction;
    }

    public double getMaxAllocationTime() {
        return maxAllocationTime;
    }
}
