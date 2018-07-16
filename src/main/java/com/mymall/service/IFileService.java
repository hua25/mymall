package com.mymall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by HUA on 2018/6/25.
 */
public interface IFileService {

    String upload(MultipartFile file, String path);
}
