package com.myin.mapper;

import com.myin.my.mapper.MyMapper;
import com.myin.pojo.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentMapper extends MyMapper<Comment> {
}