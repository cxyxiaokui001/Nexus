package com.xiaokui.nexus.core;

import com.xiaokui.nexus.ai.AICodeGeneratorService;
import com.xiaokui.nexus.ai.model.HtmlCodeResult;
import com.xiaokui.nexus.ai.model.MultiFileCodeResult;
import com.xiaokui.nexus.exception.BusinessException;
import com.xiaokui.nexus.exception.ErrorCode;
import com.xiaokui.nexus.model.enums.CodeGenTypeEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.File;

/**
 * AI代码生成外观类，组合生成和保存功能
 */
@Service
@Slf4j
public class AICodeGeneratorFacade {

    @Resource
    private AICodeGeneratorService aiCodeGeneratorService;

    /**
     * 统一入口：根据类型生成代码并保存
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


    /**
     * 统一入口：根据类型生成代码并保存
     *
     * @param userMessage 用户输入
     * @param codeGenTypeEnum 代码生成类型
     * @return 保存后的文件
     */
    public Flux<String> generateAndSaveCodeStream(String userMessage, CodeGenTypeEnum codeGenTypeEnum){
        if(codeGenTypeEnum == null){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        return switch (codeGenTypeEnum) {
            case HTML -> generateAndSaveHTMLCodeStream(userMessage);
            case MULTI_FILE -> generateAndSaveMultiCodeStream(userMessage);
            default -> throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        };
    }

    /**
     * 生成HTML代码并保存
     *
     * @param userMessage 用户输入
     * @return 保存后的文件
     */
    private Flux<String> generateAndSaveHTMLCodeStream(String userMessage) {
        Flux<String> result= aiCodeGeneratorService.generateHtmlCodeStream(userMessage);
        //当流式生成完成代码后，再保存代码
        StringBuilder codeBuilder = new StringBuilder();
        return result.doOnNext(chunk->{
            //实时收集代码片段
            codeBuilder.append(chunk);
        }).doOnComplete(()->{
            //流式返回完成后保存代码
            try{
                String completeHtmlCode=codeBuilder.toString();
                HtmlCodeResult htmlCodeResult= CodeParser.parseHtmlCode(completeHtmlCode);
                File savedFile=CodeFileSaver.saveHtmlCodeResult(htmlCodeResult);
                log.info("保存成功，路径为：{}", savedFile.getAbsolutePath());
            }catch(Exception e){
                log.error("保存失败: {}", e.getMessage());
            }
        });
    }

    /**
     * 生成多文件代码并保存
     *
     * @param userMessage 用户输入
     * @return 保存后的文件
     */
    private Flux<String> generateAndSaveMultiCodeStream(String userMessage) {
        Flux<String> result= aiCodeGeneratorService.generateMultiFileCodeStream(userMessage);
        //当流式生成完成代码后，再保存代码
        StringBuilder codeBuilder = new StringBuilder();
        return result.doOnNext(chunk->{
            //实时收集代码片段
            codeBuilder.append(chunk);
        }).doOnComplete(()->{
            //流式返回完成后保存代码
            try{
                String completeMultiFileCode=codeBuilder.toString();
                MultiFileCodeResult multiFileCodeResult= CodeParser.parseMultiFileCode(completeMultiFileCode);
                File savedFile=CodeFileSaver.saveMultiFileCodeResult(multiFileCodeResult);
                log.info("保存成功，路径为：{}", savedFile.getAbsolutePath());
            }catch(Exception e){
                log.error("保存失败: {}", e.getMessage());
            }
        });
    }
}
