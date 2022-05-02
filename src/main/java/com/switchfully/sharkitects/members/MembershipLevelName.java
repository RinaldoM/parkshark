package com.switchfully.sharkitects.members;

public enum MembershipLevelName {
    BRONZE,SILVER,GOLD;


    public static MembershipLevelName getLevelName(String level){
        switch (level){

            case "SILVER":
                return SILVER;
            case "GOLD":
                return GOLD;
            default:
                return BRONZE;
        }
    }
}
