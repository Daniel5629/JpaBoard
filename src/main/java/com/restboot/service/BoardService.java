package com.restboot.service;

import com.restboot.model.Board;
import com.restboot.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public Page<Board> getBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);

    }

    @Transactional(readOnly = true)
    public Page<Board> getBoarsWithSearchText(String title, String content, Pageable pageable) {
        return  boardRepository.findByTitleContainingOrContentContaining(title, content, pageable);
    }


    @Transactional(readOnly = true)
    public Board getBoard(Long id) {
        return boardRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));
    }

    @Transactional
    public Long postBoard(Board board) {
        return boardRepository.save(board).getId();
    }

    @Transactional
    public Long updateBoard(Board board) {
        boardRepository
                .findById(board.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        return boardRepository.save(board).getId();
    }

    @Transactional
    public void deleteBoard(Long id) {
        boardRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        boardRepository.deleteById(id);
    }
}
