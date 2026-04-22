package com.example.socapplication.model.dto.postDto;

import com.example.socapplication.model.dto.postMediaDto.AddPostMedia;

import java.util.List;

public record AddPost(
        Long userId,
        String title,
        String content,
        List<AddPostMedia> media
) {
}
