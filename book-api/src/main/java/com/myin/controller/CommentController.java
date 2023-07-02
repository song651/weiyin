package com.myin.controller;

import com.myin.base.BaseInfoProperties;
import com.myin.bo.CommentBO;
import com.myin.grace.result.GraceJSONResult;
import com.myin.service.CommentService;
import com.myin.service.VlogService;
import com.myin.vo.CommentVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Api(tags = "CommentController 评论模块的接口")
@RequestMapping("comment")
@RestController
public class CommentController extends BaseInfoProperties {
    @Autowired
    private CommentService commentService;

    @Autowired
    private VlogService vlogService;

    @PostMapping("create")
    public GraceJSONResult create(@RequestBody @Valid CommentBO commentBO) throws Exception {

        CommentVO commentVO = commentService.createComment(commentBO);
        return GraceJSONResult.ok(commentVO);
    }

    @GetMapping("counts")
    public GraceJSONResult counts(@RequestParam String vlogId) {

        String countsStr = redis.get(REDIS_VLOG_COMMENT_COUNTS + ":" + vlogId);
        if (StringUtils.isBlank(countsStr)) {
            countsStr = "0";
        }

        return GraceJSONResult.ok(Integer.valueOf(countsStr));
    }


    @GetMapping("list")
    public GraceJSONResult list(@RequestParam String vlogId,
                                @RequestParam(defaultValue = "") String userId,
                                @RequestParam Integer page,
                                @RequestParam Integer pageSize) {

        return GraceJSONResult.ok(
                commentService.queryVlogComments(
                        vlogId,
                        userId,
                        page,
                        pageSize));
    }


    @DeleteMapping("delete")
    public GraceJSONResult delete(@RequestParam String commentUserId,
                                  @RequestParam String commentId,
                                  @RequestParam String vlogId) {
        commentService.deleteComment(commentUserId,
                commentId,
                vlogId);
        return GraceJSONResult.ok();
    }


    @PostMapping("like")
    public GraceJSONResult like(@RequestParam String commentId,
                                @RequestParam String userId) {

        // Hashredis_vlog，key是REDIS_VLOG_COMMENT_LIKED_COUNTS，对应的是comment累加1
        redis.incrementHash(REDIS_VLOG_COMMENT_LIKED_COUNTS, commentId, 1);
        //用户喜欢点赞comment和用户喜欢comment
        redis.setHashValue(REDIS_USER_LIKE_COMMENT, userId + ":" + commentId, "1");
        //错误：bigkey，一百万个会有一百万个不同的id。key会非常的大
        //做路由方式的存储来纠正
        //这个setHashValue是自定义的，可以用下面的hset

        //redis.hset(REDIS_USER_LIKE_COMMENT, userId, "1");


//        // 系统消息：点赞评论
//        Comment comment = commentService.getComment(commentId);
//        Vlog vlog = vlogService.getVlog(comment.getVlogId());
//        Map msgContent = new HashMap();
//        msgContent.put("vlogId", vlog.getId());
//        msgContent.put("vlogCover", vlog.getCover());
//        msgContent.put("commentId", commentId);
//        msgService.createMsg(userId,
//                comment.getCommentUserId(),
//                MessageEnum.LIKE_COMMENT.type,
//                msgContent);
//
        return GraceJSONResult.ok();
    }

    @PostMapping("unlike")
    public GraceJSONResult unlike(@RequestParam String commentId,
                                  @RequestParam String userId) {

        redis.decrementHash(REDIS_VLOG_COMMENT_LIKED_COUNTS, commentId, 1);
        redis.hdel(REDIS_USER_LIKE_COMMENT, userId + ":" + commentId);

        return GraceJSONResult.ok();
    }


}
