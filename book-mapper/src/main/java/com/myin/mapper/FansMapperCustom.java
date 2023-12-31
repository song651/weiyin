package com.myin.mapper;

import com.myin.my.mapper.MyMapper;
import com.myin.pojo.Fans;
import com.myin.vo.FansVO;
import com.myin.vo.VlogerVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FansMapperCustom extends MyMapper<Fans> {

    public List<VlogerVO> queryMyFollows(@Param("paramMap") Map<String, Object> map);

    public List<FansVO> queryMyFans(@Param("paramMap") Map<String, Object> map);

}