package com.zhangmingjian.kpopservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangmingjian.kpopservice.entity.KpopSingerFavourite;
import com.zhangmingjian.kpopservice.entity.vo.SingerFavouriteListVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KpopSingerFavouriteMapper extends BaseMapper<KpopSingerFavourite> {

    // 后台 返回喜爱歌手列表(里面包含喜爱歌手表的id)  封装到SingerFavouriteListVo
    List<SingerFavouriteListVo> getFavouriteSingerList();
}
