package com.xzc;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test {
    static int hashLength = 1000;

    public static void main(String[] args) {
        File file = new File("C:\\Users\\Administrator\\Desktop\\题库中心—平台字典数据更新修订记录.xlsx");
        if (file.exists()) {
            System.out.println("文件存在");
        }
        EasyExcel.read(file, KnEntity.class, new AnalysisEventListener<KnEntity>() {
            @Override
            public void invoke(KnEntity o, AnalysisContext analysisContext) {
//                System.out.println("UPDATE `media_knowledge_relation` SET `knowledge_code` = '" + o.getNewCode() + "' WHERE `knowledge_code` = '" + o.getOldCode() + "';");
                System.out.println("UPDATE `paper_info` SET `survey_point` = REPLACE(`survey_point`,'" + o.getNewCode() + ",','" + o.getOldCode() + ",') WHERE `survey_point` like '%" + o.getOldCode() + ",%';");
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                System.out.println("全部解析完成");
            }
        }).sheet(0).doRead();

    }

    public static int hash(char[] word) {
        int index = 0;
        int i = 0;
        while (i < word.length) {
            index += index * 31 + word[i];
            i++;
        }
        return index % hashLength;
    }
}
