package com.jpaboard.validator;


import com.jpaboard.model.Board;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

@Component
public class BoardValidator implements Validator {

    // 어떤 타입의 객체를 검증할 때 이 객체의 클래스가 이 Validator 가 검증할 수 있는 클래스인 지를 판단하는 매서드
    @Override
    public boolean supports(Class<?> clazz) {
        return Board.class.equals(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Board board = (Board) object;

        if(StringUtils.isEmpty(board.getTitle())) {
            errors.rejectValue("title","key","제목을 입력하세요");
        }
        
        if(StringUtils.isEmpty(board.getContent())) {
            errors.rejectValue("content","key","내용을 입력하세요");
        }
    }
}
