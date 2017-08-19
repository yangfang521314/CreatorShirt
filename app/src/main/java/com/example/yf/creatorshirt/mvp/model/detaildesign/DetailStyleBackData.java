package com.example.yf.creatorshirt.mvp.model.detaildesign;

import java.util.List;

/**
 * Created by yangfang on 2017/8/19.
 * 背面数据bean
 *
 */

public class DetailStyleBackData {

    private NeckData neck;
    private ArmData arm;
    private OrnamentData ornament;
    private ColorData color;
    private PatternData pattern;

    private class NeckData {
        private String name;
        private List<DetailStyle> fileList;

    }

    private class ArmData {
        private String name;
        private List<DetailStyle> fileList;
    }

    private class OrnamentData {
        private String name;
        private List<DetailStyle> fileList;
    }

    private class ColorData {
        private String name;
        private List<DetailStyle> fileList;
    }

    private class PatternData {
        private String name;
        private List<DetailStyle> fileList;
    }
}
