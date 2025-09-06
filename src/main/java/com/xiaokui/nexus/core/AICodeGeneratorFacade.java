package com.xiaokui.nexus.core;

import com.xiaokui.nexus.ai.AiCodeGeneratorService;
import com.xiaokui.nexus.ai.model.HtmlCodeResult;
import com.xiaokui.nexus.ai.model.MultiFileCodeResult;
import com.xiaokui.nexus.exception.BusinessException;
import com.xiaokui.nexus.exception.ErrorCode;
import com.xiaokui.nexus.model.enums.CodeGenTypeEnum;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * AI代码生成外观类，组合生成和保存功能
 */
@Service
public class AICodeGeneratorFacade {

    @Resource
    private AiCodeGeneratorService aiCodeGeneratorService;

    /**
     * 根据类型生成代码并保存
     *
     * @param userMessage 用户输入
     * @param codeGenTypeEnum 代码生成类型
     * @return 保存后的文件
     */
    public File generateAndSaveCode(String userMessage, CodeGenTypeEnum codeGenTypeEnum){
        if(codeGenTypeEnum == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return switch (codeGenTypeEnum) {
            case HTML -> generateAndSaveHTMLCode(userMessage);
            case MULTI_FILE -> generateAndSaveMultiCode(userMessage);
            default -> throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        };
    }

    /**
     * 生成HTML代码并保存
     *
     * @param userMessage 用户输入
     * @return 保存后的文件
     */
    private File generateAndSaveHTMLCode(String userMessage) {
        HtmlCodeResult result= aiCodeGeneratorService.generateHtmlCode(userMessage);
        return CodeFileSaver.saveHtmlCodeResult(result);
    }

    /**
     * 生成多文件代码并保存
     *
     * @param userMessage 用户输入
     * @return 保存后的文件
     */
    private File generateAndSaveMultiCode(String userMessage) {
        MultiFileCodeResult result= aiCodeGeneratorService.generateMultiFileCode(userMessage);
        return CodeFileSaver.saveMultiFileCodeResult(result);
    }
}
