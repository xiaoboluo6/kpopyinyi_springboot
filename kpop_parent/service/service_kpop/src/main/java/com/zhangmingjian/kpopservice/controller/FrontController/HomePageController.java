package com.zhangmingjian.kpopservice.controller.FrontController;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhangmingjian.commonutils.R;
import com.zhangmingjian.kpopservice.entity.KpopBanner;
import com.zhangmingjian.kpopservice.entity.KpopSinger;
import com.zhangmingjian.kpopservice.entity.KpopSong;
import com.zhangmingjian.kpopservice.service.KpopBannerService;
import com.zhangmingjian.kpopservice.service.KpopSingerService;
import com.zhangmingjian.kpopservice.service.KpopSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/kpopservice/homepage")
@CrossOrigin
public class HomePageController {

    @Autowired
    private KpopSingerService singerService;
    @Autowired
    private KpopSongService songService;
    @Autowired
    private KpopBannerService bannerService;


    // 1. 首页展示信息
    @GetMapping("index")
    public R index(){
        // 查询前10个歌手
        LambdaQueryWrapper<KpopSinger> lqwSinger = new LambdaQueryWrapper<>();
        lqwSinger.orderByDesc(KpopSinger::getPopularity);
        lqwSinger.last("limit 10");  // 排序后期自己改
        List<KpopSinger> singerList = singerService.list(lqwSinger);

        // 查询前10条歌曲信息
        LambdaQueryWrapper<KpopSong> lqwSong = new LambdaQueryWrapper<>();
        lqwSong.eq(KpopSong::getStatus,1);
        lqwSong.orderByDesc(KpopSong::getPopularity);
        lqwSong.last("limit 10");  // 排序后期自己改
        List<KpopSong> songList = songService.list(lqwSong);

        // 查询isShow=1(表示可以展示) 的 banner轮播图   并放入redis
        List<KpopBanner> bannerList = bannerService.getFrontShow();

        return R.ok().data("singerList",singerList).data("songList",songList).data("bannerList",bannerList);
    }


    // 2. 首页展示热度最高的10首歌曲
    @GetMapping("getSongPopularityTOP")
    public R getSongPopularityTOP(){
        LambdaQueryWrapper<KpopSong> lqwSong = new LambdaQueryWrapper<>();
        lqwSong.eq(KpopSong::getStatus,1);   // 查找已经发布的歌曲
        lqwSong.orderByDesc(KpopSong::getPopularity);
        lqwSong.last("limit 10");  // 查找TOP10
        List<KpopSong> songList = songService.list(lqwSong);
        return R.ok().data("songPopularityList",songList);
    }


    // 3. 首页展示最新的10首歌曲
    @GetMapping("getSongNewTOP")
    public R getSongNewTOP(){
        LambdaQueryWrapper<KpopSong> lqwSong = new LambdaQueryWrapper<>();
        lqwSong.eq(KpopSong::getStatus,1);   // 查找已经发布的歌曲
        lqwSong.orderByDesc(KpopSong::getReleaseTime);
        lqwSong.last("limit 10");  // 查找TOP10
        List<KpopSong> songList = songService.list(lqwSong);
        return R.ok().data("songNewList",songList);
    }

    // 4. 首页展示热度最高的10位歌手
    @GetMapping("getSingerPopularityTOP")
    public R getSingerPopularityTOP(){
        // 查询前10个热度最高的歌手
        LambdaQueryWrapper<KpopSinger> lqwSinger = new LambdaQueryWrapper<>();
        lqwSinger.orderByDesc(KpopSinger::getPopularity);
        lqwSinger.last("limit 10");  // 排序后期自己改
        List<KpopSinger> singerList = singerService.list(lqwSinger);
        return R.ok().data("singerPopularityList",singerList);
    }


    // 5. 首页展示 站长推荐歌手 这里返回KpopSinger的list
    @GetMapping("getSingerFavouriteFront")
    public R getSingerFavouriteFront(){
        List<KpopSinger> singerList = singerService.getSingerFavouriteFront();
        return R.ok().data("singerFavouriteList",singerList);
    }


}
