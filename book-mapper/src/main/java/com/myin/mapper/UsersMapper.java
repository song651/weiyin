package com.myin.mapper;

import com.myin.my.mapper.MyMapper;
import com.myin.pojo.Users;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersMapper extends MyMapper<Users> {
}