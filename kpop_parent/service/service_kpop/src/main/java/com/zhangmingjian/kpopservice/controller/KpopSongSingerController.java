package com.zhangmingjian.kpopservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zhangmingjian.commonutils.R;
import com.zhangmingjian.kpopservice.entity.KpopSongSinger;
import com.zhangmingjian.kpopservice.service.KpopSongSingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kpopservice/songsingerRelated")
@CrossOrigin
public class KpopSongSingerController {

    @Autowired
    private KpopSongSingerService songSingerService;

}
