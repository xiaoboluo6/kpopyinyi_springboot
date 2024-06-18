package com.zhangmingjian.kpopservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhangmingjian.kpopservice.entity.KpopSongTransliteration;
import com.zhangmingjian.kpopservice.mapper.KpopSongTransliterationMapper;
import com.zhangmingjian.kpopservice.service.KpopSongTransliterationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * KPOP歌曲音译表 服务实现类
 * </p>
 *
 * @author zhangmingjian
 * @since 2024-01-02
 */
@Service
public class KpopSongTransliterationServiceImpl extends ServiceImpl<KpopSongTransliterationMapper, KpopSongTransliteration> implements KpopSongTransliterationService {

    // 根据songid获取音译文本
    @Override
    public KpopSongTransliteration getTransBySongId(String songId) {
        LambdaQueryWrapper<KpopSongTransliteration> lqw = new LambdaQueryWrapper<>();
        lqw.eq(KpopSongTransliteration::getSongId,songId);
        KpopSongTransliteration songTrans = this.getOne(lqw);
        return songTrans;
    }
}
