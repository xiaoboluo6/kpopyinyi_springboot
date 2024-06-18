package com.zhangmingjian.kpopservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhangmingjian.commonutils.R;
import com.zhangmingjian.kpopservice.entity.KpopBanner;
import com.zhangmingjian.kpopservice.entity.KpopSong;
import com.zhangmingjian.kpopservice.entity.vo.BannerListVo;
import com.zhangmingjian.kpopservice.entity.vo.BannerSongQuery;
import com.zhangmingjian.kpopservice.service.KpopBannerService;
import com.zhangmingjian.kpopservice.service.KpopSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/kpopservice/banner")
@CrossOrigin
public class KpopBannerController {

    @Autowired
    private KpopBannerService bannerService;

    @Autowired
    private KpopSongService songService;

    // 获取全部banner信息  封装到BannerListVo中  给后台admin进行显示
    // 注意 因为用了逻辑删除 这里的sql加入了is_deleted = 0
    @GetMapping("getAllBanner")
    public R getAllBanner(){
        List<BannerListVo> list = bannerService.getBannerInfoList();
        return R.ok().data("items",list);
    }

    // 根据id获取banner
    @GetMapping("getBannerById/{id}")
    public R getBannerById(@PathVariable String id){
        return R.ok().data("item",bannerService.getById(id));
    }

    // 添加banner的时候 需要选择对应的歌曲 采用SongBannerQuery进行查询 有两个条件 是对歌手名字和歌曲名字的模糊查询
    @PostMapping("queryBannerSong")
    public R queryBannerSong(@RequestBody BannerSongQuery bannerSongQuery){
        LambdaQueryWrapper<KpopSong> lqw = new LambdaQueryWrapper<>();
        // 歌名模糊查询
        lqw.like(bannerSongQuery.getSongName()!=null && bannerSongQuery.getSongName().trim() != "",
                KpopSong::getName,bannerSongQuery.getSongName());
        // 歌手名称模糊查询
        lqw.like(bannerSongQuery.getSingerName()!=null && bannerSongQuery.getSingerName().trim() != "",
                KpopSong::getSingerName,bannerSongQuery.getSingerName());
        lqw.eq(KpopSong::getStatus,1);// 只查找已经最终发布的歌曲
        List<KpopSong> list = songService.list(lqw);
        return R.ok().data("bannerSongQueryList",list);
    }

    // banner选择了对应的song之后,根据songId返回song的基本信息
    @GetMapping("getBannerRelatedSong/{songId}")
    public R getBannerRelatedSong(@PathVariable String songId){
        KpopSong relatedSong = songService.getById(songId);
        return R.ok().data("relatedSong",relatedSong);
    }


    // 上传图片 返回存储图片的地址
    @PostMapping("bannerPictureSave")
    public R bannerPictureSave(MultipartFile file){
        String location = bannerService.bannerPictureSave(file);
        return R.ok().data("bannerLocation",location);
    }


    // 后台<<增删改>> 一定要重写方法 在实现类里面更新redis缓存
    // 后台<<增删改>> 一定要重写方法 在实现类里面更新redis缓存
    // 后台<<增删改>> 一定要重写方法 在实现类里面更新redis缓存

    // 后台新增banner
    // ！！！这里一定要注意 重写方法 在实现类里面更新redis
    @PostMapping("saveBanner")
    public R saveBanner(@RequestBody KpopBanner banner){
        bannerService.saveBanner(banner);
        return R.ok().message("新增轮播图成功");
    }

    // 后台修改banner
    // ！！！这里一定要注意 重写方法 在实现类里面更新redis
    @PostMapping("updateBanner")
    public R updateBanner(@RequestBody KpopBanner banner){
        bannerService.updateBannerById(banner);
        return R.ok().message("修改轮播图成功");
    }

    // 删除banner
    // ！！！这里一定要注意 重写方法 在实现类里面更新redis
    @DeleteMapping("delete/{id}")
    public R deleteById(@PathVariable String id){
        bannerService.removeBannerById(id);
        return R.ok().message("删除轮播图成功");
    }


    // 切换显示状态 根据id  将banner的isShow变成0
    // ！！！这里一定要注意 重写方法 在实现类里面更新redis
    @GetMapping("turnOffById/{bannerId}")
    public R turnOffById(@PathVariable String bannerId){
        bannerService.turnOff(bannerId);
        return R.ok().message("展示状态修改完成");
    }


    // 切换显示状态 根据id  将banner的isShow变成1
    // 先要检查 歌曲的状态是否为1
    // ！！！这里一定要注意 重写方法 在实现类里面更新redis
    @GetMapping("turnOnById/{bannerId}")
    public R turnOnById(@PathVariable String bannerId){
        // 先要检查 歌曲的状态是否为1
        // 根据bannerId获取歌曲的status
        int status = bannerService.getSongStatusByBannerId(bannerId);
        if(status == 1){
            // 表示歌曲已经发布 可以将banner展示
            bannerService.turnOn(bannerId);
            return R.ok().message("展示状态修改完成");
        }
        else {
            return R.error().message("请先检查歌曲状态，确保歌曲已经发布");
        }

    }



}
