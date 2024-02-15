package src.test.resources.comment;

import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.java.com.telerikacademy.infrastructure.selenium.api.CommentController;
import src.main.java.com.telerikacademy.infrastructure.selenium.api.PostController;
import src.main.java.com.telerikacademy.infrastructure.selenium.models.Comment;
import src.main.java.com.telerikacademy.infrastructure.selenium.models.Post;
import src.main.java.com.telerikacademy.infrastructure.selenium.models.UserRegister;
import src.main.java.com.telerikacademy.infrastructure.selenium.utils.DataGenerator;
import src.main.java.com.telerikacademy.infrastructure.selenium.utils.ModelGenerator;
import src.test.resources.base.BaseTestSetup;
import io.restassured.http.Cookies;

import static com.telerikacademy.infrastructure.selenium.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreateCommentTest extends BaseTestSetup {

    String uniqueContent;
    Cookies cookies;

    @BeforeEach
    public void setup() {
        if (!isRegistered) {
            UserRegister userRegister = ModelGenerator.generateUserRegisterModel();
            register(userRegister);
            System.out.println(USER_SUCCESS_MESSAGE + userId);
            isRegistered = true;
        }
        cookies = authenticateAndFetchCookies();

        String uniqueContent = DataGenerator.generateUniqueContentPost();
        Post createPost = ModelGenerator.generatePostModel(uniqueContent);
        Response postResponse = PostController.createPost(cookies, createPost);
        createdPost = postResponse.as(Post.class);
        assertEquals(uniqueContent, createdPost.content, CONTENT_MISMATCH_MESSAGE);
        postId = createdPost.postId;
        System.out.println(POST_SUCCESS_MESSAGE + postId);
    }

    @Test
    public void createComment() {
        String uniqueContent = DataGenerator.generateUniqueContentPost();
        createComment = ModelGenerator.generateCommentModel(uniqueContent, postId, currentUserId);
        Response response = CommentController.createComment(cookies, createComment);
        isResponse200(response);

        createdComment = response.as(Comment.class);
        assertEquals(createdComment.content, uniqueContent, CONTENT_MISMATCH_MESSAGE);
        Assert.assertNotNull(createdComment.commentId);
        Assert.assertNotNull(createdComment.content, CONTENT_NULL_MESSAGE);
        Assert.assertNotNull(createdComment.likes);
        Assert.assertNotNull(createdComment.date, DATE_NULL_MESSAGE);
        Assert.assertNotNull(createdComment.liked);

        commentId = createdComment.commentId;
        System.out.println(COMMENT_SUCCESS_MESSAGE + commentId + ALL_PROPERTIES_NOT_NULL);
    }

    @AfterClass
    public void tearDown() {
       if (!isDeletedPost) {
           PostController.deletePost(cookies, postId);
           System.out.println(DELETE_POST_SUCCESS_MESSAGE + createdPost.postId);
           isDeletedPost = true;
       }
       if (!isCommentDeleted) {
           CommentController.deleteComment(cookies, createComment.commentId);
           System.out.println(DELETE_COMMENT_SUCCESS_MESSAGE + createdPost.postId);
           isCommentDeleted = true;
       }
    }
}
