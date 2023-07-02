package com.myin.controller;


import com.myin.base.BaseInfoProperties;
import com.myin.bo.RegistLoginBO;
import com.myin.grace.result.GraceJSONResult;
import com.myin.grace.result.ResponseStatusEnum;
import com.myin.pojo.Users;
import com.myin.service.UserService;
import com.myin.utils.IPUtil;
import com.myin.utils.SMSUtils;
import com.myin.vo.UsersVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.util.UUID;

@Slf4j
@Api(tags = "PassportController 短信接口模块")
@RequestMapping("passport")
@RestController
public class PassportController extends BaseInfoProperties {

    @Autowired
    private SMSUtils smsUtils;

    @Autowired
    private UserService userService;

    @PostMapping("getSMSCode")
    public GraceJSONResult getSMSCode(@RequestParam String mobile,
                                      HttpServletRequest request) throws Exception {    //RequestParam请求参数，？后面的，loginRegist
        //url: serverUrl + "/passport/getSMSCode?mobile=" + moobile
        if (StringUtils.isBlank(mobile)) {
            return GraceJSONResult.ok();
        }
        log.info(mobile);
        //判断是不是为空，什么也不返回给你
        // 获得用户ip，
        String userIp = IPUtil.getRequestIp(request);
        // 根据用户ip进行限制，限制用户在60秒之内只能获得一次验证码
        redis.setnx60s(MOBILE_SMSCODE + ":" + userIp, userIp);
        //随机生成验证码，整形加上字符串就能变成字符串
        String code = (int)((Math.random() * 9 + 1) * 100000) + "";
        smsUtils.sendSMS(mobile, code);
//        smsUtils.sendSMS(mobile, code);
        log.info(code);
        // 把验证码放入到redis中，用于后续的验证
        redis.set(MOBILE_SMSCODE + ":" + mobile, code, 30 * 60);  //30分钟

        return GraceJSONResult.ok();
    }

    @PostMapping("login")
    public GraceJSONResult login(@Valid @RequestBody RegistLoginBO registLoginBO,
//                                 BindingResult result,    // 对代码有侵入性破坏接口完整性
                                 HttpServletRequest request) throws Exception {
        String mobile = registLoginBO.getMobile();
        String code = registLoginBO.getSmsCode();

        // 1. 从redis中获得验证码进行校验是否匹配
        String redisCode = redis.get(MOBILE_SMSCODE + ":" + mobile);//reids中的验证码
        if (StringUtils.isBlank(redisCode) || !redisCode.equalsIgnoreCase(code)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.SMS_CODE_ERROR);//自定义的枚举
        }
//
        // 2. 查询数据库，判断用户是否存在
        Users user = userService.queryMobileIsExist(mobile);
        if (user == null) {
            // 2.1 如果用户为空，表示没有注册过，则为null，需要注册信息入库
            user = userService.createUser(mobile);
        }
//
        // 3. 如果不为空，可以继续下方业务，可以保存用户会话信息
        String uToken = UUID.randomUUID().toString();//生成token，作为用户的会话信息
        redis.set(REDIS_USER_TOKEN + ":" + user.getId(), uToken);//一直存在，没设置存活时间
//
        // 4. 用户登录注册成功以后，删除redis中的短信验证码，阅后即焚
        redis.del(MOBILE_SMSCODE + ":" + mobile);

        // 5. 返回用户信息，包含token令牌，新的实体类
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(user, usersVO);//拷贝一样的信息
        usersVO.setUserToken(uToken);  //设置不一样的信息

        return GraceJSONResult.ok(usersVO);
    }


    @PostMapping("logout")
    public GraceJSONResult logout(@RequestParam String userId,
                                  HttpServletRequest request) throws Exception {

        // 后端只需要清除用户的token信息即可，前端也需要清除，清除本地app中的用户信息和token会话信息
        redis.del(REDIS_USER_TOKEN + ":" + userId);

        return GraceJSONResult.ok();
    }

}
