package com.zhangmingjian.kpopservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangmingjian.kpopservice.entity.KpopSingerFavourite;
import com.zhangmingjian.kpopservice.entity.vo.SingerFavouriteListVo;
import com.zhangmingjian.kpopservice.mapper.KpopSingerFavouriteMapper;
import com.zhangmingjian.kpopservice.service.KpopSingerFavouriteService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KpopSingerFavouriteServiceImpl extends ServiceImpl<KpopSingerFavouriteMapper, KpopSingerFavourite>
        implements KpopSingerFavouriteService {

    // 后台 返回喜爱歌手列表(里面包含喜爱歌手表的id)  封装到SingerFavouriteListVo
    @Override
    public List<SingerFavouriteListVo> getFavouriteSingerList() {
        List<SingerFavouriteListVo> singerFavouriteList= baseMapper.getFavouriteSingerList();
        return singerFavouriteList;
    }

    // 先检查一下 是否已经推荐了这个歌手且is_deleted=0
    @Override
    public boolean isRepeated(String singerId) {
        LambdaQueryWrapper<KpopSingerFavourite> lqw = new LambdaQueryWrapper<>();
        lqw.eq(KpopSingerFavourite::getSingerId,singerId);
        KpopSingerFavourite one = this.getOne(lqw);
        if(one == null){
            return false;
        }else{
            return true;
        }

    }
}
