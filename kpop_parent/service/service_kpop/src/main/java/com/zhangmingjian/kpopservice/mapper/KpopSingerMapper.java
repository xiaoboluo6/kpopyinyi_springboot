package com.zhangmingjian.kpopservice.mapper;

import com.zhangmingjian.kpopservice.entity.KpopSinger;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 歌手表 Mapper 接口
 * </p>
 *
 * @author zhangmingjian
 * @since 2024-01-02
 */
@Mapper
public interface KpopSingerMapper extends BaseMapper<KpopSinger> {

    // 2023.02 开始修改多对多的底层逻辑
    // 根据歌曲id 去查询关联表songSinger 然后再去查找singer表 返回singerList
    List<KpopSinger> getSingerListBySongId(String songId);

    // 访问歌手详情页面的时候 歌手热度流行度+1
    void updateSingerPopularity(String singerId);

    // 前端首页展示 站长推荐歌手 根据KpopSingerFavourite表的信息返回KpopSinger的list
    List<KpopSinger> getSingerFavouriteFront();
}
