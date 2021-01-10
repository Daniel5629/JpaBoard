package com.restboot.service;

import com.restboot.model.Board;
import com.restboot.model.User;
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
    private final UserService userService;

    @Transactional(readOnly = true)
    public Page<Board> getBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);

    }

    @Transactional(readOnly = true)
    public Page<Board> getBoarsWithSearchText(String title, String content, Pageable pageable) {
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
        User user = userService.getUser(username);
        Board board = getBoard(id);

        Long userId = user.getId();
        Long boardUserId = board.getUser().getId();

        if (userId != boardUserId) {
            throw new RuntimeException("권한이 없는 게시물입니다.");
        }
    }

    @Transactional
    public Long postBoard(String username, Board board) {
        User user = userService.getUser(username);
        board.setUser(user);
        return boardRepository.save(board).getId();
    }

    @Transactional
    public Long updateBoard(String username, Board board) {
        checkBoardAuth(username, board.getId());

        return boardRepository.save(board).getId();
    }

    @Transactional
    public void deleteBoard(String username, Long id) {
        checkBoardAuth(username, id);

        boardRepository.deleteById(id);
    }
}
