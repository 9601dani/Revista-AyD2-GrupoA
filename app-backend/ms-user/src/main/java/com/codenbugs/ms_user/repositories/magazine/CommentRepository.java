package com.codenbugs.ms_user.repositories.magazine;

import com.codenbugs.ms_user.models.magazine.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
