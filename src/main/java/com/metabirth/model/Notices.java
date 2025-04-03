package com.metabirth.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.sql.Timestamp;

public class Notices {
    private int notice_id;
    private int admin_id;
    private String title;
    private String content;
    private byte status;
    private Timestamp created_at;
    private Timestamp updated_at;
    private Timestamp deleted_at;

    public Notices(int notice_id, int admin_id, String title, String content, byte status,
                   Timestamp created_at, Timestamp updated_at, Timestamp deleted_at) {
        this.notice_id = notice_id;
        this.admin_id = admin_id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
    }

    public int getNotice_id() {
        return notice_id;
    }

    public void setNotice_id(int notice_id) {
        this.notice_id = notice_id;
    }

    public int getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(int admin_id) {
        this.admin_id = admin_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Timestamp getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Timestamp deleted_at) {
        this.deleted_at = deleted_at;
    }

    @Override
    public String toString() {
        return "Notices{" +
                "notice_id=" + notice_id +
                ", admin_id=" + admin_id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", deleted_at=" + deleted_at +
                '}';
    }
}