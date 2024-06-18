package com.zhangmingjian.kpopservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangmingjian.commonutils.R;
import com.zhangmingjian.kpopservice.entity.KpopBanner;
import com.zhangmingjian.kpopservice.entity.KpopSinger;
import com.zhangmingjian.kpopservice.entity.KpopSong;
import com.zhangmingjian.kpopservice.entity.KpopSongSinger;
import com.zhangmingjian.kpopservice.entity.vo.*;
import com.zhangmingjian.kpopservice.service.KpopBannerService;
import com.zhangmingjian.kpopservice.service.KpopSingerService;
import com.zhangmingjian.kpopservice.service.KpopSongService;
import com.zhangmingjian.kpopservice.service.KpopSongSingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 歌曲表 前端控制器
 * </p>
 *
 * @author zhangmingjian
 * @since 2024-01-02
 */
@RestController
@RequestMapping("/kpopservice/song")
@CrossOrigin
public class KpopSongController {

    @Autowired
    private KpopSongService songService;

    @Autowired
    private KpopSingerService singerService;

    @Autowired
    private KpopSongSingerService songSingerService;

    @Autowired
    private KpopBannerService bannerService;


    // 0. 上传歌曲封面
    @PostMapping("songCoverUpload")
    public R avatarUpload(MultipartFile file){
        // 最终要返回file的地址字符串
        String songCoverAddress = songService.songCoverUpload(file);
        return R.ok().data("songCoverAddress",songCoverAddress);
    }

    // 1. 新增歌曲基本信息 baseInfo 此时还没有提交 所以status=0
    @PostMapping("addSongBaseInfo")
    public R addSongBaseInfo(@RequestBody SongBaseInfoVo songBaseInfoVo){
        // 0 取值
        KpopSong song = songBaseInfoVo.getSong();
        List<KpopSinger> singerList = songBaseInfoVo.getSingerList();

        // 1 存储歌曲的基本信息
        // 拼接歌手名字(可能存在合唱的情况)
        String singerName = singerService.createSingerName(singerList);
        song.setSingerName(singerName);
        song.setPopularity(0); // 流行值设置为0
        // 把song里面的状态码设置为0 表示未发布
        song.setStatus(0);
        // 存储song 并返回歌曲id
        songService.save(song);
        String songId = song.getId();

        // 2 存储多对多信息  到关联表中
        // 读取singerList里面的singerId 依次和songId 进行存储
        songSingerService.saveSongSingerList(songId,singerList);

        return R.ok().data("songId",songId);
    }


    // 2. 根据歌曲id获取歌曲信息和歌手列表
    @GetMapping("getSongBaseInfoById/{songId}")
    public R getSongById(@PathVariable String songId){
        KpopSong song = songService.getById(songId);
        List<KpopSinger> singerList = singerService.getSingerListBySongId(songId);
        // 有参构造器 封装类
        SongBaseInfoVo songBaseInfoVo = new SongBaseInfoVo(song,singerList);
        return R.ok().data("songBaseInfo",songBaseInfoVo);
    }

    // 3. 修改歌曲基本信息 baseInfo 此时还没有提交 所以status=0
    @PostMapping("updateSongBaseInfo")
    public R updateSongBaseInfo(@RequestBody SongBaseInfoVo songBaseInfoVo){
        // 0 取值
        KpopSong song = songBaseInfoVo.getSong();
        List<KpopSinger> singerList = songBaseInfoVo.getSingerList();

        // 1 修改歌曲的基本信息
        // 拼接歌手名字(可能存在合唱的情况)
        String singerName = singerService.createSingerName(singerList);
        song.setSingerName(singerName);
        // 管理员点击编辑后 默认进入未提交状态 即status=0
        song.setStatus(0);
        // 更新song 并返回歌曲id
        songService.updateById(song);
        String songId = song.getId();

        // 2 根据songId Hard硬删除!!!!! songSinger关联表里面的所有关联信息
        songSingerService.hardDeleteBySongId(songId);

        // 3 存储修改后的 多对多信息  到关联表中
        // 读取singerList里面的singerId 依次和songId 进行存储
        songSingerService.saveSongSingerList(songId,singerList);

        // 因为此时歌曲进入status=0的状态 如果有对应的banner 也需要将其下架
        // 4 根据 歌曲id   如果存在对应的banner,将其设置为 未展示
        LambdaQueryWrapper<KpopBanner> lqw = new LambdaQueryWrapper<>();
        lqw.eq(KpopBanner::getSongId,songId);
        KpopBanner bannerOne = bannerService.getOne(lqw);
        if(bannerOne != null){
            // 取消轮播图的展示
            bannerService.turnOff(bannerOne.getId());
        }


        return R.ok().data("songId",songId);
    }

    // 4. 根据歌曲id获取歌曲基本信息 不需要歌手列表 用于编写transliteration时候的回显
    @GetMapping("getSongSimpleInfoById/{songId}")
    public R getSongSimpleInfoById(@PathVariable String songId){
        KpopSong song = songService.getById(songId);
        return R.ok().data("songSimpleInfo",song);
    }


    // 5. 根据songId 获取歌曲完整信息 包括歌手列表 歌曲音译 即SongPublishVo
    @GetMapping("getSongPublish/{songId}")
    public R getSongPublish(@PathVariable String songId){
        // 包括   歌曲基本信息 歌手列表 歌曲音译
        SongPublishVo songPublishVo = songService.getSongPublishVo(songId);
        return R.ok().data("songPublish", songPublishVo);
    }

    // 6. 根据songId 进行最后的确认 就是把 status变成1
    @GetMapping("finalPublish/{songId}")
    public R finalpublish(@PathVariable String songId){
        boolean success = songService.finalpublish(songId);
        if(success){
            return R.ok().message("完成最终确认");
        }else{
            return R.error().message("最终确认失败");
        }
    }

    // 7. 分页!!条件!!查询 获得song/list页面的展示内容  SongListVo
    @PostMapping("songListPageQuery/{currentPage}/{pageSize}")
    public R songList(@PathVariable int currentPage, @PathVariable int pageSize,
                      @RequestBody SongListQuery songListQuery){
        // 三个查询条件
        LambdaQueryWrapper<KpopSong> lqw = new LambdaQueryWrapper<>();
        lqw.like(songListQuery.getSongName()!=null && songListQuery.getSongName().trim() != "",
                KpopSong::getName,songListQuery.getSongName());
        lqw.like(songListQuery.getSingerName()!=null && songListQuery.getSingerName().trim() != "",
                KpopSong::getSingerName,songListQuery.getSingerName());
        lqw.eq(songListQuery.getStatus()!=null,KpopSong::getStatus,songListQuery.getStatus());
        // 分页条件
        Page<KpopSong> songPage = new Page<>(currentPage,pageSize);

        songService.page(songPage,lqw);
        // 返回 分页条件查询结果
        List<KpopSong> songListPageQuery = songPage.getRecords();
        // 返回 条件查询总数
        long total = songPage.getTotal();
        return R.ok().data("total",total).data("records",songListPageQuery);
    }

    // 8. 根据歌曲id 逻辑删除 歌曲基本信息/歌曲歌手连接表/歌曲音译trans/可能存在的banner
    @DeleteMapping("deleteSong/{songId}")
    public R deleteSong(@PathVariable String songId){
        // 逻辑删除四个内容  歌曲基本信息/歌曲歌手连接表/歌曲音译trans/可能存在的banner
        songService.deleteSong(songId);
        return R.ok().message("完成逻辑删除");
    }

}

