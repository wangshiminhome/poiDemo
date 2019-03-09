package com.wsm.poidemo.poi.service;

import com.wsm.poidemo.poi.vo.PersonVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface PersonService {

    String resolveExcel(MultipartFile file) throws Exception;
}
