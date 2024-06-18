package com.zhangmingjian.kpopservice.mapper;

import com.zhangmingjian.kpopservice.entity.KpopSong;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangmingjian.kpopservice.entity.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 歌曲表 Mapper 接口
 * </p>
 *
 * @author zhangmingjian
 * @since 2024-01-02
 */
@Mapper
public interface KpopSongMapper extends BaseMapper<KpopSong> {

    // sql语句搜索
    // 根据songId 获取歌曲完整信息 填充歌曲信息和歌曲音译 即SongPublishVo
    SongPublishVo getSongBaseTrans(String songId);



    // 【前端serviceImpl】【前端serviceImpl】【前端serviceImpl】
    // 根据歌手id查询对应的歌曲列表
    List<KpopSong> getSongListBySingerId(String singerId);

    // 前端访问歌曲 先让popularity+1
    void updatePopularityById(String songId);
}
