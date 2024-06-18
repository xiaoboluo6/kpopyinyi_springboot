package com.zhangmingjian.kpopservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhangmingjian.kpopservice.entity.KpopBanner;
import com.zhangmingjian.kpopservice.entity.vo.BannerListVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface KpopBannerService extends IService<KpopBanner> {

    // 获取全部banner信息  封装到BannerListVo中  给后台admin进行显示
    List<BannerListVo> getBannerInfoList();

    // 上传banner图片，返回图片存储地址
    String bannerPictureSave(MultipartFile file);

    // 新增banner 更新redis
    void saveBanner(KpopBanner banner);

    // 更新banner 更新redis
    void updateBannerById(KpopBanner banner);

    // 删除banner 更新redis
    void removeBannerById(String id);


    // 切换显示状态 根据id  将banner的isShow变成0
    void turnOff(String bannerId);

    // 切换显示状态 根据id  将banner的isShow变成1
    void turnOn(String bannerId);

    // 根据bannerId获取歌曲的status
    int getSongStatusByBannerId(String bannerId);



    // 查询isShow=1(表示可以展示) 的 banner轮播图   并放入redis
    List<KpopBanner> getFrontShow();


}
