package com.zhangmingjian.kpopservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangmingjian.kpopservice.entity.KpopSongSinger;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface KpopSongSingerMapper extends BaseMapper<KpopSongSinger> {

    // 根据songId Hard硬删除songSinger关联表里面的所有关联信息
    void hardDeleteBySongId(String songId);
}
