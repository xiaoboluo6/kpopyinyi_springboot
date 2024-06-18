package com.zhangmingjian.kpopservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangmingjian.commonutils.R;
import com.zhangmingjian.kpopservice.entity.KpopSinger;
import com.zhangmingjian.kpopservice.entity.vo.SingerQuery;
import com.zhangmingjian.kpopservice.service.KpopSingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 歌手表 前端控制器
 * </p>
 *
 * @author zhangmingjian
 * @since 2024-01-02
 */
@RestController
@RequestMapping("/kpopservice/singer")
@CrossOrigin
public class KpopSingerController {

    @Autowired
    private KpopSingerService singerService;

    // 0. 上传歌手图片
    @PostMapping("avatarUpload")
    public R avatarUpload(MultipartFile file){
        // 最终要返回file的地址字符串
        String avatarAddress = singerService.avatarUpload(file);
        return R.ok().data("avatarAddress",avatarAddress);
    }

    // 1. 新增歌手
    @PostMapping("add")
    public R addSinger(@RequestBody KpopSinger singer){
        singerService.save(singer);
        return R.ok().message("新增歌手成功");
    }


    // 2. 歌手列表 分页!!条件查询!!
    @PostMapping("{page}/{limit}")
    public R page(@PathVariable int page, @PathVariable int limit,
              @RequestBody SingerQuery singerQuery){   // singerQuery查询
        Page<KpopSinger> singerPage = singerService.pageQuery(page,limit,singerQuery);
        long total = singerPage.getTotal();
        List<KpopSinger> records = singerPage.getRecords();

        return R.ok().data("total",total).data("records",records);
    }

    // 3. 根据id查询歌手信息
    @GetMapping("getById/{id}")
    public R getById(@PathVariable String id){
        KpopSinger singerById = singerService.getById(id);
        return R.ok().data("item",singerById);
    }

    // 4. 修改歌手信息
    @PostMapping("update")
    public R updateById(@RequestBody KpopSinger singer){
        singerService.updateById(singer);
        return R.ok().message("修改歌手成功");
    }

    // 5. 根据id删除指定歌手
    @DeleteMapping("{deleteSingerId}")
    public R delete(@PathVariable String deleteSingerId){
        boolean success = singerService.deleteSinger(deleteSingerId);
        if(success){
            return R.ok().success(true).message("删除歌手成功");
        }else{
            return R.ok().success(false).message("无法删除 请先删除该歌手的歌曲 并取消该歌手的推荐位");
        }
    }

    // 6. 歌手列表 只有条件查询!!  这是添加歌曲时候的歌手选择器
    @PostMapping("queryList")
    public R page(@RequestBody SingerQuery singerQuery){   // singerQuery查询
        List<KpopSinger> singerQueryList = singerService.onlyQuery(singerQuery);
        return R.ok().data("items",singerQueryList);
    }






}

