package com.jpaboard.service;

import com.jpaboard.model.Board;
import com.jpaboard.model.Account;
import com.jpaboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final AccountService userService;

    @Transactional(readOnly = true)
    public Page<Board> getBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);

    }

    @Transactional(readOnly = true)
    public Page<Board> getBoardsWithSearchText(String title, String content, Pageable pageable) {
        return boardRepository.findByTitleContainingOrContentContaining(title, content, pageable);
    }

    @Transactional(readOnly = true)
    public Board getBoard(Long id) {
        return boardRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));
    }

    @Transactional(readOnly = true)
    private void checkBoardAuth(String username, Long id) {
        Account user = userService.getUser(username);
        Long userId = user.getId();

        if (userId != id) {
            throw new RuntimeException("권한이 없는 게시물입니다.");
        }
    }

    @Transactional
    public Long postBoard(String username, Board board) {
        Account user = userService.getUser(username);
        board.setUser(user);
        return boardRepository.save(board).getId();
    }

    @Transactional
    public Long updateBoard(String username, Board board) { // TODO Board -> dto
        Board boardFromDB = boardRepository.findById(board.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        boardFromDB.update(board.getTitle(), board.getContent());
        checkBoardAuth(username, boardFromDB.getUser().getId());

        return boardRepository.save(boardFromDB).getId();
    }

    @Transactional
    public void deleteBoard(String username, Long id) {
        Board boardFromDB = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시물입니다."));

        checkBoardAuth(username, boardFromDB.getUser().getId());

        boardRepository.deleteById(boardFromDB.getId());
    }
}
