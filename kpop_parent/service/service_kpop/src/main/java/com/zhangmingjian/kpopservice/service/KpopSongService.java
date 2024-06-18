package com.zhangmingjian.kpopservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zhangmingjian.kpopservice.entity.FrontVo.SongFrontQuery;
import com.zhangmingjian.kpopservice.entity.KpopSong;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhangmingjian.kpopservice.entity.vo.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 歌曲表 服务类
 * </p>
 *
 * @author zhangmingjian
 * @since 2024-01-02
 */
public interface KpopSongService extends IService<KpopSong> {

    // 上传歌曲封面 返回路径str
    String songCoverUpload(MultipartFile file);

    // 根据songId 获取歌曲完整信息 包括歌手列表歌曲音译 即SongPublishVo
    SongPublishVo getSongPublishVo(String songId);

    // 根据songId 进行最后的确认 就是把 status变成1 并返回修改结果
    boolean finalpublish(String songid);

    // 根据歌曲id 删除 歌曲基本信息/歌曲歌手连接表/歌曲音译trans
    void deleteSong(String songId);



    // 【前端service】【前端service】【前端service】【前端service】
    // 根据歌手id查询对应的歌曲列表
    List<KpopSong> getSongListBySingerId(String singerId);

    // 分页条件查询 一定要查询status=1的
    Page<KpopSong> getSongListQueryPage(int currentPage,int pageSize, SongFrontQuery songFrontQuery);

    // 前端访问歌曲 先让popularity+1
    void updatePopularityById(String songId);
}
