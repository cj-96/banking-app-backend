package com.example.auth.service.impl;

import com.example.auth.exceptionhandling.InternalServerException;
import com.example.auth.exceptionhandling.NoticeException;
import com.example.auth.model.Notice;
import com.example.auth.repository.NoticeRepository;
import com.example.auth.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Override
    public List<Notice> retrieveAll() {
        log.debug("Attempting to retrieve all notices");

        try {
            List<Notice> notices = (List<Notice>) noticeRepository.findAll();

            if (notices == null || notices.isEmpty()) {
                log.warn("No notices found in the system");
                throw new NoticeException("No notices found");
            }

            log.debug("Successfully retrieved {} notices", notices.size());
            return notices;

        } catch (NoticeException e) {
            // Re-throw business exceptions
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while retrieving notices", e);
            throw new InternalServerException("Unexpected error occurred while retrieving notices");
        }
    }
}
