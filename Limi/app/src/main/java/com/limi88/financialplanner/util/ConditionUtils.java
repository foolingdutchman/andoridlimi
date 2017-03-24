package com.limi88.financialplanner.util;

/**
 * Created by hehao on 16/9/14.
 */
public class ConditionUtils {
    public static String transForm(String string) {
        if (string.equals("status")) {
            return "产品状态";
        } else if (string.equals("trust_rates.expect_rate")) {
            return "收益率";
        } else if (string.equals("period")) {
            return "产品期限";
        } else if (string.equals("distribute_type")) {
            return "付息方式";
        } else if (string.equals("distribute_big_small")) {
            return "大小额配比";
        } else if (string.equals("level")) {
            return "评级";
        } else if (string.equals("mortgage_rate")) {
            return "抵/质押率";
        } else if (string.equals("invest_area")) {
            return "投资领域";
        } else if (string.equals("invest_city")) {
            return "所在省份";
        } else if (string.equals("trust_rates.rebate_percent")) {
            return "佣金";
        } else if (string.equals("issuer_type")) {
            return "发行人类型";
        } else if (string.equals("bond_fund_type")) {
            return "基金类型";
        } else if (string.equals("is_struct")) {
            return "是否结构化";
        } else if (string.equals("forbidden_period")) {
            return "封闭期";
        } else if (string.equals("channel")) {
            return "发型通道";
        } else if (string.equals("min_start_money")) {
            return "投资门槛";
        } else if (string.equals("fund_rates.frontend_rebate_percent")) {
            return "前端佣金";
        } else if (string.equals("fund_type")) {
            return "基金类型";
        } else if (string.equals("period_limit")) {
            return "产品期限";
        } else if (string.equals("cat")) {
            return "产品类型";
        } else if (string.equals("fin_product_org_id")) {
            return "保险公司";
        } else if (string.equals("fund_rates.backend_rebate_percent")) {
            return "后端佣金";
        }
        return null;
    }

    public static String getSortPath(String string) {
        if (string.equals("默认排序")) {
            return "";
        } else if (string.equals("佣金最高")) {
            return "rebate_percent";
        } else if (string.equals("收益最高")) {
            return "expect_rate";
        } else if (string.equals("评级最优")) {
            return "level";
        } else if (string.equals("抵/质押率最优")) {
            return "mortgage_rate";
        } else if (string.equals("返佣比例从高到低排列")) {
            return "trust_rates.rebate_percent";
        } else if (string.equals("返佣比例从高到低排列")) {
            return "trust_rates.rebate_percent";
        }

        return null;
    }

    public static String getSortPathForInsurance(String string) {
        if (string.equals("默认排序")) {
            return "";
        } else if (string.equals("返佣比例从高到低排列")) {
            return "rebate_desc";
        } else if (string.equals("返佣比例从低到高排列")) {
            return "rebate_asc";
        }

        return null;
    }

}
