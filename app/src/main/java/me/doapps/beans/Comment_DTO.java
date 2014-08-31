package me.doapps.beans;

/**
 * Created by HP on 31/08/2014.
 */
public class Comment_DTO {
    private String comment;
    private int like;

    public Comment_DTO(String comment, int like) {
        this.comment = comment;
        this.like = like;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
