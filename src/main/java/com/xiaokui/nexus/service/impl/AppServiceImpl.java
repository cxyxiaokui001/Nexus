package com.xiaokui.nexus.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaokui.nexus.model.entity.App;
import com.xiaokui.nexus.mapper.AppMapper;
import com.xiaokui.nexus.service.AppService;
import org.springframework.stereotype.Service;

/**
 * 应用 服务层实现。
 *
 * @author cxyxiaokui
 */
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App>  implements AppService{

}
