package com.zhangmingjian.kpopservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhangmingjian.kpopservice.entity.KpopSingerFavourite;
import com.zhangmingjian.kpopservice.entity.vo.SingerFavouriteListVo;

import java.util.List;

public interface KpopSingerFavouriteService extends IService<KpopSingerFavourite> {

    // 后台 返回喜爱歌手列表(里面包含喜爱歌手表的id)  封装到SingerFavouriteListVo
    List<SingerFavouriteListVo> getFavouriteSingerList();

    // 先检查一下 是否已经推荐了这个歌手且is_deleted=0
    boolean isRepeated(String singerId);
}
