package com.service.extra.mall.model.vo;

import java.util.List;

public class SafetyQuestionVo {

	private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * sqAnswer : xxx
         * sqPosition : 1
         */

        private String sqAnswer;
        private int sqPosition;

        public String getSqAnswer() {
            return sqAnswer;
        }

        public void setSqAnswer(String sqAnswer) {
            this.sqAnswer = sqAnswer;
        }

        public int getSqPosition() {
            return sqPosition;
        }

        public void setSqPosition(int sqPosition) {
            this.sqPosition = sqPosition;
        }
    }
}
