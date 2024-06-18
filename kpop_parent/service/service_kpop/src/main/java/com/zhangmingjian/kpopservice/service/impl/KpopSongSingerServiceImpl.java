package com.zhangmingjian.kpopservice.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhangmingjian.kpopservice.entity.KpopSinger;
import com.zhangmingjian.kpopservice.entity.KpopSongSinger;
import com.zhangmingjian.kpopservice.mapper.KpopSongSingerMapper;
import com.zhangmingjian.kpopservice.service.KpopSongSingerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KpopSongSingerServiceImpl extends ServiceImpl<KpopSongSingerMapper, KpopSongSinger> implements KpopSongSingerService {

    // 读取singerList里面的singerId 依次和songId 进行存储
    @Override
    public void saveSongSingerList(String songId, List<KpopSinger> singerList) {
        // 遍历singerList 进行存储
        for (KpopSinger singer : singerList) {
            String singerId = singer.getId();
            KpopSongSinger songSingerRelated = new KpopSongSinger();
            songSingerRelated.setSongId(songId).setSingerId(singerId);
            // 数据库mybatisplus保存
            this.save(songSingerRelated);
        }
    }

    // 根据songId Hard硬删除songSinger关联表里面的所有关联信息
    @Override
    public void hardDeleteBySongId(String songId) {
        baseMapper.hardDeleteBySongId(songId);
    }


}
