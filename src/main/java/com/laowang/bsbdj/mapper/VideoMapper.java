package com.laowang.bsbdj.mapper;

import com.laowang.bsbdj.entity.Video;

import java.util.List;

public interface VideoMapper {
    int deleteByPrimaryKey(Long videoId);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(Long videoId);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);

    public List<Video> findByContentId(Long contentId);
}