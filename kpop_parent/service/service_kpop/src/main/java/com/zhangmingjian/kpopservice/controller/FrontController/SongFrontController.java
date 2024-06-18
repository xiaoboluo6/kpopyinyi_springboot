package com.zhangmingjian.kpopservice.controller.FrontController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangmingjian.commonutils.R;
import com.zhangmingjian.kpopservice.entity.FrontVo.SongFrontQuery;
import com.zhangmingjian.kpopservice.entity.KpopSong;
import com.zhangmingjian.kpopservice.entity.vo.SongPublishVo;
import com.zhangmingjian.kpopservice.service.KpopSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kpopservice/songfront")
@CrossOrigin
public class SongFrontController {

    @Autowired
    private KpopSongService songService;

    // 1. 前端页面访问歌曲详细信息  访问量(popularity)+1
    //    然后根据歌曲id 返回前端页面SongDetail展示页面
    @GetMapping("getFrontSongTrans/{songId}")
    public R getFrontSongTrans(@PathVariable String songId){
        // 首先修改歌曲 popularity+1
        songService.updatePopularityById(songId);

        // 包括   歌曲基本信息 歌手列表 歌曲音译
        SongPublishVo songPublishVo = songService.getSongPublishVo(songId);
        return R.ok().data("songFrontTransInfo",songPublishVo);
    }


    // 2. 前端 分页条件查询 一定要查询status=1的
    @PostMapping("songFrontList/{currentPage}/{pageSize}")
    public R songFrontList(@PathVariable int currentPage, @PathVariable int pageSize,
                      @RequestBody SongFrontQuery songFrontQuery){
        Page<KpopSong> songQueryPage = songService.getSongListQueryPage(currentPage,pageSize,songFrontQuery);

        return R.ok().data("records",songQueryPage.getRecords()).data("total",songQueryPage.getTotal());
    }

}
