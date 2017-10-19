package com.ndlp.socialstudy.Tasks;



public class TaskObject {
    private Integer taskID;
    private String taskName;
    private String taskFormat;
    private String taskCategory;
    private String taskDate;
    private String taskUser;

    //  Constructor
    public TaskObject(Integer taskID, String taskName, String taskFormat, String taskCategory, String taskDate, String taskUser) {

        this.taskID = taskID;
        this.taskName = taskName;
        this.taskFormat = taskFormat;
        this.taskCategory = taskCategory;
        this.taskDate = taskDate;
        this.taskUser = taskUser;
    }

    public Integer getTaskID() {
        return taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskFormat() {
        return taskFormat;
    }

    public String getTaskCategory() {
        return taskCategory;
    }

    public String getTaskDate() {
        return  taskDate;
    }

    public String getTaskUser() {
        return taskUser;
    }
}
