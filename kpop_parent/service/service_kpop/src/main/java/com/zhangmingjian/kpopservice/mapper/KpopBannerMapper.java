package com.zhangmingjian.kpopservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhangmingjian.kpopservice.entity.KpopBanner;
import com.zhangmingjian.kpopservice.entity.vo.BannerListVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KpopBannerMapper extends BaseMapper<KpopBanner> {

    // 获取全部banner信息  封装到BannerListVo中  给后台admin进行显示
    List<BannerListVo> getBannerInfoListShow();


    // 切换显示状态 根据id  将banner的isShow变成0
    void turnOffById(String bannerId);

    // 切换显示状态 根据id  将banner的isShow变成1
    void turnOnById(String bannerId);

    // 根据bannerId获取歌曲的status
    int getSongStatusByBannerId(String bannerId);
}
