package com.zhangmingjian.kpopservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhangmingjian.kpopservice.entity.KpopSinger;
import com.zhangmingjian.kpopservice.entity.KpopSongSinger;

import java.util.List;


public interface KpopSongSingerService extends IService<KpopSongSinger> {

    // 读取singerList里面的singerId 依次和songId 进行存储
    void saveSongSingerList(String songId, List<KpopSinger> singerList);

    // 根据songId Hard硬删除songSinger关联表里面的所有关联信息
    void hardDeleteBySongId(String songId);


}
