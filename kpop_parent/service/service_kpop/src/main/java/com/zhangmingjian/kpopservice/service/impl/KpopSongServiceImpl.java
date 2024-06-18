package com.zhangmingjian.kpopservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangmingjian.kpopservice.entity.*;
import com.zhangmingjian.kpopservice.entity.FrontVo.SongFrontQuery;
import com.zhangmingjian.kpopservice.entity.vo.*;
import com.zhangmingjian.kpopservice.mapper.KpopSongMapper;
import com.zhangmingjian.kpopservice.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 歌曲表 服务实现类
 * </p>
 *
 * @author zhangmingjian
 * @since 2024-01-02
 */
@Service
public class KpopSongServiceImpl extends ServiceImpl<KpopSongMapper, KpopSong> implements KpopSongService {

    @Value("${songCover.loaction}")
    private String songCoverLocation;

    @Autowired
    private KpopSongTransliterationService songTransliterationService;

    @Autowired
    private KpopSingerService singerService;

    @Autowired
    private KpopSongSingerService songSingerService;

    @Autowired
    private KpopBannerService bannerService;

    // 上传歌曲封面 返回路径str
    @Override
    public String songCoverUpload(MultipartFile file) {
        // 上传文件原始名称
        String fileName= file.getOriginalFilename();
        // 获取文件后缀名
        String extension = fileName.substring(fileName.lastIndexOf("."));
        // 生成新的文件名称 利用时间戳和UUID 生成新名称
        String newFileName= new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                + UUID.randomUUID().toString().replace("-","")
                + extension;
        // 根据日期生成目录
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dateDirPath = songCoverLocation + File.separator + format;

        // 新建或者找到当天记录的文件夹
        File dateDir = new File(dateDirPath);
        if(!dateDir.exists()){
            dateDir.mkdirs();
        }

        try {
            // 完整保存路径+文件名称
            Path filepath = Paths.get(dateDirPath, newFileName);
            // 将 MultipartFile 的内容保存到指定位置
            file.transferTo(filepath.toFile());
            // 返回文件保存的路径 文件夹+文件名
            return format+"/"+newFileName;
        } catch (IOException e) {
            // 处理文件传输失败的异常
            e.printStackTrace();
            // 根据应用程序的要求，你可能希望抛出自定义异常或以其他方式处理异常
            return null;
        }
    }

    // 根据songId 获取歌曲完整信息 包括歌手列表 歌曲音译 即SongPublish
    @Override
    public SongPublishVo getSongPublishVo(String songId) {
        // 首先获得除了singerList以外的信息  包括歌曲信息和歌曲音译
        SongPublishVo songPublishVo = baseMapper.getSongBaseTrans(songId);
        // 然后根据songId搜索歌手列表 封装到songPublishVo中
        List<KpopSinger> singerList = singerService.getSingerListBySongId(songId);
        songPublishVo.setSingerList(singerList);
        return songPublishVo;
    }

    // 根据songId 进行最后的确认 就是把 status变成1 返回修改结果
    @Override
    public boolean finalpublish(String songId) {
        // 直接先查再改
        KpopSong song = this.getById(songId);
        song.setStatus(1);
        boolean success = this.updateById(song);
        return success;
    }

    // 根据歌曲id 删除 歌曲基本信息/歌曲歌手连接表/歌曲音译trans
    @Override
    public void deleteSong(String songId) {
        // 1 先删song 歌曲基本信息
        this.removeById(songId);
        // 2 再删transliteration
        LambdaQueryWrapper<KpopSongTransliteration> transLqw = new LambdaQueryWrapper<>();
        transLqw.eq(KpopSongTransliteration::getSongId,songId);
        songTransliterationService.remove(transLqw);
        // 3 最后删除歌曲歌手连接表
        LambdaQueryWrapper<KpopSongSinger> songSingerLqw = new LambdaQueryWrapper<>();
        songSingerLqw.eq(KpopSongSinger::getSongId,songId);
        songSingerService.remove(songSingerLqw);
        // 4 如果存在对应的的banner，直接删除banner
        LambdaQueryWrapper<KpopBanner> bannerLqw = new LambdaQueryWrapper<>();
        bannerLqw.eq(KpopBanner::getSongId,songId);
        KpopBanner bannerOne = bannerService.getOne(bannerLqw);
        if(bannerOne != null){
            // 这里 不直接使用remove的原因是  需要通过bannerService.removeBannerById的方法更新redis
            bannerService.removeBannerById(bannerOne.getId());
        }

    }


    // 【前端serviceImpl】【前端serviceImpl】【前端serviceImpl】
    // 根据歌手id查询对应的歌曲列表
    @Override
    public List<KpopSong> getSongListBySingerId(String singerId) {
        List<KpopSong> songList = baseMapper.getSongListBySingerId(singerId);
        return songList;
    }

    // 分页条件查询 一定要查询status=1的
    @Override
    public Page<KpopSong> getSongListQueryPage(int currentPage,int pageSize, SongFrontQuery songFrontQuery) {
        // 分页条件
        Page<KpopSong> songPage = new Page<>(currentPage,pageSize);

        // 查询条件
        LambdaQueryWrapper<KpopSong> lqw = new LambdaQueryWrapper<>();
        lqw.like(songFrontQuery.getSongName()!=null && songFrontQuery.getSongName().trim() != "",
                KpopSong::getName,songFrontQuery.getSongName());
        lqw.eq(KpopSong::getStatus,1);
        // 根据发布日期排序
        if (songFrontQuery.getReleaseTimeSort()!=null && songFrontQuery.getReleaseTimeSort().trim() != "") {
            lqw.orderByDesc(KpopSong::getReleaseTime);
        }
        // 根据id排序
        if (songFrontQuery.getPopularitySort()!=null && songFrontQuery.getPopularitySort().trim() != "") {
            lqw.orderByDesc(KpopSong::getPopularity);
        }
        this.page(songPage,lqw);

        return songPage;
    }


    // 前端访问歌曲 先让popularity+1
    @Override
    public void updatePopularityById(String songId) {
        baseMapper.updatePopularityById(songId);
    }

}
