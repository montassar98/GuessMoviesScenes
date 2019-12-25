package com.montassar.guessmoviesscenes.Models;

public class Movie {

    private String id;
    private String rightAnswer;
    private String wrong1;
    private String wrong2;
    private String wrong3;
    private String wrong4;
    private String wrong5;
    private String videoName;
    private String videoURL;

    public Movie() {
    }

    public Movie(String rightAnswer, String wrong1, String wrong2, String wrong3, String wrong4, String wrong5, String videoName, String videoURL) {
        this.rightAnswer = rightAnswer;
        this.wrong1 = wrong1;
        this.wrong2 = wrong2;
        this.wrong3 = wrong3;
        this.wrong4 = wrong4;
        this.wrong5 = wrong5;
        this.videoName = videoName;
        this.videoURL = videoURL;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String getWrong1() {
        return wrong1;
    }

    public void setWrong1(String wrong1) {
        this.wrong1 = wrong1;
    }

    public String getWrong2() {
        return wrong2;
    }

    public void setWrong2(String wrong2) {
        this.wrong2 = wrong2;
    }

    public String getWrong3() {
        return wrong3;
    }

    public void setWrong3(String wrong3) {
        this.wrong3 = wrong3;
    }

    public String getWrong4() {
        return wrong4;
    }

    public void setWrong4(String wrong4) {
        this.wrong4 = wrong4;
    }

    public String getWrong5() {
        return wrong5;
    }

    public void setWrong5(String wrong5) {
        this.wrong5 = wrong5;
    }
}
