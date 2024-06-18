package com.zhangmingjian.kpopservice.service;

import com.zhangmingjian.kpopservice.entity.KpopSongTransliteration;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * KPOP歌曲音译表 服务类
 * </p>
 *
 * @author zhangmingjian
 * @since 2024-01-02
 */
public interface KpopSongTransliterationService extends IService<KpopSongTransliteration> {

    // 根据songid获取音译文本
    KpopSongTransliteration getTransBySongId(String songId);
}
