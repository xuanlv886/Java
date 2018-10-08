package com.service.extra.mall.model.vo;

import java.util.List;

public class PicVo {

	/**
     * pTag : 菜鸟教程
     * pics : [{"pName":"菜鸟教程","pNo":0},{"pName":"菜鸟教程","pNo":0},{"pName":"菜鸟教程","pNo":0}]
     */

    private String pTag;
    private List<PicsBean> pics;

    public String getPTag() {
        return pTag;
    }

    public void setPTag(String pTag) {
        this.pTag = pTag;
    }

    public List<PicsBean> getPics() {
        return pics;
    }

    public void setPics(List<PicsBean> pics) {
        this.pics = pics;
    }

    public static class PicsBean {
        /**
         * pName : 菜鸟教程
         * pNo : 0
         */

        private String pName;
        private int pNo;

        public String getPName() {
            return pName;
        }

        public void setPName(String pName) {
            this.pName = pName;
        }

        public int getPNo() {
            return pNo;
        }

        public void setPNo(int pNo) {
            this.pNo = pNo;
        }
    }
}
