package com.ruitukeji.zwbh.entity.main;

import com.bigkoo.pickerview.model.IPickerViewData;
import com.ruitukeji.zwbh.entity.BaseResult;

import java.util.List;

/**
 * Created by Administrator on 2017/12/26.
 */

public class TimeChooseBean extends BaseResult<TimeChooseBean.ResultBean> {

    public static class ResultBean {
        private List<DateChooseBean> date_choose;
        private List<HoursChooseBean> hours_choose;
        private List<MinutesChooseBean> minutes_choose;

        public List<DateChooseBean> getDate_choose() {
            return date_choose;
        }

        public void setDate_choose(List<DateChooseBean> date_choose) {
            this.date_choose = date_choose;
        }

        public List<HoursChooseBean> getHours_choose() {
            return hours_choose;
        }

        public void setHours_choose(List<HoursChooseBean> hours_choose) {
            this.hours_choose = hours_choose;
        }

        public List<MinutesChooseBean> getSleep_choose() {
            return minutes_choose;
        }

        public void setSleep_choose(List<MinutesChooseBean> minutes_choose) {
            this.minutes_choose = minutes_choose;
        }

        public static class DateChooseBean implements IPickerViewData {
            /**
             * id : 1
             * dateStr : 少量景点 深1
             * dateLong : 1
             */

            private int id;
            private String dateStr;
            private String dateLong;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getDateStr() {
                return dateStr;
            }

            public void setDateStr(String dateStr) {
                this.dateStr = dateStr;
            }

            public String getDateLong() {
                return dateLong;
            }

            public void setDateLong(String dateLong) {
                this.dateLong = dateLong;
            }

            @Override
            public String getPickerViewText() {
                return dateStr;
            }
        }

        public static class HoursChooseBean implements IPickerViewData {
            /**
             * id : 1
             * name : 0-100
             */

            private int id;
            private String hoursStr;
            private int hoursLong;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getHoursStr() {
                return hoursStr;
            }

            public void setHoursStr(String hoursStr) {
                this.hoursStr = hoursStr;
            }

            public int getHoursLong() {
                return hoursLong;
            }

            public void setHoursLong(int hoursLong) {
                this.hoursLong = hoursLong;
            }

            @Override
            public String getPickerViewText() {
                return hoursStr;
            }
        }

        public static class MinutesChooseBean implements IPickerViewData {
            /**
             * id : 1
             * name : 0-100
             */

            private int id;
            private String minutesStr;
            private int minutesLong;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMinutesStr() {
                return minutesStr;
            }

            public void setMinutesStr(String minutesStr) {
                this.minutesStr = minutesStr;
            }

            public int getMinutesLong() {
                return minutesLong;
            }

            public void setMinutesLong(int minutesLong) {
                this.minutesLong = minutesLong;
            }

            @Override
            public String getPickerViewText() {
                return minutesStr;
            }
        }
    }
}