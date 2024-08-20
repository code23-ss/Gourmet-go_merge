package com.example.mainscreen;

public class ReviewSave {
    private String title; // 리뷰 제목
    private float rating; // 평점
    private String date; // 날짜
    private String purpose; // 목적
    private String otherPurpose; // 다른 목적
    private String review; // 리뷰 내용
    private String imageUrl; // 이미지 URL
    private boolean certified; // 인증 여부

    // Firebase가 데이터를 읽을 수 있도록 하는 기본 생성자
    public ReviewSave() {}

    // 각 필드에 대한 getter 및 setter 메소드
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public float getRating() { return rating; }
    public void setRating(float rating) { this.rating = rating; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getOtherPurpose() { return otherPurpose; }
    public void setOtherPurpose(String otherPurpose) { this.otherPurpose = otherPurpose; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public boolean isCertified() { return certified; }
    public void setCertified(boolean certified) { this.certified = certified; }
}
