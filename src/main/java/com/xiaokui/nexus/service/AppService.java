package com.xiaokui.nexus.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.xiaokui.nexus.model.dto.app.AppQueryRequest;
import com.xiaokui.nexus.model.entity.App;
import com.xiaokui.nexus.model.vo.AppVO;

import java.util.List;

/**
 * 应用 服务层。
 *
 * @author cxyxiaokui
 */
public interface AppService extends IService<App> {

    AppVO getAppVO(App app);

    List<AppVO> getAppVOList(List<App> appList);

    QueryWrapper getQueryWrapper(AppQueryRequest appQueryRequest);
}
