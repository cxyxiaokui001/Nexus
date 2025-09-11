package com.xiaokui.nexus.service;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.service.IService;
import com.xiaokui.nexus.model.dto.chatHistory.ChatHistoryQueryRequest;
import com.xiaokui.nexus.model.entity.ChatHistory;
import com.xiaokui.nexus.model.entity.User;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;

import java.time.LocalDateTime;

/**
 * 对话历史 服务层。
 *
 * @author cxyxiaokui
 */
public interface ChatHistoryService extends IService<ChatHistory> {

    boolean addChatMessage(Long appId, String message, String messageType, Long userId);

    boolean deleteByAppId(Long appId);

    QueryWrapper getQueryWrapper(ChatHistoryQueryRequest chatHistoryQueryRequest);

    Page<ChatHistory> listAppChatHistoryByPage(Long appId,
                                             Integer pageSize,
                                             LocalDateTime lastCreateTime,
                                             User loginUser);

    Integer loadChatHistoryToMemory(Long appId, MessageWindowChatMemory chatMemory, Integer maxCount);
}
