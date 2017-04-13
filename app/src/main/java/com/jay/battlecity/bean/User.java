package com.jay.battlecity.bean;

import java.util.List;

/**
 * 用户类
 * Created by Jay on 2017/4/9.
 */

public class User {
    //基本信息
    private String token;
    private String id;
    private String nickname;
    private int age;
    private String sex;
    private String avatar;

    //排位信息
    private String honor;    //等级（青铜……）
    private int mvp;    //MVP数
    private int gameTimes;   //比赛次数
    private int totalKill;  //总击杀
    private int maxKill;    //最多击杀
    private int rankOfFriends;  //好友排名
    private int rankOfworld;    //世界排名

    //财产信息
    private int money;
    private List<Integer> skinsOfTank; //已购买的所有坦克皮肤id
    private List<Integer> skinsOfBullet;   //已购买的所有子弹皮肤id
    private List<Integer> boxes;    //已拥有的所有宝箱id

    //其他
    private List<Integer> friends;  //好友id
    private List<Integer> mails;   //邮件id
    private int signin; //本月签到天数
}
