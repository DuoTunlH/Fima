package com.example.fima.models;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.Objects;

public class Target{
    private String planCode;
    private String  planName;
    // tổng chi phí cần chuẩn bị
    private double totalBudget;
    private double savedBudget;
    private Date deadline;
    // mức dộ ưu tiên
    private int priorityLevel;

    // loại mục tiêu vừa, nhỏ, to
    private String targetType;
    private String imgSrc;

    public Target() {
    }

    public Target(String planCode, String planName, double totalBudget, double savedBudget, Date deadline, int priorityLevel, String targetType, String imgSrc) {
        this.planCode = planCode;
        this.planName = planName;
        this.totalBudget = totalBudget;
        this.savedBudget = savedBudget;
        this.deadline = deadline;
        this.priorityLevel = priorityLevel;
        this.targetType = targetType;
        this.imgSrc = imgSrc;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(double totalBudget) {
        this.totalBudget = totalBudget;
    }

    public double getSavedBudget() {
        return savedBudget;
    }

    public void setSavedBudget(double savedBudget) {
        this.savedBudget = savedBudget;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(int priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }
}
