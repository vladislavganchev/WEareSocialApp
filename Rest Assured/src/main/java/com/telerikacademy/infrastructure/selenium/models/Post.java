package src.main.java.com.telerikacademy.infrastructure.selenium.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    public String picture;
    public String content;
    public ArrayList<Object> likes;
    public ArrayList<Object> comments;
    public int rank;
    public boolean mypublic;
}
