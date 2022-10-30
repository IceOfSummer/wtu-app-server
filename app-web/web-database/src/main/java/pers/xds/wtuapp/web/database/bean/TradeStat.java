package pers.xds.wtuapp.web.database.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import net.minidev.json.annotate.JsonIgnore;
import java.io.Serializable;

/**
 * 交易统计
 * @author DeSen Xu
 */
@TableName(value ="trade_stat")
public class TradeStat implements Serializable {
    /**
     * 用户id
     */
    @TableId
    @JsonIgnore
    private Integer userId;

    /**
     * 总收入
     */
    private Double income;

    /**
     * 总支出
     */
    private Double expenditure;

    /**
     * 总订单数
     */
    private Integer sumOrder;

    /**
     * 交易成功的订单数
     */
    private Integer successOrder;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 用户id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 总收入
     */
    public Double getIncome() {
        return income;
    }

    /**
     * 总收入
     */
    public void setIncome(Double income) {
        this.income = income;
    }

    /**
     * 总支出
     */
    public Double getExpenditure() {
        return expenditure;
    }

    /**
     * 总支出
     */
    public void setExpenditure(Double expenditure) {
        this.expenditure = expenditure;
    }

    /**
     * 总订单数
     */
    public Integer getSumOrder() {
        return sumOrder;
    }

    /**
     * 总订单数
     */
    public void setSumOrder(Integer sumOrder) {
        this.sumOrder = sumOrder;
    }

    /**
     * 交易成功的订单数
     */
    public Integer getSuccessOrder() {
        return successOrder;
    }

    /**
     * 交易成功的订单数
     */
    public void setSuccessOrder(Integer successOrder) {
        this.successOrder = successOrder;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                " [" +
                ", userId=" + userId +
                ", income=" + income +
                ", expenditure=" + expenditure +
                ", sumOrder=" + sumOrder +
                ", successOrder=" + successOrder +
                ", serialVersionUID=" + serialVersionUID +
                "]";
    }
}