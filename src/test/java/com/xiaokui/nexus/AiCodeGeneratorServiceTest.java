package com.xiaokui.nexus;

import com.xiaokui.nexus.ai.AICodeGeneratorService;
import com.xiaokui.nexus.ai.model.HtmlCodeResult;
import com.xiaokui.nexus.ai.model.MultiFileCodeResult;
import com.xiaokui.nexus.core.AICodeGeneratorFacade;
import com.xiaokui.nexus.model.enums.CodeGenTypeEnum;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.util.List;

@SpringBootTest
class AiCodeGeneratorServiceTest {

    @Resource
    private AICodeGeneratorService aiCodeGeneratorService;


    @Resource
    private AICodeGeneratorFacade aiCodeGeneratorFacade;

    @Test
    void generateHtmlCode() {
        HtmlCodeResult result = aiCodeGeneratorService.generateHtmlCode("做个程序媛小葵的工作记录小工具");
        Assertions.assertNotNull(result);
    }

    @Test
    void generateMultiFileCode() {
        MultiFileCodeResult multiFileCode = aiCodeGeneratorService.generateMultiFileCode("做个程序媛小葵的留言板");
        Assertions.assertNotNull(multiFileCode);
    }

    @Test
    void generateAndSaveCodeStream() {
        Flux<String> codeStream = aiCodeGeneratorFacade.generateAndSaveCodeStream("个人博客网站", CodeGenTypeEnum.MULTI_FILE);
        // 阻塞等待所有数据收集完成
        List<String> result = codeStream.collectList().block();
        // 验证结果
        Assertions.assertNotNull(result);
        String completeContent = String.join("", result);
        Assertions.assertNotNull(completeContent);
    }

}
