package com.zhangmingjian.kpopservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhangmingjian.commonutils.R;
import com.zhangmingjian.kpopservice.entity.KpopSingerFavourite;
import com.zhangmingjian.kpopservice.entity.vo.SingerFavouriteListVo;
import com.zhangmingjian.kpopservice.service.KpopSingerFavouriteService;
import com.zhangmingjian.servicebase.exceptionHandler.KpopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kpopservice/singerFavourite")
@CrossOrigin
public class KpopSingerFavouriteController {

    @Autowired
    private KpopSingerFavouriteService singerFavouriteService;

    // 1. 添加 喜爱歌手
    @GetMapping("add/{singerId}")
    public R addFavouriteSinger(@PathVariable String singerId){
        // 先检查一下 是否已经添加了这个歌手且is_deleted=0
        boolean flag = singerFavouriteService.isRepeated(singerId);
        if(flag == true){
            return R.error().message("不要重复添加推荐歌手");
        }else{
            // 定义一个 喜爱歌手对象
            KpopSingerFavourite singerFavourite = new KpopSingerFavourite();
            singerFavourite.setSingerId(singerId);
            singerFavouriteService.save(singerFavourite);
            return R.ok().message("推荐歌手添加成功");
        }
    }

    // 2. 移除 选中的喜爱歌手
    @DeleteMapping("delete/{singerFavouriteId}")
    public R deleteFavouriteSinger(@PathVariable String singerFavouriteId){
        singerFavouriteService.removeById(singerFavouriteId);
        return R.ok().message("移除歌手添加成功");
    }

    // 3. 后台 返回喜爱歌手列表 封装到SingerFavouriteListVo 里面有喜爱表id和歌手id
    @GetMapping("getFavouriteSingerList")
    public R getFavouriteSingerList(){
        List<SingerFavouriteListVo> singerFavouriteList = singerFavouriteService.getFavouriteSingerList();
        return R.ok().data("singerFavouriteList",singerFavouriteList);
    }

    // 歌手选择器 直接用singerController里面的方法

}
