package com.example.ui_do_an;

public class Note {
    private int note_id;
    private String note_title;
    private String note_day;

    public Note(int note_id, String note_title, String note_day, String note_content) {
        this.note_id = note_id;
        this.note_title = note_title;
        this.note_day = note_day;
        this.note_content = note_content;
    }

    private String note_content;
    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public String getNote_title() {
        return note_title;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }

    public String getNote_day() {
        return note_day;
    }

    public void setNote_day(String note_day) {
        this.note_day = note_day;
    }

    public String getNote_content() {
        return note_content;
    }

    public void setNote_content(String note_content) {
        this.note_content = note_content;
    }
}
