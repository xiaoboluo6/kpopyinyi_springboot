package com.zhangmingjian.kpopservice.entity.vo;


import com.zhangmingjian.kpopservice.entity.KpopSinger;
import com.zhangmingjian.kpopservice.entity.KpopSong;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


// 新增修改歌曲的封装类  包括歌曲本身的信息  以及关联的singerList
@Data
public class SongBaseInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private KpopSong song;
    private List<KpopSinger> singerList;

    // 无参构造方法
    public SongBaseInfoVo() {
    }

    // 有参构造方法
    public SongBaseInfoVo(KpopSong song, List<KpopSinger> singerList) {
        this.song = song;
        this.singerList = singerList;
    }
}
