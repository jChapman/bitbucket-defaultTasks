package com.superfluoussextant.defaulttasks.impl;

import com.atlassian.bitbucket.comment.AddCommentReplyRequest;
import com.atlassian.bitbucket.comment.AddCommentRequest;
import com.atlassian.bitbucket.comment.Comment;
import com.atlassian.bitbucket.comment.CommentService;
import com.atlassian.bitbucket.event.pull.PullRequestOpenedEvent;
import com.atlassian.bitbucket.pull.PullRequest;
import com.atlassian.bitbucket.pull.PullRequestService;
import com.atlassian.bitbucket.repository.Repository;
import com.atlassian.bitbucket.task.TaskAnchorType;
import com.atlassian.bitbucket.task.TaskCreateRequest;
import com.atlassian.bitbucket.task.TaskService;
import com.atlassian.event.api.EventListener;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;


@Named("pullRequestCreatedListener")
public class PullRequestCreatedListener {
    private static final Logger log = LoggerFactory.getLogger(PullRequestCreatedListener.class);

    private final CommentService commentService;
    private final TaskService taskService;

    @Inject
    public PullRequestCreatedListener(@ComponentImport CommentService commentService, @ComponentImport TaskService taskService) {
        this.commentService = commentService;
        this.taskService = taskService;
    }


    @EventListener
    public void onPullReqCreated(PullRequestOpenedEvent event) {
        log.error("Found created pull request: " + event.getPullRequest().getTitle());
        PullRequest req = event.getPullRequest();
        AddCommentRequest commentReq = new AddCommentRequest.Builder(req, "This is the comment text").build();
        Comment comment = commentService.addComment(commentReq);
        TaskCreateRequest taskReq = new TaskCreateRequest.Builder().anchorType(TaskAnchorType.COMMENT).anchorId(comment.getId()).text("This is the task text").build();
        taskService.create(taskReq);
    }
}
