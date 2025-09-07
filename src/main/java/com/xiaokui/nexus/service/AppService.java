package com.xiaokui.nexus.service;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.xiaokui.nexus.model.dto.app.AppQueryRequest;
import com.xiaokui.nexus.model.entity.App;
import com.xiaokui.nexus.model.entity.User;
import com.xiaokui.nexus.model.vo.AppVO;
import reactor.core.publisher.Flux;

import java.util.List;

/**
 * 应用 服务层。
 *
 * @author cxyxiaokui
 */
public interface AppService extends IService<App> {

    Flux<String> chatToGenCode(Long appId, String message, User loginUser);

    String deployApp(Long appId, User loginUser);

    AppVO getAppVO(App app);

    List<AppVO> getAppVOList(List<App> appList);

    QueryWrapper getQueryWrapper(AppQueryRequest appQueryRequest);
}
