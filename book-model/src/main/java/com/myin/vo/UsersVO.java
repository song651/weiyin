package com.myin.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsersVO {
    private String id;
    private String mobile;
    private String nickname;
    private String imoocNum;
    private String face;
    private Integer sex;
    private Date birthday;
    private String country;
    private String province;
    private String city;
    private String district;
    private String description;
    private String bgImg;
    private Integer canImoocNumBeUpdated;
    private Date createdTime;
    private Date updatedTime;
    private String userToken;       // 用户token，传递给前端


    private Integer myFollowsCounts;  //关注博主总数量
    private Integer myFansCounts;     //粉丝总数量
//    private Integer myLikedVlogCounts;  //所有喜欢vlog的数量,不用返回给前端
    private Integer totalLikeMeCounts;  //所有点赞我的总数

}