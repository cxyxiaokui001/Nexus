package com.xiaokui.nexus.core.saver;

import com.xiaokui.nexus.ai.model.HtmlCodeResult;
import com.xiaokui.nexus.exception.BusinessException;
import com.xiaokui.nexus.exception.ErrorCode;
import com.xiaokui.nexus.model.enums.CodeGenTypeEnum;
import cn.hutool.core.util.StrUtil;

/**
 * HTML代码文件保存器
 *
 * @author xiaokui
 */
public class HtmlCodeFileSaverTemplate extends CodeFileSaverTemplate<HtmlCodeResult> {

    @Override
    protected CodeGenTypeEnum getCodeType() {
        return CodeGenTypeEnum.HTML;
    }

    @Override
    protected void saveFiles(HtmlCodeResult result, String baseDirPath) {
        // 保存 HTML 文件
        writeToFile(baseDirPath, "index.html", result.getHtmlCode());
    }

    @Override
    protected void validateInput(HtmlCodeResult result) {
        super.validateInput(result);
        // HTML 代码不能为空
        if (StrUtil.isBlank(result.getHtmlCode())) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "HTML代码内容不能为空");
        }
    }
}
