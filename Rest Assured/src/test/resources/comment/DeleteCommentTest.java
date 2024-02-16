package src.test.resources.comment;

import io.restassured.http.Cookies;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import src.main.java.com.telerikacademy.infrastructure.selenium.api.CommentController;
import src.main.java.com.telerikacademy.infrastructure.selenium.api.PostController;
import src.main.java.com.telerikacademy.infrastructure.selenium.models.*;
import src.main.java.com.telerikacademy.infrastructure.selenium.utils.*;
import src.test.resources.base.BaseTestSetup;

import static com.telerikacademy.infrastructure.selenium.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteCommentTest extends BaseTestSetup {

    String uniqueContent;

    @BeforeClass
    public void setup() {
        if (!isRegistered) {
            UserRegister userRegister = ModelGenerator.generateUserRegisterModel();
            register(userRegister);
            System.out.println(USER_SUCCESS_MESSAGE + userId);
            isRegistered = true;
        }
        cookies = authenticateAndFetchCookies();
        if (isDeletedPost) {
            uniqueContent = DataGenerator.generateUniqueContentPost();
            Post createPost = ModelGenerator.generatePostModel(uniqueContent);
            Response response = PostController.createPost(cookies, createPost);
            createdPost = response.as(Post.class);
            assertEquals(createdPost.content, uniqueContent, CONTENT_MISMATCH_MESSAGE);
            postId = createdPost.postId;
            System.out.println(POST_SUCCESS_MESSAGE + " " + postId);
            isDeletedPost = false;
        }
        if (isCommentDeleted) {
            Comment createComment = ModelGenerator.generateCommentModel(DataGenerator.generateUniqueContentComment(), postId, userId);
            Response response = CommentController.createComment(cookies, createComment);
            isResponse200(response);
            createdComment = response.as(Comment.class);
            System.out.println(COMMENT_SUCCESS_MESSAGE + " " + createdComment.commentId);
            isCommentDeleted = false;
        }
    }

    @Test
    public void deleteComment() {
        Response response = CommentController.deleteComment(cookies, createdComment.commentId);
        isResponse200(response);
        System.out.println(String.format(DELETE_COMMENT_SUCCESS_MESSAGE,createdComment.commentId));
        isCommentDeleted = true;
    }

    private void isResponse200(Response response) {
        assertEquals(response.getStatusCode(), 200, "Expected status code 200, but found " + response.getStatusCode());
    }
}
